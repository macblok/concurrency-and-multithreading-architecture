package tasks.taskfive.module;

import tasks.taskfive.model.Account;
import tasks.taskfive.model.Currency;
import tasks.taskfive.model.ExchangeRate;
import tasks.taskfive.service.AccountService;
import tasks.taskfive.utility.ExchangeUtility;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class CurrencyExchangeModule {

    private final AccountService accountService;
    private final Map<Currency, Map<Currency, ExchangeRate>> exchangeRates;
    private final ExecutorService executorService;
    private final Logger logger = Logger.getLogger(CurrencyExchangeModule.class.getName());

    public CurrencyExchangeModule(AccountService accountService, Map<Currency, Map<Currency, ExchangeRate>> exchangeRates) {
        this.accountService = accountService;
        this.exchangeRates = exchangeRates;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public void exchangeCurrency(String accountId, Currency fromCurrency, Currency toCurrency, BigDecimal amount) {
        executorService.submit(() -> {
            BigDecimal convertedAmount = ExchangeUtility.convertCurrency(amount, fromCurrency, toCurrency, exchangeRates);
            logger.info("Converted " + amount + " " + fromCurrency.getCode() + " to " + convertedAmount + " " + toCurrency.getCode());
            accountService.withdraw(accountId, fromCurrency, amount);
            accountService.deposit(accountId, toCurrency, convertedAmount);
        });
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
