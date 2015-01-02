package info.tepp.osgi.manifest.parser;

/**
 * Type that may have {@link Some some} value or {@link #None none}
 */
public abstract class Maybe<T> {

    private Maybe() {/* sealed implementation */}

    /**
     * Maybe return {@link info.tepp.osgi.manifest.parser.Maybe.Some Some} value
     * (or {@link #None}, if input is {@literal null})
     * @param value value or {@literal null}
     * @param <T> type of the value
     * @return Returns a value wrapped in instance of Some, if the value is not null;
     *    return None otherwise
     */
    public static <T> Maybe<T> Some(T value) {
        if (value == null) {
            return None();
        }

        return new Some<T>(value);
    }

    public abstract <R> Maybe<R> map(Function<T, R> func);

    public abstract <R> Maybe<R> flatMap(Function<T, Maybe<R>> func);

    /**
     * Typesafely returns None
     * @param <T> type of the None
     */
    @SuppressWarnings("unchecked")
    public static <T> Maybe<T> None() {
        return (Maybe<T>) None;
    }

    public abstract T or(T fallback);

    /**
     * Some value of type T
     * @param <T> type of the value
     */
    public static final class Some<T> extends Maybe<T> {
        public final T value;

        public Some(T value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Some some = (Some) o;
            return value.equals(some.value);
        }

        @Override
        public <R> Maybe<R> map(Function<T, R> func) {
            return Some(func.apply(value));
        }

        @Override
        public <R> Maybe<R> flatMap(Function<T, Maybe<R>> func) {
            return func.apply(value);
        }

        @Override
        public T or(T ignore) {
            return value;
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        public String toString() {
            return "Some " + value;
        }
    }

    /**
     * Singleton object
     */
    public static final Maybe<Object> None = new Maybe<Object>() {
        @Override
        @SuppressWarnings("unchecked")
        public <R> Maybe<R> map(Function<Object, R> func) {
            return (Maybe<R>) this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <R> Maybe<R> flatMap(Function<Object, Maybe<R>> func) {
            return (Maybe<R>) this;
        }

        @Override
        public Object or(Object value) {
            return value;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            return o == this;
        }

        @Override
        public String toString() {
            return "None";
        }
    };
}
