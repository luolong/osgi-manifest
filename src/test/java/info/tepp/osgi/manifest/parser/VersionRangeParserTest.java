package info.tepp.osgi.manifest.parser;

import info.tepp.osgi.manifest.Version;
import info.tepp.osgi.manifest.VersionRange;
import info.tepp.osgi.manifest.parser.Result.Failure;
import info.tepp.osgi.manifest.parser.Result.Success;
import org.junit.Test;

import static info.tepp.osgi.manifest.VersionRange.Exclusive;
import static info.tepp.osgi.manifest.VersionRange.Inclusive;
import static org.junit.Assert.assertEquals;

@SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
public class VersionRangeParserTest {

    @Test
    public void nullInputReturnsFailure() {
        assertEquals(Failure.of("null"), new VersionRangeParser().parse(null));
    }

    @Test
    public void emptyInputReturnsFailure() {
        assertEquals(Failure.of("empty"), new VersionRangeParser().parse(""));
    }

    @Test
    public void parsesAtLeastVersion() {
        assertEquals(Success.of(
                VersionRange.atLeast(Version.valueOf(1, 2, 3)), ","),
                new VersionRangeParser().parse("1.2.3,"));
    }

    @Test
    public void parsesVersionRange() {
        assertEquals(Success.of(
                new VersionRange(Inclusive(Version.valueOf(1, 2)),
                                 Exclusive(Version.valueOf(2))), ""),
                new VersionRangeParser().parse("[1.2,2)"));
    }

    @Test
    public void failsToParseInvalidVersionRange() {
        assertEquals(
                Failure.of("Version range separator expected, but got ']'!"),
                new VersionRangeParser().parse("[1.2]"));
    }

    @Test
    public void failsToParseInvalidVersionRange1() {
        assertEquals(
                Failure.of("Version range end expected, but got '('!"),
                new VersionRangeParser().parse("[1.2,2("));
    }

    @Test
    public void failsToParseInvalidVersionRange2() {
        assertEquals(
                Failure.of("['0'..'9'] expected, but got ','"),
                new VersionRangeParser().parse("[,2)"));
    }

    @Test
    public void failsToParseInvalidVersionRange3() {
        assertEquals(
                Failure.of("Version Range expected, but got ']'"),
                new VersionRangeParser().parse("]1.2]"));
    }

}