package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Eclipse_PlatformFilter;

public class OsgiAttribute_EclipsePlatformFilter_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Eclipse-PlatformFilter", "test");
        Assert.assertEquals("test", attributes.getValue(Eclipse_PlatformFilter));
    }

    @Test
    public void can_putValue() {
        attributes.put(Eclipse_PlatformFilter, "test");
        Assert.assertEquals("test", attributes.getValue("Eclipse-PlatformFilter"));
    }

    @Ignore @Test
    public void can_getEclipsePlatformFilter() {
        //attributes.put(Eclipse_PlatformFilter, "test");
        //Assert.assertEquals("test", attributes.getEclipsePlatformFilter());
    }

    @Ignore @Test
    public void can_setEclipsePlatformFilter() {
        //attributes.setEclipsePlatformFilter("test");
        //Assert.assertEquals("test", attributes.getValue(Eclipse_PlatformFilter));
    }
}