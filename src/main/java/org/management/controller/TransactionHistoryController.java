package org.management.controller;

import org.management.dto.TransactionHistoryDTO;
import org.management.service.TransactionHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/api")
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;

    public TransactionHistoryController(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }


    @PostMapping("/deposit")
    public ResponseEntity<TransactionHistoryDTO> deposit(@RequestParam Long accountId, @RequestParam double fund) throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(transactionHistoryService.deposit(accountId, fund), HttpStatus.OK);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<TransactionHistoryDTO> withdrawal(@RequestParam Long accountId, @RequestParam double fund) throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(transactionHistoryService.withdrawal(accountId, fund), HttpStatus.OK);
    }


    @PostMapping("/transfer_funds")
    public ResponseEntity<TransactionHistoryDTO> transferFunds(@RequestParam Long senderId, @RequestParam Long receiverID, @RequestParam double fund) throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(transactionHistoryService.transferFund(senderId, receiverID, fund), HttpStatus.OK);
    }


    @GetMapping("/transaction_history/{bankAccountId}")
    public ResponseEntity<Page> transactionHistory(Pageable pageable, @PathVariable Long bankAccountId) {
        return new ResponseEntity<>(transactionHistoryService.transactionHistory(pageable, bankAccountId), HttpStatus.OK);
    }

}

