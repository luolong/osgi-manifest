package info.tepp.osgi.manifest;

import org.junit.Test;

import static info.tepp.osgi.manifest.VersionRange.Exclusive;
import static info.tepp.osgi.manifest.VersionRange.Inclusive;
import static org.junit.Assert.*;

public class VersionRangeTest {

    private Version min = Version.parseVersion("1.0");
    private Version max = Version.parseVersion("2.0");

    @Test
    public void atleastVersionRange_toString() {
        assertEquals(min.toString(), VersionRange.atLeast(min).toString());
    }

    @Test
    public void atleastVersionRange_containsMinVersion() {
        assertTrue(VersionRange.atLeast(min).contains(min));
    }

    @Test
    public void atleastVersionRange_containsMaxVersion() {
        assertTrue(VersionRange.atLeast(min).contains(max));
    }

    @Test
    public void atleastVersionRange_DoesNotContainVersionLessThanMin() {
        assertFalse(VersionRange.atLeast(min).contains(Version.valueOf(0, 9)));
    }

    @Test
    public void inclusiveExclusiveVersionRange_toString() {
        assertEquals("[1.0,2.0)", new VersionRange(Inclusive(min), Exclusive(max)).toString());
    }

    @Test
    public void inclusiveExclusiveVersionRange_containsMinVersion() {
        assertTrue(new VersionRange(Inclusive(min), Exclusive(max)).contains(min));
    }

    @Test
    public void inclusiveExclusiveVersionRange_doesNotContainMaxVersion() {
        assertFalse(new VersionRange(Inclusive(min), Exclusive(max)).contains(max));
    }

    @Test
    public void exclusiveInclusiveVersionRange_toString() {
        assertEquals("(1.0,2.0]", new VersionRange(Exclusive(min), Inclusive(max)).toString());
    }

    @Test
    public void exclusiveInclusiveVersionRange_doesNotContainMinVersion() {
        assertFalse(new VersionRange(Exclusive(min), Inclusive(max)).contains(min));
    }

    @Test
    public void exclusiveInclusiveVersionRange_containsMaxVersion() {
        assertTrue(new VersionRange(Exclusive(min), Inclusive(max)).contains(max));
    }

}