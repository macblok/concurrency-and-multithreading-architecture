package tasks.taskfive;

import tasks.taskfive.dao.FileAccountDAO;
import tasks.taskfive.model.Currency;
import tasks.taskfive.model.ExchangeRate;
import tasks.taskfive.module.CurrencyExchangeModule;
import tasks.taskfive.service.AccountService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //TODO rewrite whole task 5

        String directoryPath = "accounts";

        // First, generate the sample account file
        AccountFileGenerator.generateSampleAccountFile("12345", directoryPath);

        // Initialize the necessary services and modules
        FileAccountDAO accountDao = new FileAccountDAO(directoryPath);
        AccountService accountService = new AccountService(accountDao);
        Currency usd = new Currency("USD");
        Currency eur = new Currency("EUR");

        // Simulate setting exchange rates (normally this would come from a service or be pre-configured)
        Map<Currency, Map<Currency, ExchangeRate>> exchangeRates = new HashMap<>();
        Map<Currency, ExchangeRate> usdRates = new HashMap<>();
        usdRates.put(eur, new ExchangeRate(usd, eur, new BigDecimal("0.85")));
        exchangeRates.put(usd, usdRates);

        CurrencyExchangeModule exchangeModule = new CurrencyExchangeModule(accountService, exchangeRates);

        // Load and print initial state of the account
        System.out.println("Initial account state:");
        printAccountState(accountDao.getAccount("12345"));

        // Suppose the user wants to perform a currency exchange
        exchangeModule.exchangeCurrency("12345", usd, eur, new BigDecimal("100"));

        // Let the exchanges process
        try {
            Thread.sleep(2000);  // Pause to allow currency exchange tasks to complete
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        // Print the updated state of the account
        System.out.println("Updated account state:");
        printAccountState(accountDao.getAccount("12345"));

        // Properly shutdown the executor in CurrencyExchangeModule
        exchangeModule.shutdown();
    }

    private static void printAccountState(tasks.taskfive.model.Account account) {
        account.getBalances().forEach((currency, amount) ->
                System.out.println(currency.getCode() + ": " + amount)
        );
    }
}