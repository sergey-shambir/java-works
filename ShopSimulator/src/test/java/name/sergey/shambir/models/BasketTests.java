package name.sergey.shambir.models;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasketTests extends Assert {
    private final Product coffee;
    private final Product milk;
    private final Product plate;

    public BasketTests() {
        coffee = new Product("coffee", Product.Category.Food, new BigDecimal(100), 5);
        milk = new Product("milk", Product.Category.Food, new BigDecimal(10), 5);
        plate = new Product("plate", Product.Category.Dish, new BigDecimal(200), 10);
    }

    @Test(expected = RuntimeException.class)
    public void testTakeNegativeCount() {
        Basket basket = new Basket();
        basket.takeProduct(this.coffee, -1);
    }

    @Test
    public void testPutThanTake() {
        Basket basket = new Basket();
        assertFalse(basket.takeProduct(this.coffee, 1));
        assertFalse(basket.hasProducts());

        basket.putProduct(this.milk, 2);
        assertTrue(basket.hasProducts());

        assertFalse(basket.takeProduct(this.coffee, 1));
        assertTrue(basket.takeProduct(this.milk, 1));
        assertTrue(basket.takeProduct(this.milk, 1));
        assertFalse(basket.takeProduct(this.milk, 1));
        assertFalse(basket.hasProducts());
    }

    @Test(expected = RuntimeException.class)
    public void testPutNegativeCount() {
        Basket basket = new Basket();
        basket.putProduct(this.milk, 2);
        basket.putProduct(this.milk, -1);
    }

    @Test
    public void testPutDifferentProducts() {
        Basket basket = new Basket();
        basket.putProduct(this.milk, 2);
        basket.putProduct(this.coffee, 3);

        assertEquals(basket.getProductCount(this.milk), 2);
        assertEquals(basket.getProductCount(this.coffee), 3);
        assertEquals(basket.getProductCount(this.plate), 0);

        basket.putProduct(this.coffee, 6);
        assertEquals(basket.getProductCount(this.coffee), 9);

        List<Product> products = Arrays.asList(basket.getUniqueProducts());
        assertEquals(products.size(), 2);
        assertTrue(products.indexOf(this.milk) != -1);
        assertTrue(products.indexOf(this.coffee) != -1);
    }

    @Test
    public void testClear() {
        Basket basket = new Basket();
        basket.putProduct(this.milk, 2);
        basket.putProduct(this.coffee, 3);
        assertEquals(basket.getProductCount(this.milk), 2);
        assertEquals(basket.getProductCount(this.coffee), 3);

        basket.clear();
        assertEquals(basket.getProductCount(this.milk), 0);
        assertEquals(basket.getProductCount(this.coffee), 0);
        assertFalse(basket.hasProducts());
    }
}
