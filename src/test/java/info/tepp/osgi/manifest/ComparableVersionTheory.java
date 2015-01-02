package info.tepp.osgi.manifest;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class ComparableVersionTheory {

    @DataPoint public static final Version v1_0_0_q = new Version(1, 0, 0, "q");
    @DataPoint public static final Version v0_1_0_q = new Version(0, 1, 0, "q");
    @DataPoint public static final Version v0_1_1_q = new Version(0, 1, 1, "q");
    @DataPoint public static final Version v0_0_1_q = new Version(0, 0, 1, "q");
    @DataPoint public static final Version v1_0_0_a = new Version(0, 0, 1, "a");

    @Theory
    public void versionWithGreaterMajorComponentIsGreater(Version one, Version two) {
        assumeTrue("major version is greater", one.major > two.major);

        assertThat(one, is(greaterThan(two)));
    }

    @Theory
    public void versionWithGreaterMinorComponentIsGreater(Version one, Version two) {
        assumeTrue("major versions are equal", one.major == two.major);
        assumeTrue("minor version is greater", one.minor > two.minor);

        assertThat(one, is(greaterThan(two)));
    }

    @Theory
    public void versionWithGreaterMicroComponentIsGreater(Version one, Version two) {
        assumeTrue("major versions are equal", one.major == two.major);
        assumeTrue("minor versions are equal", one.minor == two.minor);
        assumeTrue("micro version is greater", one.micro > two.micro);

        assertThat(one, is(greaterThan(two)));
    }

    @Theory
    public void versionWithGreaterQualifierComponentIsGreater(Version one, Version two) {
        assumeTrue("major versions are equal", one.major == two.major);
        assumeTrue("minor versions are equal", one.minor == two.minor);
        assumeTrue("micro versions are equal", one.micro == two.micro);
        assumeThat("qualifier version is greater", one.qualifier, is(greaterThan(two.qualifier)));

        assertThat(one, is(greaterThan(two)));
    }
}