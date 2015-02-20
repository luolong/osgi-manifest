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
public class QualifierTokenTest {

    private Token parser = Token.QUALIFIER;

    @DataPoints
    public static String[] randomQualifiers() {
        String qualifierChars = qualifierChars();
        int length = qualifierChars.length();

        Random rnd = new Random();
        List<String> qualifiers = new ArrayList<String>();
        for (int i = 0; i < qualifierChars.length(); i++) {
            StringBuilder sb = new StringBuilder(String.valueOf(qualifierChars.charAt(i)));
            qualifiers.add(sb.toString());

            int ln = rnd.nextInt(length);
            for (int j = 0; j<ln; j++) {
                sb.append(qualifierChars.charAt(rnd.nextInt(length)));
            }
            qualifiers.add(sb.toString());
        }
        return qualifiers.toArray(new String[qualifiers.size()]);
    }

    private static String qualifierChars() {
        StringBuilder sb = new StringBuilder("_-");
        String[] alphanums = AlphaNumTokenTest.alphanums();
        for (String ch : alphanums) {
            sb.append(ch);
        }
        return sb.toString();
    }


    @Theory
    public void parsesRandomQualifiers(String qualifier) {
        assertEquals(Success.of(qualifier, ""), parser.parse(qualifier));
    }

    @Theory
    public void parsesRandomQualifierOnly(String qualifier) {
        assertEquals(Success.of(qualifier, " something else"),
                     parser.parse(qualifier + " something else"));
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