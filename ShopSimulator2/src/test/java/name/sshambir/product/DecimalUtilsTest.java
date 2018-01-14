package name.sshambir.product;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class DecimalUtilsTest extends Assert {
    @Test
    public void textNormalize() {
        double[] cases = {3.4, 18.181254, 20, 0, -1, -9145.4195128};

        for (double value : cases) {
            final BigDecimal valueBefore = new BigDecimal(3.4);
            final int digitsAfterDotBefore = getDigitsAfterDotCount(valueBefore.toString());

            final BigDecimal valueAfter = DecimalUtils.normalizeCurrency(valueBefore);
            final int digitsAfterDotAfter = getDigitsAfterDotCount(valueAfter.toString());

            // Rounds up to 2 digits after dot.
            assertTrue(digitsAfterDotBefore > 2);
            assertEquals(2, digitsAfterDotAfter);
        }
    }

    private int getDigitsAfterDotCount(String number) {
        final int dotIndex = number.indexOf('.');
        if (dotIndex == -1) {
            return 0;
        }
        return number.length() - dotIndex - 1;
    }
}
