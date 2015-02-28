package info.tepp.osgi.manifest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public abstract class Filter {

    public static class And extends Filter {
        public final List<Filter> filterList;

        public And(Filter ... filters) {
            this(Arrays.asList(filters));
        }

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
    public static class Or extends Filter {
        public final List<Filter> filterList;

        public Or(Filter ... filters) {
            this(Arrays.asList(filters));
        }

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

}
