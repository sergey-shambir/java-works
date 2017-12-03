package name.sergey.shambir.controllers;

import name.sergey.shambir.models.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;

public class CashDeskSystem {
    private final CashDesk cashDesk;
    private final PriceCalculator priceCalculator;
    private final ShopEventsListener listener;
    private LinkedList<Customer> customersQueue;

    public CashDeskSystem(CashDesk cashDesk, PriceCalculator priceCalculator, ShopEventsListener listener)
    {
        this.cashDesk = cashDesk;
        this.priceCalculator = priceCalculator;
        this.listener = listener;
        this.customersQueue = new LinkedList<>();
    }

    public void addCustomer(Customer customer)
    {
        customersQueue.push(customer);
    }

    public final int getCustomersCount()
    {
        return customersQueue.size();
    }

    public void update()
    {
        if (!customersQueue.isEmpty()) {
            Customer customer = customersQueue.remove();
            serveCustomer(customer);
        }
    }

    private void serveCustomer(Customer customer)
    {
        if (customer.basket.isEmpty())
        {
            listener.onCustomerLeaveDry(customer);
            return;
        }

        BigDecimal price = this.priceCalculator.getCustomerBasketCost(customer);
        BigDecimal bonuses = this.priceCalculator.getCustomerBasketBonuses(customer);
        if (customer.pay(price))
        {
            String[] productNames = collectProductNames(customer.basket.getProducts());
            cashDesk.addCash(price);
            customer.basket.clear();
            customer.addBonuses(bonuses);
            listener.onCustomerPaid(customer, productNames, price, bonuses);
        }
        else
        {
            listener.onCustomerPaymentFailed(customer, price);
        }
    }

    private static String[] collectProductNames(HashMap<Product, Integer> products)
    {
        String[] names = new String[products.size()];
        int index = 0;
        for (Product product : products.keySet()) {
            names[index] = product.getName();
            ++index;
        }
        return names;
    }
}
