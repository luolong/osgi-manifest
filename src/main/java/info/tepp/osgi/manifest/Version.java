package info.tepp.osgi.manifest;

import info.tepp.osgi.manifest.parser.*;
import info.tepp.osgi.manifest.parser.Result.Failure;
import info.tepp.osgi.manifest.parser.Result.Success;
import info.tepp.osgi.manifest.parser.Tuple.Tuple2;
import info.tepp.osgi.manifest.parser.Tuple.Tuple4;

import java.text.ParseException;

import static info.tepp.osgi.manifest.parser.Parser.Maybe;
import static info.tepp.osgi.manifest.parser.Parser.Right;
import static info.tepp.osgi.manifest.parser.Predicates.anyOf;
import static info.tepp.osgi.manifest.parser.Token.AnyOf;
import static info.tepp.osgi.manifest.parser.Token.Char;
import static info.tepp.osgi.manifest.parser.Token.OneOrMore;

public class Version implements Comparable<Version> {

    public final int major;
    public final int minor;
    public final int micro;
    public final String qualifier; // never null

    public Version(int major, int minor, int micro, String qualifier) {
        this.major = major;
        this.minor = minor;
        this.micro = micro;
        this.qualifier = qualifier != null ? qualifier : "";
    }

    @Override
    public int compareTo(Version other) {
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

        if (major != version.major) return false;
        if (micro != version.micro) return false;
        if (minor != version.minor) return false;
        if (!qualifier.equals(version.qualifier)) return false;

        return true;
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
    public String toString() {
        String string = "" + major + "." + minor;

        int q = qualifier != null ? qualifier.length() : 0;
        if ((micro | q) != 0) {
            string += "." + micro;
            if (q != 0)
                string += "." + qualifier;
        }

        return string;
    }

    public static Version parseVersion(String versionString) throws ParseException {
        Parser<Major> major = Token.NUMBER.as(Major.class);
        Parser<Minor> minor = Token.NUMBER.as(Minor.class);
        Parser<Micro> micro = Token.NUMBER.as(Micro.class);
        return null;
    }

    public static final class Major extends Number {
        public static Major valueOf(int intValue) {
            return new Major(intValue);
        }
        public static Major valueOf(String string) {
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
    }

    public static final class Minor extends Number {
        public static Minor valueOf(int intValue) {
            return new Minor(intValue);
        }
        public static Minor valueOf(String string) {
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
    }

    public static final class Micro extends Number {
        public static Micro valueOf(int intValue) {
            return new Micro(intValue);
        }
        public static Micro valueOf(String string) {
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
    }
}
