package info.tepp.osgi.manifest.parser;

import info.tepp.osgi.manifest.parser.Result.Failure;
import info.tepp.osgi.manifest.parser.Result.Success;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

@RunWith(Theories.class)
public class TokenTokenTest {

    private Token parser = Token.TOKEN;

    @DataPoints
    public static String[] randomTokens() {
        String tokenChars = tokenChars();
        int length = tokenChars.length();

        Random rnd = new Random();
        List<String> tokens = new ArrayList<String>();
        for (int i = 0; i < tokenChars.length(); i++) {
            StringBuilder sb = new StringBuilder(String.valueOf(tokenChars.charAt(i)));
            tokens.add(sb.toString());

            int ln = rnd.nextInt(length);
            for (int j = 0; j<ln; j++) {
                sb.append(tokenChars.charAt(rnd.nextInt(length)));
            }
            tokens.add(sb.toString());
        }
        return tokens.toArray(new String[tokens.size()]);
    }

    private static String tokenChars() {
        StringBuilder sb = new StringBuilder("_-");
        String[] alphanums = AlphaNumTokenTest.alphanums();
        for (String ch : alphanums) {
            sb.append(ch);
        }
        return sb.toString();
    }


    @Theory
    public void parsesRandomTokens(String token) {
        assertEquals(Success.of(token, ""), parser.parse(token));
    }

    @Theory
    public void parsesRandomTokenOnly(String token) {
        assertEquals(Success.of(token, " something else"),
                     parser.parse(token + " something else"));
    }

    @Theory
    public void failsIfFirstCharIsWrong(String token) {
        assertEquals(Failure.of("['0'..'9'] | ['a'..'z'] | ['A'..'Z'] | '_' | '-' expected, but got ' '"),
                     parser.parse(" " + token));
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
        assertEquals(Failure.of("['0'..'9'] | ['a'..'z'] | ['A'..'Z'] | '_' | '-' expected, but got ','"),
                     parser.parse(","));
    }

}