package name.sshambir.shop;

import name.sshambir.product.CustomerCategory;
import name.sshambir.product.ProductStore;

public interface SolventClient extends ProductStore { CustomerCategory getCustomerCategory(); }
