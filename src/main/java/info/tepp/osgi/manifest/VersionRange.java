package info.tepp.osgi.manifest;

import info.tepp.osgi.manifest.parser.Result;
import info.tepp.osgi.manifest.parser.VersionRangeParser;

import javax.annotation.Nonnull;
import java.text.ParseException;

public class VersionRange {

    private final VersionBoundary floor;

    private final Boundary ceiling;

    public VersionRange(@Nonnull VersionBoundary floor, @Nonnull Boundary ceiling) {
        this.floor = floor;
        this.ceiling = ceiling;
    }

    public VersionBoundary getFloor() {
        return floor;
    }

    public Version getFloorVersion() {
        return floor.version;
    }

    public Boundary getCeiling() {
        return ceiling;
    }

    /**
     * Returns matchesCeiling version (or {@code null} if
     * this is <i>atleast</i> version range).
     */
    public Version getCeilingVersion() {
        if (ceiling == Infinity) return null;
        return ((VersionBoundary) ceiling).version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VersionRange that = (VersionRange) o;

        return ceiling.equals(that.ceiling)
                && floor.equals(that.floor);

    }

    @Override
    public int hashCode() {
        int result = floor.hashCode();
        result = 31 * result + ceiling.hashCode();
        return result;
    }

    @Override
    public String toString() {
        if (ceiling == Infinity) {
            return floor.version.toString();
        }

        String floor = this.floor.toString();
        String ceiling = this.ceiling.toString();

        return String.format("%s,%s", floor.substring(0, floor.length()-1), ceiling.substring(1));
    }

    public static VersionRange parseVersionRange(String versionRange) throws ParseException {
        Result<VersionRange> result = new VersionRangeParser().parse(versionRange);
        Result.Success<VersionRange> success = result.getSuccessOrThrow();
        if (success.rest.length() > 0)
            throw new ParseException("Unexpected characters: " + success.rest, 0);

        return success.value;
    }

    /**
     * Returns true if this version range contains (encompasses) provided version.
     */
    public boolean contains(Version version) {
        return floor.matchesFloor(version) && ceiling.matchesCeiling(version);
    }

    public static VersionRange atLeast(Version version) {
        return new VersionRange(Inclusive(version), Infinity);
    }

    /**
     * Version boundary in a version range.
     *
     * Boundaries can be either inclusive or exclusive.
     * There is special boundary {@linkplain info.tepp.osgi.manifest.VersionRange#Infinity}
     * that will match all versions for the purpose of the _At least_ version range.
     */
    public static abstract class Boundary {
        private Boundary() {/* sealed implementation */}

        /** Returns {@code true} if the provided version matches this boundary at version range floor (i.e. minimum version number) */
        public abstract boolean matchesFloor(Version version);

        /** Returns {@code true} if the provided version matches this boundary at version range ceiling (i.e. maximum version number) */
        public abstract boolean matchesCeiling(Version other);

    }

    /** Infinite version boundary that matches all versions. */
    public static final Boundary Infinity = new Infinity();
    private static final class Infinity extends Boundary {
        private Infinity() {/* sealed singleton implementation */}

        @Override
        public boolean matchesFloor(Version other) {
            return true;
        }

        @Override
        public boolean matchesCeiling(Version other) {
            return true;
        }
    }

    public static abstract class VersionBoundary extends Boundary {
        public final Version version;

        protected VersionBoundary(Version version) {
            this.version = version;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            if (getClass() != o.getClass()) return false;

            Version otherVersion = ((VersionBoundary) o).version;
            return version.equals(otherVersion);
        }

        @Override
        public int hashCode() {
            return version.hashCode();
        }

        public static VersionBoundary valueOf(char ch, Version version) {
            switch (ch) {
                case '(' : return Exclusive(version);
                case '[' : return Inclusive(version);

                default: throw new IllegalArgumentException("Invalid range boundary character: '" + ch + "'");
            }
        }

        public static VersionBoundary valueOf(Version version, char ch) {
            switch (ch) {
                case ')' : return Exclusive(version);
                case ']' : return Inclusive(version);

                default: throw new IllegalArgumentException("Invalid range boundary character: '" + ch + "'");
            }
        }
    }

    public static Inclusive Inclusive(Version version) {
        return new Inclusive(version);
    }
    public static final class Inclusive extends VersionBoundary {
        public Inclusive(Version version) {
            super(version);
        }

        @Override
        public String toString() {
            return String.format("[%s]", version);
        }

        @Override
        public boolean matchesFloor(Version other) {
            int cmp = version.compareTo(other);
            return cmp <= 0;
        }

        @Override
        public boolean matchesCeiling(Version other) {
            int cmp = version.compareTo(other);
            return cmp >= 0;
        }
    }

    public static Exclusive Exclusive(Version version) {
        return new Exclusive(version);
    }
    public static final class Exclusive extends VersionBoundary {
        public Exclusive(Version version) {
            super(version);
        }


        @Override
        public boolean matchesFloor(Version other) {
            int cmp = version.compareTo(other);
            return cmp < 0;
        }

        @Override
        public boolean matchesCeiling(Version other) {
            int cmp = version.compareTo(other);
            return cmp > 0;
        }

        @Override
        public String toString() {
            return String.format("(%s)", version);
        }
    }
}
