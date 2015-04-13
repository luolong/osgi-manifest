package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_NativeCode;

public class OsgiAttribute_BundleNativeCode_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-NativeCode", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_NativeCode));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_NativeCode, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-NativeCode"));
    }

    @Ignore @Test
    public void can_getBundleNativeCode() {
        //attributes.put(Bundle_NativeCode, "test");
        //Assert.assertEquals("test", attributes.getBundleNativeCode());
    }

    @Ignore @Test
    public void can_setBundleNativeCode() {
        //attributes.setBundleNativeCode("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_NativeCode));
    }
}