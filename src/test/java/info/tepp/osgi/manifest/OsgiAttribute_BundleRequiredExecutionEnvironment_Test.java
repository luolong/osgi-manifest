package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_RequiredExecutionEnvironment;

public class OsgiAttribute_BundleRequiredExecutionEnvironment_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-RequiredExecutionEnvironment", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_RequiredExecutionEnvironment));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_RequiredExecutionEnvironment, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-RequiredExecutionEnvironment"));
    }

    @Ignore @Test
    public void can_getBundleRequiredExecutionEnvironment() {
        //attributes.put(Bundle_RequiredExecutionEnvironment, "test");
        //Assert.assertEquals("test", attributes.getBundleRequiredExecutionEnvironment());
    }

    @Ignore @Test
    public void can_setBundleRequiredExecutionEnvironment() {
        //attributes.setBundleRequiredExecutionEnvironment("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_RequiredExecutionEnvironment));
    }
}