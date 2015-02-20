package info.tepp.osgi.manifest.parser;

import info.tepp.osgi.manifest.parser.Result.Failure;
import info.tepp.osgi.manifest.parser.Result.Success;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransformingParserTest {

    Parser<Integer> parser = Token.Char('.').then(Token.NUMBER.as(Integer.class)).as(Tuple.<String, Integer>Right());

    @Test
    public void successfullyTransformsSuccessValue(){
        assertEquals(Success.of(3, ".rest"), parser.parse(".3.rest"));
    }

    @Test
    public void transitivelyFails(){
        assertEquals(Failure.of("['0'..'9'] expected, but got '.'"), parser.parse("..rest"));
    }

    @Test
    public void transitivelyFails2(){
        assertEquals(Failure.of("'.' expected, but got '3'"), parser.parse("3.rest"));
    }

}
