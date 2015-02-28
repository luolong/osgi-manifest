package info.tepp.osgi.manifest.parser;

import info.tepp.osgi.manifest.Version;
import info.tepp.osgi.manifest.VersionRange;
import info.tepp.osgi.manifest.VersionRange.VersionBoundary;
import info.tepp.osgi.manifest.parser.Result.Failure;
import info.tepp.osgi.manifest.parser.Result.Success;

import javax.annotation.Nonnull;


/**
 * A version range describes a range of versions using a mathematical interval notation.
 * See [13] Mathe- matical Convention for Interval Notation.
 *
 * The syntax of a version range is:
 *
 * <pre>version-range ::= interval | atleast
 * interval ::= ( '[' | '(' ) floor ',' ceiling ( ']' | ')' )
 * atleast ::= version
 * floor ::= version
 * ceiling ::= version</pre>
 *
 * If a version range is specified as a single version, it must be interpreted as the range [version,∞). The default for a non-specified version range is 0, which maps to [0.0.0,∞).
 *
 * Note that the use of a comma in the version range requires it to be enclosed in double quotes. For example:
 * <pre>Import-Package: com.acme.foo;version="[1.23, 2)", «
 * com.acme.bar;version="[4.0, 5.0)"</pre>
 */
public class VersionRangeParser extends Parser<VersionRange> {
    private final VersionParser versionParser = new VersionParser();

    public VersionRangeParser() {
    }

    @Nonnull
    @Override
    public Result<VersionRange> parse(CharSequence input) {
        if (input == null)
            return Failure.of("null").asResult();

        if (input.length() == 0)
            return Failure.of("empty").asResult();

        char ch = input.charAt(0);
        if (ch == '[' || ch == '(') {
            Result<VersionRange> versionRangeResult = parseVersionRange(input);
            if (versionRangeResult.isSuccess()) return versionRangeResult;
            return Failure.of(versionRangeResult).asResult();
        }

        if (Character.isDigit(ch)){
            Result<Version> versionResult = versionParser.parse(input);
            if (versionResult.isSuccess()) {
                Success<Version> success = (Success<Version>) versionResult;
                VersionRange versionRange = VersionRange.atLeast(success.value);
                return Success.of(versionRange, success.rest);
            }
            return Failure.of(versionResult).asResult();
        }

        return Failure.of("Version Range expected, but got '" + ch + "'").asResult();
    }

    private Result<VersionRange> parseVersionRange(CharSequence input) {
        Result<VersionBoundary> result = parseRangeStart(input);
        if (result.isSuccess()) {
            Success<VersionBoundary> success = (Success<VersionBoundary>) result;
            if (success.rest.length() == 0)
                return Failure.of("VersionRange separator expected, but got <EOF>!").asResult();

            char ch = success.rest.charAt(0);
            if (ch != ',')
                return Failure.of("Version range separator expected, but got '" + ch + "'!").asResult();

            VersionBoundary floor = success.value;
            input = success.rest.subSequence(1, success.rest.length());

            result = parseRangeEnd(input);
            if (result.isSuccess()) {
                success = (Success<VersionBoundary>) result;
                VersionBoundary ceiling = success.value;
                return Success.of(new VersionRange(floor, ceiling), success.rest);
            }
        }

        return Failure.of(result).asResult();
    }

    private Result<VersionBoundary> parseRangeStart(CharSequence input) {
        char ch = input.charAt(0);
        if (ch == '[' || ch == '(') {
            Result<Version> result = versionParser.parse(input.subSequence(1, input.length()));
            if (result instanceof Success) {
                Success<Version> success = (Success<Version>) result;
                VersionBoundary value = VersionBoundary.valueOf(ch, success.value);
                return Success.of(value, success.rest);
            }

            return Failure.of(result).asResult();
        }

        return Failure.of("Version range start expected, but got '" + ch + "'").asResult();
    }

    private Result<VersionBoundary> parseRangeEnd(CharSequence input) {
        Result<Version> result = versionParser.parse(input);
        if (result instanceof Success) {
            Success<Version> success = (Success<Version>) result;
            if (success.rest.length() == 0)
                return Failure.of("VersionRange end expected, but got <EOF>!").asResult();

            char ch = success.rest.charAt(0);
            if (ch != ']' && ch != ')')
                return Failure.of("Version range end expected, but got '" + ch + "'!").asResult();

            VersionBoundary value = VersionBoundary.valueOf(success.value, ch);
            return Success.of(value, success.rest.subSequence(1, success.rest.length()));
        }

        return Failure.of(result).asResult();
    }
}
