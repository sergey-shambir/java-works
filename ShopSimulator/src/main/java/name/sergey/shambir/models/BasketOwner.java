package name.sergey.shambir.models;

public class BasketOwner implements ProductStore {
    private Basket basket;

    public BasketOwner()
    {
        this.basket = new Basket();
    }

    @Override
    public int getProductCount(Product product) {
        return basket.getProductCount(product);
    }

    @Override
    public void putProduct(Product product, int count)
    {
        basket.putProduct(product, count);
    }

    @Override
    public boolean takeProduct(Product product, int count) {
        return basket.takeProduct(product, count);
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
