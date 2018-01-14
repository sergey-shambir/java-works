package name.sergey.shambir.controllers;

import name.sergey.shambir.mocks.MockDiscountInfo;
import name.sergey.shambir.mocks.MockShopEventsListener;
import name.sergey.shambir.models.CashDesk;
import name.sergey.shambir.models.CostCalculator;
import name.sergey.shambir.models.Customer;
import name.sergey.shambir.models.Product;
import name.sergey.shambir.quantity.Quantity;
import name.sergey.shambir.quantity.QuantityCategory;
import name.sergey.shambir.utils.DecimalUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CashDeskSystemTest extends Assert {
    private MockShopEventsListener listener;
    private CashDeskSystem system;

    @Before
    public void setup() {
        MockDiscountInfo discountInfo = new MockDiscountInfo();
        this.listener = new MockShopEventsListener();

        final CashDesk cashDesk = new CashDesk();
        final CostCalculator costCalculator = new CostCalculator(discountInfo);
        this.system = new CashDeskSystem(cashDesk, costCalculator, listener);
    }

    @Test
    public void testCustomerLeavesDry() {
        Customer john = new Customer("John Doe", Customer.Category.Adult, DecimalUtils.toCurrency(1000), DecimalUtils.toCurrency(100));

        this.system.addCustomer(john);
        assertNull(this.listener.lastCustomerEntered);
        assertNull(this.listener.lastCustomerLeavedDry);
        assertNull(this.listener.lastCustomerPaid);

        this.system.update();
        assertNull(this.listener.lastCustomerEntered);
        assertEquals(this.listener.lastCustomerLeavedDry, john);
        assertNull(this.listener.lastCustomerPaid);
    }

    @Test
    public void testCustomerPaid() {
        Customer john = new Customer("John Doe", Customer.Category.Adult, DecimalUtils.toCurrency(1000), DecimalUtils.toCurrency(100));
        Product product = new Product("potato", Product.Category.Food, QuantityCategory.Uncountable, 300, 7);
        john.putProduct(product, Quantity.uncountable(2.5));

        this.system.addCustomer(john);
        assertNull(this.listener.lastCustomerEntered);
        assertNull(this.listener.lastCustomerLeavedDry);
        assertNull(this.listener.lastCustomerPaid);

        this.system.update();
        assertNull(this.listener.lastCustomerEntered);
        assertNull(this.listener.lastCustomerLeavedDry);
        assertEquals(this.listener.lastCustomerPaid, john);
    }
}
