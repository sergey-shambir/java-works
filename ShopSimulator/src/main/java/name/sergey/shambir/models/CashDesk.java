package name.sergey.shambir.models;

import name.sergey.shambir.utils.MoneyUtils;

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
        this.cash = MoneyUtils.normalize(this.cash.add(value));
    }
}
