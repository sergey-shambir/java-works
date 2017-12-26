package name.sergey.shambir.models;

import name.sergey.shambir.utils.DecimalUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class CashDeskTests extends Assert {
    @Test
    public void testCashStorage() {
        int[] payments = {10, 99, 20, 11, 8};

        CashDesk desk = new CashDesk();
        int sum = 0;
        for (int payment : payments) {
            desk.addCash(new BigDecimal(payment));
            sum += payment;
        }
        assertEquals(desk.getCash(), DecimalUtils.toCurrency(sum));
    }
}
