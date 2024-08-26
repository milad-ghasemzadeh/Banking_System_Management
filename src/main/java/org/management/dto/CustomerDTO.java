package org.management.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {

    private Long id;
    private String name;
    private String nationalCode;
    private String phone;
    private String email;
}
