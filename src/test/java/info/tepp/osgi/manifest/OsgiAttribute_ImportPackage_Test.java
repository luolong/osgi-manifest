package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Import_Package;

public class OsgiAttribute_ImportPackage_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Import-Package", "test");
        Assert.assertEquals("test", attributes.getValue(Import_Package));
    }

    @Test
    public void can_putValue() {
        attributes.put(Import_Package, "test");
        Assert.assertEquals("test", attributes.getValue("Import-Package"));
    }

    @Ignore @Test
    public void can_getImportPackage() {
        //attributes.put(Import_Package, "test");
        //Assert.assertEquals("test", attributes.getImportPackage());
    }

    @Ignore @Test
    public void can_setImportPackage() {
        //attributes.setImportPackage("test");
        //Assert.assertEquals("test", attributes.getValue(Import_Package));
    }
}