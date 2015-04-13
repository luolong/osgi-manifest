package info.tepp.osgi.manifest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static info.tepp.osgi.manifest.OsgiAttributes.Name.Fragment_Host;

public class OsgiAttribute_FragmentHost_Test {

    OsgiManifest manifest = new OsgiManifest();
    OsgiAttributes attributes = manifest.getMainAttributes();

    @Test
    public void can_getValue() {
        attributes.putValue("Fragment-Host", "test");
        Assert.assertEquals("test", attributes.getValue(Fragment_Host));
    }

    @Test
    public void can_putValue() {
        attributes.put(Fragment_Host, "test");
        Assert.assertEquals("test", attributes.getValue("Fragment-Host"));
    }

    @Ignore @Test
    public void can_getFragmentHost() {
        //attributes.put(Fragment_Host, "test");
        //Assert.assertEquals("test", attributes.getFragmentHost());
    }

    @Ignore @Test
    public void can_setFragmentHost() {
        //attributes.setFragmentHost("test");
        //Assert.assertEquals("test", attributes.getValue(Fragment_Host));
    }
}