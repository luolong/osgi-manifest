package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Export_Service;

public class OsgiAttribute_ExportService_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Export-Service", "test");
        Assert.assertEquals("test", attributes.getValue(Export_Service));
    }

    @Test
    public void can_putValue() {
        attributes.put(Export_Service, "test");
        Assert.assertEquals("test", attributes.getValue("Export-Service"));
    }

    @Ignore @Test
    public void can_getExportService() {
        //attributes.put(Export_Service, "test");
        //Assert.assertEquals("test", attributes.getExportService());
    }

    @Ignore @Test
    public void can_setExportService() {
        //attributes.setExportService("test");
        //Assert.assertEquals("test", attributes.getValue(Export_Service));
    }
}