package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_ActivationPolicy;

public class OsgiAttribute_BundleActivationPolicy_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-ActivationPolicy", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_ActivationPolicy));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_ActivationPolicy, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-ActivationPolicy"));
    }

    @Ignore @Test
    public void can_getBundleActivationPolicy() {
        //attributes.put(Bundle_ActivationPolicy, "test");
        //Assert.assertEquals("test", attributes.getBundleActivationPolicy());
    }

    @Ignore @Test
    public void can_setBundleActivationPolicy() {
        //attributes.setBundleActivationPolicy("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_ActivationPolicy));
    }
}