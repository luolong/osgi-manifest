package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_Localization;

public class OsgiAttribute_BundleLocalization_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-Localization", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_Localization));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_Localization, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-Localization"));
    }

    @Ignore @Test
    public void can_getBundleLocalization() {
        //attributes.put(Bundle_Localization, "test");
        //Assert.assertEquals("test", attributes.getBundleLocalization());
    }

    @Ignore @Test
    public void can_setBundleLocalization() {
        //attributes.setBundleLocalization("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_Localization));
    }
}