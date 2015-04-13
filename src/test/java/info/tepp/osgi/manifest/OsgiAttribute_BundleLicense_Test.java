package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_License;

public class OsgiAttribute_BundleLicense_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-License", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_License));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_License, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-License"));
    }

    @Ignore @Test
    public void can_getBundleLicense() {
        //attributes.put(Bundle_License, "test");
        //Assert.assertEquals("test", attributes.getBundleLicense());
    }

    @Ignore @Test
    public void can_setBundleLicense() {
        //attributes.setBundleLicense("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_License));
    }
}