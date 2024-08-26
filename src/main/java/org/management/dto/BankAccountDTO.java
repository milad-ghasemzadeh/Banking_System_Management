package org.management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccountDTO {

    private Long id;
    private CustomerDTO customer;
    private double balance;

}
