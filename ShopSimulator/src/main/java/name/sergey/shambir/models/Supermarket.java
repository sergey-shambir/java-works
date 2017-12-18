package name.sergey.shambir.models;

import java.util.HashMap;

public class Supermarket extends BasketOwner implements DiscountInfo {
    private Basket supply;
    private HashMap<Customer.Category, Integer> discounts;

    private static final Integer ZERO = new Integer(0);

    public Supermarket() {
        this.discounts = new HashMap<>();
    }

    public void setDiscount(Customer.Category category, int value) {
        value = Math.max(0, Math.min(100, value));
        discounts.put(category, new Integer(value));
    }

    @Override
    public int getDiscountPercentage(Customer customer) {
        return discounts.getOrDefault(customer.getCategory(), ZERO).intValue();
    }
}
