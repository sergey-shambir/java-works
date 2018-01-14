package name.sshambir.product;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class CostCalculatorTest extends Assert {
    private MockDiscountInfo discountInfo;
    private Product potato;
    private Product milk;

    @Before
    public void setup() {
        this.discountInfo = new MockDiscountInfo();
        this.potato = new Product("potato", Product.Category.Food, QuantityCategory.Uncountable, 10, 10);
        this.milk = new Product("milk", Product.Category.Food, QuantityCategory.Uncountable, 5, 0);
    }

    @Test
    public void testGetProductCost() {
        CostCalculator calc = new CostCalculator(this.discountInfo);
        this.discountInfo.discountPercentage = 11;
        BigDecimal cost = calc.getProductsCost(BigDecimal.ZERO, this.milk, Quantity.uncountable(7));
        assertEquals(DecimalUtils.toCurrency(35), cost);

        cost = calc.getProductsCost(new BigDecimal(50), this.potato, Quantity.uncountable(5));
        assertEquals(DecimalUtils.toCurrency(25), cost);

        cost = calc.getProductsCost(new BigDecimal(50), this.potato, Quantity.uncountable(0));
        assertEquals(BigDecimal.ZERO, cost);
    }

    @Test
    public void testGetBonuses() {
        CostCalculator calc = new CostCalculator(this.discountInfo);
        this.discountInfo.discountPercentage = 10;

        BigDecimal value = calc.getProductBonuses(this.milk, new BigDecimal(300));
        assertEquals(DecimalUtils.normalizeCurrency(BigDecimal.ZERO), value);

        value = calc.getProductBonuses(this.potato, new BigDecimal(200));
        assertEquals(DecimalUtils.toCurrency(20), value);

        value = calc.getProductBonuses(this.potato, new BigDecimal(-10));
        assertEquals(BigDecimal.ZERO, value);
    }

    @Test
    public void testGetCustomerBasketCost() {
        final int expectedDiscount = 20;
        final BigDecimal expectedDiscountDecimal = new BigDecimal(expectedDiscount);
        this.discountInfo.discountPercentage = expectedDiscount;
        CostCalculator calc = new CostCalculator(this.discountInfo);
        BigDecimal expectedCost;
        BigDecimal expectedBonuses;

        Basket basket = new Basket();

        // PassiveCustomer has empty basket just after creation.
        expectedCost = BigDecimal.ZERO;
        expectedBonuses = BigDecimal.ZERO;
        assertEquals(expectedCost, calc.getCustomerBasketCost(basket, CustomerCategory.Adult));
        assertEquals(expectedCost, calc.getProductStoreCost(basket, expectedDiscountDecimal));
        assertEquals(expectedBonuses, calc.getCustomerBasketBonuses(basket, CustomerCategory.Adult));
        assertEquals(expectedBonuses, calc.getProductStoreBonuses(basket, expectedDiscountDecimal));

        // Potato adds both cost and bonuses.
        basket.putProduct(this.potato, Quantity.uncountable(12));
        expectedCost = DecimalUtils.toCurrency(12 * 10 * 0.8);
        expectedBonuses = DecimalUtils.normalizeCurrency(expectedCost.multiply(DecimalUtils.toCurrency(0.10)));
        assertEquals(expectedCost, calc.getCustomerBasketCost(basket, CustomerCategory.Adult));
        assertEquals(expectedCost, calc.getProductStoreCost(basket, expectedDiscountDecimal));
        assertEquals(expectedBonuses, calc.getCustomerBasketBonuses(basket, CustomerCategory.Adult));
        assertEquals(expectedBonuses, calc.getProductStoreBonuses(basket, expectedDiscountDecimal));

        // Milk adds cost, but doesn't add bonuses.
        basket.putProduct(this.milk, Quantity.uncountable(5));
        expectedCost = expectedCost.add(DecimalUtils.toCurrency(5 * 5 * 0.8));
        assertEquals(expectedCost, calc.getCustomerBasketCost(basket, CustomerCategory.Adult));
        assertEquals(expectedCost, calc.getProductStoreCost(basket, expectedDiscountDecimal));
        assertEquals(expectedBonuses, calc.getCustomerBasketBonuses(basket, CustomerCategory.Adult));
        assertEquals(expectedBonuses, calc.getProductStoreBonuses(basket, expectedDiscountDecimal));
    }
}
