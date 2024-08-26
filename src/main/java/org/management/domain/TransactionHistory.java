package org.management.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "transaction_history")
public class TransactionHistory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double deposit;
    private double withdrawal;


    // bank Account depositor
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_account_depositor_id")
    private BankAccount bankAccountDepositor;


    // bank Account withdrawal
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_account_Withdrawal_id")
    private BankAccount bankAccountWithdrawal;

}
