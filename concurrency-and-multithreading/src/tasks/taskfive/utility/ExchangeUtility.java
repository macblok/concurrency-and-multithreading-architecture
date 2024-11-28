package tasks.taskfive.utility;

import tasks.taskfive.model.Currency;
import tasks.taskfive.model.ExchangeRate;

import java.math.BigDecimal;
import java.util.Map;

public class ExchangeUtility {
    public static BigDecimal convertCurrency(BigDecimal amount, Currency sourceCurrency, Currency targetCurrency, Map<Currency, Map<Currency, ExchangeRate>> exchangeRates) {
        if (sourceCurrency.equals(targetCurrency)) {
            return amount;
        }

        Map<Currency, ExchangeRate> innerMap = exchangeRates.get(sourceCurrency);
        if (innerMap != null) {
            ExchangeRate exchangeRate = innerMap.get(targetCurrency);
            if (exchangeRate != null) {
                return amount.multiply(exchangeRate.getRate());
            }
        }
        throw new IllegalStateException("Exchange rate not found for " + sourceCurrency.getCode() + " to " + targetCurrency.getCode());
    }
}
