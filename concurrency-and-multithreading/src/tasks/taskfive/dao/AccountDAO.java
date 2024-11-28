package tasks.taskfive.dao;

import tasks.taskfive.model.Account;

public interface AccountDAO {
    Account getAccount(String accountId);
    void saveAccount(Account account);
}
