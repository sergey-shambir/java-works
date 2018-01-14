package name.sergey.shambir.models;

import name.sergey.shambir.quantity.Quantity;
import name.sergey.shambir.quantity.QuantityCategory;
import name.sergey.shambir.utils.DecimalUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class CustomerTests extends Assert {
    @Test
    public void testGetters() {
        Customer customer = new Customer("Elizabeth", Customer.Category.Child, new BigDecimal(0), new BigDecimal(100));
        assertEquals(customer.getName(), "Elizabeth");
        assertEquals(customer.getCategory(), Customer.Category.Child);
        assertFalse(customer.hasProducts());

        Product beef = new Product("beef", Product.Category.Food, QuantityCategory.Uncountable, 100, 0);
        Product beef2 = new Product("beef", Product.Category.Food, QuantityCategory.Uncountable, 100, 0);
        customer.putProduct(beef, Quantity.uncountable(1));
        customer.putProduct(beef, Quantity.uncountable(2.5));
        assertTrue(customer.hasProducts());
        assertEquals(customer.getUniqueProducts().length, 1);
        assertEquals(customer.getUniqueProducts()[0], beef);

        assertTrue(customer.takeProduct(beef2, Quantity.uncountable(1.5)));
        assertFalse(customer.takeProduct(beef2, Quantity.uncountable(4.5)));

        customer.clearBasket();
        assertFalse(customer.hasProducts());
        assertEquals(customer.getUniqueProducts().length, 0);
        assertFalse(customer.takeProduct(beef2, Quantity.uncountable(1.5)));
    }

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
        Customer customer = new Customer("John Doe", Customer.Category.Adult, new BigDecimal(0), new BigDecimal(2000));
        customer.addBonuses(new BigDecimal(1000));

        assertEquals(customer.getCategory(), Customer.Category.Adult);
        assertEquals(customer.getName(), "John Doe");

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

    @SuppressWarnings({"EqualsBetweenInconvertibleTypes", "EqualsWithItself", "ObjectEqualsNull"})
    @Test
    public void testEquals() {
        final BigDecimal price = new BigDecimal(20);
        final Customer customer1v1 = new Customer("John Doe", Customer.Category.Adult, DecimalUtils.toCurrency(10), DecimalUtils.toCurrency(20));
        final Customer customer1v2 = new Customer("John Doe", Customer.Category.Adult, DecimalUtils.toCurrency(10), DecimalUtils.toCurrency(20));
        final Customer customer3 = new Customer("John Lennon", Customer.Category.Adult, DecimalUtils.toCurrency(10), DecimalUtils.toCurrency(20));

        // Common cases
        assertTrue(customer1v1.equals(customer1v2));
        assertTrue(customer1v2.equals(customer1v1));
        assertFalse(customer1v1.equals(customer3));
        assertFalse(customer3.equals(customer1v1));

        // Special cases
        assertTrue(customer1v1.equals(customer1v1));
        assertFalse(customer1v1.equals(null));
        assertFalse(customer1v1.equals(price));
    }
}
