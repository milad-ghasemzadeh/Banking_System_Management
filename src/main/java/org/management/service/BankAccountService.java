package org.management.service;

import org.management.dto.BankAccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BankAccountService {

    // for save or update Customer
    BankAccountDTO save(BankAccountDTO bankAccountDTO, int state);

    // search all customers
    Page searchBankAccount(Pageable pageable, Long customerId);

    // get a bank according to id
    BankAccountDTO getBankAccount(Long id);

}
