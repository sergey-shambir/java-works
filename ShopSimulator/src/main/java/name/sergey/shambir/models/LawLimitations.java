package name.sergey.shambir.models;

import java.util.ArrayList;

class LawLimitations {
    private class AgeLimitation {
        final Customer.Category customerCategory;
        final Product.Category productCategory;

        AgeLimitation(Customer.Category customerCategory, Product.Category productCategory) {
            this.customerCategory = customerCategory;
            this.productCategory = productCategory;
        }
    }

    private final ArrayList<AgeLimitation> ageLimitations;

    public LawLimitations() {
        this.ageLimitations = new ArrayList<>();
    }

    public boolean hasLimitation(Customer customer, Product product) {
        for (AgeLimitation limitation : ageLimitations) {
            if (customer.getCategory() == limitation.customerCategory &&
                product.getCategory() == limitation.productCategory) {
                return true;
            }
        }
        return false;
    }

    void addAgeLimitation(Customer.Category customerCategory, Product.Category productCategory) {
        this.ageLimitations.add(new AgeLimitation(customerCategory, productCategory));
    }
}
