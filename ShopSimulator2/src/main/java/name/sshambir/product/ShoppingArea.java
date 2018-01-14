package name.sshambir.product;

public interface ShoppingArea extends ProductStore, DiscountInfo {
    boolean allowsSellProduct(CustomerCategory customerCategory, Product product);
}
