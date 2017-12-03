package name.sergey.shambir.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Random;

public class EnumRandomGeneratorTests extends Assert {
    private enum Language { Russian, English, French }

    private static final long[] SEEDS = {42, 9147124, 54281771};
    private static final int SIMULATION_COUNT = 1000;
    private static double MAX_DEVIATION = 0.05;

    @Test
    public void testSetWeight() {
        for (long seed : SEEDS) {
            Random random = new Random(seed);
            EnumRandomGenerator<Language> generator = new EnumRandomGenerator<>(random, Language.class);

            // By default, weight is 1 and probability is near to 0.(3)
            double probability = getConstantProbability(generator, Language.English);
            assertEquals(1.0 / 3.0, probability, MAX_DEVIATION);

            probability = getConstantProbability(generator, Language.Russian);
            assertEquals(1.0 / 3.0, probability, MAX_DEVIATION);

            // With weight 2.0 probability will increase up to 0.5
            generator.setWeight(Language.Russian, 2.0);
            probability = getConstantProbability(generator, Language.Russian);
            assertEquals(1.0 / 2.0, probability, MAX_DEVIATION);

            // With weight 10000 probability is near to 1
            generator.setWeight(Language.French, 10000.0);
            probability = getConstantProbability(generator, Language.French);
            assertEquals(1.0, probability, MAX_DEVIATION);
        }
    }

    /**
     * Returns constant probability at simulation in range [0..1]
     * @param generator - random enum constants generator
     * @param language - enum constant which probability should be counted
     * @return
     */
    private static double getConstantProbability(EnumRandomGenerator<Language> generator, Language language) {
        int constantCont = 0;
        for (int i = 0; i < SIMULATION_COUNT; ++i) {
            if (generator.nextValue() == language) {
                ++constantCont;
            }
        }
        return (double)constantCont / (double)SIMULATION_COUNT;
    }
}
