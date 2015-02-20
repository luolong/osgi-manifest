package info.tepp.osgi.manifest.parser;

import java.lang.reflect.Field;
import java.util.Arrays;

public abstract class Tuple {
    private final int _hashCode;

    private Tuple(int hashCode) {
        _hashCode = hashCode;
    }

    public static <L, R> Tuple2<L, R> of(L left, R right) {
        return new Tuple2<L, R>(left, right);
    }
    public static final class Tuple2<L, R> extends Tuple{
        public final L left;
        public final R right;

        public Tuple2(L left, R right) {
            super(hashOf(left,right));
            this.left = left;
            this.right = right;
        }

        public <T> Tuple3 add(T third) {
            return Tuple.of(left, right, third);
        }

        @Override
        public String toString() {
            return Tuple.toString(left, right);
        }
    }
    public static <L, R> TupleRightFunction<L, R> Right() {
        return new TupleRightFunction<L, R>();
    }
    public static class TupleRightFunction<L, R> implements Function<Tuple2<L, R>, R> {
        @Override
        public R apply(Tuple2<L, R> tuple) {
            return tuple.right;
        }
    }

    public static <L, R> TupleLeftFunction<L, R> Left() {
        return new TupleLeftFunction<L, R>();
    }
    public static class TupleLeftFunction<L, R> implements Function<Tuple2<L, R>, L> {
        @Override
        public L apply(Tuple2<L, R> tuple) {
            return tuple.left;
        }
    }


    public static <F, S, T> Tuple3 of(F first, S second, T third) {
        return new Tuple3<F, S, T>(first, second, third);
    }
    public static final class Tuple3<F, S, T>  extends Tuple{
        public final F first;
        public final S second;
        public final T third;

       @Override
        public String toString() {
            return Tuple.toString(first, second, third);
        }

        private Tuple3(F first, S second, T third) {
            super(hashOf());

            this.first = first;
            this.second = second;
            this.third = third;
        }

        public Tuple2<S, T> rest() {
            return Tuple.of(second, third);
        }

        @SuppressWarnings("unchecked")
        public static <T1, T2, T3> Tuple3<T1, T2, T3> normalize(Tuple2<Tuple2<T1, T2>, T3> input) {
            return Tuple.of(input.left.left, input.left.right, input.right);
        }
    }

    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> of (T1 first, T2 second, T3 third, T4 fourth) {
        return new Tuple4<T1, T2, T3, T4>(first, second, third, fourth);
    }

    public static class Tuple4<T1, T2, T3, T4> extends Tuple {
        public final T1 first;
        public final T2 second;
        public final T3 third;
        public final T4 fourth;

        public Tuple4(T1 first, T2 second, T3 third, T4 fourth) {
            super(hashOf(first, second, third, fourth));

            this.first = first;
            this.second = second;
            this.third = third;
            this.fourth = fourth;
        }

        @Override
        public String toString() {
            return Tuple.toString(first,second,third,fourth);
        }

        public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> normalize(Tuple2<T1, Tuple2<T2, Tuple2<T3, T4>>> tuple2) {
            return Tuple.of(tuple2.left, tuple2.right.left, tuple2.right.right.left, tuple2.right.right.right);
        }

    }



    @Override
    public final int hashCode() {
        return _hashCode;
    }

    /** Returns a hashCode over list of values */
    private static int hashOf(Object ... hashableValues) {
        int hash = 0;
        for (Object o : hashableValues) {
            hash = 31 * hash + (o != null ? o.hashCode() : 0);
        }
        return hash;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                Object v1 = field.get(this);
                Object v2 = field.get(o);
                if (!equals(v1, v2)) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                return false;
            }
        }

        return true;
    }

    private boolean equals(Object o1, Object o2) {
        if (o1 == o2) return true;
        if (o1 == null || o2 == null) return false;

        return o1.getClass() == o2.getClass() && o1.equals(o2);
    }

    private static String toString(Object ... values) {
        return "Tuple [" + Arrays.toString(values) + "]";
    }
}
