package info.tepp.osgi.manifest.parser;

import static java.lang.Character.isWhitespace;

public abstract class Predicates {
    static final Predicate Whitespace = new Predicate() {
        @Override
        public boolean accept(char ch) {
            return isWhitespace(ch);
        }

        @Override
        public String toString() {
            return "Whitespace";
        }
    };
    static final Predicate Digit = range('0', '9');
    static final Predicate Alpha = anyOf(range('a', 'z'), range('A', 'Z'));
    static final Predicate AlphaNum = anyOf(Digit, Alpha);

    static final Predicate JavaLetter = new Predicate() {
        @Override
        public boolean accept(char ch) {
            return Character.isJavaIdentifierStart(ch);
        }

        @Override
        public String toString() {
            return "JavaLetter";
        }
    };

    static final Predicate JavaLetterOrDigit = new Predicate() {
        @Override
        public boolean accept(char ch) {
            return Character.isJavaIdentifierPart(ch);
        }

        @Override
        public String toString() {
            return "JavaLetterOrDigit";
        }
    };



    static Predicate range(final char from, final char to) {
        return new Predicate() {
            @Override
            public boolean accept(char ch) {
                return from <= ch && ch <= to;
            }

            @Override
            public String toString() {
                return "['" + from + "'..'" + to + "']";
            }
        };
    }

    public static Predicate anyOf(final char ... chars) {
        return new Predicate() {
            @Override
            public boolean accept(char ch) {
                for (int i = 0; i < chars.length; i++) {
                    if (ch == chars[i]) return true;
                }
                return false;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder("[");
                for (int i = 0; i < chars.length; i++) {
                    char ch = chars[i];
                    if (Character.isISOControl(ch))
                        sb.append("#").append(Integer.toHexString(ch));
                    else
                        sb.append(ch);
                }
                return sb.append("]").toString();
            }
        };
    }

    public static Predicate noneOf(final char ... chars) {
        return new Predicate() {
            @Override
            public boolean accept(char ch) {
                for (int i = 0; i < chars.length; i++) {
                    if (ch == chars[i]) return true;
                }
                return false;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder("[");
                for (int i = 0; i < chars.length; i++) {
                    char ch = chars[i];
                    if (Character.isISOControl(ch))
                        sb.append("#").append(Integer.toHexString(ch));
                    else
                        sb.append(ch);
                }
                return sb.append("]").toString();
            }
        };
    }

    public static Predicate anyOf(final Predicate... predicates) {
        return new Predicate() {
            @Override
            public boolean accept(char ch) {
                for (Predicate predicate : predicates) {
                    if (predicate.accept(ch))
                        return true;
                }
                return false;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < predicates.length; i++) {
                    if (i > 0) sb.append(" | ");
                    sb.append(predicates[i]);
                }
                return sb.toString();
            }
        };
    }

    public static Predicate ch(final char character) {
        return new Predicate() {
            @Override
            public boolean accept(char ch) {
                return ch == character;
            }

            @Override
            public String toString() {
                return "'" + character + "'";
            }
        };
    }

}
