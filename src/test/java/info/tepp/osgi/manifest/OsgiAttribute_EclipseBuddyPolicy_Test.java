package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Eclipse_BuddyPolicy;

public class OsgiAttribute_EclipseBuddyPolicy_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Eclipse-BuddyPolicy", "test");
        Assert.assertEquals("test", attributes.getValue(Eclipse_BuddyPolicy));
    }

    @Test
    public void can_putValue() {
        attributes.put(Eclipse_BuddyPolicy, "test");
        Assert.assertEquals("test", attributes.getValue("Eclipse-BuddyPolicy"));
    }

    @Ignore @Test
    public void can_getEclipseBuddyPolicy() {
        //attributes.put(Eclipse_BuddyPolicy, "test");
        //Assert.assertEquals("test", attributes.getEclipseBuddyPolicy());
    }

    @Ignore @Test
    public void can_setEclipseBuddyPolicy() {
        //attributes.setEclipseBuddyPolicy("test");
        //Assert.assertEquals("test", attributes.getValue(Eclipse_BuddyPolicy));
    }
}