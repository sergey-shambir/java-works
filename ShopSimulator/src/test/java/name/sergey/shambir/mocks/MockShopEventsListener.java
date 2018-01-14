package name.sergey.shambir.mocks;

import name.sergey.shambir.controllers.ShopEventsListener;
import name.sergey.shambir.models.Customer;
import name.sergey.shambir.models.Product;
import name.sergey.shambir.quantity.Quantity;

import java.math.BigDecimal;

public class MockShopEventsListener implements ShopEventsListener {
    public Customer lastCustomerEntered;
    public Customer lastCustomerLeavedDry;
    public Customer lastCustomerPaid;

    @Override
    public void onCustomerEntered(Customer customer) {
        this.lastCustomerEntered = customer;
    }

    @Override
    public void onCustomerPickUpProduct(Customer customer, String productName, Quantity count) {
    }

    @Override
    public void onCustomerStoppedShopping(Customer customer) {

    }

    @Override
    public void onCustomerPaid(Customer customer, Product[] products, BigDecimal price, BigDecimal bonuses) {
        this.lastCustomerPaid = customer;
    }

    @Override
    public void onCustomerPaymentFailed(Customer customer, BigDecimal price) {

    }

    @Override
    public void onCustomerLeaveDry(Customer customer) {
        this.lastCustomerLeavedDry = customer;
    }
}
