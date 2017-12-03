package name.sergey.shambir.controllers;

import name.sergey.shambir.models.Customer;

import java.math.BigDecimal;

public interface ShopEventsListener {
    void onCustomerEntered(Customer customer);
    void onCustomerPickUpProduct(Customer customer, String productName, int count);
    void onCustomerStoppedShopping(Customer customer);
    void onCustomerPaid(Customer customer, String[] productsNames, BigDecimal price, BigDecimal bonuses);
    void onCustomerPaymentFailed(Customer customer, BigDecimal price);
    void onCustomerLeaveDry(Customer customer);
}
