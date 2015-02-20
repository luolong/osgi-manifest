package info.tepp.osgi.manifest.parser;

import info.tepp.osgi.manifest.parser.Result.Failure;
import info.tepp.osgi.manifest.parser.Result.Success;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DigitTokenTest {

    @Test
    public void parsesSingleDigitChar() {
        assertEquals(Success.of("1", "2345"), Token.DIGIT.parse("12345"));
    }

    @Test
    public void failsOnNullInput() {
        assertEquals(Failure.of("null"), Token.DIGIT.parse(null));
    }

    @Test
    public void failsOnEmptyInput() {
        assertEquals(Failure.of("empty"), Token.DIGIT.parse(""));
    }

    @Test
    public void failsOnUnexpectedInput() {
        assertEquals(Failure.of("['0'..'9'] expected, but got ','"), Token.DIGIT.parse(","));
    }

}