package name.sergey.shambir.models;

import name.sergey.shambir.quantity.QuantityCategory;
import name.sergey.shambir.utils.DecimalUtils;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    public enum Category { Food, Dish, HygieneProduct, Alcohol, Smockables }

    private static final BigDecimal FULL_PERCENTAGE = new BigDecimal(100);

    private final String name;
    private final Category category;
    private final QuantityCategory quantityCategory;
    private final BigDecimal price;
    private final BigDecimal bonusesPercentage;

    public Product(String name, Category category, QuantityCategory quantityCategory,
                   double price, double bonusPercentage) {
        assert bonusPercentage >= 0 && bonusPercentage <= 100;
        assert price >= 0;
        this.name = name;
        this.category = category;
        this.quantityCategory = quantityCategory;
        this.price = DecimalUtils.toCurrency(price);
        this.bonusesPercentage = DecimalUtils.toCurrency(bonusPercentage);
    }

    public Product(String name, Category category, QuantityCategory quantityCategory,
                   BigDecimal price, BigDecimal bonusPercentage) {
        assert price.compareTo(BigDecimal.ZERO) >= 0;
        assert bonusPercentage.compareTo(BigDecimal.ZERO) >= 0;
        assert bonusPercentage.compareTo(FULL_PERCENTAGE) <= 0;
        this.name = name;
        this.category = category;
        this.quantityCategory = quantityCategory;
        this.price = DecimalUtils.normalizeCurrency(price);
        this.bonusesPercentage = DecimalUtils.normalizeCurrency(bonusPercentage);
    }

    public final String getName() {
        return this.name;
    }

    public final Category getCategory() {
        return this.category;
    }

    public final BigDecimal getPrice() {
        return this.price;
    }

    public final BigDecimal getBonusesPercentage() {
        return this.bonusesPercentage;
    }

    public final QuantityCategory getQuantityCategory() {
        return this.quantityCategory;
    }

    public final int hashCode() {
        return Objects.hash(name, category, quantityCategory, price, bonusesPercentage);
    }

    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if ((other == null) || (getClass() != other.getClass())) {
            return false;
        }
        Product otherProduct = (Product)other;
        return name.equals(otherProduct.name)
                && category.equals(otherProduct.category)
                && quantityCategory.equals(otherProduct.quantityCategory)
                && price.equals(otherProduct.price)
                && (bonusesPercentage.equals(otherProduct.bonusesPercentage));
    }
}
