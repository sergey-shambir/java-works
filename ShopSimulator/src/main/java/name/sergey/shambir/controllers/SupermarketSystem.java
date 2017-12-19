package name.sergey.shambir.controllers;

import name.sergey.shambir.models.Customer;
import name.sergey.shambir.models.PriceCalculator;
import name.sergey.shambir.models.Product;
import name.sergey.shambir.models.Supermarket;
import name.sergey.shambir.quantity.Quantity;
import name.sergey.shambir.quantity.QuantityGenerator;
import name.sergey.shambir.random.EasyRandom;
import name.sergey.shambir.random.EnumRandomGenerator;
import name.sergey.shambir.random.ProductGenerator;

import java.math.BigDecimal;
import java.util.*;

public class SupermarketSystem {
    private enum CustomerAction { PickUp, Walk, EndShopping }

    private static final int MIN_PRODUCT_COUNT = 20;
    private static final int MAX_PRODUCT_COUNT = 40;
    private static final int MIN_PRODUCT_QUANTITY_VALUE = 10;
    private static final int MAX_PRODUCT_QUANTITY_VALUE = 10;
    private static final int RETIRED_CUSTOMER_DISCOUNT = 10;

    private final Supermarket supermarket;
    private final ShopEventsListener listener;
    private final LinkedList<Customer> customers;
    private final EasyRandom random;
    private final EnumRandomGenerator<CustomerAction> actionGenerator;
    private final QuantityGenerator quantityGenerator;
    private final PriceCalculator priceCalculator;

    public SupermarketSystem(Supermarket supermarket, PriceCalculator priceCalculator, EasyRandom random,
                             ShopEventsListener listener) {
        this.supermarket = supermarket;
        this.priceCalculator = priceCalculator;
        this.listener = listener;
        this.customers = new LinkedList<>();
        this.random = random;

        this.actionGenerator = new EnumRandomGenerator<>(random, CustomerAction.class);
        this.actionGenerator.setWeight(CustomerAction.PickUp, 5);
        this.actionGenerator.setWeight(CustomerAction.Walk, 10);

        this.quantityGenerator = new QuantityGenerator(random);

        initializeShopProducts();
        initializeDiscounts();
    }

    public void addCustomer(Customer customer) {
        customers.push(customer);
    }

    public final int getCustomersCount() {
        return customers.size();
    }

    public void update() {
        for (Iterator<Customer> iterator = this.customers.iterator(); iterator.hasNext();) {
            Customer customer = iterator.next();
            CustomerAction action = this.actionGenerator.nextValue();

            switch (action) {
                case Walk:
                    // Do nothing.
                    break;
                case PickUp:
                    pickupRandomProduct(customer);
                    break;
                case EndShopping:
                    listener.onCustomerStoppedShopping(customer);
                    iterator.remove();
                    break;
            }
        }
    }

    private void initializeShopProducts() {
        final int productCount = this.random.nextIntInRange(MIN_PRODUCT_COUNT, MAX_PRODUCT_COUNT);

        ProductGenerator productGenerator = new ProductGenerator(this.random);
        QuantityGenerator quantityGenerator = new QuantityGenerator(this.random);
        for (int i = 0; i < productCount; ++i) {
            Product product = productGenerator.nextProduct();
            Quantity quantity = quantityGenerator.nextQuantityInRange(MIN_PRODUCT_QUANTITY_VALUE, MAX_PRODUCT_QUANTITY_VALUE);
            supermarket.putProduct(product, quantity);
        }
    }

    private void initializeDiscounts() {
        supermarket.setDiscount(Customer.Category.Retired, RETIRED_CUSTOMER_DISCOUNT);
    }

    private void pickupRandomProduct(Customer customer) {
        final Product[] availableProducts = this.supermarket.getUniqueProducts();
        if (availableProducts.length == 0) {
            return;
        }

        final Product selectedProduct = this.random.nextItem(availableProducts);
        final Quantity maxQuantity = supermarket.getProductQuantity(selectedProduct);
        final Quantity selectedQuantity = this.quantityGenerator.nextQuantity(maxQuantity);

        final BigDecimal cost = this.priceCalculator.getProductsCost(BigDecimal.ZERO, selectedProduct, selectedQuantity);

        // Customer avoids products with law limitations.
        if (this.supermarket.hasLimitation(customer, selectedProduct)) {
            return;
        }

        // Customer selects product if and only if he can pay without discounts
        // and bonuses.
        if (customer.canPay(cost)) {
            supermarket.takeProduct(selectedProduct, selectedQuantity);
            customer.putProduct(selectedProduct, selectedQuantity);
            this.listener.onCustomerPickUpProduct(customer, selectedProduct.getName(), selectedQuantity);
        }
    }
}
