package name.sergey.shambir.models;

import java.util.HashMap;

public class Supermarket implements DiscountInfo {
    public Basket supply;
    public HashMap<Customer.Category, Integer> discounts;

    private static final Integer ZERO = new Integer(0);

    public Supermarket() {
        this.supply = new Basket();
        this.discounts = new HashMap<>();
    }

    public void setDiscount(Customer.Category category, int value) {
        discounts.put(category, new Integer(value));
    }

    @Override
    public int getDiscountPercentage(Customer customer) {
        discounts.getOrDefault(customer.getCategory(), ZERO);
        return 0;
    }
}
