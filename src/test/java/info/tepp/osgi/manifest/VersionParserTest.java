package info.tepp.osgi.manifest;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VersionParserTest {

    @Test
    public void qualifierIsNeverNull() {
        assertNotNull(new Version(1, 0, 0, null).qualifier);
    }

    @Test
    public void nullQualifierIsEmpty() {
        assertEquals("", new Version(1, 0, 0, null).qualifier);
    }

    @Test
    public void itCanParseSingleDigitVersion() throws ParseException {
        assertEquals(new Version(1, 0, 0, ""), Version.parseVersion("1"));
    }

    @Test
    public void itCanParseMajorAndMinorVersion() throws ParseException {
        assertEquals(new Version(1, 1, 0, ""), Version.parseVersion("1.1"));
    }

    @Test
    public void itCanParseMajorMinorAndMicroVersion() throws ParseException {
        assertEquals(new Version(1, 1, 1, ""), Version.parseVersion("1.1.1"));
    }

    @Test
    public void itCanParseFullVersionWithQualifier() throws ParseException {
        assertEquals(new Version(1, 1, 1, "qualifier"), Version.parseVersion("1.1.1.qualifier"));
    }

}