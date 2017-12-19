package name.sergey.shambir.models;

import name.sergey.shambir.utils.DecimalUtils;

import java.math.BigDecimal;

public class CashDesk {
    private BigDecimal cash;

    public CashDesk() {
        this.cash = BigDecimal.ZERO;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void addCash(BigDecimal value) {
        this.cash = DecimalUtils.normalizeCurrency(this.cash.add(value));
    }
}
