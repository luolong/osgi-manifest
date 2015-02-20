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
public class AlphaNumTokenTest {

    private Token.Char parser = Token.ALPHANUM;

    @DataPoints
    public static  String[] alphanums() {
        String[] chars = new String['z' - 'a' + 'Z' - 'A' + 12];

        int i = 0;
        for (char ch = 'a'; ch <= 'z'; ch++, i++) {
            chars[i] = String.valueOf(ch);
        }
        for (char ch = 'A'; ch <= 'Z'; ch++, i++) {
            chars[i] = String.valueOf(ch);
        }
        for (char ch = '0'; ch <= '9'; ch++, i++) {
            chars[i] = String.valueOf(ch);
        }

        return chars;
    }

    @Theory
    public void parsesSngleAplhaNumChar(String alpha) {
        assertEquals(Success.of(alpha, "abc"), parser.parse(alpha + "abc"));
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
        assertEquals(Failure.of("['0'..'9'] | ['a'..'z'] | ['A'..'Z'] expected, but got ','"), parser.parse(","));
    }

}