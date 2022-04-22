package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import utils.CalcException;
import utils.NumberFormatter;

/**
 * Class to test edge cases correct behavior of the NumberFormatter class.
 */
public class FormatterTest {

    /**
     * All edge cases.
     */
    @org.junit.Test
    public void testNumberFormat() {
        assertEquals("2.34E-7", NumberFormatter.trimZeros("2.3400E-07"));

        try {

        assertEquals("0", NumberFormatter.format(0, 10, 10, 10));
        /* rounding to max decimal digits */
        assertEquals("3.142", NumberFormatter.format(Math.PI, 7, 3, 3));
        /* small number, should be formatted in scientific notation */
        assertEquals("2.34E-7", NumberFormatter.format(234* 0.000000001, 7, 3, 3));
        /* small number, but not enough for scientific notation */
        assertEquals("0.00000001", NumberFormatter.format(0.00000001, 7, 8, 8));
        /* java rounding error, should not be visible */
        assertEquals("0.3", NumberFormatter.format(0.1 + 0.2, 7, 8, 8));
        /* big numbers with different parameters */
        assertEquals("1.23E18", NumberFormatter.format(1234567891000000000.0, 7, 2, 2));
        assertEquals("1.23E18", NumberFormatter.format(1230000000000000000.0, 7, 5, 5));
        assertEquals("1.2301E18", NumberFormatter.format(1230100000000000000.0, 7, 5, 5));
        assertEquals("4.56E120", NumberFormatter.format(4562315E114, 10, 2, 2));
        assertEquals("4.56E106", NumberFormatter.format(4562315E100, 10, 2, 2));
        assertEquals("4.56E100", NumberFormatter.format(4562315E94, 10, 2, 2));
        /* Out of range number */
        assertThrows(CalcException.class, () -> NumberFormatter.format(1E308 * 10, 7, 10, 4));
        assertDoesNotThrow(() -> NumberFormatter.format(1E308, 7, 10, 4));
        /* Smallest possible number */
        assertEquals("1E-320", NumberFormatter.format(1E-320, 7, 10, 4));
        assertEquals("0", NumberFormatter.format(1E-321, 7, 10, 4));
        } catch (CalcException e) {
            fail(e.getMessage());
        }
    }
}
