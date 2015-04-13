package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Require_Capability;

public class OsgiAttribute_RequireCapability_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Require-Capability", "test");
        Assert.assertEquals("test", attributes.getValue(Require_Capability));
    }

    @Test
    public void can_putValue() {
        attributes.put(Require_Capability, "test");
        Assert.assertEquals("test", attributes.getValue("Require-Capability"));
    }

    @Ignore @Test
    public void can_getRequireCapability() {
        //attributes.put(Require_Capability, "test");
        //Assert.assertEquals("test", attributes.getRequireCapability());
    }

    @Ignore @Test
    public void can_setRequireCapability() {
        //attributes.setRequireCapability("test");
        //Assert.assertEquals("test", attributes.getValue(Require_Capability));
    }
}