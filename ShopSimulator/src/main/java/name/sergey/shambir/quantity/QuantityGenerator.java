package name.sergey.shambir.quantity;

import name.sergey.shambir.random.EasyRandom;
import name.sergey.shambir.random.EnumRandomGenerator;

import java.math.BigDecimal;

public class QuantityGenerator {
    private EasyRandom random;
    private EnumRandomGenerator<QuantityCategory> categoryGenerator;

    public QuantityGenerator(EasyRandom random) {
        this.random = random;
        this.categoryGenerator = new EnumRandomGenerator<>(random, QuantityCategory.class);
    }

    public Quantity nextQuantity(Quantity max) {
        switch (max.getCategory()) {
            case Countable:
                int intValue = random.nextIntInRange(0, max.value().intValue());
                return new Quantity(new BigDecimal(intValue), max.getCategory());
            case Uncountable:
                double doubleValue = random.nextDoubleInRange(0, max.value().doubleValue());
                return new Quantity(new BigDecimal(doubleValue), max.getCategory());
            default:
                throw new RuntimeException("unknown quantity category");
        }
    }

    public Quantity nextQuantityInRange(double min, double max) {
        QuantityCategory category = categoryGenerator.nextValue();
        switch (category) {
            case Countable:
                int intValue = random.nextIntInRange((int)min, (int)max);
                return new Quantity(new BigDecimal(intValue), category);
            case Uncountable:
                double doubleValue = random.nextDoubleInRange(min, max);
                return new Quantity(new BigDecimal(doubleValue), category);
            default:
                throw new RuntimeException("unknown quantity category");
        }
    }
}
