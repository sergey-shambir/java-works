package name.sergey.shambir.controllers;

import name.sergey.shambir.models.Customer;
import name.sergey.shambir.models.PriceCalculator;
import name.sergey.shambir.models.Product;
import name.sergey.shambir.models.Supermarket;
import name.sergey.shambir.random.EasyRandom;
import name.sergey.shambir.random.EnumRandomGenerator;
import name.sergey.shambir.random.ProductGenerator;

import java.math.BigDecimal;
import java.util.*;

public class SupermarketSystem {
    private enum CustomerAction { PickUp, Walk, EndShopping }

    private static final int MIN_PRODUCT_COUNT = 20;
    private static final int MAX_PRODUCT_COUNT = 40;
    private static final int MAX_PRODUCT_INSTANCE_COUNT = 10;
    private static final int RETIRED_CUSTOMER_DISCOUNT = 10;

    private final Supermarket supermarket;
    private final ShopEventsListener listener;
    private final LinkedList<Customer> customers;
    private final EasyRandom random;
    private final EnumRandomGenerator<CustomerAction> actionGenerator;
    private final PriceCalculator priceCalculator;

    public SupermarketSystem(Supermarket supermarket, PriceCalculator priceCalculator, EasyRandom random,
                             ShopEventsListener listener) {
        this.supermarket = supermarket;
        this.priceCalculator = priceCalculator;
        this.listener = listener;
        this.customers = new LinkedList<>();
        this.random = random;

        this.actionGenerator = new EnumRandomGenerator<>(this.random, CustomerAction.class);
        this.actionGenerator.setWeight(CustomerAction.PickUp, 5);
        this.actionGenerator.setWeight(CustomerAction.Walk, 10);

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

        ProductGenerator generator = new ProductGenerator(this.random);
        for (int i = 0; i < productCount; ++i) {
            Product product = generator.nextProduct();
            final int instanceCount = this.random.nextIntInRange(1, MAX_PRODUCT_INSTANCE_COUNT);
            supermarket.putProduct(product, instanceCount);
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
        final int maxCount = supermarket.getProductCount(selectedProduct);
        final int selectedCount = this.random.nextIntInRange(1, maxCount);

        final BigDecimal cost = this.priceCalculator.getProductsCost(BigDecimal.ZERO, selectedProduct, selectedCount);

        // Customer selects product if and only if he can pay without discounts
        // and bonuses.
        if (customer.canPay(cost)) {
            supermarket.takeProduct(selectedProduct, selectedCount);
            customer.basket.putProduct(selectedProduct, selectedCount);
            this.listener.onCustomerPickUpProduct(customer, selectedProduct.getName(), selectedCount);
        }
    }
}
