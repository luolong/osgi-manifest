package info.tepp.osgi.manifest.parser;

import info.tepp.osgi.manifest.parser.Result.Failure;
import info.tepp.osgi.manifest.parser.Result.Success;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SequenceParserTest {

    Parser.TupleSequenceParser<String, Integer> parser = Token.Char('.').then(Token.NUMBER.as(Integer.class));

    @Test
    public void successfullyParsesNumberAndDot(){
        assertEquals(Success.of(".", 12, ".3.4.5"), parser.parse(".12.3.4.5"));
    }

    @Test
    public void failsIfFirstParserFails(){
        assertEquals(Failure.of("'.' expected, but got '1'"), parser.parse("12.3.4.5"));
    }

    @Test
    public void failsIfSecondParserFails(){
        assertEquals(Failure.of("['0'..'9'] expected, but got '.'"), parser.parse("..12.3.4.5"));
    }

}