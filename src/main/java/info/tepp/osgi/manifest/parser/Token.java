package info.tepp.osgi.manifest.parser;

import info.tepp.osgi.manifest.parser.Result.Failure;
import info.tepp.osgi.manifest.parser.Result.Success;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;

import static info.tepp.osgi.manifest.parser.Predicates.*;

public abstract class Token extends Parser<String> {

    public static final Char WS = new Char(Whitespace);

    public static final Char DIGIT = Char(Digit);

    public static final Char ALPHA = Char(Alpha);

    public static final Char ALPHANUM = Char(AlphaNum);

    public static final Token TOKEN = OneOrMore( Char( anyOf(AlphaNum, ch('_'), ch('-') ) ) );

    public static final Token NUMBER = OneOrMore( DIGIT );

    public static final Token JLETTER = Char(JavaLetter);

    public static final Token JLETTERORDIGIT = Char(JavaLetterOrDigit);

    //TODO: public static final Token QNAME = new Char(JavaLetterOrDigit);

    public static final Token IDENTIFIER = Sequence(JLETTER, ZeroOrMore(JLETTERORDIGIT));

    public static final Token EXTENDED = OneOrMore( Char( anyOf(AlphaNum, ch('_'), ch('-'), ch('.')) ) );

    public static final Token QUOTED_STRING = Sequence(
            Char('"'),
                ZeroOrMore(
                    AnyOf(
                        new Char(noneOf('"', '\\', (char) 0x0D, (char) 0x0A, (char) 0x00)),
                        Str("\\\""), Str("\\\\")
                    )
                ),
            Char('"'));

    public static final Token ARGUMENT = AnyOf( EXTENDED,  QUOTED_STRING );

    public static final Token DIRECTIVE = Sequence(EXTENDED, Str(":="), ARGUMENT);

    public static final Token ATTRIBUTE = Sequence(EXTENDED, Char('='), ARGUMENT);

    @SuppressWarnings("unused")
    public static final Token PARAMETER = AnyOf(DIRECTIVE, ATTRIBUTE);

    public static final Token UNIQUE_NAME = Sequence( IDENTIFIER , ZeroOrMore( Sequence( Char('.'), IDENTIFIER ) ) );

    @SuppressWarnings("unused")
    public static final Token SYMBOLIC_NAME = Sequence( TOKEN, ZeroOrMore( Sequence( Char('.'), TOKEN ) ) );

    @SuppressWarnings("unused")
    public static final Token PACKAGE_NAME = UNIQUE_NAME;

    public static final Token PATH_SEP = Char( '/' );
    public static final Token PATH_ELEMENT = OneOrMore( Char( noneOf( '/', '\"', (char)0x0D, (char)0x0A, (char)0x00) ) );

    public static final Token PATH_UNQUOTED = AnyOf(
            PATH_SEP,
            Sequence( Optional( PATH_SEP ), PATH_ELEMENT, ZeroOrMore( Sequence( PATH_SEP, PATH_ELEMENT ) ) )
    );

    public static final Token PATH_QUOTED = Sequence( Char( '"' ), PATH_UNQUOTED, Char( '"' ) );

    public static final Token PATH = AnyOf( PATH_UNQUOTED, PATH_QUOTED );


    public static final Token QUALIFIER = OneOrMore(AnyOf(Token.ALPHANUM, Char('_'), Char('-')));

    public <R> Parser<R> as(final Class<R> type) {
        try {
            final Method valueOf = type.getDeclaredMethod("valueOf", String.class);
            if (type.isAssignableFrom(valueOf.getReturnType())) {
                return this.as(new Function<String, R>() {
                    @Override
                    public R apply(String input) {
                        return applyMethod(type, valueOf, input);
                    }

                    @SuppressWarnings("unused")
                    public Failure failureOf(NumberFormatException e) {
                        String message = e.getMessage();
                        return Failure.of("Invalid number" + message.substring(message.indexOf(":")));
                    }
                });
            }
        } catch (NoSuchMethodException e) {
            // ignore and try constructor
        }

        try {
            final Constructor<R> ctor = type.getConstructor(String.class);
            return this.as(new Function<String, R>() {
                @Override
                public R apply(String input) {
                    return applyConstructor(ctor, input);
                }
            });
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Either static method valueOf or single String constructor is expected!");
        }
    }

    private static <R> R applyConstructor(Constructor<R> ctor, String input) {
        try {
            return ctor.newInstance(input);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof NumberFormatException) {
                throw (NumberFormatException) e.getCause();
            }
            throw new RuntimeException(e.getCause());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <R> R applyMethod(Class<R> type, Method method, String input) {
        try {
            method.setAccessible(true);
            return type.cast(method.invoke(null, input));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof NumberFormatException) {
                throw (NumberFormatException) e.getCause();
            }
            throw new RuntimeException(e.getCause());
        }
    }

    private static Token Optional(Token token) {
        return new Optional( token );
    }
    public static final class Optional extends Token {
        private final Token token;

        public Optional(Token token) {
            this.token = token;
        }

        @Nonnull
        @Override
        public Result<String> parse(CharSequence input) {
            if (input == null) return Failure.of("null").asResult();

            Result<String> result = token.parse(input);
            if (result instanceof Success)
                return result;

            return Success.of("", input);
        }
    }

    public static Char Char(char ch) {
        return new Char(ch);
    }
    public static Char Char(Predicate ch) {
        return new Char(ch);
    }
    public static final class Char extends Token {
        private final Predicate predicate;

        public Char(char ch) {
            this(Predicates.ch(ch));
        }
        public Char(Predicate predicate) {
            this.predicate = predicate;
        }

        @Nonnull
        @Override
        public Result<String> parse(CharSequence input) {
            if (input == null) return Failure.of("null").asResult();

            int length = input.length();
            if (length == 0) return Failure.of("empty").asResult();

            char ch = input.charAt(0);
            if (predicate.accept(ch))
                return Success.of(String.valueOf(ch), input.subSequence(1, length));

            return Failure.of(getFailureMessage(ch)).asResult();
        }

        protected String getFailureMessage(char ch) {
            return String.format("%s expected, but got '%s'",
                    predicate.toString(), String.valueOf(ch));
        }

        @Override
        public String toString() {
            return predicate.toString();
        }
    }

    public static Str Str( String pattern ) {
        return new Str( pattern );
    }
    public static final class Str extends Token {
        private final String pattern;

        public Str(String pattern) {
            this.pattern = pattern;
        }

        @Nonnull
        @Override
        public Result<String> parse(CharSequence input) {
            if (input == null) return Failure.of("null").asResult();

            int inputLength = input.length();
            if (inputLength == 0) return Failure.of("empty").asResult();

            int length = pattern.length();
            if (inputLength < length)
                return Failure.of("\"" + pattern + "\" expected, but got \"" + input + "\"")
                              .asResult();

            for (int i = 0; i < length; i++) {
                if (pattern.charAt(i) != input.charAt(i))
                    Failure.of("'" + pattern.charAt(i) + "' expected, but got '" + input.charAt(i) + "'")
                           .asResult();
            }

            return Success.of(pattern, input.subSequence(length, inputLength));
        }
    }

    public static Token OneOrMore (Token token) {
        return new OneOrMore(token);
    }
    public static final class OneOrMore extends Token {
        private final Token token;

        public OneOrMore(Token token) {
            this.token = token;
        }

        @Nonnull
        @Override
        public Result<String> parse(CharSequence input) {
            StringBuilder output = new StringBuilder();
            Result<String> result = token.parse(input);
            if (result instanceof Failure)
                return result;

            Success success;
            do {
                success = (Success) result;
                output.append(success.value);
                result = token.parse(success.rest);
            }
            while (result instanceof Success);

            return Success.of(output.toString(), success.rest);
        }

        @Override
        public String toString() {
            return "( " + token.toString() + " )+" ;
        }
    }

    public static ZeroOrMore ZeroOrMore(Token token) {
        return new ZeroOrMore(token);
    }
    public static final class ZeroOrMore extends Token {
        private final Token token;

        public ZeroOrMore(Token token) {
            this.token = token;
        }

        @Nonnull
        @Override
        public Result<String> parse(CharSequence input) {
            StringBuilder out = new StringBuilder();
            Result<String> result = token.parse(input);
            if (result instanceof Failure)
                return Success.of("", input);

            Success success;
            do {
                success = (Success) result;
                out.append(success.value);
                result = token.parse(success.rest);
            }
            while (result instanceof Success);

            return Success.of(out.toString(), success.rest);
        }

        @Override
        public String toString() {
            return "( " + token.toString() + " )*";
        }
    }

    public static Sequence Sequence(Token ... tokens) {
        return new Sequence(tokens);
    }
    public static final class Sequence extends Token {
        private final Token[] tokens;

        public Sequence(Token ... tokens) {
            this.tokens = tokens;
        }

        @Nonnull
        @Override
        public Result<String> parse(CharSequence input) {
            StringBuilder out = new StringBuilder();
            Iterator<Token> it = Arrays.asList(tokens).iterator();

            Result<String> result = nextResult(it, input);
            while (result instanceof Success) {
                Success success = (Success) result;
                out.append(success.value);

                if (!it.hasNext())
                    break;

                result = nextResult(it, success.rest);
            }

            if (result instanceof Failure)
                return result;

            @SuppressWarnings("ConstantConditions")
            Success success = (Success) result;
            out.append(success.value);

            return Success.of(out.toString(), success.rest);
        }

        private Result<String> nextResult(Iterator<Token> it, CharSequence input) {
            Token token = it.next();
            return token.parse(input);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tokens.length; i++) {
                if (i > 0) sb.append(' ');
                sb.append(tokens[i]);
            }
            return sb.toString();
        }
    }

    public static Token AnyOf(Token ... tokens) {
        return new AnyOf( tokens );
    }
    public static final class AnyOf extends Token {
        private final Token[] tokens;

        public AnyOf(Token... tokens) {
            this.tokens = tokens;
        }

        @Nonnull
        @Override
        public Result<String> parse(CharSequence input) {
            if (input == null)
                return Failure.of("null").asResult();

            if (input.length() == 0)
                return Failure.of("empty").asResult();

            for (Token token : tokens) {
                Result<String> result = token.parse(input);
                if (result instanceof Success)
                    return result;
            }

            return Failure.of(getFailureMessage(input)).asResult();
        }

        protected String getFailureMessage(CharSequence input) {
            return this.toString() + " expected, but got \'" + input.charAt(0) + "'";
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tokens.length; i++) {
                if (i > 0) sb.append(" | ");
                sb.append(tokens[i]);
            }
            return sb.toString();
        }
    }
}
