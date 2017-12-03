package name.sergey.shambir.models;

import name.sergey.shambir.utils.MoneyUtils;

import java.math.BigDecimal;

public class Customer {
    public enum Category {
        Child,
        Adult,
        Retired,
    }

    public Basket basket;

    public Customer(String name, Category category, BigDecimal cashOnCard, BigDecimal netCash) {
        this.name = name;
        this.category = category;
        this.bonuses = BigDecimal.ZERO;
        this.cashOnCard = MoneyUtils.normalize(cashOnCard);
        this.netCash = MoneyUtils.normalize(netCash);
        this.basket = new Basket();
    }

    public final String getName() {
        return name;
    }

    public final Category getCategory() {
        return category;
    }

    public void addBonuses(BigDecimal diff) {
        assert(diff.compareTo(BigDecimal.ZERO) >= 0);
        this.bonuses = MoneyUtils.normalize(bonuses.add(diff));
    }

    // Pays money in following order: bonuses first, then netCash, then
    // cashOnCard Returns true if customer did pay.
    public boolean pay(BigDecimal price) {
        return payImpl(price, false);
    }

    // Returns true if customer can pay.
    // Simulates paying money in following order: bonuses first, then netCash,
    // then cashOnCard
    public boolean simulatePay(BigDecimal price) {
        return payImpl(price, false);
    }

    private String name;
    private Category category;
    private BigDecimal cashOnCard;
    private BigDecimal netCash;
    private BigDecimal bonuses;

    private boolean payImpl(BigDecimal price, boolean simulate) {
        assert(price.compareTo(BigDecimal.ZERO) > 0);
        price = MoneyUtils.normalize(price);

        BigDecimal bonuses = this.bonuses;
        BigDecimal netCash = this.netCash;
        BigDecimal cashOnCard = this.cashOnCard;
        boolean succeed = false;
        if (bonuses.compareTo(price) >= 0) {
            bonuses = bonuses.subtract(price);
            succeed = true;
        } else {
            price = price.subtract(bonuses);
            bonuses = BigDecimal.ZERO;
            if (netCash.compareTo(price) >= 0) {
                netCash = netCash.subtract(price);
                succeed = true;
            } else {
                price = price.subtract(netCash);
                netCash = BigDecimal.ZERO;
                if (cashOnCard.compareTo(price) >= 0) {
                    cashOnCard = cashOnCard.subtract(price);
                    succeed = true;
                }
            }
        }

        if (succeed && !simulate) {
            this.bonuses = MoneyUtils.normalize(bonuses);
            this.netCash = MoneyUtils.normalize(netCash);
            this.cashOnCard = MoneyUtils.normalize(cashOnCard);
        }

        return succeed;
    }
}
