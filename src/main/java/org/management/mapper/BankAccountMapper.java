package org.management.mapper;


import org.management.domain.BankAccount;
import org.management.dto.BankAccountDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface BankAccountMapper extends EntityMapper<BankAccountDTO, BankAccount> {

    // convert Entity to Dto
    BankAccountDTO toDto(BankAccount bankAccount);

    // convert Dto to Entity
    BankAccount toEntity(BankAccountDTO bankAccountDTO);

}

