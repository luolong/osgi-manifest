package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_Activator;

public class OsgiAttribute_BundleActivator_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-Activator", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_Activator));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_Activator, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-Activator"));
    }

    @Ignore @Test
    public void can_getBundleActivator() {
        //attributes.put(Bundle_Activator, "test");
        //Assert.assertEquals("test", attributes.getBundleActivator());
    }

    @Ignore @Test
    public void can_setBundleActivator() {
        //attributes.setBundleActivator("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_Activator));
    }
}