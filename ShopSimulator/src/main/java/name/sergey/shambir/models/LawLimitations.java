package name.sergey.shambir.models;

import java.util.ArrayList;

public class LawLimitations {
    private class AgeLimitation {
        public final Customer.Category customerCategory;
        public final Product.Category productCategory;

        public AgeLimitation(Customer.Category customerCategory, Product.Category productCategory) {
            this.customerCategory = customerCategory;
            this.productCategory = productCategory;
        }
    }

    private ArrayList<AgeLimitation> ageLimitations;

    public LawLimitations() {
        this.ageLimitations = new ArrayList<>();
    }

    public boolean hasLimitation(Customer customer, Product product) {
        for (int i = 0; i < ageLimitations.size(); ++i) {
            AgeLimitation limitation = ageLimitations.get(i);
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
