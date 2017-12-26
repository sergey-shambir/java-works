package name.sergey.shambir.models;

import java.util.HashMap;

public class Supermarket extends BasketOwner implements DiscountInfo {
    private final HashMap<Customer.Category, Integer> discounts;
    private final LawLimitations lawLimitations;

    private static final Integer ZERO = 0;

    public Supermarket() {
        this.discounts = new HashMap<>();
        this.lawLimitations = new LawLimitations();
        this.lawLimitations.addAgeLimitation(Customer.Category.Child, Product.Category.Alcohol);
        this.lawLimitations.addAgeLimitation(Customer.Category.Child, Product.Category.Smockables);
    }

    public void setDiscount(Customer.Category category, int value) {
        value = Math.max(0, Math.min(100, value));
        discounts.put(category, value);
    }

    public boolean hasLimitation(Customer customer, Product product) {
        return lawLimitations.hasLimitation(customer, product);
    }

    @Override
    public int getDiscountPercentage(Customer customer) {
        return discounts.getOrDefault(customer.getCategory(), ZERO);
    }
}
