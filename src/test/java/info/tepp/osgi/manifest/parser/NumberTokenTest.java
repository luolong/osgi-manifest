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
public class NumberTokenTest {

    private Token parser = Token.NUMBER;

    @DataPoints
    public static String[] randomNumbers() {
        String numberChars = "0123456789";
        int length = 10;

        Random rnd = new Random();
        List<String> numbers = new ArrayList<String>();
        for (int i = 0; i < numberChars.length(); i++) {
            StringBuilder sb = new StringBuilder(String.valueOf(numberChars.charAt(i)));
            numbers.add(sb.toString());

            int ln = rnd.nextInt(length);
            for (int j = 0; j<ln; j++) {
                sb.append(numberChars.charAt(rnd.nextInt(length)));
            }
            numbers.add(sb.toString());
        }
        return numbers.toArray(new String[numbers.size()]);
    }

    @Theory
    public void parsesRandomNumbers(String number) {
        assertEquals(Success.of(number, ""), parser.parse(number));
    }

    @Theory
    public void parsesRandomNumberOnly(String token) {
        assertEquals(Success.of(token, ".234"),
                     parser.parse(token + ".234"));
    }

    @Theory
    public void failsIfFirstCharIsWrong(String token) {
        assertEquals(Failure.of("['0'..'9'] expected, but got '.'"),
                     parser.parse("." + token));
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
        assertEquals(Failure.of("['0'..'9'] expected, but got ','"),
                     parser.parse(","));
    }

}