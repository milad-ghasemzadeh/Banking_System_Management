package org.management.mapper;

import org.management.domain.TransactionHistory;
import org.management.dto.TransactionHistoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",  uses = {BankAccountMapper.class})
public interface TransactionHistoryMapper extends EntityMapper<TransactionHistoryDTO, TransactionHistory> {

    // convert Entity to Dto
    TransactionHistoryDTO toDto(TransactionHistory transactionHistory);

    // convert Dto to Entity
    TransactionHistory toEntity(TransactionHistoryDTO transactionHistoryDTO);

}