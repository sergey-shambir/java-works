package name.sergey.shambir.models;

import name.sergey.shambir.utils.DecimalUtils;

import java.math.BigDecimal;

public class CashDesk implements CashStorage {
    private BigDecimal cash;

    public CashDesk() {
        this.cash = BigDecimal.ZERO;
    }

    public BigDecimal getCash() {
        return cash;
    }

    @Override
    public void addCash(BigDecimal value) {
        this.cash = DecimalUtils.normalizeCurrency(this.cash.add(value));
    }
}
