package tasks.taskfive;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import tasks.taskfive.model.Account;
import tasks.taskfive.model.Currency;

public class AccountFileGenerator {
    public static void generateSampleAccountFile(String accountId, String directoryPath) {
        ensureAccountsDirectoryExists(directoryPath);
        createSampleAccountFile(accountId, directoryPath);
    }

    private static void ensureAccountsDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
            System.out.println("Created directory: " + directory.getAbsolutePath());
        }
    }

    private static void createSampleAccountFile(String accountId, String directoryPath) {
        Account account = new Account(accountId);
        Currency usd = new Currency("USD");
        Currency eur = new Currency("EUR");

        account.getBalances().put(usd, new BigDecimal("1000"));
        account.getBalances().put(eur, new BigDecimal("850"));

        File accountFile = new File(directoryPath + File.separator + accountId + ".acc");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(accountFile))) {
            oos.writeObject(account);
            System.out.println("Successfully created sample account file: " + accountFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing account file: " + accountFile.getAbsolutePath());
        }
    }
}
