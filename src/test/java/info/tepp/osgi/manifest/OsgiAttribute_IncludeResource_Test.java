package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Include_Resource;

public class OsgiAttribute_IncludeResource_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Include-Resource", "test");
        Assert.assertEquals("test", attributes.getValue(Include_Resource));
    }

    @Test
    public void can_putValue() {
        attributes.put(Include_Resource, "test");
        Assert.assertEquals("test", attributes.getValue("Include-Resource"));
    }

    @Ignore @Test
    public void can_getIncludeResource() {
        //attributes.put(Include_Resource, "test");
        //Assert.assertEquals("test", attributes.getIncludeResource());
    }

    @Ignore @Test
    public void can_setIncludeResource() {
        //attributes.setIncludeResource("test");
        //Assert.assertEquals("test", attributes.getValue(Include_Resource));
    }
}