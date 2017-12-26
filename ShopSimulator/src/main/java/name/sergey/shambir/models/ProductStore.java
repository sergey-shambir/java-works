package name.sergey.shambir.models;

import name.sergey.shambir.quantity.Quantity;

@SuppressWarnings("unused")
interface ProductStore {
    Quantity getProductQuantity(Product product);
    void putProduct(Product product, Quantity count);
    boolean takeProduct(Product product, Quantity count);
    boolean hasProducts();
    Product[] getUniqueProducts();
}
