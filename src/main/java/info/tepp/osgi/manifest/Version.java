package info.tepp.osgi.manifest;

import info.tepp.osgi.manifest.parser.Maybe;
import info.tepp.osgi.manifest.parser.Parser;
import info.tepp.osgi.manifest.parser.Result;
import info.tepp.osgi.manifest.parser.Tuple;
import info.tepp.osgi.manifest.parser.Tuple.Tuple2;

import java.text.ParseException;

import static info.tepp.osgi.manifest.parser.Parser.*;

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
        Parser<Tuple2<Integer, Maybe<Tuple2<Integer, Maybe<Tuple2<Integer, Maybe<String>>>>>>> parser;
        parser = NUMBER.then(
                Maybe(Right(DOT.then(NUMBER)).then(
                        Maybe(Right(DOT.then(NUMBER)).then(
                                Maybe(Right(DOT.then(REST)))
                        ))
                ))
            ).then(EOF)
             .as(Tuple.<Tuple2<Integer, Maybe<Tuple2<Integer, Maybe<Tuple2<Integer, Maybe<String>>>>>>, Void>Left());

        Result<Tuple2<Integer, Maybe<Tuple2<Integer, Maybe<Tuple2<Integer, Maybe<String>>>>>>> result;
        result = parser.parse(versionString);
        if (result instanceof Result.Failure) {
            throw new ParseException(((Result.Failure) result).message, -1);
        }

        Tuple2<Integer, Maybe<Tuple2<Integer, Maybe<Tuple2<Integer, Maybe<String>>>>>> value;
        value = ((Result.Success<Tuple2<Integer, Maybe<Tuple2<Integer, Maybe<Tuple2<Integer, Maybe<String>>>>>>>) result).value;

        int major = value.left.intValue();
        Maybe<Tuple2<Integer, Maybe<Tuple2<Integer, Maybe<String>>>>> maybeMinor = value.right;
        int minor = maybeMinor.map(Tuple.<Integer, Maybe<Tuple2<Integer, Maybe<String>>>>Left()).or(0).intValue();

        Maybe<Tuple2<Integer, Maybe<String>>> maybeMicro = maybeMinor
                .flatMap(Tuple.<Integer, Maybe<Tuple2<Integer, Maybe<String>>>>Right());
        int micro = maybeMicro.map(Tuple.<Integer, Maybe<String>>Left()).or(0).intValue();

        Maybe<String> maybeQualifier = maybeMicro.flatMap(Tuple.<Integer, Maybe<String>>Right());
        return new Version(major, minor, micro, maybeQualifier.or(""));
    }
}
