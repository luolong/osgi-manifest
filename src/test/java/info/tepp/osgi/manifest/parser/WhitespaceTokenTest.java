package info.tepp.osgi.manifest.parser;

import info.tepp.osgi.manifest.parser.Result.Failure;
import info.tepp.osgi.manifest.parser.Result.Success;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(Theories.class)
public class WhitespaceTokenTest {

    @DataPoints
    public static
    String[] whitespaceChars = {
            "\t",     // U+0009 HORIZONTAL TABULATION
            "\n",     // U+000A LINE FEED
            "\u000B", // U+000B VERTICAL TABULATION
            "\f",     // U+000C FORM FEED
            "\r",     // U+000D CARRIAGE RETURN
            "\u001C", // U+001C FILE SEPARATOR
            "\u001D", // U+001D GROUP SEPARATOR
            "\u001E", // U+001E RECORD SEPARATOR
            "\u001F"  // U+001F UNIT SEPARATOR
    };
    private Token.Char parser = Token.WS;

    @Theory
    public void parsesAnySingleWhitespaceChar(String input) {
        parser = Token.WS;
        assertEquals(Success.of(input, "\t"), parser.parse(input + "\t"));
    }

    @Test
    public void failsOnNullInput() {
        assertEquals(Failure.of("null"), parser.parse(null));
    }

    @Test
    public void failsOnEmptyInput() {
        assertEquals(Failure.of("empty"), parser.parse(""));
    }

    @Test
    public void failsOnUnexpectedInput() {
        assertEquals(Failure.of("Whitespace expected, but got ','"),
                parser.parse(",\t"));
    }

}