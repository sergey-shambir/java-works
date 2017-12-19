package name.sergey.shambir.models;

import name.sergey.shambir.utils.DecimalUtils;
import org.junit.Assert;
import org.junit.Test;

public class SupermarketTests extends Assert {
    @Test
    public void testProperties() {
        Customer adult = new Customer("Ann", Customer.Category.Adult, DecimalUtils.toCurrency(100), DecimalUtils.toCurrency(10));
        Customer retired = new Customer("George", Customer.Category.Retired, DecimalUtils.toCurrency(200), DecimalUtils.toCurrency(0));
        Customer child = new Customer("Billy", Customer.Category.Child, DecimalUtils.toCurrency(0), DecimalUtils.toCurrency(50));

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
}
