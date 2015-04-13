package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Require_Bundle;

public class OsgiAttribute_RequireBundle_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Require-Bundle", "test");
        Assert.assertEquals("test", attributes.getValue(Require_Bundle));
    }

    @Test
    public void can_putValue() {
        attributes.put(Require_Bundle, "test");
        Assert.assertEquals("test", attributes.getValue("Require-Bundle"));
    }

    @Ignore @Test
    public void can_getRequireBundle() {
        //attributes.put(Require_Bundle, "test");
        //Assert.assertEquals("test", attributes.getRequireBundle());
    }

    @Ignore @Test
    public void can_setRequireBundle() {
        //attributes.setRequireBundle("test");
        //Assert.assertEquals("test", attributes.getValue(Require_Bundle));
    }
}