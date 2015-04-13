package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_Category;

public class OsgiAttribute_BundleCategory_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-Category", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_Category));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_Category, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-Category"));
    }

    @Ignore @Test
    public void can_getBundleCategory() {
        //attributes.put(Bundle_Category, "test");
        //Assert.assertEquals("test", attributes.getBundleCategory());
    }

    @Ignore @Test
    public void can_setBundleCategory() {
        //attributes.setBundleCategory("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_Category));
    }
}