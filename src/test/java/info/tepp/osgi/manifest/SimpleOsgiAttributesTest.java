package info.tepp.osgi.manifest;

import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.*;
import static org.junit.Assert.assertEquals;

public class SimpleOsgiAttributesTest {

    OsgiAttributes attributes = new OsgiAttributes();

    @Test
    public void canGetBundleName() {
        attributes.put(Bundle_Name, "Bundle name");
        assertEquals("Bundle name", attributes.getBundleName());
    }

    @Test
    public void canSetBundleName() {
        attributes.setBundleName("Bundle name");
        assertEquals("Bundle name", attributes.getBundleName());
    }

    @Test
    public void canGetBundleContactAddress() {
        attributes.put(Bundle_ContactAddress, "2400 Oswego Road, Austin, TX 74563");
        assertEquals("2400 Oswego Road, Austin, TX 74563", attributes.getBundleContactAddress());
    }

    @Test
    public void canSetBundleContactAddress() {
        attributes.setBundleContactAddress("2400 Oswego Road, Austin, TX 74563");
        assertEquals("2400 Oswego Road, Austin, TX 74563", attributes.getBundleContactAddress());
    }

    @Test
    public void canGetBundleCopyright() {
        attributes.put(Bundle_Copyright, "OSGi (c) 2002");
        assertEquals("OSGi (c) 2002", attributes.getBundleCopyright());
    }

    @Test
    public void canSetBundleCopyright() {
        attributes.setBundleCopyright("OSGi (c) 2002");
        assertEquals("OSGi (c) 2002", attributes.getBundleCopyright());
    }

    @Test
    public void canGetBundleDescription() {
        attributes.put(Bundle_Description, "Network Firewall");
        assertEquals("Network Firewall", attributes.getBundleDescription());
    }

    @Test
    public void canSetBundleDescription() {
        attributes.setBundleDescription("Network Firewall");
        assertEquals("Network Firewall", attributes.getBundleDescription());
    }

    @Test
    public void canGetBundleVendor() {
        attributes.put(Bundle_Vendor, "OSGi Alliance");
        assertEquals("OSGi Alliance", attributes.getBundleVendor());
    }

    @Test
    public void canSetBundleVendor() {
        attributes.setBundleVendor("OSGi Alliance");
        assertEquals("OSGi Alliance", attributes.getBundleVendor());
    }

}