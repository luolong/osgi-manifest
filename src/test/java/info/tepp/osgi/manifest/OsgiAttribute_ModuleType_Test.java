package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Module_Type;

public class OsgiAttribute_ModuleType_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Module-Type", "test");
        Assert.assertEquals("test", attributes.getValue(Module_Type));
    }

    @Test
    public void can_putValue() {
        attributes.put(Module_Type, "test");
        Assert.assertEquals("test", attributes.getValue("Module-Type"));
    }

    @Ignore @Test
    public void can_getModuleType() {
        //attributes.put(Module_Type, "test");
        //Assert.assertEquals("test", attributes.getModuleType());
    }

    @Ignore @Test
    public void can_setModuleType() {
        //attributes.setModuleType("test");
        //Assert.assertEquals("test", attributes.getValue(Module_Type));
    }
}