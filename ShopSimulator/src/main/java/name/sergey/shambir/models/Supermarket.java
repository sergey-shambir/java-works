package name.sergey.shambir.models;

import java.util.HashMap;

public class Supermarket implements DiscountInfo {
    private Basket supply;
    private HashMap<Customer.Category, Integer> discounts;

    private static final Integer ZERO = new Integer(0);

    public Supermarket() {
        this.supply = new Basket();
        this.discounts = new HashMap<>();
    }

    public void setDiscount(Customer.Category category, int value) {
        discounts.put(category, new Integer(value));
    }

    public void putProduct(Product product, int count) {
        supply.putProduct(product, count);
    }

    public void takeProduct(Product product, int count) {
        supply.takeProduct(product, count);
    }

    public final Product[] getUniqueProducts() {
        return supply.getUniqueProducts();
    }

    public final int getProductCount(Product product) {
        return supply.getProductCount(product);
    }

    @Override
    public int getDiscountPercentage(Customer customer) {
        return discounts.getOrDefault(customer.getCategory(), ZERO).intValue();
    }
}
