package test;
import static org.junit.Assert.assertEquals;
/**
 * 
 * This class tests bitwise operations.
 *
 */
public class BitwiseOperationsTest {
    private static double TOLERANCE = 0.01;
    private double not(final double n) {
        return ~(int) n;
    }
    private double and(final double n1, final double n2) {
        return (int) n1 & (int) n2;
    }
    private double or(final double n1, final double n2) {
        return (int) n1 | (int) n2;
    }
    /**
     * Test for "not" operation.
     */
    @org.junit.Test
    public void testNot() {
        //100000 32     011111 31
        assertEquals(-33.0, not(32.0), TOLERANCE);
    }
    /**
     * Test for "and" operation.
     */
    @org.junit.Test
    public void testAnd() {
        //(100000 32 and 100000 32) = 100000 32
        assertEquals(32.0, and(32.0, 32.0), TOLERANCE);
        //(100001 33 and 100000 32) = 100000 32
        assertEquals(32.0, and(33.0, 32.0), TOLERANCE);
        //(100001 33 and 100010 34) = 100000 32
        assertEquals(32.0, and(33.0, 34.0), TOLERANCE);
    }
    /**
     * Test for "or" operation.
     */
    @org.junit.Test
    public void testOr() {
        //(100000 32 or 100000 32) = 100000 32
        assertEquals(32.0, or(32.0, 32.0), TOLERANCE);
        //(100001 33 or 100000 32) = 100001 33
        assertEquals(33.0, or(33.0, 32.0), TOLERANCE);
        //(100001 33 and 100010 34) = 100011 35
        assertEquals(35.0, or(33.0, 34.0), TOLERANCE);
    }
}
