package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_Icon;

public class OsgiAttribute_BundleIcon_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-Icon", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_Icon));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_Icon, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-Icon"));
    }

    @Ignore @Test
    public void can_getBundleIcon() {
        //attributes.put(Bundle_Icon, "test");
        //Assert.assertEquals("test", attributes.getBundleIcon());
    }

    @Ignore @Test
    public void can_setBundleIcon() {
        //attributes.setBundleIcon("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_Icon));
    }
}