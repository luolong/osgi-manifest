package info.tepp.osgi.manifest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FilterTest {

    @Test
    public void canCreateSimpleEqualsOperation() {
        assertEquals("(a=b)", new Filter.Simple("a", "=", "b").toString());
    }

    @Test
    public void canCreateSimpleApproxOperation() {
        assertEquals("(a~=b)", new Filter.Simple("a", "~=", "b").toString());
    }

    @Test
    public void canCreateSimpleGreaterEqOperation() {
        assertEquals("(a>=b)", new Filter.Simple("a", ">=", "b").toString());
    }

    @Test
    public void canCreateSimpleLessEqOperation() {
        assertEquals("(a<=b)", new Filter.Simple("a", "<=", "b").toString());
    }

    @Test
    public void canCreatePresentOperation() {
        assertEquals("(a=*)", new Filter.Simple("a", "=", "*").toString());
    }

    @Test
    public void canCreateSubstringOperation() {
        assertEquals("(a=*b*c)", new Filter.Simple("a", "=", "*b*c").toString());
    }

    @Test
    public void canCreateNotFilter() {
        assertEquals("(!(a=b))", new Filter.Not(new Filter.Simple("a", "=", "b")).toString());
    }

    @Test
    public void canCreateAndFilter() {
        assertEquals("(&(a=b)(a~=3))", new Filter.And(new Filter.Simple("a", "=", "b"), new Filter.Simple("a", "~=", "3")).toString());
    }

    @Test
    public void canCreateOrFilter() {
        assertEquals("(|(a=b)(a~=3))", new Filter.Or(new Filter.Simple("a", "=", "b"), new Filter.Simple("a", "~=", "3")).toString());
    }

}