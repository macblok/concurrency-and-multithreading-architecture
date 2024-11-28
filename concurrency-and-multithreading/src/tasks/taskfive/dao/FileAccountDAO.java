package tasks.taskfive.dao;


import tasks.taskfive.model.Account;

import java.io.*;

public class FileAccountDAO implements AccountDAO {
    private String directoryPath;

    public FileAccountDAO(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public Account getAccount(String accountId) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getAccountFilePath(accountId)))) {
            return (Account) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAccount(Account account) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getAccountFilePath(account.getAccountId())))) {
            oos.writeObject(account);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getAccountFilePath(String accountId) {
        return directoryPath + File.separator + accountId + ".dat";
    }
}
