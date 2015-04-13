package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Import_Bundle;

public class OsgiAttribute_ImportBundle_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Import-Bundle", "test");
        Assert.assertEquals("test", attributes.getValue(Import_Bundle));
    }

    @Test
    public void can_putValue() {
        attributes.put(Import_Bundle, "test");
        Assert.assertEquals("test", attributes.getValue("Import-Bundle"));
    }

    @Ignore @Test
    public void can_getImportBundle() {
        //attributes.put(Import_Bundle, "test");
        //Assert.assertEquals("test", attributes.getImportBundle());
    }

    @Ignore @Test
    public void can_setImportBundle() {
        //attributes.setImportBundle("test");
        //Assert.assertEquals("test", attributes.getValue(Import_Bundle));
    }
}