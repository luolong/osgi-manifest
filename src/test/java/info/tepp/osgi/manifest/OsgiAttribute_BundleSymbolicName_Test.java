package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_SymbolicName;

public class OsgiAttribute_BundleSymbolicName_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-SymbolicName", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_SymbolicName));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_SymbolicName, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-SymbolicName"));
    }

    @Ignore @Test
    public void can_getBundleSymbolicName() {
        //attributes.put(Bundle_SymbolicName, "test");
        //Assert.assertEquals("test", attributes.getBundleSymbolicName());
    }

    @Ignore @Test
    public void can_setBundleSymbolicName() {
        //attributes.setBundleSymbolicName("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_SymbolicName));
    }
}