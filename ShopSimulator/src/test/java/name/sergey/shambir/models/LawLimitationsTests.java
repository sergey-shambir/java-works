package name.sergey.shambir.models;

import name.sergey.shambir.quantity.QuantityCategory;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class LawLimitationsTests extends Assert {
    @Test
    public void testAgeLimitations() {
        LawLimitations limitations = new LawLimitations();
        Customer adult = new Customer("John Doe", Customer.Category.Adult, new BigDecimal(300), new BigDecimal(100));
        Customer retired = new Customer("Christopher Columbus", Customer.Category.Retired, new BigDecimal(200), new BigDecimal(100));
        Customer child = new Customer("Billy", Customer.Category.Child, new BigDecimal(0), new BigDecimal(50));
        Product vine = new Product("Vine", Product.Category.Alcohol, QuantityCategory.Countable, 10, 10);
        Product milk = new Product("Milk", Product.Category.Food, QuantityCategory.Countable, 10, 10);

        assertFalse(limitations.hasLimitation(adult, vine));
        assertFalse(limitations.hasLimitation(retired, vine));
        assertFalse(limitations.hasLimitation(child, vine));
        assertFalse(limitations.hasLimitation(adult, milk));
        assertFalse(limitations.hasLimitation(retired, milk));
        assertFalse(limitations.hasLimitation(child, milk));

        limitations.addAgeLimitation(Customer.Category.Child, Product.Category.Alcohol);
        limitations.addAgeLimitation(Customer.Category.Retired, Product.Category.Food);

        assertFalse(limitations.hasLimitation(adult, vine));
        assertFalse(limitations.hasLimitation(retired, vine));
        assertTrue(limitations.hasLimitation(child, vine));
        assertFalse(limitations.hasLimitation(adult, milk));
        assertTrue(limitations.hasLimitation(retired, milk));
        assertFalse(limitations.hasLimitation(child, milk));
    }
}
