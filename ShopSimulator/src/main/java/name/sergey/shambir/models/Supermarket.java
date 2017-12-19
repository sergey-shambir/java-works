package name.sergey.shambir.models;

import java.util.HashMap;

public class Supermarket extends BasketOwner implements DiscountInfo {
    private HashMap<Customer.Category, Integer> discounts;
    private LawLimitations lawLimitations;

    private static final Integer ZERO = new Integer(0);

    public Supermarket() {
        this.discounts = new HashMap<>();
        this.lawLimitations = new LawLimitations();
        this.lawLimitations.addAgeLimitation(Customer.Category.Child, Product.Category.Alcohol);
        this.lawLimitations.addAgeLimitation(Customer.Category.Child, Product.Category.Smockables);
    }

    public void setDiscount(Customer.Category category, int value) {
        value = Math.max(0, Math.min(100, value));
        discounts.put(category, new Integer(value));
    }

    public boolean hasLimitation(Customer customer, Product product) {
        return lawLimitations.hasLimitation(customer, product);
    }

    @Override
    public int getDiscountPercentage(Customer customer) {
        return discounts.getOrDefault(customer.getCategory(), ZERO).intValue();
    }
}
