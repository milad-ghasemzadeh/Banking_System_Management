package org.management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionHistoryDTO {

    private Long id;
    private double deposit;
    private double withdrawal;

    // bank Account depositor
    private BankAccountDTO bankAccountDepositor;

    // bank Account withdrawal
    private BankAccountDTO bankAccountWithdrawal;


}
