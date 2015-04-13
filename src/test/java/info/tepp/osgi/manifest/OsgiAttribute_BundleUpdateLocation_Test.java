package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_UpdateLocation;

public class OsgiAttribute_BundleUpdateLocation_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-UpdateLocation", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_UpdateLocation));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_UpdateLocation, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-UpdateLocation"));
    }

    @Ignore @Test
    public void can_getBundleUpdateLocation() {
        //attributes.put(Bundle_UpdateLocation, "test");
        //Assert.assertEquals("test", attributes.getBundleUpdateLocation());
    }

    @Ignore @Test
    public void can_setBundleUpdateLocation() {
        //attributes.setBundleUpdateLocation("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_UpdateLocation));
    }
}