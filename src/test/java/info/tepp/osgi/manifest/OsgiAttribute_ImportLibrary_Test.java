package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Import_Library;

public class OsgiAttribute_ImportLibrary_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Import-Library", "test");
        Assert.assertEquals("test", attributes.getValue(Import_Library));
    }

    @Test
    public void can_putValue() {
        attributes.put(Import_Library, "test");
        Assert.assertEquals("test", attributes.getValue("Import-Library"));
    }

    @Ignore @Test
    public void can_getImportLibrary() {
        //attributes.put(Import_Library, "test");
        //Assert.assertEquals("test", attributes.getImportLibrary());
    }

    @Ignore @Test
    public void can_setImportLibrary() {
        //attributes.setImportLibrary("test");
        //Assert.assertEquals("test", attributes.getValue(Import_Library));
    }
}