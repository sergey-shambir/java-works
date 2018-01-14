package name.sshambir.random;

import java.util.Random;

public class EasyRandom {
    private final Random random;

    public EasyRandom(Random random) {
        this.random = random;
    }

    public final int nextIntInRange(int min, int max) {
        return min + this.random.nextInt(1 + max - min);
    }

    public final double nextDoubleInRange(double min, double max) {
        return min + this.random.nextDouble() * (max - min);
    }

    public final <T> T nextItem(T[] items) {
        final int index = this.random.nextInt(items.length);
        return items[index];
    }

    // Returns random values in range [mean - standardDeviation, mean + stddev],
    // where mean is median value and
    //  stddev is standard deviation for Normal distribution.
    public double nextClampedGaussian(double mean, double stddev) {
        // Try to generate up to 10 times.
        for (int i = 0; i < 10; ++i) {
            double value = random.nextGaussian() * stddev + mean;
            if (value >= mean - stddev && value <= mean + stddev) {
                return value;
            }
        }
        // There is low probability that we still cannot get clamped value.
        return mean;
    }
}
