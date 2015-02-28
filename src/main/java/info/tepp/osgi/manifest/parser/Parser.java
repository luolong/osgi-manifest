package info.tepp.osgi.manifest.parser;

import info.tepp.osgi.manifest.parser.Result.Failure;
import info.tepp.osgi.manifest.parser.Result.Success;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Very simple <a href="http://en.wikipedia.org/wiki/Parser_combinator">parser combinator</a>
 * library for parsing OSGi bundle manifest header values.
 */
public abstract class Parser<T> {

    /**
     * Parses an input and returns a {@link info.tepp.osgi.manifest.parser.Result result}.
     */
    @Nonnull
    public abstract Result<T> parse(CharSequence input);

    /**
     * Greedily parses rest of the input.
     */
    public static final Parser<String> REST = new RestParser();
    private static class RestParser extends Parser<String> {
        @Nonnull
        @Override
        public Result<String> parse(CharSequence input) {
            return Success.of(input.toString(), "");
        }
    }

    public static final Parser<Void> EOF = new Parser<Void>() {
        @Nonnull
        @Override
        @SuppressWarnings("unchecked")
        public Result<Void> parse(CharSequence input) {
            if (input.length() > 0)
                return Failure.of("EOF Expected, but got '" + input + "'!");

            return Success.of(null, "");
        }
    };

    /**
     * Returns a parser that combines <i>this</i> and then the <i>other</i> parser,
     * returning a tuple of results if both parsers return success.
     */
    public <O> TupleSequenceParser<T, O> then(Parser<O> other) {
        return new TupleSequenceParser<T, O>(this, other);
    }
    public static final class TupleSequenceParser<L, R> extends Parser<Tuple.Tuple2<L, R>> {
        private final Parser<L> left;
        private final Parser<R> right;

        public TupleSequenceParser(Parser<L> left, Parser<R> right) {
            this.left = left;
            this.right = right;
        }

        @Nonnull
        @Override
        public Result<Tuple.Tuple2<L, R>> parse(CharSequence input) {
            Result<L> leftResult = left.parse(input);
            if (leftResult instanceof Failure) {
                return ((Failure) leftResult).asResult();
            }

            Success<L> leftSuccess = (Success<L>) leftResult;
            Result<R> rightResult = right.parse(leftSuccess.rest);
            if (rightResult instanceof Failure) {
                return ((Failure) rightResult).asResult();
            }

            Success<R> rightSuccess = (Success<R>) rightResult;
            return Success.of(leftSuccess.value, rightSuccess.value,
                    rightSuccess.rest);
        }

        public <T> Parser<T> as(final BiFunction<L, R, T> func) {
            final TupleSequenceParser<L, R> parser = this;
            return new Parser<T>() {
                @Nonnull
                @Override
                public Result<T> parse(CharSequence input) {
                    Result<Tuple.Tuple2<L, R>> result = parser.parse(input);
                    if (result instanceof Failure) {
                        return ((Failure) result).asResult();
                    }

                    Success<Tuple.Tuple2<L, R>> success = (Success<Tuple.Tuple2<L, R>>) result;
                    try {
                        Tuple.Tuple2<L, R> tuple = success.value;
                        return Success.of(func.apply(tuple.left, tuple.right), success.rest);
                    }
                    catch (Throwable t) {
                        return Failure.of("Conversion failure: " + t.getMessage(), t).asResult();
                    }
                }
            };
        }
    }

    /**
     * Optionally parses input sequence
     */
    public static <T> OptionalParser<T> Maybe(Parser<T> parser) {
        return new OptionalParser<T>(parser);
    }
    public static class OptionalParser<T> extends Parser<Maybe<T>> {
        private final Parser<T> parser;

        public OptionalParser(Parser<T> parser) {
            this.parser = parser;
        }

        @Nonnull
        @Override
        public Result<Maybe<T>> parse(CharSequence input) {
            Result<T> result = parser.parse(input);
            if (result instanceof Success) {
                Success<T> success = (Success<T>) result;
                return Success.of(Maybe.Some(success.value), success.rest);
            }
            return Success.of(Maybe.<T>None(), input);
        }

        public <R> OptionalParser<R> as(final Function<T, R> func) {
            final OptionalParser<T> optional = this;
            return new OptionalParser<R>(null) {
                @Nonnull
                @Override
                public Result<Maybe<R>> parse(CharSequence input) {
                    Result<Maybe<T>> result = optional.parse(input);
                    Success<Maybe<T>> maybe = (Success<Maybe<T>>) result;
                    return Success.of(maybe.value.map(func), maybe.rest);
                }
            };
        }

        @SuppressWarnings("unused")
        public Parser<T> orElse(final T value) {
            final OptionalParser<T> parser = this;
            return new Parser<T>() {
                @Nonnull
                @Override
                public Result<T> parse(CharSequence input) {
                    Result<Maybe<T>> result = parser.parse(input);
                    Success<Maybe<T>> maybe = (Success<Maybe<T>>) result;
                    return Success.of(maybe.value.orElse(value), maybe.rest);
                }
            };
        }
    }

    /**
     * Transforms successful parser result
     */
    public <R> Parser<R> as(final Function<T, R> func) {
        final Parser<T> parser = this;
        return new Parser<R>() {
            @Nonnull
            @Override
            public Result<R> parse(CharSequence input) {
                Result<T> result = parser.parse(input);
                if (result instanceof Failure) {
                    return ((Failure) result).asResult();
                }

                Success<T> success = (Success<T>) result;
                try {
                    return Success.of(func.apply(success.value), success.rest);
                }
                catch (Throwable t) {
                    return failureOf(t).asResult();
                }
            }

            @SuppressWarnings("unchecked")
            private Failure failureOf(Throwable t) {
                Class<? extends Throwable> type = t.getClass();
                while (type != null) {
                    try {
                        Method method = func.getClass().getDeclaredMethod("failureOf", type);
                        if (Failure.class.isAssignableFrom(method.getReturnType())) {
                            Object result = method.invoke(func, t);
                            return Failure.class.cast(result);
                        }
                    }
                    catch (NoSuchMethodException ignore) {}
                    catch (InvocationTargetException ignore) {}
                    catch (IllegalAccessException ignore) {}

                    type = (Class<? extends Throwable>) type.getSuperclass();
                }

                return Failure.of(t.getMessage(), t);
            }
        };
    }

    /**
     * Parses a sequence and returns right side result of success value
     */
    public static <L, R> Parser<R> Right(TupleSequenceParser<L, R> parser) {
        return parser.as(Tuple.<L, R>Right());
    }

    @SuppressWarnings("unused")
    public Parser<T> thenEof() {
        return then(EOF).as(Tuple.<T, Void>Left());
    }

}
