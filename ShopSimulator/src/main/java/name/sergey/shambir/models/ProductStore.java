package name.sergey.shambir.models;

public interface ProductStore {
    int getProductCount(Product product);
    void putProduct(Product product, int count);
    boolean takeProduct(Product product, int count);
    boolean hasProducts();
    Product[] getUniqueProducts();
}
