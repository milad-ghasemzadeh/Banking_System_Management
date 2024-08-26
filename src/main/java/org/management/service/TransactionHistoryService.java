package org.management.service;

import org.management.dto.TransactionHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.ExecutionException;

public interface TransactionHistoryService {

    // deposit to account
    TransactionHistoryDTO deposit(Long accountId, double fund) throws ExecutionException, InterruptedException;

    // withdrawal from account
    TransactionHistoryDTO withdrawal(Long accountId, double fund) throws ExecutionException, InterruptedException;

    // transfer fund between two accounts
    TransactionHistoryDTO transferFund(Long senderId, Long receiverID, double fund) throws ExecutionException, InterruptedException;

    // transactionHistory for specific bank account id
    Page transactionHistory(Pageable pageable, Long bankAccountId);

}
