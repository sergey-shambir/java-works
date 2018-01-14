package name.sshambir.product;

import java.util.ArrayList;

class LawLimitations {
    private class AgeLimitation {
        final CustomerCategory customerCategory;
        final Product.Category productCategory;

        AgeLimitation(CustomerCategory customerCategory, Product.Category productCategory) {
            this.customerCategory = customerCategory;
            this.productCategory = productCategory;
        }
    }

    private final ArrayList<AgeLimitation> ageLimitations;

    public LawLimitations() {
        this.ageLimitations = new ArrayList<>();
    }

    public boolean hasLimitation(CustomerCategory customerCategory, Product product) {
        for (AgeLimitation limitation : ageLimitations) {
            if (customerCategory == limitation.customerCategory &&
                product.getCategory() == limitation.productCategory) {
                return true;
            }
        }
        return false;
    }

    void addAgeLimitation(CustomerCategory customerCategory, Product.Category productCategory) {
        this.ageLimitations.add(new AgeLimitation(customerCategory, productCategory));
    }
}
