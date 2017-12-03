package name.sergey.shambir.models;

import java.util.HashMap;

public class Basket {
    private HashMap<Product, Integer> products;

    public Basket()
    {
        this.products = new HashMap<>();
    }

    public void putProduct(Product product, int count)
    {
        assert(count > 0);
        Integer countValue = products.get(product);
        if (countValue != null)
        {
            count += countValue.intValue();
        }
        products.put(product, new Integer(count));
    }

    public void takeProduct(Product product, int count)
    {
        Integer existingCount = products.get(product);
        assert(existingCount != null && existingCount.intValue() >= count);
        final int countLeft = existingCount.intValue() - count;
        if (countLeft == 0)
        {
            products.remove(product);
        }
    }

    public final boolean isEmpty()
    {
        return this.products.isEmpty();
    }

    public final HashMap<Product, Integer> getProducts()
    {
        return this.products;
    }

    public void clear()
    {
        this.products.clear();
    }
}
