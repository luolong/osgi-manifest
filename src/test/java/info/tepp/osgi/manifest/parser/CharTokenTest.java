package info.tepp.osgi.manifest.parser;

import info.tepp.osgi.manifest.parser.Result.Failure;
import info.tepp.osgi.manifest.parser.Result.Success;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CharTokenTest {

    @Test
    public void parsesSingleCharacter() {
        assertEquals(Success.of(".", ".."), new Token.Char('.').parse("..."));
    }

    @Test
    public void failsOnNullInput() {
        assertEquals(Failure.of("null"), new Token.Char('.').parse(null));
    }

    @Test
    public void failsOnEmptyInput() {
        assertEquals(Failure.of("empty"), new Token.Char('.').parse(""));
    }

    @Test
    public void failsOnUnexpectedInput() {
        assertEquals(Failure.of("'.' expected, but got ','"), new Token.Char('.').parse(","));
    }

}