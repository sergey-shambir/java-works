package name.sshambir.product;

import java.util.HashMap;
import java.util.Set;

public class Basket implements ProductStore {
    private final HashMap<Product, Quantity> products;

    public Basket() {
        this.products = new HashMap<>();
    }

    @Override
    public void putProduct(Product product, Quantity quantity) {
        Quantity currentQuantity = products.get(product);
        if (currentQuantity != null) {
            quantity = quantity.add(currentQuantity);
        }
        products.put(product, quantity);
    }

    @Override
    public boolean takeProduct(Product product, Quantity quantity) {
        Quantity currentQuantity = products.get(product);
        if (currentQuantity != null && currentQuantity.compareTo(quantity) >= 0) {
            final Quantity quantityLeft = currentQuantity.subtract(quantity);
            if (quantityLeft.isZero()) {
                products.remove(product);
            } else {
                products.put(product, quantityLeft);
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean hasProducts() {
        return !this.products.isEmpty();
    }

    @Override
    public final Quantity getProductQuantity(Product product) {
        Quantity quantity = this.products.get(product);
        if (quantity == null) {
            return new Quantity(product.getQuantityCategory());
        }
        return quantity;
    }

    @Override
    public final Product[] getUniqueProducts() {
        final Set<Product> keys = products.keySet();
        return keys.toArray(new Product[keys.size()]);
    }

    public void clear() {
        this.products.clear();
    }
}
