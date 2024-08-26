package org.management.repository;

import org.management.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Page<Customer> searchAllByName(Pageable pageable, String name);

    Page<Customer> searchAllByPhone(Pageable pageable, String phone);

    Page<Customer> searchAllByNameAndPhone(Pageable pageable, String name, String phone);

    boolean existsAllByNationalCode(String nationalCode);

}
