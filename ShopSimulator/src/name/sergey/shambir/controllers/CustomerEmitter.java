package name.sergey.shambir.controllers;

import name.sergey.shambir.models.Customer;
import name.sergey.shambir.utils.EnumRandomGenerator;
import name.sergey.shambir.utils.NameGenerator;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Random;

public class CustomerEmitter {
    private static final int MAX_MINUTES_BEFORE_CUSTOMER = 20;

    private final LocalTime openingTime;
    private final Random random;
    private final EnumRandomGenerator<Customer.Category> categoryGenerator;
    private final NameGenerator nameGenerator;
    private LocalTime nextCustomerTime;
    private ShopEventsListener listener;

    public CustomerEmitter(LocalTime openingTime, Random random,
                           ShopEventsListener listener) {
        this.openingTime = openingTime;
        this.listener = listener;
        this.random = random;
        this.nameGenerator = new NameGenerator(this.random);

        this.categoryGenerator =
            new EnumRandomGenerator<>(this.random, Customer.Category.class);
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
                netCash = randomClampedGaussian(110.0, 90.0);
                bonuses = this.random.nextDouble() * 20;
                cashOnCard = this.random.nextDouble() * 2000;
                break;
            case Child:
                netCash = randomClampedGaussian(20.0, 10.0);
                bonuses = this.random.nextDouble() * 5;
                cashOnCard = 0;
                break;
            case Retired:
                netCash = randomClampedGaussian(50.0, 30.0);
                bonuses = this.random.nextDouble() * 10;
                cashOnCard = this.random.nextDouble() * 500;
                break;
        }
        final String name = this.nameGenerator.nextName();

        final Customer customer =
            new Customer(name, category, new BigDecimal(cashOnCard),
                         new BigDecimal(netCash));
        customer.addBonuses(new BigDecimal(bonuses));

        return customer;
    }

    // Returns random values in range [mean - standardDeviation, mean + stddev],
    // where mean is median value and
    //  stddev is standard deviation for Normal distribution.
    private double randomClampedGaussian(double mean, double stddev) {
        // Try to generate up to 10 times.
        for (int i = 0; i < 10; ++i) {
            double value = random.nextGaussian() * stddev + mean;
            if (value >= mean - stddev && value <= mean + stddev) {
                return value;
            }
        }
        // There is low probability that we still cannot get clamped value.
        return mean;
    }

    private void selectNextCustomerTime() {
        final int minutesBeforeCustomer =
            random.nextInt(MAX_MINUTES_BEFORE_CUSTOMER);
        this.nextCustomerTime =
            this.nextCustomerTime.plusMinutes(minutesBeforeCustomer);
    }
}
