package name.sshambir.product;

import name.sshambir.shop.BasketOwner;

import java.util.HashMap;

public class PassiveShop extends BasketOwner implements ShoppingArea {
    private final HashMap<CustomerCategory, Integer> discounts;
    private final LawLimitations lawLimitations;

    public PassiveShop() {
        this.discounts = new HashMap<>();

        this.lawLimitations = new LawLimitations();
        this.lawLimitations.addAgeLimitation(CustomerCategory.Child, Product.Category.Alcohol);
        this.lawLimitations.addAgeLimitation(CustomerCategory.Child, Product.Category.Smockables);
    }

    @Override
    public boolean allowsSellProduct(CustomerCategory customerCategory, Product product) {
        return !lawLimitations.hasLimitation(customerCategory, product);
    }

    @Override
    public int getDiscountPercentage(CustomerCategory customerCategory) {
        return discounts.getOrDefault(customerCategory, 0);
    }
}
