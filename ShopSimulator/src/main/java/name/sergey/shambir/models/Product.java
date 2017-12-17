package name.sergey.shambir.models;

import name.sergey.shambir.utils.MoneyUtils;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    public enum Category { Food, Dish, HygieneProduct, Alcohol, Smockables }

    private final String name;
    private final Category category;
    private final BigDecimal price;
    private final BigDecimal bonusesPercentage;

    public Product(String name, Category category, BigDecimal price, int bonusPercentage) {
        assert bonusPercentage >= 0 && bonusPercentage <= 100;
        this.name = name;
        this.category = category;
        this.price = MoneyUtils.normalize(price);
        this.bonusesPercentage = new BigDecimal(bonusPercentage);
    }

    public final String getName() {
        return this.name;
    }

    public final BigDecimal getPrice() {
        return this.price;
    }

    public final BigDecimal getBonusesPercentage() {
        return this.bonusesPercentage;
    }

    public final int hashCode() {
        return Objects.hash(name, category, price, bonusesPercentage);
    }

    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if ((other == null) || (getClass() != other.getClass())) {
            return false;
        }
        Product otherProduct = (Product)other;
        return name.equals(otherProduct.name) && category.equals(otherProduct.category) &&
            price.equals(otherProduct.price) && (bonusesPercentage == otherProduct.bonusesPercentage);
    }
}
