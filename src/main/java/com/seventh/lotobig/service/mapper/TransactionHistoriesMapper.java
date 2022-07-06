package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.TransactionHistories;
import com.seventh.lotobig.service.dto.TransactionHistoriesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionHistories} and its DTO {@link TransactionHistoriesDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransactionHistoriesMapper extends EntityMapper<TransactionHistoriesDTO, TransactionHistories> {}
