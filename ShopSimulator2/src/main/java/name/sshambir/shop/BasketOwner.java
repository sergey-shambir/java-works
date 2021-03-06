package name.sshambir.shop;

import name.sshambir.product.*;

public class BasketOwner implements ProductStore {
    private final Basket basket;

    public BasketOwner() {
        this.basket = new Basket();
    }

    @Override
    public Quantity getProductQuantity(Product product) {
        return basket.getProductQuantity(product);
    }

    @Override
    public void putProduct(Product product, Quantity quantity) {
        basket.putProduct(product, quantity);
    }

    @Override
    public boolean takeProduct(Product product, Quantity quantity) {
        return basket.takeProduct(product, quantity);
    }

    @Override
    public boolean hasProducts() {
        return basket.hasProducts();
    }

    @Override
    public final Product[] getUniqueProducts() {
        return basket.getUniqueProducts();
    }

    public void clearBasket() {
        basket.clear();
    }
}
