package info.tepp.osgi.manifest.parser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RestParserTest {

    @Test
    public void parsesEntireInput() {
        Parser<String> parser = Parser.REST;
        assertEquals(Result.Success.of("all of the input", ""), parser.parse("all of the input"));
    }
}
