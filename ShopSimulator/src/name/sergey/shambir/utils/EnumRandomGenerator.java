package name.sergey.shambir.utils;

import java.util.Arrays;
import java.util.Random;

public class EnumRandomGenerator<E> {
    private final Random random;
    private final E[] values;
    private double[] weights;
    private double[] weightsPrefixSum;

    public EnumRandomGenerator(Random random, Class<E> enumClass)
    {
        this.random = random;
        this.values = enumClass.getEnumConstants();
        assert values.length != 0;
        this.weights = new double[this.values.length];
        Arrays.fill(weights, 1);
    }

    /**
     * Sets custom weight for given value (default weight is 1).
     * @param value - value with custom weight
     * @param weight - weight value, must be positive.
     */
    public void setWeight(E value, double weight)
    {
        assert weight > 0;
        int valueIndex = Arrays.binarySearch(this.values, value);
        if (valueIndex < 0)
        {
            throw new RuntimeException("Unexpected enum value");
        }
        this.weights[valueIndex] = weight;

        // Invalidate prefix sum.
        this.weightsPrefixSum = null;
    }

    public E nextValue()
    {
        lazyInitPrefixSum();
        final double totalWeight = weightsPrefixSum[values.length - 1];
        final double event = random.nextFloat() * totalWeight;

        // Ignore last value since (event < totalWeight).
        for (int i = values.length - 2; i >= 0; --i)
        {
            if (weightsPrefixSum[i] <= event)
            {
                return values[i + 1];
            }
        }
        return values[0];
    }

    private void lazyInitPrefixSum()
    {
        if (this.weightsPrefixSum == null)
        {
            this.weightsPrefixSum = getPrefixSum(this.weights);
        }
    }

    private static double[] getPrefixSum(double[] values)
    {
        assert values.length != 0;
        double[] sum = new double[values.length];
        sum[0] = values[0];
        for (int i = 1; i < values.length; ++i)
        {
            sum[i] = sum[i - 1] + values[i];
        }
        return sum;
    }
}
