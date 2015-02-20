package info.tepp.osgi.manifest.parser;

public class Functions {

    public static <L, R, T> BiFunction<R, L, T> revert(BiFunction<L, R, T> f) {
        if (f instanceof RevertFunction) {
            return ((RevertFunction<L, R, T>) f).fn;
        }
        return new RevertFunction<R, L, T>(f);
    }

    private static class RevertFunction<R, L, T> implements BiFunction<R, L, T> {
        private final BiFunction<L, R, T> fn;

        private RevertFunction(BiFunction<L, R, T> fn) {
            this.fn = fn;
        }

        @Override
        public T apply(R right, L left) {
            return fn.apply(left, right);
        }
    }
}
