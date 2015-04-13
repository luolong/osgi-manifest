package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Web_DispatcherServletUrlPatterns;

public class OsgiAttribute_WebDispatcherServletUrlPatterns_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Web-DispatcherServletUrlPatterns", "test");
        Assert.assertEquals("test", attributes.getValue(Web_DispatcherServletUrlPatterns));
    }

    @Test
    public void can_putValue() {
        attributes.put(Web_DispatcherServletUrlPatterns, "test");
        Assert.assertEquals("test", attributes.getValue("Web-DispatcherServletUrlPatterns"));
    }

    @Ignore @Test
    public void can_getWebDispatcherServletUrlPatterns() {
        //attributes.put(Web_DispatcherServletUrlPatterns, "test");
        //Assert.assertEquals("test", attributes.getWebDispatcherServletUrlPatterns());
    }

    @Ignore @Test
    public void can_setWebDispatcherServletUrlPatterns() {
        //attributes.setWebDispatcherServletUrlPatterns("test");
        //Assert.assertEquals("test", attributes.getValue(Web_DispatcherServletUrlPatterns));
    }
}