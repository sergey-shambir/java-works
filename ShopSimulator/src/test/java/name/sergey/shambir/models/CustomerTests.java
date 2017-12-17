package name.sergey.shambir.models;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class CustomerTests extends Assert {
    @Test
    public void testPaymentFromCard() {
        Customer customer = new Customer("John", Customer.Category.Adult, new BigDecimal(3000), new BigDecimal(0));
        assertTrue(customer.canPay(new BigDecimal(3000)));
        assertTrue(customer.canPay(new BigDecimal(2000)));
        assertTrue(customer.canPay(new BigDecimal(1)));

        assertTrue(customer.pay(new BigDecimal(2000)));
        assertTrue(customer.canPay(new BigDecimal(1000)));
        assertFalse(customer.canPay(new BigDecimal(1001)));

        assertFalse(customer.pay(new BigDecimal(1001)));
        assertTrue(customer.canPay(new BigDecimal(1000)));

        assertTrue(customer.pay(new BigDecimal(1000)));
        assertFalse(customer.canPay(new BigDecimal(1)));
    }

    @Test
    public void testPaymentFromNetCash() {
        Customer customer = new Customer("John", Customer.Category.Adult, new BigDecimal(1000), new BigDecimal(2000));
        assertTrue(customer.canPay(new BigDecimal(3000)));
        assertTrue(customer.canPay(new BigDecimal(2000)));
        assertTrue(customer.canPay(new BigDecimal(1)));

        assertTrue(customer.pay(new BigDecimal(2000)));
        assertTrue(customer.canPay(new BigDecimal(1000)));
        assertFalse(customer.canPay(new BigDecimal(1001)));

        assertFalse(customer.pay(new BigDecimal(1001)));
        assertTrue(customer.canPay(new BigDecimal(1000)));

        assertTrue(customer.pay(new BigDecimal(1000)));
        assertFalse(customer.canPay(new BigDecimal(1)));
    }

    @Test
    public void testPaymentFromBonuses() {
        Customer customer = new Customer("John", Customer.Category.Adult, new BigDecimal(0), new BigDecimal(2000));
        customer.addBonuses(new BigDecimal(1000));

        assertTrue(customer.canPay(new BigDecimal(3000)));
        assertTrue(customer.canPay(new BigDecimal(2000)));
        assertTrue(customer.canPay(new BigDecimal(1)));

        assertTrue(customer.pay(new BigDecimal(2000)));
        assertTrue(customer.canPay(new BigDecimal(1000)));
        assertFalse(customer.canPay(new BigDecimal(1001)));

        assertFalse(customer.pay(new BigDecimal(1001)));
        assertTrue(customer.canPay(new BigDecimal(1000)));

        assertTrue(customer.pay(new BigDecimal(1000)));
        assertFalse(customer.canPay(new BigDecimal(1)));

        customer.addBonuses(new BigDecimal(1000));
        assertTrue(customer.canPay(new BigDecimal(1)));
        assertTrue(customer.canPay(new BigDecimal(1000)));
    }
}
