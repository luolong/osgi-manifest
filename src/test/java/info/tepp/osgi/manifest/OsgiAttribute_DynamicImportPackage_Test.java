package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.DynamicImport_Package;

public class OsgiAttribute_DynamicImportPackage_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("DynamicImport-Package", "test");
        Assert.assertEquals("test", attributes.getValue(DynamicImport_Package));
    }

    @Test
    public void can_putValue() {
        attributes.put(DynamicImport_Package, "test");
        Assert.assertEquals("test", attributes.getValue("DynamicImport-Package"));
    }

    @Ignore @Test
    public void can_getDynamicImportPackage() {
        //attributes.put(DynamicImport_Package, "test");
        //Assert.assertEquals("test", attributes.getDynamicImportPackage());
    }

    @Ignore @Test
    public void can_setDynamicImportPackage() {
        //attributes.setDynamicImportPackage("test");
        //Assert.assertEquals("test", attributes.getValue(DynamicImport_Package));
    }
}