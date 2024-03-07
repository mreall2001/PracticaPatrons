import org.junit.Test;

import static org.junit.Assert.*;

public class FindTest {
    @Test
    public void simple_matches() {
       Find f = new Find("Liceu");
        assertTrue(f.match("ic"));
        assertFalse(f.match("li"));
        assertTrue(f.match("eu"));
        assertTrue(f.match("i"));
        assertFalse(f.match("ec"));
        assertFalse(f.match("xet"));
        assertFalse(f.match("Liceuu"));
        assertTrue(f.match("Liceu"));
        assertFalse(f.match(""));

        f = new Find("Esternocleidomastoideo");
        assertTrue(f.match("Ester"));
        assertTrue(f.match("E"));
        assertTrue(f.match("eido"));
        assertTrue(f.match("oid"));
        assertTrue(f.match("oideo"));
        assertTrue(f.match("cle"));
        assertFalse(f.match("ccl"));
        assertFalse(f.match("Liceu"));
        assertFalse(f.match("ester"));
        assertFalse(f.match("Eo"));
        assertFalse(f.match("Esternocleidomastoideoascds"));
        assertTrue(f.match("Esternocleidomastoideo"));
    }

    @Test
    public void specials() {
        Find f = new Find("a?b");
        assertTrue(f.match("a@?b"));

        f = new Find("bill.gates@microsoft.com");
        assertTrue(f.match("s@@micro"));

        f = new Find("ho[az]");
        assertTrue(f.match("o@["));
        assertTrue(f.match("z@]"));

        f = new Find("stars*d+");
        assertTrue(f.match("s@*"));
        assertTrue(f.match("@*d@+"));
        assertTrue(f.match("ars@*"));

        f = new Find("%hello$");
        assertTrue(f.match("@%"));
        assertTrue(f.match("@$"));

    }

    @Test
    public void qmark() {
        Find f = new Find("Llibre");
        assertTrue(f.match("Llibre"));
        assertTrue(f.match("Llibr?"));
        assertFalse(f.match("Llibp?"));
        assertTrue(f.match("?libr?"));
        assertTrue(f.match("L?ib"));
        assertFalse(f.match("i??ke"));
        assertFalse(f.match("ab?cd"));
        assertFalse(f.match("Ll?ibre"));
        assertTrue(f.match("?ib"));
        assertTrue(f.match("?"));
        assertFalse(f.match("???????"));
        assertTrue(f.match("??????"));
    }

    @Test
    public void positions() {
        Find f = new Find("This computer");
        assertTrue(f.match("%This"));
        assertFalse(f.match("%his"));
        assertFalse(f.match("%computer"));
        assertTrue(f.match("%This computer"));
        assertTrue(f.match("%This computer$"));
        assertTrue(f.match("This computer$"));
        assertTrue(f.match("This computer"));
        assertFalse(f.match("That computer"));
        assertFalse(f.match("%computer$"));
        assertFalse(f.match("%This computer$ is black$"));
        assertTrue(f.match("ter$"));
    //    assertFalse(f.match("This$"));

        f = new Find("This computer$ is black");
        assertTrue(f.match("%This computer$ is black$"));
//        f = new Find("This computer$ is black$");
//        assertFalse(f.match("%This computer$ is black$"));
//        f = new Find("%This computer$ is black$");
//        assertFalse(f.match("%This computer$ is black$$"));
        f = new Find("%This computer$ is black$");
        assertTrue(f.match("%%This computer$ is black$$"));
    }

    @Test
    public void charClasses() {
        Find f = new Find("This is your life");
        assertTrue(f.match("T[h]is"));
        assertTrue(f.match("T[abhc]is"));
        assertFalse(f.match("T[abc]is"));
        assertTrue(f.match("[tT]hi[ksn]"));
        assertFalse(f.match("This [is] your life"));
        assertTrue(f.match("This [is][si] your life"));
        assertTrue(f.match("This is [sdfyjkl]our life"));
    }

    @Test
    public void charClasses2() {
        Find f = new Find("Do what you can");
        assertTrue(f.match("wha[r-v]"));
        assertFalse(f.match("[a-m]hat"));
        assertTrue(f.match("D[h-z] wha[j-u]"));
        assertFalse(f.match("[a-z]o "));
        assertTrue(f.match("[A-Z]o "));
        assertFalse(f.match("ca[A-Z]"));
        assertTrue(f.match("ca[A-Zn]"));
        assertTrue(f.match("ca[A-Za-z]"));
    }

    @Test
    public void closures1() {
        Find f = new Find("bb");
        assertTrue(f.match("b+"));
        assertTrue(f.match("[abc]+"));
        assertFalse(f.match("b[ac]+"));

        f = new Find("aaaaaaabc");
        assertTrue(f.match("a+bc"));
        assertFalse(f.match("a+kbc"));
        assertTrue(f.match("ab+c"));
        assertFalse(f.match("abb+c"));
        assertFalse(f.match("az+c"));
        assertTrue(f.match("a+bc+$"));
        assertTrue(f.match("%[abc]+$"));
        assertFalse(f.match("%[ab]+$"));
        assertFalse(f.match("az+bc"));
    }

    @Test
    public void closures2() {
        Find f = new Find("bb");
        assertTrue(f.match("b*"));
        assertTrue(f.match("[abc]*"));
        assertTrue(f.match("b[ac]*"));

        f = new Find("aaaaaaabc");
        assertTrue(f.match("a*bc"));
        assertFalse(f.match("a*kbc"));
        assertTrue(f.match("ab*c"));
        assertTrue(f.match("abb*c"));
        assertFalse(f.match("abbb*c"));
        assertTrue(f.match("az*bc"));
        assertTrue(f.match("[abc]*c"));

        f = new Find("192228888888888888886722222226");
        assertTrue(f.match("192*8*672*6"));
        assertFalse(f.match("2*78*6"));
        assertTrue(f.match("1*"));
        assertTrue(f.match("8*"));
        assertTrue(f.match("k*"));
        assertTrue(f.match("%1[92867]*67"));
        assertFalse(f.match("14*2"));
        assertTrue(f.match("14*9"));
        assertFalse(f.match("14*9$"));
    }

    @Test
    public void captures() {
        Find f = new Find("aabbcc");
        assertEquals("bbc", f.capture("bbc"));
        assertEquals("bbc", f.capture("??c"));
        assertEquals("bcc", f.capture("??c$"));
        assertNull(null, f.capture("bcd"));
        assertEquals("abbc", f.capture("a[abc][abc]c"));
        assertEquals("aa", f.capture("%[abc][abc]"));
        assertEquals("cc", f.capture("[abc][abc]$"));
        assertEquals("abbc", f.capture("[ac][ab][ab][ac]"));
        assertEquals("aabb", f.capture("[ac][ab][bc][ab]"));
        assertNull(f.capture("[abc][ab]$"));

        f = new Find("12333333333668");
        assertEquals("2333333333668", f.capture("23*6*8"));
        assertEquals("12333333333668", f.capture("?*"));
        assertEquals("1233333333366", f.capture("1?*6"));
        assertEquals("1233333333366", f.capture("[123]?*3?+6"));
        assertEquals("23333333336", f.capture("[2368]*[238]6"));
        assertEquals("2333333333668", f.capture("[2368]*"));
        assertEquals("12333333333", f.capture("1[23]*"));
        assertEquals("23", f.capture("2[6]*3"));
        assertNull(f.capture("2[6]+3"));

        f = new Find("?*.+%$");
        assertEquals("?*.", f.capture("??."));
        assertEquals(".", f.capture("@?*."));
        assertNull(f.capture("@?+."));
        assertEquals("%$", f.capture("@%@$$"));
        assertEquals(".+%", f.capture(".?+%"));
        assertEquals("?*.+", f.capture("[?*.]*@+"));
        assertEquals("*.+%$", f.capture("@*?*$"));
        assertNull(f.capture("%??.*$"));
    }

}