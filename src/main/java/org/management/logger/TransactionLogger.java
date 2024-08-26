package org.management.logger;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class TransactionLogger implements TransactionObserver {

    private static final String LOG_FILE = "transactions.log";

    @Override
    public void onTransaction(String accountNumber, String transactionType, double amount) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.printf("Account: %s | Transaction: %s | Amount: %.2f\n", accountNumber, transactionType, amount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}