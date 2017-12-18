package name.sergey.shambir.models;

import name.sergey.shambir.mocks.MockDiscountInfo;
import name.sergey.shambir.utils.MoneyUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class PriceCalculatorTests extends Assert {
    private MockDiscountInfo discountInfo;
    private Product potato;
    private Product milk;

    @Before
    public void setup()
    {
        this.discountInfo = new MockDiscountInfo();
        this.potato = new Product("potato", Product.Category.Food, new BigDecimal(10), 10);
        this.milk = new Product("milk", Product.Category.Food, new BigDecimal(5), 0);
    }

    @Test
    public void testGetProductCost()
    {
        PriceCalculator calc = new PriceCalculator(this.discountInfo);
        this.discountInfo.discountPercentage = 11;
        BigDecimal cost = calc.getProductsCost(BigDecimal.ZERO, this.milk, 7);
        assertEquals(MoneyUtils.toCurrency(35), cost);

        cost = calc.getProductsCost(new BigDecimal(50), this.potato, 5);
        assertEquals(MoneyUtils.toCurrency(25), cost);

        cost = calc.getProductsCost(new BigDecimal(50), this.potato, 0);
        assertEquals( BigDecimal.ZERO, cost);

        cost = calc.getProductsCost(new BigDecimal(50), this.potato, -2);
        assertEquals(BigDecimal.ZERO, cost);
    }

    @Test
    public void testGetBonuses()
    {
        PriceCalculator calc = new PriceCalculator(this.discountInfo);
        this.discountInfo.discountPercentage = 10;

        BigDecimal value = calc.getProductBonuses(this.milk, new BigDecimal(300));
        assertEquals(MoneyUtils.normalize(BigDecimal.ZERO), value);

        value = calc.getProductBonuses(this.potato, new BigDecimal(200));
        assertEquals(MoneyUtils.toCurrency(20), value);

        value = calc.getProductBonuses(this.potato, new BigDecimal(-10));
        assertEquals(BigDecimal.ZERO, value);
    }

    @Test
    public void testGetCustomerBasketCost()
    {
        final int expectedDiscount = 20;
        final BigDecimal expectedDiscountDecimal = new BigDecimal(expectedDiscount);
        this.discountInfo.discountPercentage = expectedDiscount;
        PriceCalculator calc = new PriceCalculator(this.discountInfo);
        BigDecimal expectedCost;
        BigDecimal expectedBonuses;

        // Customer has empty basket just after creation.
        Customer customer = new Customer("John Doe", Customer.Category.Adult, new BigDecimal(0), new BigDecimal(0));
        expectedCost = BigDecimal.ZERO;
        expectedBonuses = BigDecimal.ZERO;
        assertEquals(expectedCost, calc.getCustomerBasketCost(customer));
        assertEquals(expectedCost, calc.getProductStoreCost(customer, expectedDiscountDecimal));
        assertEquals(expectedBonuses, calc.getCustomerBasketBonuses(customer));
        assertEquals(expectedBonuses, calc.getProductStoreBonuses(customer, expectedDiscountDecimal));

        // Potato adds both cost and bonuses.
        customer.putProduct(this.potato, 12);
        expectedCost = MoneyUtils.toCurrency(12 * 10 * 0.8);
        expectedBonuses = MoneyUtils.normalize(expectedCost.multiply(MoneyUtils.toCurrency(0.10)));
        assertEquals(expectedCost, calc.getCustomerBasketCost(customer));
        assertEquals(expectedCost, calc.getProductStoreCost(customer, expectedDiscountDecimal));
        assertEquals(expectedBonuses, calc.getCustomerBasketBonuses(customer));
        assertEquals(expectedBonuses, calc.getProductStoreBonuses(customer, expectedDiscountDecimal));

        // Milk adds cost, but doesn't add bonuses.
        customer.putProduct(this.milk, 5);
        expectedCost = expectedCost.add(MoneyUtils.toCurrency(5 * 5 * 0.8));
        assertEquals(expectedCost, calc.getCustomerBasketCost(customer));
        assertEquals(expectedCost, calc.getProductStoreCost(customer, expectedDiscountDecimal));
        assertEquals(expectedBonuses, calc.getCustomerBasketBonuses(customer));
        assertEquals(expectedBonuses, calc.getProductStoreBonuses(customer, expectedDiscountDecimal));
    }
}
