package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_Version;

public class OsgiAttribute_BundleVersion_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-Version", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_Version));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_Version, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-Version"));
    }

    @Ignore @Test
    public void can_getBundleVersion() {
        //attributes.put(Bundle_Version, "test");
        //Assert.assertEquals("test", attributes.getBundleVersion());
    }

    @Ignore @Test
    public void can_setBundleVersion() {
        //attributes.setBundleVersion("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_Version));
    }
}