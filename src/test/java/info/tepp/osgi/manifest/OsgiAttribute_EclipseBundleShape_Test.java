package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Eclipse_BundleShape;

public class OsgiAttribute_EclipseBundleShape_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Eclipse-BundleShape", "test");
        Assert.assertEquals("test", attributes.getValue(Eclipse_BundleShape));
    }

    @Test
    public void can_putValue() {
        attributes.put(Eclipse_BundleShape, "test");
        Assert.assertEquals("test", attributes.getValue("Eclipse-BundleShape"));
    }

    @Ignore @Test
    public void can_getEclipseBundleShape() {
        //attributes.put(Eclipse_BundleShape, "test");
        //Assert.assertEquals("test", attributes.getEclipseBundleShape());
    }

    @Ignore @Test
    public void can_setEclipseBundleShape() {
        //attributes.setEclipseBundleShape("test");
        //Assert.assertEquals("test", attributes.getValue(Eclipse_BundleShape));
    }
}