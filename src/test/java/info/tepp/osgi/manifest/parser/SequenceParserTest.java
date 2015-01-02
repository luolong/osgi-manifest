package info.tepp.osgi.manifest.parser;

import info.tepp.osgi.manifest.parser.Result.Failure;
import info.tepp.osgi.manifest.parser.Result.Success;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SequenceParserTest {

    @Test
    public void successfullyParsesNumberAndDot(){
        Parser.TupleSequenceParser<Character, Integer> parser = Parser.DOT.then(Parser.NUMBER);
        assertEquals(Success.of('.', 12, ".3.4.5"), parser.parse(".12.3.4.5"));
    }

    @Test
    public void failsIfFirstParserFails(){
        Parser.TupleSequenceParser<Character, Integer> parser = Parser.DOT.then(Parser.NUMBER);
        assertEquals(Failure.of("Expexted '.', but got '1'"), parser.parse("12.3.4.5"));
    }

    @Test
    public void failsIfSecondParserFails(){
        Parser.TupleSequenceParser<Character, Integer> parser = Parser.DOT.then(Parser.NUMBER);
        assertEquals(Failure.of("NaN: ."), parser.parse("..12.3.4.5"));
    }

}