package info.tepp.osgi.manifest;

import info.tepp.osgi.manifest.parser.Parser;
import info.tepp.osgi.manifest.parser.Result;
import info.tepp.osgi.manifest.parser.Result.Failure;
import info.tepp.osgi.manifest.parser.Result.Success;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.ParseException;

import static info.tepp.osgi.manifest.parser.Parser.Right;
import static info.tepp.osgi.manifest.parser.Token.*;

public class Version implements Comparable<Version> {

    public final int major;
    public final int minor;
    public final int micro;
    public final @Nonnull String qualifier; // never null

    public Version(int major, int minor, int micro, @Nullable String qualifier) {
        this.major = major;
        this.minor = minor;
        this.micro = micro;
        this.qualifier = qualifier != null ? qualifier : "";
    }

    @Override
    public int compareTo(@Nonnull Version other) {
        int major = this.major - other.major;
        if (major != 0) return major;

        int minor = this.minor - other.minor;
        if (minor != 0) return minor;

        int micro = this.micro - other.micro;
        if (micro != 0) return micro;

        return this.qualifier.compareTo(other.qualifier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Version version = (Version) o;

        return major == version.major
            && micro == version.micro
            && minor == version.minor
            && qualifier.equals(version.qualifier);
    }

    @Override
    public int hashCode() {
        int result = major;
        result = 31 * result + minor;
        result = 31 * result + micro;
        result = 31 * result + qualifier.hashCode();
        return result;
    }

    @Override
    public @Nonnull String toString() {
        String string = "" + major + "." + minor;

        int q = qualifier.length();
        if ((micro | q) != 0) {
            string += "." + micro;
            if (q != 0)
                string += "." + qualifier;
        }

        return string;
    }

    @SuppressWarnings("unused")
    public static @Nonnull Version valueOf(int major) {
        return new Version(major, 0, 0, "");
    }

    @SuppressWarnings("unused")
    public static @Nonnull Version valueOf(@Nonnull Major major) {
        return new Version(major.intValue(), 0, 0, "");
    }

    @SuppressWarnings("unused")
    public static @Nonnull Version valueOf(int major, int minor) {
        return new Version(major, minor, 0, "");
    }

    @SuppressWarnings("unused")
    public static @Nonnull Version valueOf(@Nonnull Major major, @Nonnull Minor minor) {
        return new Version(major.intValue(), minor.intValue(), 0, "");
    }

    @SuppressWarnings("unused")
    public static @Nonnull Version valueOf(int major, int minor, int micro) {
        return new Version(major, minor, micro, "");
    }

    @SuppressWarnings("unused")
    public static @Nonnull Version valueOf(@Nonnull Major major, @Nonnull Minor minor, @Nonnull Micro micro) {
        return new Version(major.intValue(), minor.intValue(), micro.intValue(), "");
    }

    @SuppressWarnings("unused")
    private static @Nonnull Version valueOf(@Nonnull Major major, @Nonnull Minor minor, @Nonnull Micro micro, @Nonnull Qualifier qualifier) {
        return new Version(major.intValue(), minor.intValue(), micro.intValue(), qualifier.toString());
    }

    public static @Nonnull Version parseVersion(@Nonnull String versionString) throws ParseException {
        Success<Major> major = Major.parseMajor(versionString);
        if (major.rest.length() == 0)
            return Version.valueOf(major.value);

        Success<Minor> minor = Minor.parseMinor(major.rest);
        if (minor.rest.length() == 0)
            return Version.valueOf(major.value, minor.value);

        Success<Micro> micro = Micro.parseMicro(minor.rest);
        if (micro.rest.length() == 0)
            return Version.valueOf(major.value, minor.value, micro.value);

        Success<Qualifier> qualifier = Qualifier.parseQualifier(micro.rest);
        return Version.valueOf(major.value, minor.value, micro.value, qualifier.value);
    }

    public static final class Major extends Number {
        @SuppressWarnings("unused")
        public static @Nonnull Major valueOf(int intValue) {
            return new Major(intValue);
        }

        @SuppressWarnings("unused")
        public static @Nonnull Major valueOf(@Nonnull String string) throws NumberFormatException {
            return new Major(Integer.parseInt(string));
        }

        private final int value;
        public Major(int value) {
            this.value = value;
        }

        @Override
        public int intValue() {
            return value;
        }

        @Override
        public long longValue() {
            return value;
        }

        @Override
        public float floatValue() {
            return value;
        }

        @Override
        public double doubleValue() {
            return value;
        }

        public static Success<Major> parseMajor(CharSequence input) throws ParseException {
            Parser<Major> parser = NUMBER.as(Major.class);
            Result<Major> result = parser.parse(input);
            if (result instanceof Success) {
                return (Success<Major>) result;
            }

            Failure failure = Failure.of(result);
            throw failure.asException();
        }
    }

    public static final class Minor extends Number {
        @SuppressWarnings("unused")
        public static @Nonnull Minor valueOf(int intValue) {
            return new Minor(intValue);
        }

        @SuppressWarnings("unused")
        public static @Nonnull Minor valueOf(@Nonnull String string) throws NumberFormatException {
            return new Minor(Integer.parseInt(string));
        }

        private final int value;
        public Minor(int value) {
            this.value = value;
        }

        @Override
        public int intValue() {
            return value;
        }

        @Override
        public long longValue() {
            return value;
        }

        @Override
        public float floatValue() {
            return value;
        }

        @Override
        public double doubleValue() {
            return value;
        }

        public static @Nonnull Success<Minor> parseMinor(@Nonnull CharSequence input) throws ParseException {
            Parser<Minor> parser = Right(Char('.').then(NUMBER.as(Minor.class)));
            Result<Minor> result = parser.parse(input);
            if (result instanceof Success) {
                return (Success<Minor>) result;
            }

            Failure failure = Failure.of(result);
            throw failure.asException();
        }
    }

    public static final class Micro extends Number {

        @SuppressWarnings("unused")
        public static @Nonnull Micro valueOf(int intValue) {
            return new Micro(intValue);
        }

        @SuppressWarnings("unused")
        public static @Nonnull Micro valueOf(@Nonnull String string) throws NumberFormatException {
            return new Micro(Integer.parseInt(string));
        }

        private final int value;
        public Micro(int value) {
            this.value = value;
        }

        @Override
        public int intValue() {
            return value;
        }

        @Override
        public long longValue() {
            return value;
        }

        @Override
        public float floatValue() {
            return value;
        }

        @Override
        public double doubleValue() {
            return value;
        }

        public static @Nonnull Success<Micro> parseMicro(@Nonnull CharSequence input) throws ParseException {
            Parser<Micro> parser = Right(Char('.').then(NUMBER.as(Micro.class)));
            Result<Micro> result = parser.parse(input);
            if (result instanceof Success) {
                return (Success<Micro>) result;
            }

            Failure failure = Failure.of(result);
            throw failure.asException();
        }
    }

    public static final class Qualifier implements CharSequence {

        @SuppressWarnings("unused")
        public static @Nonnull Qualifier valueOf(@Nonnull String value) {
            return new Qualifier(value);
        }

        private final String value;
        public Qualifier(@Nonnull String value) {
            this.value = value;
        }

        @Override
        public int length() {
            return value.length();
        }

        @Override
        public char charAt(int i) {
            return value.charAt(i);
        }

        @Override
        public CharSequence subSequence(int i, int i1) {
            return value.subSequence(i, i1);
        }

        @Override
        public @Nonnull String toString() {
            return value;
        }

        public static @Nonnull Success<Qualifier> parseQualifier(@Nonnull CharSequence input) throws ParseException {
            Parser<Qualifier> parser = Right(Char('.').then(QUALIFIER.as(Qualifier.class)));
            Result<Qualifier> result = parser.parse(input);
            if (result instanceof Success) {
                return (Success<Qualifier>) result;
            }

            Failure failure = Failure.of(result);
            throw failure.asException();
        }
    }
}
