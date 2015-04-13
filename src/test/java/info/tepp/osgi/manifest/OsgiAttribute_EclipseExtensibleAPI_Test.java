package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Eclipse_ExtensibleAPI;

public class OsgiAttribute_EclipseExtensibleAPI_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Eclipse-ExtensibleAPI", "test");
        Assert.assertEquals("test", attributes.getValue(Eclipse_ExtensibleAPI));
    }

    @Test
    public void can_putValue() {
        attributes.put(Eclipse_ExtensibleAPI, "test");
        Assert.assertEquals("test", attributes.getValue("Eclipse-ExtensibleAPI"));
    }

    @Ignore @Test
    public void can_getEclipseExtensibleAPI() {
        //attributes.put(Eclipse_ExtensibleAPI, "test");
        //Assert.assertEquals("test", attributes.getEclipseExtensibleAPI());
    }

    @Ignore @Test
    public void can_setEclipseExtensibleAPI() {
        //attributes.setEclipseExtensibleAPI("test");
        //Assert.assertEquals("test", attributes.getValue(Eclipse_ExtensibleAPI));
    }
}