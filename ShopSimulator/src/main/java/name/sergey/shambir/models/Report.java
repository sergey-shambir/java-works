package name.sergey.shambir.models;

import name.sergey.shambir.quantity.Quantity;
import name.sergey.shambir.utils.DecimalUtils;

import java.math.BigDecimal;
import java.util.HashMap;

public class Report {
    BigDecimal totalCash;
    HashMap<Customer.Category, BigDecimal> cashPerCategory;
    HashMap<Product, Quantity> soldProductQuantities;

    public Report() {
        totalCash = BigDecimal.ZERO;
        this.cashPerCategory = new HashMap<>();
        this.soldProductQuantities = new HashMap<>();
    }

    public void addSoldProduct(Product product, Quantity quantity) {
        Quantity oldQuantity = soldProductQuantities.getOrDefault(product, new Quantity(product.getQuantityCategory()));
        soldProductQuantities.put(product, quantity.add(oldQuantity));
    }

    public void addCashFromCustomer(BigDecimal cash, Customer customer) {
        totalCash = totalCash.add(cash);
        BigDecimal oldCash = cashPerCategory.getOrDefault(customer.getCategory(), BigDecimal.ZERO);
        cashPerCategory.put(customer.getCategory(), DecimalUtils.normalizeCurrency(cash.add(oldCash)));
    }

    public BigDecimal getTotalCash() {
        return totalCash;
    }

    public BigDecimal getCashPerCategory(Customer.Category category) {
        return cashPerCategory.getOrDefault(category, DecimalUtils.ZERO_CURRENCY);
    }

    public Quantity getSoldQuantity(Product product) {
        return soldProductQuantities.getOrDefault(product, new Quantity(product.getQuantityCategory()));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder
            .append("---------- cash desk report -------------\n")
            .append("Total cash: " + totalCash.toString() + "\n")
            .append("-----------------------------------------\n");

        for (Customer.Category category : cashPerCategory.keySet()) {
            builder.append("Cash from " + getCategoryPluralForm(category) + ": "
                    + cashPerCategory.get(category).toString().toLowerCase() + "\n");
        }
        builder.append("-----------------------------------------\n");

        for (Product product : soldProductQuantities.keySet()) {
            builder.append(product.getName() + " quantity: "
                    + soldProductQuantities.get(product).toString() + "\n");
        }

        return builder.toString();
    }

    private String getCategoryPluralForm(Customer.Category category) {
        switch (category) {
            case Retired:
                return "retired customers";
            case Child:
                return "children";
            case Adult:
                return "adults";
            default:
                return "";
        }
    }
}
