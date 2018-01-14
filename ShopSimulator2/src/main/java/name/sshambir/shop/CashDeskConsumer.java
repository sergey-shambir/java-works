package name.sshambir.shop;

import java.math.BigDecimal;

public class CashDeskConsumer {
    private BigDecimal cash;

    private void addCash(BigDecimal value) {
        this.cash = this.cash.add(value);
    }
}
