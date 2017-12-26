package name.sergey.shambir.quantity;

import name.sergey.shambir.random.EasyRandom;
import name.sergey.shambir.utils.DecimalUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Random;

public class QuantityGeneratorTests extends Assert {
    private static final int SIMULATION_COUNT = 1000;
    private static final long[] SEEDS = {42, 9147124, 54281771};

    @Test
    public void testNextQuantity() {
        Quantity zeroAmount = Quantity.countable(0);
        Quantity maxAmount = Quantity.countable(1000);

        Quantity zeroNumber = Quantity.uncountable(0);
        Quantity maxNumber = Quantity.uncountable(1000);

        for (long seed : SEEDS) {
            QuantityGenerator generator = new QuantityGenerator(new EasyRandom(new Random(seed)));
            for (int i = 0; i < SIMULATION_COUNT; ++i) {
                Quantity amount = generator.nextQuantity(maxAmount);
                assertTrue(amount.compareTo(zeroAmount) >= 0);
                assertTrue(amount.compareTo(maxAmount) <= 0);

                Quantity number = generator.nextQuantity(maxNumber);
                assertTrue(number.compareTo(zeroNumber) >= 0);
                assertTrue(number.compareTo(maxNumber) <= 0);
            }
        }
    }

    @Test
    public void testNextQuantityInRange() {
        BigDecimal min = DecimalUtils.toNumber(10);
        BigDecimal max = DecimalUtils.toNumber(1000);
        for (long seed : SEEDS) {
            QuantityGenerator generator = new QuantityGenerator(new EasyRandom(new Random(seed)));
            for (int i = 0; i < SIMULATION_COUNT; ++i) {
                Quantity quantity = generator.nextQuantityInRange(min.doubleValue(), max.doubleValue());
                assertTrue(quantity.value().compareTo(min) >= 0);
                assertTrue(quantity.value().compareTo(max) <= 0);
            }
        }
    }
}
