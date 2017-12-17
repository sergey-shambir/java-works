package name.sergey.shambir.models;

import java.math.BigDecimal;
import java.util.HashMap;

public class PriceCalculator {
    private static final BigDecimal fullPercentage = new BigDecimal(100);
    private final DiscountInfo discountInfo;

    public PriceCalculator(DiscountInfo discountInfo) {
        this.discountInfo = discountInfo;
    }

    public final BigDecimal getProductsCost(BigDecimal discountPercentage, Product product, int productCount) {
        assert productCount > 0;

        // Formulae: productPrice * productCount * ((fullPercentage - discountPercentage) / fullPercentage);
        final BigDecimal pricePercentage = fullPercentage.subtract(discountPercentage);
        final BigDecimal priceRatio = pricePercentage.divide(fullPercentage);
        return product.getPrice().multiply(new BigDecimal(productCount)).multiply(priceRatio);
    }

    public final BigDecimal getProductBonuses(Product product, BigDecimal cost) {
        assert cost.compareTo(BigDecimal.ZERO) > 0;
        return cost.multiply(product.getBonusesPercentage()).divide(fullPercentage);
    }

    public final BigDecimal getCustomerBasketCost(Customer customer) {
        final BigDecimal discountPercentage = new BigDecimal(this.discountInfo.getDiscountPercentage(customer));
        return getBasketCost(customer.basket, discountPercentage);
    }

    public final BigDecimal getCustomerBasketBonuses(Customer customer) {
        final BigDecimal discountPercentage = new BigDecimal(this.discountInfo.getDiscountPercentage(customer));
        return getBasketBonuses(customer.basket, discountPercentage);
    }

    public final BigDecimal getBasketCost(Basket basket, BigDecimal discountPercentage) {
        BigDecimal price = BigDecimal.ZERO;
        Product[] products = basket.getUniqueProducts();
        for (Product product : products) {
            final int productCount = basket.getProductCount(product);
            price = price.add(getProductsCost(discountPercentage, product, productCount));
        }
        return price;
    }

    public BigDecimal getBasketBonuses(Basket basket, BigDecimal discountPercentage) {
        BigDecimal bonuses = BigDecimal.ZERO;
        Product[] products = basket.getUniqueProducts();
        for (Product product : products) {
            final int productCount = basket.getProductCount(product);
            final BigDecimal price = getProductsCost(discountPercentage, product, productCount);
            bonuses = bonuses.add(getProductBonuses(product, price));
        }
        return bonuses;
    }
}
