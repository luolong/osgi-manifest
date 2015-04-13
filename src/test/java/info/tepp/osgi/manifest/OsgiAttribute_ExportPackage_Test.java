package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Export_Package;

public class OsgiAttribute_ExportPackage_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Export-Package", "test");
        Assert.assertEquals("test", attributes.getValue(Export_Package));
    }

    @Test
    public void can_putValue() {
        attributes.put(Export_Package, "test");
        Assert.assertEquals("test", attributes.getValue("Export-Package"));
    }

    @Ignore @Test
    public void can_getExportPackage() {
        //attributes.put(Export_Package, "test");
        //Assert.assertEquals("test", attributes.getExportPackage());
    }

    @Ignore @Test
    public void can_setExportPackage() {
        //attributes.setExportPackage("test");
        //Assert.assertEquals("test", attributes.getValue(Export_Package));
    }
}