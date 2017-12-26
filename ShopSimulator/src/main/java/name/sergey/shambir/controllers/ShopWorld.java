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
    private static final long SLEEP_MILLISECONDS_BETWEEN_STEPS = 100;
    private LocalTime currentTime;
    private final EventsLogger logger;

    private final Report report;

    private final CustomerEmitter customerEmitter;
    private final SupermarketSystem supermarketSystem;
    private final CashDeskSystem cashDeskSystem;

    public ShopWorld() {
        this.currentTime = OPENING_TIME;
        this.logger = new EventsLogger(this.currentTime);
        EasyRandom random = new EasyRandom(new Random());

        Supermarket supermarket = new Supermarket();
        CashDesk cashDesk = new CashDesk();
        PriceCalculator priceCalculator = new PriceCalculator(supermarket);
        this.report = new Report();

        this.cashDeskSystem = new CashDeskSystem(cashDesk, priceCalculator, this);
        this.supermarketSystem = new SupermarketSystem(supermarket, priceCalculator, random, this);
        this.customerEmitter = new CustomerEmitter(OPENING_TIME, random, this);
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
            Thread.sleep(SLEEP_MILLISECONDS_BETWEEN_STEPS);
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

    private int getCustomerCount() {
        return supermarketSystem.getCustomersCount() + cashDeskSystem.getCustomersCount();
    }

    private String[] getStoredProductNames(Product[] uniqueProducts) {
        String[] names = new String[uniqueProducts.length];
        for (int i = 0; i < names.length; ++i) {
            names[i] = uniqueProducts[i].getName();
        }
        return names;
    }
}
