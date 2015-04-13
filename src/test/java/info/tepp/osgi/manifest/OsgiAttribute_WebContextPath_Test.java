package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Web_ContextPath;

public class OsgiAttribute_WebContextPath_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Web-ContextPath", "test");
        Assert.assertEquals("test", attributes.getValue(Web_ContextPath));
    }

    @Test
    public void can_putValue() {
        attributes.put(Web_ContextPath, "test");
        Assert.assertEquals("test", attributes.getValue("Web-ContextPath"));
    }

    @Ignore @Test
    public void can_getWebContextPath() {
        //attributes.put(Web_ContextPath, "test");
        //Assert.assertEquals("test", attributes.getWebContextPath());
    }

    @Ignore @Test
    public void can_setWebContextPath() {
        //attributes.setWebContextPath("test");
        //Assert.assertEquals("test", attributes.getValue(Web_ContextPath));
    }
}