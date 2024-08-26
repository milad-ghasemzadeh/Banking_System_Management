package org.management.service.impl;

import org.management.domain.Customer;
import org.management.dto.CustomerDTO;
import org.management.exception.CustomException;
import org.management.exception.ErrorMessageConstants;
import org.management.mapper.CustomerMapper;
import org.management.repository.CustomerRepository;
import org.management.repository.TransactionHistoryRepository;
import org.management.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CustomerMapper customerMapper;

    private final TransactionHistoryRepository transactionHistoryRepository;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, TransactionHistoryRepository transactionHistoryRepository,
                               CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO, int state) {

        if (state == 1) {
            if (!customerRepository.existsById(customerDTO.getId())) {

                logger.error(ErrorMessageConstants.NotExistID.developer_message);
                throw new CustomException(ErrorMessageConstants.NotExistID.message,
                        ErrorMessageConstants.NotExistID.developer_message,
                        ErrorMessageConstants.NotExistID.status);
            }
        }

        if (customerRepository.existsAllByNationalCode(customerDTO.getNationalCode())) {

            // for state create new customer
            if (state == 0) {
                throw new CustomException(ErrorMessageConstants.UniqueByNationalCode.message,
                        ErrorMessageConstants.UniqueByNationalCode.developer_message,
                        ErrorMessageConstants.UniqueByNationalCode.status);
            }
            // for state update customer
            else {
                Customer customer = customerRepository.findById(customerDTO.getId()).orElse(null);

                if (!customer.getNationalCode().equals(customerDTO.getNationalCode())) {
                    throw new CustomException(ErrorMessageConstants.CannotChangeNationalCode.message,
                            ErrorMessageConstants.CannotChangeNationalCode.developer_message,
                            ErrorMessageConstants.CannotChangeNationalCode.status);
                }
            }
        }

        // save or update the customer
        Customer customer = customerMapper.toEntity(customerDTO);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);

    }


    @Override
    public Page searchCustomer(Pageable pageable, String name, String phone) {
        // search for the customer
        if (name == null || phone == null) {

            if (name != null) {
                return customerRepository.searchAllByName(pageable, name).map(customerMapper::toDto);

            } else if (phone != null) {
                return customerRepository.searchAllByPhone(pageable, phone).map(customerMapper::toDto);

            } else {
                return customerRepository.findAll(pageable).map(customerMapper::toDto);
            }
        }
        return customerRepository.searchAllByNameAndPhone(pageable, name, phone).map(customerMapper::toDto);
    }
}