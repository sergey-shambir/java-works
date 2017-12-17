package name.sergey.shambir.models;

import java.util.HashMap;
import java.util.Set;

public class Basket {
    private HashMap<Product, Integer> products;

    public Basket() {
        this.products = new HashMap<>();
    }

    public void putProduct(Product product, int count) {
        if (count <= 0) {
            throw new RuntimeException("product count should be greater than 0");
        }
        Integer countValue = products.get(product);
        if (countValue != null) {
            count += countValue.intValue();
        }
        products.put(product, new Integer(count));
    }

    public boolean takeProduct(Product product, int count) {
        if (count <= 0) {
            throw new RuntimeException("product count should be greater than 0");
        }
        Integer existingCount = products.get(product);
        if (existingCount != null && existingCount.intValue() >= count) {
            final int countLeft = existingCount.intValue() - count;
            if (countLeft == 0) {
                products.remove(product);
            } else {
                products.put(product, countLeft);
            }
            return true;
        }
        return false;
    }

    public final boolean isEmpty() {
        return this.products.isEmpty();
    }

    public final Product[] getUniqueProducts() {
        final Set<Product> keys = products.keySet();
        return keys.toArray(new Product[keys.size()]);
    }

    public final int getProductCount(Product product) {
        Integer count = this.products.get(product);
        if (count == null) {
            return 0;
        }
        return count.intValue();
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
