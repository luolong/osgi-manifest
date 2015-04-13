package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Bundle_DocURL;

public class OsgiAttribute_BundleDocURL_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Bundle-DocURL", "test");
        Assert.assertEquals("test", attributes.getValue(Bundle_DocURL));
    }

    @Test
    public void can_putValue() {
        attributes.put(Bundle_DocURL, "test");
        Assert.assertEquals("test", attributes.getValue("Bundle-DocURL"));
    }

    @Ignore @Test
    public void can_getBundleDocURL() {
        //attributes.put(Bundle_DocURL, "test");
        //Assert.assertEquals("test", attributes.getBundleDocURL());
    }

    @Ignore @Test
    public void can_setBundleDocURL() {
        //attributes.setBundleDocURL("test");
        //Assert.assertEquals("test", attributes.getValue(Bundle_DocURL));
    }
}