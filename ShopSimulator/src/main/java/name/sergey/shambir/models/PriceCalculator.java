package name.sergey.shambir.models;

import name.sergey.shambir.quantity.Quantity;
import name.sergey.shambir.utils.DecimalUtils;

import java.math.BigDecimal;

@SuppressWarnings("BigDecimalMethodWithoutRoundingCalled")
public class PriceCalculator {
    private static final BigDecimal fullPercentage = new BigDecimal(100);
    private final DiscountInfo discountInfo;

    public PriceCalculator(DiscountInfo discountInfo) {
        this.discountInfo = discountInfo;
    }

    public final BigDecimal getProductsCost(BigDecimal discountPercentage, Product product, Quantity quantity) {
        if (quantity.isZero()) {
            return BigDecimal.ZERO;
        }

        // Formulae: productPrice * productCount * ((fullPercentage - discountPercentage) / fullPercentage);
        final BigDecimal pricePercentage = fullPercentage.subtract(discountPercentage);
        final BigDecimal priceRatio = pricePercentage.divide(fullPercentage);
        return DecimalUtils.normalizeCurrency(product.getPrice().multiply(quantity.value()).multiply(priceRatio));
    }

    public final BigDecimal getProductBonuses(Product product, BigDecimal cost) {
        if (cost.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        return DecimalUtils.normalizeCurrency(cost.multiply(product.getBonusesPercentage()).divide(fullPercentage));
    }

    public final BigDecimal getCustomerBasketCost(Customer customer) {
        final BigDecimal discountPercentage = new BigDecimal(this.discountInfo.getDiscountPercentage(customer));
        return getProductStoreCost(customer, discountPercentage);
    }

    public final BigDecimal getCustomerBasketBonuses(Customer customer) {
        final BigDecimal discountPercentage = new BigDecimal(this.discountInfo.getDiscountPercentage(customer));
        return getProductStoreBonuses(customer, discountPercentage);
    }

    public final BigDecimal getProductStoreCost(ProductStore store, BigDecimal discountPercentage) {
        BigDecimal price = BigDecimal.ZERO;
        Product[] products = store.getUniqueProducts();
        for (Product product : products) {
            final Quantity quantity = store.getProductQuantity(product);
            price = price.add(getProductsCost(discountPercentage, product, quantity));
        }
        return price;
    }

    public BigDecimal getProductStoreBonuses(ProductStore store, BigDecimal discountPercentage) {
        BigDecimal bonuses = BigDecimal.ZERO;
        Product[] products = store.getUniqueProducts();
        for (Product product : products) {
            final Quantity quantity = store.getProductQuantity(product);
            final BigDecimal price = getProductsCost(discountPercentage, product, quantity);
            bonuses = bonuses.add(getProductBonuses(product, price));
        }
        return bonuses;
    }
}
