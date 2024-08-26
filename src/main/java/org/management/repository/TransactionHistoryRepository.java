package org.management.repository;

import org.management.domain.TransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    @Query("SELECT t FROM TransactionHistory t WHERE t.bankAccountDepositor.id = :bankAccountId OR t.bankAccountWithdrawal.id = :bankAccountId")
    Page<TransactionHistory> searchAllByBankAccount(Pageable pageable, @Param("bankAccountId") Long bankAccountId);


}
