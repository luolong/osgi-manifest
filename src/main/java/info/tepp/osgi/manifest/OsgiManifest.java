package info.tepp.osgi.manifest;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class OsgiManifest extends Manifest {

    public OsgiManifest() {
        super();
    }

    public OsgiManifest(InputStream inputStream) throws IOException {
        super(inputStream);
    }

    public OsgiManifest(Manifest manifest) {
        super(manifest);
    }

    @Override
    public OsgiAttributes getMainAttributes() {
        return new OsgiAttributes(super.getMainAttributes());
    }

    @Override
    public Map<String, Attributes> getEntries() {
        return super.getEntries();
    }

    public Version getBundleVersion() throws ParseException {
        return Version.parseVersion(getMainAttributes().getBundleVersion());
    }
}
