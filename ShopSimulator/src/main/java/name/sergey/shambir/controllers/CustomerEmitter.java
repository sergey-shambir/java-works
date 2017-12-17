package name.sergey.shambir.controllers;

import name.sergey.shambir.models.Customer;
import name.sergey.shambir.random.EasyRandom;
import name.sergey.shambir.random.EnumRandomGenerator;
import name.sergey.shambir.random.NameGenerator;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Random;

public class CustomerEmitter {
    private static final int MAX_MINUTES_BEFORE_CUSTOMER = 20;

    private final EasyRandom random;
    private final EnumRandomGenerator<Customer.Category> categoryGenerator;
    private final NameGenerator nameGenerator;
    private LocalTime nextCustomerTime;
    private ShopEventsListener listener;

    public CustomerEmitter(LocalTime openingTime, EasyRandom random, ShopEventsListener listener) {
        this.listener = listener;
        this.random = random;
        this.nameGenerator = new NameGenerator(random);

        this.categoryGenerator = new EnumRandomGenerator<>(random, Customer.Category.class);
        this.categoryGenerator.setWeight(Customer.Category.Adult, 2.0);
        this.categoryGenerator.setWeight(Customer.Category.Child, 0.5);

        this.nextCustomerTime = openingTime;
        selectNextCustomerTime();
    }

    public void update(LocalTime time) {
        while (time.compareTo(this.nextCustomerTime) >= 0) {
            listener.onCustomerEntered(createCustomer());
            selectNextCustomerTime();
        }
    }

    private Customer createCustomer() {
        final Customer.Category category = this.categoryGenerator.nextValue();
        double netCash = 0;
        double cashOnCard = 0;
        double bonuses = 0;
        switch (category) {
            case Adult:
                netCash = this.random.nextClampedGaussian(110.0, 90.0);
                bonuses = this.random.nextDoubleInRange(0.0, 20.0);
                cashOnCard = this.random.nextDoubleInRange(200.0, 2000.0);
                break;
            case Child:
                netCash = this.random.nextClampedGaussian(20.0, 10.0);
                bonuses = this.random.nextDoubleInRange(0.0, 10.0);
                cashOnCard = 0;
                break;
            case Retired:
                netCash = this.random.nextClampedGaussian(50.0, 30.0);
                bonuses = this.random.nextDoubleInRange(0.0, 10.0);
                cashOnCard = this.random.nextDoubleInRange(0.0, 1000.0);
                break;
        }
        final String name = this.nameGenerator.nextName();

        final Customer customer = new Customer(name, category, new BigDecimal(cashOnCard), new BigDecimal(netCash));
        customer.addBonuses(new BigDecimal(bonuses));

        return customer;
    }

    private void selectNextCustomerTime() {
        final int minutesBeforeCustomer = random.nextIntInRange(1, MAX_MINUTES_BEFORE_CUSTOMER);
        this.nextCustomerTime = this.nextCustomerTime.plusMinutes(minutesBeforeCustomer);
    }
}
