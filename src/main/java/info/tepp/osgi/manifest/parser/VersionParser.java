package info.tepp.osgi.manifest.parser;

import info.tepp.osgi.manifest.Version;
import info.tepp.osgi.manifest.parser.Result.Failure;
import info.tepp.osgi.manifest.parser.Result.Success;

import javax.annotation.Nonnull;

import static info.tepp.osgi.manifest.parser.Token.*;


/**
 * Version specifications are used in several places.
 *
 * A version has the following grammar:
 * <pre>version ::=
 * major( '.' minor ( '.' micro ( '.' qualifier )? )? )?
 * major ::= number // See 1.3.2 minor ::=number
 * micro ::=number
 * qualifier ::= ( alphanum | ’_’ | '-' )+</pre>
 *
 * <p>A version must not contain any white space. The default value for a version is {@value 0.0.0.}</p>
 */
public class VersionParser extends Parser<Version> {

    @Nonnull
    @Override
    public Result<Version> parse(CharSequence input) {

        // Major version
        Result<Version.Major> majorResult = MajorParser.parse(input);
        if (majorResult instanceof Failure)
            return ((Failure) majorResult).asResult();

        Success<Version.Major> major = Success.of(majorResult);
        if (major.rest.length() == 0)
            return Success.of(Version.valueOf(major.value), major.rest);


        // Minor version
        Result<Version.Minor> minorResult = MinorParser.parse(major.rest);
        if (minorResult instanceof Failure) // So, we don't have minor version...
            return Success.of(Version.valueOf(major.value), major.rest);

        Success<Version.Minor> minor = Success.of(minorResult);
        if (minor.rest.length() == 0)
            return Success.of(Version.valueOf(major.value, minor.value), minor.rest);


        // Micro version
        Result<Version.Micro> microResult = MicroParser.parse(minor.rest);
        if (microResult instanceof Failure) // So, we don't have micro version...
            return Success.of(Version.valueOf(major.value, minor.value), minor.rest);

        Success<Version.Micro> micro = Success.of(microResult);
        if (micro.rest.length() == 0)
            return Success.of(Version.valueOf(major.value, minor.value, micro.value), micro.rest);


        // Qualifier version
        Result<Version.Qualifier> qualifierResult = QualifierParser.parse(micro.rest);
        if (qualifierResult instanceof Failure)
            return Success.of(Version.valueOf(major.value, minor.value, micro.value), micro.rest);

        Success<Version.Qualifier> qualifier = Success.of(qualifierResult);
        return Success.of(Version.valueOf(major.value, minor.value, micro.value, qualifier.value), qualifier.rest);
    }

    public static final Parser<Version.Major> MajorParser = Token.NUMBER.as(Version.Major.class);
    public static final Parser<Version.Minor> MinorParser = Right(Char('.').then(NUMBER.as(Version.Minor.class)));
    public static final Parser<Version.Micro> MicroParser = Right(Char('.').then(NUMBER.as(Version.Micro.class)));
    public static final Parser<Version.Qualifier> QualifierParser = Right(Char('.').then(QUALIFIER.as(Version.Qualifier.class)));

}
