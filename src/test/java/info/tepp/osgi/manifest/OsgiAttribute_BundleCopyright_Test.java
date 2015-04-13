package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_Copyright;

public class OsgiAttribute_BundleCopyright_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-Copyright", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_Copyright));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_Copyright, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-Copyright"));
    }

    @Ignore @Test
    public void can_getBundleCopyright() {
        //attributes.put(Bundle_Copyright, "test");
        //Assert.assertEquals("test", attributes.getBundleCopyright());
    }

    @Ignore @Test
    public void can_setBundleCopyright() {
        //attributes.setBundleCopyright("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_Copyright));
    }
}