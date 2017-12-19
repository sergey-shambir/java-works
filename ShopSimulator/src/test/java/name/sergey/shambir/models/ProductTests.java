package name.sergey.shambir.models;

import name.sergey.shambir.quantity.Quantity;
import name.sergey.shambir.quantity.QuantityCategory;
import name.sergey.shambir.utils.DecimalUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ProductTests extends Assert {
    @Test
    public void testGetters() {
        Product product = new Product("potato", Product.Category.Food, QuantityCategory.Uncountable, 300, 7);
        assertEquals(product.getName(), "potato");
        assertEquals(product.getCategory(), Product.Category.Food);
        assertEquals(product.getPrice(), DecimalUtils.toCurrency(300));
        assertEquals(product.getBonusesPercentage(), new BigDecimal(7).setScale(2));
    }

    @Test
    public void testEquals() {
        final BigDecimal price = new BigDecimal(20);
        final Product cigarettes1 = new Product("cigarettes", Product.Category.Smockables, QuantityCategory.Countable, price, new BigDecimal(1));
        final Product cigarettes2 = new Product("cigarettes", Product.Category.Smockables, QuantityCategory.Countable, price.doubleValue(), 1);
        final Product cigarettes3 = new Product("cigarettes", Product.Category.Smockables, QuantityCategory.Countable, price.doubleValue(), 3);
        final Product milk = new Product("milk", Product.Category.Food, QuantityCategory.Uncountable, price.doubleValue(), 1);

        // Common cases
        assertTrue(cigarettes1.equals(cigarettes2));
        assertTrue(cigarettes2.equals(cigarettes1));
        assertFalse(cigarettes1.equals(cigarettes3));
        assertFalse(cigarettes1.equals(milk));

        // Special cases
        assertTrue(cigarettes1.equals(cigarettes1));
        assertFalse(cigarettes1.equals(null));
        assertFalse(cigarettes1.equals(price));
    }
}
