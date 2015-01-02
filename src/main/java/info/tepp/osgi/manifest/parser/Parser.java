package info.tepp.osgi.manifest.parser;

/**
 * Very simple <a href="http://en.wikipedia.org/wiki/Parser_combinator">parser combinator</a>
 * library for parsing OSGi bundle manifest header values.
 */
public abstract class Parser<T> {

    /**
     * Parses an input and returns a {@link info.tepp.osgi.manifest.parser.Result result}.
     */
    public abstract Result<T> parse(CharSequence input);

    /** Parses positive integer numbers tokens */
    public static final IntegerParser NUMBER = new IntegerParser();
    public static final class IntegerParser extends Parser<Integer> {
        @Override
        public Result<Integer> parse(CharSequence input) {
            // Heavily inspired from JDK Integer.parseInt method
            if (input == null) return Result.Failure.of("null").asResult();

            int length = input.length();
            if (length <= 0) return Result.Failure.of("empty").asResult();

            int radix = 10;

            int result = 0;
            int index = 0;

            int limit = -Integer.MAX_VALUE;
            int multmin = limit / radix;
            int digit;

            while (index < length) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = Character.digit(input.charAt(index++), radix);
                if (digit < 0)
                    if (index <= 1) return Result.Failure.of("NaN: " + input.subSequence(0, index)).asResult();
                    else { index -= 1; break; }

                if (result < multmin)
                    return Result.Failure.of("Number too large: " + input.subSequence(0, index)).asResult();

                result *= radix;
                if (result < limit + digit)
                    return Result.Failure.of("Number too large: " + input.subSequence(0, index)).asResult();

                result -= digit;
            }

            return Result.Success.of(-result, input.subSequence(index, length));
        }
    }

    /** Parses a single dot character (i.e. '.') */
    public static final CharacterParser DOT = new CharacterParser('.');
    public static final class CharacterParser extends Parser<Character> {
        private final char ch;
        public CharacterParser(char ch) { this.ch = ch; }

        @Override
        public Result<Character> parse(CharSequence input) {
            if (input == null) return Result.Failure.of("null").asResult();

            int length = input.length();
            if (length == 0) return Result.Failure.of("empty").asResult();

            char c0 = input.charAt(0);
            if (c0  == ch)
                return new Result.Success<Character>(c0, input.subSequence(1, length));

            return Result.Failure.of(String.format("Expexted '%s', but got '%s'", ch, c0)).asResult();
        }
    }

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

        @Override
        public Result<Tuple.Tuple2<L, R>> parse(CharSequence input) {
            Result<L> leftResult = left.parse(input);
            if (leftResult instanceof Result.Failure) {
                return ((Result.Failure) leftResult).asResult();
            }

            Result.Success<L> leftSuccess = (Result.Success<L>) leftResult;
            Result<R> rightResult = right.parse(leftSuccess.rest);
            if (rightResult instanceof Result.Failure) {
                return ((Result.Failure) rightResult).asResult();
            }

            Result.Success<R> rightSuccess = (Result.Success<R>) rightResult;
            return Result.Success.of(leftSuccess.value, rightSuccess.value,
                    rightSuccess.rest);
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

        @Override
        public Result<Maybe<T>> parse(CharSequence input) {
            Result<T> result = parser.parse(input);
            if (result instanceof Result.Success) {
                Result.Success<T> success = (Result.Success<T>) result;
                return Result.Success.of(Maybe.Some(success.value), success.rest);
            }
            return Result.Success.of(Maybe.<T>None(), input);
        }
    }

    /**
     * Transforms successful parser result
     */
    public <R> Parser<R> as(final Function<T, R> func) {
        final Parser<T> parser = this;
        return new Parser<R>() {
            @Override
            public Result<R> parse(CharSequence input) {
                Result<T> result = parser.parse(input);
                if (result instanceof Result.Failure) {
                    return ((Result.Failure) result).asResult();
                }

                Result.Success<T> success = (Result.Success<T>) result;
                return Result.Success.of(func.apply(success.value), success.rest);
            }
        };
    }

    /**
     * Parses a sequence and returns right side result of success value
     */
    public static <L, R> Parser<R> Right(TupleSequenceParser<L, R> parser) {
        return parser.as(Tuple.<L, R>Right());
    }

    /**
     * Greedily parses rest of the input.
     */
    public static final Parser<String> REST = new RestParser();
    private static class RestParser extends Parser<String> {
        @Override
        public Result<String> parse(CharSequence input) {
            return Result.Success.of(input.toString(), "");
        }
    }

    public static final Parser<Void> EOF = new Parser<Void>() {
        @Override
        @SuppressWarnings("unchecked")
        public Result<Void> parse(CharSequence input) {
            if (input.length() > 0)
                return Result.Failure.of("EOF Expected, but got '" + input + "");

            return Result.Success.of(null, "");
        }
    };
}
