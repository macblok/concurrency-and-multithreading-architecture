package tasks.taskfive.model;

import java.math.BigDecimal;

public class ExchangeRate {
    private Currency sourceCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;

    public ExchangeRate(Currency sourceCurrency, Currency targetCurrency, BigDecimal rate) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public Currency getSourceCurrency() {
        return sourceCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
