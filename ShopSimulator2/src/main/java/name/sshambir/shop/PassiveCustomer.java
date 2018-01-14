package name.sshambir.shop;

import name.sshambir.product.CustomerCategory;
import name.sshambir.product.DecimalUtils;

import java.math.BigDecimal;

public class PassiveCustomer extends BasketOwner implements SolventClient {
    private final String name;
    private final CustomerCategory customerCategory;
    private BigDecimal cashOnCard;
    private BigDecimal netCash;
    private BigDecimal bonuses;

    public PassiveCustomer(String name, CustomerCategory customerCategory) {
        this.name = name;
        this.customerCategory = customerCategory;
        this.bonuses = BigDecimal.ZERO;
        this.cashOnCard = BigDecimal.ZERO;
        this.netCash = BigDecimal.ZERO;
    }

    public void addBonuses(BigDecimal diff) {
        assert diff.compareTo(BigDecimal.ZERO) >= 0;
        this.bonuses = DecimalUtils.normalizeCurrency(bonuses.add(diff));
    }

    public void addCardCash(BigDecimal diff) {
        assert diff.compareTo(BigDecimal.ZERO) >= 0;
        this.cashOnCard = DecimalUtils.normalizeCurrency(cashOnCard.add(diff));
    }

    public void addNetCash(BigDecimal diff) {
        assert diff.compareTo(BigDecimal.ZERO) >= 0;
        this.netCash = DecimalUtils.normalizeCurrency(netCash.add(diff));
    }

    @Override
    public CustomerCategory getCustomerCategory() {
        return this.customerCategory;
    }
}
