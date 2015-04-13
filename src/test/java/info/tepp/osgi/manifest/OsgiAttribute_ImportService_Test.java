package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Import_Service;

public class OsgiAttribute_ImportService_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Import-Service", "test");
        Assert.assertEquals("test", attributes.getValue(Import_Service));
    }

    @Test
    public void can_putValue() {
        attributes.put(Import_Service, "test");
        Assert.assertEquals("test", attributes.getValue("Import-Service"));
    }

    @Ignore @Test
    public void can_getImportService() {
        //attributes.put(Import_Service, "test");
        //Assert.assertEquals("test", attributes.getImportService());
    }

    @Ignore @Test
    public void can_setImportService() {
        //attributes.setImportService("test");
        //Assert.assertEquals("test", attributes.getValue(Import_Service));
    }
}