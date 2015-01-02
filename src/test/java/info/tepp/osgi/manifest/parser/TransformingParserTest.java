package info.tepp.osgi.manifest.parser;

import info.tepp.osgi.manifest.parser.Result.Failure;
import info.tepp.osgi.manifest.parser.Result.Success;
import org.junit.Test;

import static info.tepp.osgi.manifest.parser.Parser.DOT;
import static info.tepp.osgi.manifest.parser.Parser.NUMBER;
import static org.junit.Assert.assertEquals;

public class TransformingParserTest {

    @Test
    public void successfullyTransformsSuccessValue(){
        Parser<Integer> parser = DOT.then(NUMBER).as(Tuple.<Character, Integer>Right());
        assertEquals(Success.of(3, ".rest"), parser.parse(".3.rest"));
    }

    @Test
    public void transitivelyFails(){
        Parser<Integer> parser = DOT.then(NUMBER).as(Tuple.<Character, Integer>Right());
        assertEquals(Failure.of("NaN: ."), parser.parse("..rest"));
    }

}
