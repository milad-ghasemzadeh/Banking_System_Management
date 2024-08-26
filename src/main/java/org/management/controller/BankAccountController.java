package org.management.controller;

import org.management.dto.BankAccountDTO;
import org.management.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BankAccountController {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/bankAccount")
    public ResponseEntity<BankAccountDTO> saveBankAccount(@RequestBody BankAccountDTO bankAccountDTO) {

        if (bankAccountDTO.getId() != null) {
            logger.error("id bankAccountDTO must be a null");
            throw new IllegalArgumentException("id bankAccountDTO must be a null");
        }
        validateBankAccount(bankAccountDTO);
        return new ResponseEntity<>(bankAccountService.save(bankAccountDTO, 0), HttpStatus.OK);

    }

    @PutMapping("/bankAccount")
    public ResponseEntity<BankAccountDTO> updateBankAccount(@RequestBody BankAccountDTO bankAccountDTO) {

        if (bankAccountDTO.getId() == null) {
            logger.error("id bankAccountDTO must be a value");
            throw new IllegalArgumentException("id bankAccountDTO must be a value");
        }
        validateBankAccount(bankAccountDTO);
        return new ResponseEntity<>(bankAccountService.save(bankAccountDTO, 1), HttpStatus.OK);

    }


    @GetMapping("/bankAccount")
    public ResponseEntity<Page> searchBankAccount(Pageable pageable, @RequestParam(required = false) Long customerId) {
        return new ResponseEntity<>(bankAccountService.searchBankAccount(pageable, customerId), HttpStatus.OK);
    }


    @GetMapping("/bankAccount/{id}")
    public ResponseEntity<BankAccountDTO> getBankAccount(@PathVariable Long id) {
        return new ResponseEntity<>(bankAccountService.getBankAccount(id), HttpStatus.OK);
    }


    // validate the BankAccountDTO
    private void validateBankAccount(BankAccountDTO bankAccountDTO) {

        if (bankAccountDTO.getCustomer().getId() != null) {
            // check the balance of account
            if (bankAccountDTO.getBalance() < 0) {
                logger.error("balance customer shouldn't be negative");
                throw new IllegalArgumentException("balance customer shouldn't be negative");
            }

        } else {
            logger.error("id CustomerDTO from bankAccountDTO must be a value");
            throw new IllegalArgumentException("id CustomerDTO from bankAccountDTO must be a value");

        }
    }

}
