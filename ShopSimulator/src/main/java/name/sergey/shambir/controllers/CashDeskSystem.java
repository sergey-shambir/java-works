package name.sergey.shambir.controllers;

import name.sergey.shambir.models.*;

import java.math.BigDecimal;
import java.util.LinkedList;

class CashDeskSystem {
    private final CashStorage cashDesk;
    private final CostCalculator priceCalculator;
    private final ShopEventsListener listener;
    private final LinkedList<Customer> customersQueue;

    public CashDeskSystem(CashDesk cashDesk, CostCalculator costCalculator, ShopEventsListener listener) {
        this.cashDesk = cashDesk;
        this.priceCalculator = costCalculator;
        this.listener = listener;
        this.customersQueue = new LinkedList<>();
    }

    public void addCustomer(Customer customer) {
        customersQueue.push(customer);
    }

    public final int getCustomersCount() {
        return customersQueue.size();
    }

    public void update() {
        if (!customersQueue.isEmpty()) {
            Customer customer = customersQueue.remove();
            serveCustomer(customer);
        }
    }

    private void serveCustomer(Customer customer) {
        if (!customer.hasProducts()) {
            listener.onCustomerLeaveDry(customer);
            return;
        }

        BigDecimal cost = this.priceCalculator.getCustomerBasketCost(customer);
        BigDecimal bonuses = this.priceCalculator.getCustomerBasketBonuses(customer);
        if (customer.pay(cost)) {
            cashDesk.addCash(cost);
            listener.onCustomerPaid(customer, customer.getUniqueProducts(), cost, bonuses);
            customer.clearBasket();
            customer.addBonuses(bonuses);
        } else {
            listener.onCustomerPaymentFailed(customer, cost);
        }
    }
}
