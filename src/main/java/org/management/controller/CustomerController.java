package org.management.controller;

import org.management.dto.CustomerDTO;
import org.management.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CustomerController {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customer")
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO) {

        if (customerDTO.getId() != null) {
            logger.error("id customer must be a null");
            throw new IllegalArgumentException("id customer must be a null");
        }
        validateCustomer(customerDTO);
        return new ResponseEntity<>(customerService.save(customerDTO, 0), HttpStatus.OK);

    }

    @PutMapping("/customer")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO) {

        if (customerDTO.getId() == null) {
            logger.error("id customer must be a value");
            throw new IllegalArgumentException("id customer must be a value");
        }
        validateCustomer(customerDTO);
        return new ResponseEntity<>(customerService.save(customerDTO, 1), HttpStatus.OK);

    }


    @GetMapping("/customer")
    public ResponseEntity<Page> searchCustomer(Pageable pageable, @RequestParam(required = false) String name,
                                               @RequestParam(required = false) String phone) {
        return new ResponseEntity<>(customerService.searchCustomer(pageable, name, phone), HttpStatus.OK);
    }


    // validate the customerDTO
    private void validateCustomer(CustomerDTO customerDTO) {
        //check the name customer
        if (customerDTO.getName() == null || customerDTO.getName().equals("")) {
            logger.error("name customer must be entered");
            throw new IllegalArgumentException("name customer must be entered");
        }
        //check the phone customer
        if (customerDTO.getPhone() == null || customerDTO.getPhone().equals("")) {
            logger.error("phone customer must be entered");
            throw new IllegalArgumentException("phone customer must be entered");
        }
        //check the phone customer - for validating phone
        if (customerDTO.getPhone().length() != 10) {
            logger.error("phone customer must be entered in correct way like 9304914585");
            throw new IllegalArgumentException("phone customer must be entered in correct way like 9304914585");
        }

        // check the email of customer
        if (customerDTO.getEmail() == null || customerDTO.getEmail().equals("")) {
            logger.error("email customer must be entered");
            throw new IllegalArgumentException("email customer must be entered");
        }

        // check the email of customer
        if (!customerDTO.getEmail().contains("@") || !customerDTO.getEmail().contains(".")) {
            logger.error("Email customer must be entered in correct way like milad.ghasemzadeh74@gmail.com");
            throw new IllegalArgumentException("Email customer must be entered in correct way like milad.ghasemzadeh74@gmail.com");
        }

        if (customerDTO.getNationalCode() == null || customerDTO.getNationalCode().equals("")) {
            logger.error("NationalCode customer must be entered");
            throw new IllegalArgumentException("NationalCode customer must be entered");
        }

        // check the nationalCode of customer
        if (customerDTO.getNationalCode().length() != 10 ) {
            logger.error("nationalCode customer must be entered in correct way like 0018755853");
            throw new IllegalArgumentException("nationalCode customer must be entered in correct way like 0018755853");
        }

    }

}