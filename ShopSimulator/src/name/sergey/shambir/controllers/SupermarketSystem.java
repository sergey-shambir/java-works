package name.sergey.shambir.controllers;

import name.sergey.shambir.models.Customer;
import name.sergey.shambir.models.PriceCalculator;
import name.sergey.shambir.models.Product;
import name.sergey.shambir.models.Supermarket;
import name.sergey.shambir.utils.EnumRandomGenerator;
import name.sergey.shambir.utils.ProductGenerator;

import java.math.BigDecimal;
import java.util.*;

public class SupermarketSystem {
    private enum CustomerAction
    {
        PickUp,
        Walk,
        EndShopping
    }

    private static final int MIN_PRODUCT_COUNT = 20;
    private static final int MAX_PRODUCT_COUNT = 40;
    private static final int MAX_PRODUCT_INSTANCE_COUNT = 10;
    private static final int RETIRED_CUSTOMER_DISCOUNT = 10;

    private final Supermarket supermarket;
    private final ShopEventsListener listener;
    private final LinkedList<Customer> customers;
    private final Random random;
    private final EnumRandomGenerator<CustomerAction> actionGenerator;
    private final PriceCalculator priceCalculator;

    public SupermarketSystem(Supermarket supermarket, PriceCalculator priceCalculator, Random random, ShopEventsListener listener)
    {
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

    public void addCustomer(Customer customer)
    {
        customers.push(customer);
    }

    public final int getCustomersCount()
    {
        return customers.size();
    }

    public void update()
    {
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

    private void initializeShopProducts()
    {
        final int productCount = MIN_PRODUCT_COUNT + this.random.nextInt(MAX_PRODUCT_COUNT - MIN_PRODUCT_COUNT + 1);

        ProductGenerator generator = new ProductGenerator(this.random);
        for (int i = 0; i < productCount; ++i)
        {
            Product product = generator.nextProduct();
            final int instanceCount = 1 + this.random.nextInt(MAX_PRODUCT_INSTANCE_COUNT);
            supermarket.supply.putProduct(product, instanceCount);
        }
    }

    private void initializeDiscounts()
    {
        supermarket.setDiscount(Customer.Category.Retired, RETIRED_CUSTOMER_DISCOUNT);
    }

    private void pickupRandomProduct(Customer customer)
    {
        final HashMap<Product, Integer> supply = this.supermarket.supply.getProducts();
        if (supply.isEmpty())
        {
            return;
        }

        final Set<Product> keys = supply.keySet();
        final Product[] products = keys.toArray(new Product[keys.size()]);
        final int productIndex = this.random.nextInt(products.length);
        final Product selectedProduct = products[productIndex];
        final int maxCount = supply.get(selectedProduct);
        final int selectedCount = this.random.nextInt(maxCount + 1);

        final BigDecimal cost = this.priceCalculator.getProductsCost(BigDecimal.ZERO, selectedProduct, selectedCount);

        // Customer selects product if and only if he can pay without discounts and bonuses.
        if (customer.simulatePay(cost))
        {
            supermarket.supply.takeProduct(selectedProduct, selectedCount);
            customer.basket.putProduct(selectedProduct, selectedCount);
            this.listener.onCustomerPickUpProduct(customer, selectedProduct.getName(), selectedCount);
        }
    }
}
