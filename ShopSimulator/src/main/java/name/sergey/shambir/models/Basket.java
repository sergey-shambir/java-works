package name.sergey.shambir.models;

import java.util.HashMap;
import java.util.Set;

public class Basket {
    private HashMap<Product, Integer> products;

    public Basket() {
        this.products = new HashMap<>();
    }

    public void putProduct(Product product, int count) {
        assert(count > 0);
        Integer countValue = products.get(product);
        if (countValue != null) {
            count += countValue.intValue();
        }
        products.put(product, new Integer(count));
    }

    public void takeProduct(Product product, int count) {
        Integer existingCount = products.get(product);
        assert existingCount != null && existingCount.intValue() >= count;
        final int countLeft = existingCount.intValue() - count;
        if (countLeft == 0) {
            products.remove(product);
        }
    }

    public final boolean isEmpty() {
        return this.products.isEmpty();
    }

    public final Product[] getUniqueProducts() {
        final Set<Product> keys = products.keySet();
        return keys.toArray(new Product[keys.size()]);
    }

    public final int getProductCount(Product product) {
        return this.products.get(product).intValue();
    }

    public final String[] getProductNames() {
        Product[] uniqueProducts = getUniqueProducts();
        String[] names = new String[uniqueProducts.length];
        for (int i = 0; i < names.length; ++i) {
            names[i] = uniqueProducts[i].getName();
        }
        return names;
    }

    public void clear() {
        this.products.clear();
    }
}
