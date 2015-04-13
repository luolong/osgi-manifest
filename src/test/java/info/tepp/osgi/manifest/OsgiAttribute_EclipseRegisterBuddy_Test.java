package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Eclipse_RegisterBuddy;

public class OsgiAttribute_EclipseRegisterBuddy_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Eclipse-RegisterBuddy", "test");
        Assert.assertEquals("test", attributes.getValue(Eclipse_RegisterBuddy));
    }

    @Test
    public void can_putValue() {
        attributes.put(Eclipse_RegisterBuddy, "test");
        Assert.assertEquals("test", attributes.getValue("Eclipse-RegisterBuddy"));
    }

    @Ignore @Test
    public void can_getEclipseRegisterBuddy() {
        //attributes.put(Eclipse_RegisterBuddy, "test");
        //Assert.assertEquals("test", attributes.getEclipseRegisterBuddy());
    }

    @Ignore @Test
    public void can_setEclipseRegisterBuddy() {
        //attributes.setEclipseRegisterBuddy("test");
        //Assert.assertEquals("test", attributes.getValue(Eclipse_RegisterBuddy));
    }
}