package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.CreditSolde;
import com.seventh.lotobig.service.dto.CreditSoldeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CreditSolde} and its DTO {@link CreditSoldeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CreditSoldeMapper extends EntityMapper<CreditSoldeDTO, CreditSolde> {}
