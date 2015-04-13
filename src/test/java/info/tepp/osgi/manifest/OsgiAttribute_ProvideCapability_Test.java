package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Provide_Capability;

public class OsgiAttribute_ProvideCapability_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Provide-Capability", "test");
        Assert.assertEquals("test", attributes.getValue(Provide_Capability));
    }

    @Test
    public void can_putValue() {
        attributes.put(Provide_Capability, "test");
        Assert.assertEquals("test", attributes.getValue("Provide-Capability"));
    }

    @Ignore @Test
    public void can_getProvideCapability() {
        //attributes.put(Provide_Capability, "test");
        //Assert.assertEquals("test", attributes.getProvideCapability());
    }

    @Ignore @Test
    public void can_setProvideCapability() {
        //attributes.setProvideCapability("test");
        //Assert.assertEquals("test", attributes.getValue(Provide_Capability));
    }
}