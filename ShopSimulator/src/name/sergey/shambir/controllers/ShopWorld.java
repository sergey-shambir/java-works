package name.sergey.shambir.controllers;

import name.sergey.shambir.models.CashDesk;
import name.sergey.shambir.models.Customer;
import name.sergey.shambir.models.PriceCalculator;
import name.sergey.shambir.models.Supermarket;
import name.sergey.shambir.utils.EventsLogger;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Random;

public class ShopWorld implements ShopEventsListener {
    private static final LocalTime OPENING_TIME = LocalTime.of(8, 0, 0);
    private static final LocalTime CLOSING_TIME = LocalTime.of(22, 0, 0);
    private static final int MINUTES_PER_STEP = 5;
    private static final long SLEEP_MSEC_BETWEEN_STEPS = 500;
    private LocalTime currentTime;
    private EventsLogger logger;
    private final Random random;

    private Supermarket supermarket;
    private CashDesk cashDesk;
    private PriceCalculator priceCalculator;

    private CustomerEmitter customerEmitter;
    private SupermarketSystem supermarketSystem;
    private CashDeskSystem cashDeskSystem;

    public ShopWorld() {
        this.currentTime = OPENING_TIME;
        this.logger = new EventsLogger(this.currentTime);
        this.random = new Random();

        this.supermarket = new Supermarket();
        this.cashDesk = new CashDesk();
        this.priceCalculator = new PriceCalculator(this.supermarket);

        this.cashDeskSystem =
            new CashDeskSystem(this.cashDesk, this.priceCalculator, this);
        this.supermarketSystem = new SupermarketSystem(
            this.supermarket, this.priceCalculator, this.random, this);
        this.customerEmitter =
            new CustomerEmitter(OPENING_TIME, this.random, this);
    }

    public void runLoop() {
        while (currentTime.compareTo(CLOSING_TIME) < 0) {
            update();
            currentTime = currentTime.plusMinutes(MINUTES_PER_STEP);
            sleep();
        }
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

    @Override
    public void onCustomerEntered(Customer customer) {
        this.supermarketSystem.addCustomer(customer);
        logger.logCustomerEntered(customer.getName(), getCutomerCount());
    }

    @Override
    public void onCustomerPickUpProduct(Customer customer, String productName,
                                        int count) {
        logger.logCustomerPickUpProduct(customer.getName(), productName, count);
    }

    @Override
    public void onCustomerStoppedShopping(Customer customer) {
        this.cashDeskSystem.addCustomer(customer);
        logger.logCustomerStoppedShopping(customer.getName());
    }

    @Override
    public void onCustomerPaid(Customer customer, String[] productsNames,
                               BigDecimal price, BigDecimal bonuses) {
        logger.logCustomerPaid(customer.getName(), productsNames, price,
                               bonuses);
    }

    @Override
    public void onCustomerPaymentFailed(Customer customer, BigDecimal price) {
        logger.logCustomerPaymentFailed(customer.getName(), price);
    }

    @Override
    public void onCustomerLeaveDry(Customer customer) {
        logger.logCustomerLeaveDry(customer.getName());
    }

    private final int getCutomerCount() {
        return supermarketSystem.getCustomersCount() +
            cashDeskSystem.getCustomersCount();
    }
}
