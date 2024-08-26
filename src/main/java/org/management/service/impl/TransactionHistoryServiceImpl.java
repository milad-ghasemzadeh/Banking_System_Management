package org.management.service.impl;

import org.management.domain.BankAccount;
import org.management.domain.TransactionHistory;
import org.management.dto.TransactionHistoryDTO;
import org.management.exception.CustomException;
import org.management.exception.ErrorMessageConstants;
import org.management.logger.TransactionLogger;
import org.management.mapper.BankAccountMapper;
import org.management.mapper.TransactionHistoryMapper;
import org.management.repository.BankAccountRepository;
import org.management.repository.TransactionHistoryRepository;
import org.management.service.TransactionHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class TransactionHistoryServiceImpl implements TransactionHistoryService {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TransactionHistoryRepository transactionHistoryRepository;
    private final TransactionHistoryMapper transactionHistoryMapper;
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;
    private final ExecutorService executorService;
    private final TransactionLogger transactionLogger;


    public TransactionHistoryServiceImpl(TransactionHistoryRepository transactionHistoryRepository,
                                         TransactionHistoryMapper transactionHistoryMapper,
                                         BankAccountRepository bankAccountRepository,
                                         BankAccountMapper bankAccountMapper,
                                         ExecutorService executorService, TransactionLogger transactionLogger) {

        this.transactionHistoryRepository = transactionHistoryRepository;
        this.transactionHistoryMapper = transactionHistoryMapper;
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountMapper = bankAccountMapper;
        this.executorService = executorService;
        this.transactionLogger = transactionLogger;
    }

    @Override
    public TransactionHistoryDTO deposit(Long accountId, double fund) throws ExecutionException, InterruptedException {

        final TransactionHistoryDTO transactionHistoryDTO = new TransactionHistoryDTO();

        Future<?> future = executorService.submit(() -> {

            BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);

            if (checkBankAccountID(bankAccount)) {
                synchronized (bankAccount) {

                    transactionHistoryDTO.setDeposit(fund);
                    transactionHistoryDTO.setBankAccountDepositor(bankAccountMapper.toDto(bankAccount));

                    bankAccount.setBalance(bankAccount.getBalance() + fund);
                    bankAccountRepository.save(bankAccount);

                    TransactionHistory transactionHistory = transactionHistoryMapper.toEntity(transactionHistoryDTO);
                    transactionHistory = transactionHistoryRepository.save(transactionHistory);
                    transactionHistoryDTO.setId(transactionHistory.getId());
                    transactionHistoryDTO.getBankAccountDepositor().setBalance(bankAccount.getBalance());
                    transactionLogger.onTransaction((accountId.toString()), "Deposit", fund);

                }
            }
        });

        // Wait for the task to complete
        future.get();
        return transactionHistoryDTO;
    }

    @Override
    public TransactionHistoryDTO withdrawal(Long accountId, double fund) throws ExecutionException, InterruptedException {

        final TransactionHistoryDTO transactionHistoryDTO = new TransactionHistoryDTO();


        Future<?> future = executorService.submit(() -> {

            BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);

            synchronized (bankAccount) {


                transactionHistoryDTO.setWithdrawal(fund);
                transactionHistoryDTO.setBankAccountWithdrawal(bankAccountMapper.toDto(bankAccount));


                if (checkBankAccountID(bankAccount) && checkEnoughMoney(bankAccount.getBalance(), fund)) {
                    bankAccount.setBalance(bankAccount.getBalance() - fund);
                }

                bankAccountRepository.save(bankAccount);


                TransactionHistory transactionHistory = transactionHistoryMapper.toEntity(transactionHistoryDTO);
                transactionHistory = transactionHistoryRepository.save(transactionHistory);
                transactionHistoryDTO.setId(transactionHistory.getId());
                transactionHistoryDTO.getBankAccountWithdrawal().setBalance(bankAccount.getBalance());
                transactionLogger.onTransaction((accountId.toString()), "Withdrawal", fund);


            }

        });

        // Wait for the task to complete
        future.get();
        return transactionHistoryDTO;
    }


    @Override
    public Page transactionHistory(Pageable pageable, Long bankAccountId) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId).orElse(null);
        if (checkBankAccountID(bankAccount)) {
            return transactionHistoryRepository.searchAllByBankAccount(pageable, bankAccountId).map(transactionHistoryMapper::toDto);
        }
        return null;
    }

    @Override
    public TransactionHistoryDTO transferFund(Long senderId, Long receiverID, double fund) throws ExecutionException, InterruptedException {

        final TransactionHistoryDTO transactionHistoryDTO = new TransactionHistoryDTO();


        Future<?> future = executorService.submit(() -> {

            BankAccount bankAccountSender = bankAccountRepository.findById(senderId).orElse(null);

            synchronized (bankAccountSender) {

                if (checkBankAccountID(bankAccountSender) && checkEnoughMoney(bankAccountSender.getBalance(), fund)) {
                    bankAccountSender.setBalance(bankAccountSender.getBalance() - fund);
                }


                BankAccount bankAccountReceiver = bankAccountRepository.findById(receiverID).orElse(null);

                if (checkBankAccountID(bankAccountReceiver)) {
                    bankAccountReceiver.setBalance(bankAccountReceiver.getBalance() + fund);
                }


                transactionHistoryDTO.setDeposit(fund);
                transactionHistoryDTO.setBankAccountDepositor(bankAccountMapper.toDto(bankAccountReceiver));

                transactionHistoryDTO.setWithdrawal(fund);
                transactionHistoryDTO.setBankAccountWithdrawal(bankAccountMapper.toDto(bankAccountSender));


                bankAccountRepository.save(bankAccountSender);
                bankAccountRepository.save(bankAccountReceiver);


                TransactionHistory transactionHistory = transactionHistoryMapper.toEntity(transactionHistoryDTO);
                transactionHistory = transactionHistoryRepository.save(transactionHistory);
                transactionHistoryDTO.setId(transactionHistory.getId());
                transactionHistoryDTO.getBankAccountDepositor().setBalance(bankAccountReceiver.getBalance());
                transactionHistoryDTO.getBankAccountWithdrawal().setBalance(bankAccountSender.getBalance());
                transactionLogger.onTransaction((receiverID.toString()), "Deposit", fund);
                transactionLogger.onTransaction((senderId.toString()), "Withdrawal", fund);


            }

        });

        // Wait for the task to complete
        future.get();
        return transactionHistoryDTO;
    }

    // check if this bank account exists
    private boolean checkBankAccountID(BankAccount bankAccount) {
        if (bankAccount == null) {
            logger.error(ErrorMessageConstants.CouldNotFindBankAccount.developer_message);
            throw new CustomException(ErrorMessageConstants.CouldNotFindBankAccount.message,
                    ErrorMessageConstants.CouldNotFindBankAccount.developer_message,
                    ErrorMessageConstants.CouldNotFindBankAccount.status);
        }
        return true;
    }

    //check if we have enough money to withdrawal
    private boolean checkEnoughMoney(double balance, double withdrawal) {

        if (balance < withdrawal) {
            logger.error(ErrorMessageConstants.NotEnoughMoney.developer_message);
            throw new CustomException(ErrorMessageConstants.NotEnoughMoney.message,
                    ErrorMessageConstants.NotEnoughMoney.developer_message,
                    ErrorMessageConstants.NotEnoughMoney.status);
        }
        return true;
    }


    @PreDestroy
    public void shutdownExecutorService() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
