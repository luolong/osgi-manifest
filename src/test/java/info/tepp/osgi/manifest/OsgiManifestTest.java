package info.tepp.osgi.manifest;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class OsgiManifestTest {

    OsgiManifest manifest;

    private InputStream inputStreamOf(String ... attributes) throws UnsupportedEncodingException {
        String manifestString = "";
        for (String attribute : attributes) {
            manifestString += splitByLength(attribute, 72) + '\n';
        }
        return new ByteArrayInputStream(manifestString.getBytes("UTF-8"));
    }

    @Before
    public void setUp() throws Exception {
        manifest = new OsgiManifest(inputStreamOf(
                "Bundle-Version: 1.2"
        ));
    }

    @Test
    public void canGetBundleVersion() throws ParseException {
        assertEquals(Version.valueOf(1, 2), manifest.getBundleVersion());
    }

    @Test
    public void splitByLengthWorks() {
        assertEquals("Package\n Import:\n  com.fo\n o.acme;\n version\n =1.2",
                splitByLength("PackageImport: com.foo.acme;version=1.2", 7));
    }

    private String splitByLength(String attribute, int length) {
        if (attribute.length() <= length) return attribute;

        int end = attribute.length(), start = length;
        StringBuilder sb = new StringBuilder(attribute.substring(0, length));
        do {
            sb.append("\n ").append(attribute.substring(start, Math.min(start + length, end)));
            start += length;
        } while (start < end);

        return sb.toString();
    }
}