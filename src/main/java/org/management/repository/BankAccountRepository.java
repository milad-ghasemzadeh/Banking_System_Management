package org.management.repository;

import org.management.domain.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Page<BankAccount> searchAllByCustomer_Id(Pageable pageable, Long customerID);

}
