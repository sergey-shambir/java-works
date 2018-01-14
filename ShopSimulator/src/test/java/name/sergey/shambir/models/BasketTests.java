package name.sergey.shambir.models;

import name.sergey.shambir.quantity.Quantity;
import name.sergey.shambir.quantity.QuantityCategory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class BasketTests extends Assert {
    private final Product coffee;
    private final Product milk;
    private final Product plate;

    public BasketTests() {
        coffee = new Product("coffee", Product.Category.Food, QuantityCategory.Countable, 100, 5);
        milk = new Product("milk", Product.Category.Food, QuantityCategory.Uncountable, 10, 5);
        plate = new Product("plate", Product.Category.Dish, QuantityCategory.Countable, 200, 10);
    }

    @Test
    public void testPutThanTake() {
        Basket basket = new Basket();
        assertFalse(basket.takeProduct(this.coffee, Quantity.countable(1)));
        assertFalse(basket.hasProducts());

        basket.putProduct(this.milk, Quantity.uncountable(2));
        assertTrue(basket.hasProducts());

        assertFalse(basket.takeProduct(this.coffee, Quantity.countable(1)));
        assertTrue(basket.takeProduct(this.milk, Quantity.uncountable(1)));
        assertTrue(basket.takeProduct(this.milk, Quantity.uncountable(1)));
        assertFalse(basket.takeProduct(this.milk, Quantity.uncountable(1)));
        assertFalse(basket.hasProducts());
    }

    @Test
    public void testPutDifferentProducts() {
        Basket basket = new Basket();
        basket.putProduct(this.milk, Quantity.uncountable(2));
        basket.putProduct(this.coffee, Quantity.countable(3));

        assertEquals(basket.getProductQuantity(this.milk), Quantity.uncountable(2));
        assertEquals(basket.getProductQuantity(this.coffee), Quantity.countable(3));
        assertEquals(basket.getProductQuantity(this.plate), Quantity.countable(0));

        basket.putProduct(this.coffee, Quantity.countable(6));
        assertEquals(basket.getProductQuantity(this.coffee), Quantity.countable(9));

        List<Product> products = Arrays.asList(basket.getUniqueProducts());
        assertEquals(products.size(), 2);
        assertTrue(products.indexOf(this.milk) != -1);
        assertTrue(products.indexOf(this.coffee) != -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutCountableThenUncountable() {
        Basket basket = new Basket();
        basket.putProduct(this.coffee, Quantity.countable(3));
        basket.putProduct(this.coffee, Quantity.uncountable(3));
    }

    @Test
    public void testClear() {
        Basket basket = new Basket();
        basket.putProduct(this.milk, Quantity.uncountable(2));
        basket.putProduct(this.coffee, Quantity.countable(3));
        assertEquals(basket.getProductQuantity(this.milk), Quantity.uncountable(2));
        assertEquals(basket.getProductQuantity(this.coffee), Quantity.countable(3));

        basket.clear();
        assertEquals(basket.getProductQuantity(this.milk), Quantity.uncountable(0));
        assertEquals(basket.getProductQuantity(this.coffee), Quantity.countable(0));
        assertFalse(basket.hasProducts());
    }
}
