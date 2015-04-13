package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_ContactAddress;

public class OsgiAttribute_BundleContactAddress_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-ContactAddress", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_ContactAddress));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_ContactAddress, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-ContactAddress"));
    }

    @Ignore @Test
    public void can_getBundleContactAddress() {
        //attributes.put(Bundle_ContactAddress, "test");
        //Assert.assertEquals("test", attributes.getBundleContactAddress());
    }

    @Ignore @Test
    public void can_setBundleContactAddress() {
        //attributes.setBundleContactAddress("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_ContactAddress));
    }
}