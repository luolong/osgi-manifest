package info.tepp.osgi.manifest.parser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CharacterParserTest {

    Parser.CharacterParser parser = new Parser.CharacterParser('.');

    @Test
    public void returnsFailureOnNullInput() {
        assertEquals(Result.Failure.of("null"), parser.parse(null));
    }

    @Test
    public void returnsFailureOnEmptyInput() {
        assertEquals(Result.Failure.of("empty"), parser.parse(""));
    }

    @Test
    public void returnsFailureOnUnexpectedCharacter() {
        assertEquals(Result.Failure.of("Expexted '.', but got 'c'"), parser.parse("c"));
    }

    @Test
    public void succesfullyConsumesCharacter() {
        assertEquals(Result.Success.of('.', ".."), parser.parse("..."));
    }

}