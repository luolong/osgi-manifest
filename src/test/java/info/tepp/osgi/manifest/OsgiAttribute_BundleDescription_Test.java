package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_Description;

public class OsgiAttribute_BundleDescription_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-Description", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_Description));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_Description, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-Description"));
    }

    @Ignore @Test
    public void can_getBundleDescription() {
        //attributes.put(Bundle_Description, "test");
        //Assert.assertEquals("test", attributes.getBundleDescription());
    }

    @Ignore @Test
    public void can_setBundleDescription() {
        //attributes.setBundleDescription("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_Description));
    }
}