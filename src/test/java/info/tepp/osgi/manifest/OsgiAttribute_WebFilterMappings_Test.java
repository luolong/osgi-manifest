package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Web_FilterMappings;

public class OsgiAttribute_WebFilterMappings_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Web-FilterMappings", "test");
        Assert.assertEquals("test", attributes.getValue(Web_FilterMappings));
    }

    @Test
    public void can_putValue() {
        attributes.put(Web_FilterMappings, "test");
        Assert.assertEquals("test", attributes.getValue("Web-FilterMappings"));
    }

    @Ignore @Test
    public void can_getWebFilterMappings() {
        //attributes.put(Web_FilterMappings, "test");
        //Assert.assertEquals("test", attributes.getWebFilterMappings());
    }

    @Ignore @Test
    public void can_setWebFilterMappings() {
        //attributes.setWebFilterMappings("test");
        //Assert.assertEquals("test", attributes.getValue(Web_FilterMappings));
    }
}