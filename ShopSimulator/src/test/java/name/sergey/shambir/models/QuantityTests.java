package name.sergey.shambir.models;

import name.sergey.shambir.quantity.Quantity;
import name.sergey.shambir.quantity.QuantityCategory;
import name.sergey.shambir.utils.DecimalUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class QuantityTests extends Assert {
    @Test
    public void testCountable() {
        Quantity zero = new Quantity(BigDecimal.ZERO, QuantityCategory.Countable);
        assertTrue(zero.isZero());
        assertEquals(zero.value(), BigDecimal.ZERO);
        assertEquals(zero.getCategory(), QuantityCategory.Countable);

        Quantity nonZero = new Quantity(new BigDecimal(101.2), QuantityCategory.Countable);
        assertFalse(nonZero.isZero());
        assertEquals(nonZero.value(), DecimalUtils.toNumber(101));
        assertEquals(zero.getCategory(), QuantityCategory.Countable);

        assertEquals(zero.compareTo(zero), 0);
        assertEquals(nonZero.compareTo(nonZero), 0);
        assertEquals(zero.compareTo(nonZero), -1);
        assertEquals(nonZero.compareTo(zero), 1);
    }

    @Test
    public void testUncountable() {
        Quantity zero = new Quantity(BigDecimal.ZERO, QuantityCategory.Uncountable);
        assertTrue(zero.isZero());
        assertEquals(zero.value(), DecimalUtils.ZERO_AMOUNT);
        assertEquals(zero.getCategory(), QuantityCategory.Uncountable);

        Quantity nonZero = new Quantity(new BigDecimal(101.23819), QuantityCategory.Uncountable);
        assertFalse(nonZero.isZero());
        assertEquals(nonZero.value(), DecimalUtils.toAmount(101.238));
        assertEquals(zero.getCategory(), QuantityCategory.Uncountable);

        assertEquals(zero.compareTo(zero), 0);
        assertEquals(nonZero.compareTo(nonZero), 0);
        assertEquals(zero.compareTo(nonZero), -1);
        assertEquals(nonZero.compareTo(zero), 1);
    }

    @Test(expected = RuntimeException.class)
    public void testConstructNegativeCountable() {
        new Quantity(new BigDecimal(-1), QuantityCategory.Countable);
    }

    @Test(expected = RuntimeException.class)
    public void testConstructNegativeUncountable() {
        new Quantity(new BigDecimal(-2), QuantityCategory.Uncountable);
    }

    @Test
    public void testAddCountable() {
        Quantity a = Quantity.countable(192);
        Quantity b = Quantity.countable(101);
        assertEquals(a.add(b).value(), a.value().add(b.value()));
    }

    @Test
    public void testAddUncountable() {
        Quantity a = Quantity.uncountable(10.5);
        Quantity b = Quantity.uncountable(1.4);
        assertEquals(a.add(b).value(), a.value().add(b.value()));
    }

    @Test(expected = RuntimeException.class)
    public void testAddDifferent() {
        Quantity a = Quantity.countable(10);
        Quantity b = Quantity.uncountable(8.72);
        a.add(b);
    }

    @Test
    public void testSubtractCountable() {
        Quantity a = Quantity.countable(192);
        Quantity b = Quantity.countable(101);
        assertEquals(a.subtract(b).value(), a.value().subtract(b.value()));
        assertFalse(a.subtract(b).isZero());
    }

    @Test
    public void testSubtractCountableEqual() {
        Quantity a = Quantity.countable(101);
        Quantity b = Quantity.countable(101);
        assertTrue(a.subtract(b).isZero());
    }

    @Test(expected = RuntimeException.class)
    public void testSubtractCountableGreater() {
        Quantity a = Quantity.countable(101);
        Quantity b = Quantity.countable(102);
        a.subtract(b).value();
    }

    @Test
    public void testSubtractUncountable() {
        Quantity a = Quantity.uncountable(1.88);
        Quantity b = Quantity.uncountable(1.12);
        assertEquals(a.subtract(b).value(), a.value().subtract(b.value()));
        assertFalse(a.subtract(b).isZero());
    }

    @Test
    public void testSubtractUncountableEqual() {
        Quantity a = Quantity.uncountable(101);
        Quantity b = Quantity.uncountable(101);
        assertTrue(a.subtract(b).isZero());
    }

    @Test(expected = RuntimeException.class)
    public void testSubtractUncountableGreater() {
        Quantity a = Quantity.uncountable(101);
        Quantity b = Quantity.uncountable(101.5);
        a.subtract(b).value();
    }
}
