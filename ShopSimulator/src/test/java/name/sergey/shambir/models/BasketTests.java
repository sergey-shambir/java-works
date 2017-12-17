package name.sergey.shambir.models;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

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
    public void testTakeNegativeount() {
        Basket basket = new Basket();
        basket.takeProduct(this.coffee, -1);
    }

    @Test
    public void testPutThanTake() {
        Basket basket = new Basket();
        assertFalse(basket.takeProduct(this.coffee, 1));
        assertTrue(basket.isEmpty());

        basket.putProduct(this.milk, 2);
        assertFalse(basket.isEmpty());

        assertFalse(basket.takeProduct(this.coffee, 1));
        assertTrue(basket.takeProduct(this.milk, 1));
        assertTrue(basket.takeProduct(this.milk, 1));
        assertFalse(basket.takeProduct(this.milk, 1));
        assertTrue(basket.isEmpty());
    }

    @Test
    public void testPutTakeDifferentProducts() {
        Basket basket = new Basket();
        basket.putProduct(this.milk, 2);
        basket.putProduct(this.coffee, 3);

        assertEquals(basket.getProductCount(this.milk), 2);
        assertEquals(basket.getProductCount(this.coffee), 3);
        assertEquals(basket.getProductCount(this.plate), 0);
    }
}
