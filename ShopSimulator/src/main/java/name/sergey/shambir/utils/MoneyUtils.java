package name.sergey.shambir.utils;

import java.math.BigDecimal;

public class MoneyUtils {
    public static BigDecimal normalize(BigDecimal value) {
        return value.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public static BigDecimal toCurrency(double value) {
        return normalize(new BigDecimal(value));
    };
}
