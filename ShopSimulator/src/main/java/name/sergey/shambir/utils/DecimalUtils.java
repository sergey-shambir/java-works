package name.sergey.shambir.utils;

import java.math.BigDecimal;

public class DecimalUtils {
    public static final BigDecimal ZERO_CURRENCY = normalizeCurrency(BigDecimal.ZERO);
    public static final BigDecimal ZERO_AMOUNT = normalizeAmount(BigDecimal.ZERO);

    public static BigDecimal normalizeCurrency(BigDecimal value) {
        return value.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public static BigDecimal normalizeAmount(BigDecimal value) {
        return value.setScale(3, BigDecimal.ROUND_HALF_EVEN);
    }

    public static BigDecimal normalizeNumber(BigDecimal value) {
        return value.setScale(0, BigDecimal.ROUND_HALF_EVEN);
    }

    public static BigDecimal toCurrency(double value) {
        return normalizeCurrency(new BigDecimal(value));
    }

    public static BigDecimal toAmount(double value) {
        return normalizeAmount(new BigDecimal(value));
    }

    public static BigDecimal toNumber(double value) {
        return normalizeNumber(new BigDecimal(value));
    }
}
