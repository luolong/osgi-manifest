package info.tepp.osgi.manifest;

import info.tepp.osgi.manifest.Filter.*;
import org.junit.Test;

import static info.tepp.osgi.manifest.Filter.*;
import static org.junit.Assert.assertEquals;

public class FilterTest {

    @Test
    public void canCreateSimpleEqualsOperation() {
        assertEquals("(a=b)",
                new Simple("a", "=", "b").toString());
    }

    @Test
    public void canCreateSimpleApproxOperation() {
        assertEquals("(a~=b)",
                new Simple("a", "~=", "b").toString());
    }

    @Test
    public void canCreateSimpleGreaterEqOperation() {
        assertEquals("(a>=b)",
                new Simple("a", ">=", "b").toString());
    }

    @Test
    public void canCreateSimpleLessEqOperation() {
        assertEquals("(a<=b)",
                new Simple("a", "<=", "b").toString());
    }

    @Test
    public void canCreatePresentOperation() {
        assertEquals("(a=*)",
                new Simple("a", "=", "*").toString());
    }

    @Test
    public void canCreateSubstringOperation() {
        assertEquals("(a=*b*c)",
                new Simple("a", "=", "*b*c").toString());
    }

    @Test
    public void canCreateNotFilter() {
        assertEquals("(!(a=b))",
                Not(Eq("a", "b")).toString());
    }

    @Test
    public void canCreateAndFilter() {
        assertEquals("(&(a>=2)(a<=3))",
                And(GreaterEq("a", "2"), LessEq("a", "3")).toString());
    }

    @Test
    public void canCreateOrFilter() {
        assertEquals("(|(a=b)(b~=3.0))",
                Or(Eq("a", "b"), Approx("b", "3.0")).toString());
    }

    @Test
    public void canCreateComplexFilter() {
        assertEquals("(&(a=*)(!(&(a>=2)(a>=3))))",
                And(Has("a"), Not(And(GreaterEq("a", "2"), GreaterEq("a", "3")))).toString());
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidFilterTypeOperatorThrowsIllegalArgumentException() {
        FilterType.of("?");
    }
}