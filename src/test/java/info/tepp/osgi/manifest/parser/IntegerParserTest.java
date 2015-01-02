package info.tepp.osgi.manifest.parser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntegerParserTest {

    @Test
    public void returnsFailureOnNullInput(){
        assertEquals(Result.Failure.of("null"), Parser.NUMBER.parse(null));
    }

    @Test
    public void returnsFailureOnEmptyInput(){
        assertEquals(Result.Failure.of("empty"), Parser.NUMBER.parse(""));
    }

    @Test
    public void returnsFailureOnSingleMinus(){
        assertEquals(Result.Failure.of("NaN: -"), Parser.NUMBER.parse("-"));
    }

    @Test
    public void returnsFailureForNegativeInteger(){
        assertEquals(Result.Failure.of("NaN: -"), Parser.NUMBER.parse("-1"));
    }

    @Test
    public void returnsFailureOnMinusFollowedByNonNumberChar(){
        assertEquals(Result.Failure.of("NaN: -"), Parser.NUMBER.parse("-z"));
    }

    @Test
    public void returnsFailureOnNonNumberChar(){
        assertEquals(Result.Failure.of("NaN: !"), Parser.NUMBER.parse("!"));
    }

    @Test
    public void returnsFailureOnSamllLetterZ(){
        assertEquals(Result.Failure.of("NaN: z"), Parser.NUMBER.parse("z"));
    }

    @Test
    public void parsesSingleDigitInteger(){
        assertEquals(Result.Success.of(1, ""), Parser.NUMBER.parse("1"));
    }

    @Test
    public void parsesSingleDigitFollowedByMinus(){
        assertEquals(Result.Success.of(1, "-"), Parser.NUMBER.parse("1-"));
    }

    @Test
    public void parsesMaxIntegerValue(){
        assertEquals(Result.Success.of(2147483647, "-123"), Parser.NUMBER.parse("2147483647-123"));
    }

    @Test
    public void parsesMaxIntegerValue2(){
        assertEquals(Result.Success.of(2147483647, ".123"), Parser.NUMBER.parse("2147483647.123"));
    }

    @Test
    public void returnsFailureOnMaxIntPlusOne(){
        assertEquals(Result.Failure.of("Number too large: 2147483648"), Parser.NUMBER.parse("2147483648"));
    }

    @Test
    public void returnsFailureOnNumberTooLarge(){
        assertEquals(Result.Failure.of("Number too large: 21474836471"), Parser.NUMBER.parse("21474836471"));
    }

}