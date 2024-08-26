package org.management.service.impl;

import org.management.domain.BankAccount;
import org.management.dto.BankAccountDTO;
import org.management.exception.CustomException;
import org.management.exception.ErrorMessageConstants;
import org.management.mapper.BankAccountMapper;
import org.management.repository.BankAccountRepository;
import org.management.repository.CustomerRepository;
import org.management.repository.TransactionHistoryRepository;
import org.management.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final BankAccountMapper bankAccountMapper;
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;


    public BankAccountServiceImpl(BankAccountMapper bankAccountMapper,
                                  TransactionHistoryRepository transactionHistoryRepository,
                                  BankAccountRepository bankAccountRepository,
                                  CustomerRepository customerRepository) {
        this.bankAccountMapper = bankAccountMapper;
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public BankAccountDTO save(BankAccountDTO bankAccountDTO, int state) {

        // check the account_Id
        if (state == 1) {
            if (!bankAccountRepository.existsById(bankAccountDTO.getId())) {

                logger.error(ErrorMessageConstants.NotExistID.developer_message);
                throw new CustomException(ErrorMessageConstants.NotExistID.message,
                        ErrorMessageConstants.NotExistID.developer_message,
                        ErrorMessageConstants.NotExistID.status);
            }
        }


        // save or update the bank account
        BankAccount bankAccount = bankAccountMapper.toEntity(bankAccountDTO);
        bankAccount = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.toDto(bankAccount);

    }


    @Override
    public BankAccountDTO getBankAccount(Long id) {
        return bankAccountMapper.toDto(bankAccountRepository.findById(id).orElse(null));
    }

    @Override
    public Page searchBankAccount(Pageable pageable, Long customerId) {
        // search for the customer
        if (customerId == null) {
            return bankAccountRepository.findAll(pageable).map(bankAccountMapper::toDto);

        }
        return bankAccountRepository.searchAllByCustomer_Id(pageable, customerId).map(bankAccountMapper::toDto);
    }
}
