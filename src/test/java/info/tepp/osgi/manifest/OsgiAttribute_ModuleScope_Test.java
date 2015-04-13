package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Module_Scope;

public class OsgiAttribute_ModuleScope_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Module-Scope", "test");
        Assert.assertEquals("test", attributes.getValue(Module_Scope));
    }

    @Test
    public void can_putValue() {
        attributes.put(Module_Scope, "test");
        Assert.assertEquals("test", attributes.getValue("Module-Scope"));
    }

    @Ignore @Test
    public void can_getModuleScope() {
        //attributes.put(Module_Scope, "test");
        //Assert.assertEquals("test", attributes.getModuleScope());
    }

    @Ignore @Test
    public void can_setModuleScope() {
        //attributes.setModuleScope("test");
        //Assert.assertEquals("test", attributes.getValue(Module_Scope));
    }
}