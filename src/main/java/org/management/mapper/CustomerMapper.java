package org.management.mapper;

import org.management.domain.Customer;
import org.management.dto.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {

    // convert Entity to Dto
    CustomerDTO toDto(Customer customer);

    // convert Dto to Entity
    Customer toEntity(CustomerDTO customerDTO);

}