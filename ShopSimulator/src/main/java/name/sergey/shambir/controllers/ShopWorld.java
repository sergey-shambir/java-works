package name.sergey.shambir.controllers;

import name.sergey.shambir.models.*;
import name.sergey.shambir.quantity.Quantity;
import name.sergey.shambir.random.EasyRandom;
import name.sergey.shambir.utils.EventsLogger;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Random;

public class ShopWorld implements ShopEventsListener {
    private static final LocalTime OPENING_TIME = LocalTime.of(8, 0, 0);
    private static final LocalTime CLOSING_TIME = LocalTime.of(22, 0, 0);
    private static final int MINUTES_PER_STEP = 5;
    private static final long SLEEP_MSEC_BETWEEN_STEPS = 100;
    private LocalTime currentTime;
    private EventsLogger logger;
    private final EasyRandom random;

    private Supermarket supermarket;
    private CashDesk cashDesk;
    private PriceCalculator priceCalculator;
    private Report report;

    private CustomerEmitter customerEmitter;
    private SupermarketSystem supermarketSystem;
    private CashDeskSystem cashDeskSystem;

    public ShopWorld() {
        this.currentTime = OPENING_TIME;
        this.logger = new EventsLogger(this.currentTime);
        this.random = new EasyRandom(new Random());

        this.supermarket = new Supermarket();
        this.cashDesk = new CashDesk();
        this.priceCalculator = new PriceCalculator(this.supermarket);
        this.report = new Report();

        this.cashDeskSystem = new CashDeskSystem(this.cashDesk, this.priceCalculator, this);
        this.supermarketSystem = new SupermarketSystem(this.supermarket, this.priceCalculator, this.random, this);
        this.customerEmitter = new CustomerEmitter(OPENING_TIME, this.random, this);
    }

    public void runLoop() {
        while (currentTime.compareTo(CLOSING_TIME) < 0) {
            update();
            currentTime = currentTime.plusMinutes(MINUTES_PER_STEP);
            sleep();
        }
        printReport();
    }

    private void update() {
        this.logger.setCurrentTime(currentTime);
        customerEmitter.update(this.currentTime);
        supermarketSystem.update();
        cashDeskSystem.update();
    }

    private void sleep() {
        try {
            Thread.sleep(SLEEP_MSEC_BETWEEN_STEPS);
        } catch (InterruptedException ex) {
            // ignore interrupt.
        }
    }

    private void printReport() {
        System.out.println(this.report.toString());
    }

    @Override
    public void onCustomerEntered(Customer customer) {
        this.supermarketSystem.addCustomer(customer);
        logger.logCustomerEntered(customer.getName(), getCustomerCount());
    }

    @Override
    public void onCustomerPickUpProduct(Customer customer, String productName, Quantity quantity) {
        logger.logCustomerPickUpProduct(customer.getName(), productName, quantity);
    }

    @Override
    public void onCustomerStoppedShopping(Customer customer) {
        this.cashDeskSystem.addCustomer(customer);
        logger.logCustomerStoppedShopping(customer.getName());
    }

    @Override
    public void onCustomerPaid(Customer customer, Product[] uniqueProducts, BigDecimal price, BigDecimal bonuses) {
        logger.logCustomerPaid(customer.getName(), getStoredProductNames(uniqueProducts), price, bonuses);
        for (Product product : uniqueProducts) {
            report.addSoldProduct(product, customer.getProductQuantity(product));
        }
        report.addCashFromCustomer(price, customer);
    }

    @Override
    public void onCustomerPaymentFailed(Customer customer, BigDecimal price) {
        logger.logCustomerPaymentFailed(customer.getName(), price);
    }

    @Override
    public void onCustomerLeaveDry(Customer customer) {
        logger.logCustomerLeaveDry(customer.getName());
    }

    private final int getCustomerCount() {
        return supermarketSystem.getCustomersCount() + cashDeskSystem.getCustomersCount();
    }

    private final String[] getStoredProductNames(Product[] uniqueProducts) {
        String[] names = new String[uniqueProducts.length];
        for (int i = 0; i < names.length; ++i) {
            names[i] = uniqueProducts[i].getName();
        }
        return names;
    }
}
