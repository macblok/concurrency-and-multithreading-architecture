package tasks.taskfive.service;

import tasks.taskfive.dao.AccountDAO;
import tasks.taskfive.exception.AccountNotFoundException;
import tasks.taskfive.exception.InsufficientFundsException;
import tasks.taskfive.model.Account;
import tasks.taskfive.model.Currency;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

public class AccountService {
    private final AccountDAO accountDao;
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Logger logger = Logger.getLogger(AccountService.class.getName());

    public AccountService(AccountDAO accountDao) {
        this.accountDao = accountDao;
    }

    public void deposit(String accountId, Currency currency, BigDecimal amount) {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try {
            Account account = accountDao.getAccount(accountId);
            if (account == null) {
                throw new AccountNotFoundException("Account not found: " + accountId);
            }
            account.getBalances().merge(currency, amount, BigDecimal::add);
            accountDao.saveAccount(account);
            logger.info("Deposited " + amount + " " + currency.getCode() + " to account ID " + accountId);
        } finally {
            writeLock.unlock();
        }
    }

    public void withdraw(String accountId, Currency currency, BigDecimal amount) {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try {
            Account account = accountDao.getAccount(accountId);
            if (account == null) {
                throw new AccountNotFoundException("Account not found: " + accountId);
            }
            BigDecimal currentBalance = account.getBalances().getOrDefault(currency, BigDecimal.ZERO);
            if (currentBalance.compareTo(amount) < 0) {
                throw new InsufficientFundsException("Insufficient funds for withdrawal");
            }
            account.getBalances().put(currency, currentBalance.subtract(amount));
            accountDao.saveAccount(account);
            logger.info("Withdrew " + amount + " " + currency.getCode() + " from account ID " + accountId);
        } finally {
            writeLock.unlock();
        }
    }
}
