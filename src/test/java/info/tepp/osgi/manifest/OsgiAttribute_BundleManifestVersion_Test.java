package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_ManifestVersion;

public class OsgiAttribute_BundleManifestVersion_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-ManifestVersion", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_ManifestVersion));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_ManifestVersion, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-ManifestVersion"));
    }

    @Ignore @Test
    public void can_getBundleManifestVersion() {
        //attributes.put(Bundle_ManifestVersion, "test");
        //Assert.assertEquals("test", attributes.getBundleManifestVersion());
    }

    @Ignore @Test
    public void can_setBundleManifestVersion() {
        //attributes.setBundleManifestVersion("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_ManifestVersion));
    }
}