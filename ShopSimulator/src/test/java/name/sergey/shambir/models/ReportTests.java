package name.sergey.shambir.models;

import name.sergey.shambir.quantity.Quantity;
import name.sergey.shambir.quantity.QuantityCategory;
import name.sergey.shambir.utils.DecimalUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class ReportTests extends Assert {
    private Customer adult;
    private Customer child;
    private Product coffee;
    private Product milk;
    private Product chocolate;

    @Before
    public void setup() {
        this.adult = new Customer("John Doe", Customer.Category.Adult, new BigDecimal(300), new BigDecimal(100));
        this.child = new Customer("Billy", Customer.Category.Child, new BigDecimal(0), new BigDecimal(50));
        this.coffee = new Product("coffee", Product.Category.Food, QuantityCategory.Countable, 100, 5);
        this.milk = new Product("milk", Product.Category.Food, QuantityCategory.Uncountable, 10, 5);
        this.chocolate = new Product("chocolate", Product.Category.Food, QuantityCategory.Uncountable, 10, 5);
    }

    @Test
    public void testSettersGetters() {
        Report report = new Report();
        report.addCashFromCustomer(DecimalUtils.toCurrency(100), adult);
        report.addCashFromCustomer(DecimalUtils.toCurrency(40), child);
        report.addCashFromCustomer(DecimalUtils.toCurrency(20), child);

        report.addSoldProduct(coffee, Quantity.countable(5));
        report.addSoldProduct(coffee, Quantity.countable(1));
        report.addSoldProduct(milk, Quantity.uncountable(1.3));
        report.addSoldProduct(milk, Quantity.uncountable(1.2));

        assertEquals(DecimalUtils.toCurrency(160), report.getTotalCash());
        assertEquals(DecimalUtils.toCurrency(100), report.getCashPerCategory(Customer.Category.Adult));
        assertEquals(DecimalUtils.toCurrency(60), report.getCashPerCategory(Customer.Category.Child));
        assertEquals(DecimalUtils.toCurrency(0), report.getCashPerCategory(Customer.Category.Retired));

        assertEquals(Quantity.countable(6), report.getSoldQuantity(this.coffee));
        assertEquals(Quantity.uncountable(2.5), report.getSoldQuantity(this.milk));
    }

    @Test
    public void testToString() {
        Report report = new Report();
        report.addCashFromCustomer(DecimalUtils.toCurrency(100), adult);
        report.addCashFromCustomer(DecimalUtils.toCurrency(40), child);
        report.addCashFromCustomer(DecimalUtils.toCurrency(20), child);

        report.addSoldProduct(coffee, Quantity.countable(5));
        report.addSoldProduct(coffee, Quantity.countable(1));
        report.addSoldProduct(milk, Quantity.uncountable(1.3));
        report.addSoldProduct(milk, Quantity.uncountable(1.2));

        String result = report.toString();
        assertTrue(result.contains("adults"));
        assertTrue(result.contains("children"));
        assertTrue(!result.contains("retired customers"));
        assertTrue(result.contains(this.milk.getName()));
        assertTrue(result.contains(this.coffee.getName()));
        assertTrue(!result.contains(this.chocolate.getName()));
    }
}
