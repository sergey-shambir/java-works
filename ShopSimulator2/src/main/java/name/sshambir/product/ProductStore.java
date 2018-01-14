package name.sshambir.product;

@SuppressWarnings("unused")
public interface ProductStore {
    Quantity getProductQuantity(Product product);
    void putProduct(Product product, Quantity count);
    boolean takeProduct(Product product, Quantity count);
    boolean hasProducts();
    Product[] getUniqueProducts();
}
