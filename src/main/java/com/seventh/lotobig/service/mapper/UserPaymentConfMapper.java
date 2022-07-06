package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.UserPaymentConf;
import com.seventh.lotobig.service.dto.UserPaymentConfDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserPaymentConf} and its DTO {@link UserPaymentConfDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserPaymentConfMapper extends EntityMapper<UserPaymentConfDTO, UserPaymentConf> {}
