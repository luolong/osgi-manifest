package info.tepp.osgi.manifest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static info.tepp.osgi.manifest.Filter.FilterType.*;
import static java.util.Collections.unmodifiableList;

public abstract class Filter {

    public static And And(Filter first, Filter second, Filter ... filters) {
        return new And(asList(first, second, filters));
    }

    public static class And extends Filter {
        public final List<Filter> filterList;

        public And(List<Filter> filterList) {
            this.filterList = unmodifiableList(
                    new ArrayList<Filter>(filterList));
        }

        @Override
        public String toString() {
            String string = "(&";
            for (Filter filter: filterList)
                string += filter.toString();

            return string + ")";
        }
    }

    public static Or Or(Filter first, Filter second, Filter ... filters){
        return new Or(asList(first, second, filters));
    }

    public static class Or extends Filter {
        public final List<Filter> filterList;

        public Or(List<Filter> filterList) {
            this.filterList = unmodifiableList(
                    new ArrayList<Filter>(filterList));
        }

        @Override
        public String toString() {
            String s = "(|";
            for (Filter filter: filterList)
                s += filter.toString();

            return s + ")";
        }
    }

    public static Not Not(Filter filter) {
        return new Not(filter);
    }
    public static class Not extends Filter {
        public final Filter filter;
        public Not(Filter filter) {
            this.filter = filter;
        }

        @Override
        public String toString() {
            return "(!" + filter.toString() + ")";
        }
    }

    public static abstract class Operation extends Filter {}

    public static Operation Eq(String attr, String value) {
        return new Simple(attr, equal, value);
    }

    public static Operation Approx(String attr, String value) {
        return new Simple(attr, approx, value);
    }

    public static Operation GreaterEq(String attr, String value) {
        return new Simple(attr, greaterEq, value);
    }

    public static Operation LessEq(String attr, String value) {
        return new Simple(attr, lessEq, value);
    }

    public static Operation Has(String attr) {
        return new Simple(attr, equal, "*");
    }

    public static class Simple extends Operation {
        public final String attr;
        public final FilterType filterType;
        public final String value;

        public Simple(String attr, String filterType, String value) {
            this(attr, FilterType.of(filterType), value);
        }

        public Simple(String attr, FilterType filterType, String value) {
            this.attr = attr;
            this.filterType = filterType;
            this.value = value;
        }

        @Override
        public String toString() {
            return "(" + attr + filterType + value + ")";
        }
    }

    public enum FilterType {
        equal("="), approx("~="), greaterEq(">="), lessEq("<=");

        private final String string;
        FilterType(String string) {
            this.string = string;
        }

        @Override
        public String toString() {
            return string;
        }

        public static FilterType of(String operator) {
            for (FilterType ft : values()) {
                if (ft.toString().equals(operator))
                    return ft;
            }

            throw new IllegalArgumentException(operator);
        }
    }

    private static <T> List<T> asList(T t1, T t2, T[] rest) {
        ArrayList<T> ts = new ArrayList<T>(rest.length + 2);
        ts.add(t1);
        ts.add(t2);
        ts.addAll(Arrays.asList(rest));
        return ts;
    }

}
