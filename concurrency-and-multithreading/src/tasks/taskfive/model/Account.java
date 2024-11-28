package tasks.taskfive.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

public class Account implements Serializable {
    private String accountId;
    private ConcurrentHashMap<Currency, BigDecimal> balances;

    public Account(String accountId) {
        this.accountId = accountId;
        this.balances = new ConcurrentHashMap<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public ConcurrentHashMap<Currency, BigDecimal> getBalances() {
        return balances;
    }
}
