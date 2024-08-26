package org.management.logger;

public interface TransactionObserver {
    void onTransaction(String accountNumber, String transactionType, double amount);
}
