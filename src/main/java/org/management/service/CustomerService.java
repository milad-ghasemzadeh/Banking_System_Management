package org.management.service;

import org.management.dto.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    // for save or update Customer
    CustomerDTO save(CustomerDTO customerDTO, int state);

    // search all customers
    Page searchCustomer(Pageable pageable, String name, String phone);
}
