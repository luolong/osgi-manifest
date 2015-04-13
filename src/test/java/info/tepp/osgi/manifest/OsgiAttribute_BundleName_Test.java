package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_Name;

public class OsgiAttribute_BundleName_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-Name", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_Name));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_Name, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-Name"));
    }

    @Ignore @Test
    public void can_getBundleName() {
        //attributes.put(Bundle_Name, "test");
        //Assert.assertEquals("test", attributes.getBundleName());
    }

    @Ignore @Test
    public void can_setBundleName() {
        //attributes.setBundleName("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_Name));
    }
}