package info.tepp.osgi.manifest;

import org.junit.Ignore;
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
        assertEquals(new Version(1, 2, 0, ""), Version.parseVersion("1.2"));
    }

    @Test
    public void itCanParseMajorMinorAndMicroVersion() throws ParseException {
        assertEquals(new Version(1, 2, 3, ""), Version.parseVersion("1.2.3"));
    }

    @Test
    public void itCanParseFullVersionWithQualifier() throws ParseException {
        assertEquals(new Version(1, 2, 3, "qualifier"), Version.parseVersion("1.2.3.qualifier"));
    }

    @Test
    public void itFailsToParseQualifierWithoutMicro() throws ParseException {
        assertEquals(new Version(1, 2, 3, "qualifier"), Version.parseVersion("1.2.qualifier"));
    }

    @Test
    @Ignore
    public void itFailsToParseQualifierWithoutMinor() throws ParseException {
        assertEquals(new Version(1, 2, 3, "qualifier"), Version.parseVersion("1.qualifier"));
    }

}