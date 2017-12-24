package name.sergey.shambir.models;

import name.sergey.shambir.quantity.QuantityCategory;
import name.sergey.shambir.utils.DecimalUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class SupermarketTests extends Assert {
    @Test
    public void testProperties() {
        Customer adult =
            new Customer("Ann", Customer.Category.Adult, DecimalUtils.toCurrency(100), DecimalUtils.toCurrency(10));
        Customer retired =
            new Customer("George", Customer.Category.Retired, DecimalUtils.toCurrency(200), DecimalUtils.toCurrency(0));
        Customer child =
            new Customer("Billy", Customer.Category.Child, DecimalUtils.toCurrency(0), DecimalUtils.toCurrency(50));

        Supermarket supermarket = new Supermarket();
        assertEquals(supermarket.getDiscountPercentage(adult), 0);
        assertEquals(supermarket.getDiscountPercentage(retired), 0);
        assertEquals(supermarket.getDiscountPercentage(child), 0);

        supermarket.setDiscount(Customer.Category.Child, 10);
        assertEquals(supermarket.getDiscountPercentage(adult), 0);
        assertEquals(supermarket.getDiscountPercentage(retired), 0);
        assertEquals(supermarket.getDiscountPercentage(child), 10);

        supermarket.setDiscount(Customer.Category.Adult, 80);
        assertEquals(supermarket.getDiscountPercentage(adult), 80);
        assertEquals(supermarket.getDiscountPercentage(retired), 0);
        assertEquals(supermarket.getDiscountPercentage(child), 10);

        supermarket.setDiscount(Customer.Category.Adult, -10);
        assertEquals(supermarket.getDiscountPercentage(adult), 0);
        assertEquals(supermarket.getDiscountPercentage(retired), 0);
        assertEquals(supermarket.getDiscountPercentage(child), 10);

        supermarket.setDiscount(Customer.Category.Retired, 180);
        assertEquals(supermarket.getDiscountPercentage(adult), 0);
        assertEquals(supermarket.getDiscountPercentage(retired), 100);
        assertEquals(supermarket.getDiscountPercentage(child), 10);
    }

    @Test
    public void testDefaultAgeLimitations() {
        Supermarket supermarket = new Supermarket();
        Customer adult = new Customer("John Doe", Customer.Category.Adult, new BigDecimal(300), new BigDecimal(100));
        Customer retired =
            new Customer("Christopher Columbus", Customer.Category.Retired, new BigDecimal(200), new BigDecimal(100));
        Customer child = new Customer("Billy", Customer.Category.Child, new BigDecimal(0), new BigDecimal(50));
        Product vine = new Product("Vine", Product.Category.Alcohol, QuantityCategory.Countable, 10, 10);
        Product milk = new Product("Milk", Product.Category.Food, QuantityCategory.Countable, 10, 10);
        Product cigarettes = new Product("Cigarettes", Product.Category.Smockables, QuantityCategory.Countable, 10, 10);

        assertFalse(supermarket.hasLimitation(adult, vine));
        assertFalse(supermarket.hasLimitation(retired, vine));
        assertTrue(supermarket.hasLimitation(child, vine));
        assertFalse(supermarket.hasLimitation(adult, milk));
        assertFalse(supermarket.hasLimitation(retired, milk));
        assertFalse(supermarket.hasLimitation(child, milk));
        assertFalse(supermarket.hasLimitation(adult, cigarettes));
        assertFalse(supermarket.hasLimitation(retired, cigarettes));
        assertTrue(supermarket.hasLimitation(child, cigarettes));
    }
}
