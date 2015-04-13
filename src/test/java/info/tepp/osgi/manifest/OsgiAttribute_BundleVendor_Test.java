package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_Vendor;

public class OsgiAttribute_BundleVendor_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-Vendor", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_Vendor));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_Vendor, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-Vendor"));
    }

    @Ignore @Test
    public void can_getBundleVendor() {
        //attributes.put(Bundle_Vendor, "test");
        //Assert.assertEquals("test", attributes.getBundleVendor());
    }

    @Ignore @Test
    public void can_setBundleVendor() {
        //attributes.setBundleVendor("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_Vendor));
    }
}