package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_ClassPath;

public class OsgiAttribute_BundleClassPath_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-ClassPath", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_ClassPath));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_ClassPath, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-ClassPath"));
    }

    @Ignore @Test
    public void can_getBundleClassPath() {
        //attributes.put(Bundle_ClassPath, "test");
        //Assert.assertEquals("test", attributes.getBundleClassPath());
    }

    @Ignore @Test
    public void can_setBundleClassPath() {
        //attributes.setBundleClassPath("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_ClassPath));
    }
}