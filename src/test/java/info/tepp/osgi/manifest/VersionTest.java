package info.tepp.osgi.manifest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class VersionTest {

    @Test
    public void versionsAreEqual() {
        assertEquals(Version.valueOf(1, 2, 3), new Version(1, 2, 3, ""));
    }

    @Test
    public void versionsAreNotEqual() {
        assertNotEquals(Version.valueOf(1, 2, 3), new Version(1, 2, 3, "q"));
    }

    @Test
    public void compareTo_equal() {
        assertEquals(0, new Version(1, 2, 3, "4").compareTo(new Version(1, 2, 3, "4")));
    }

}