package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.MembershipConf;
import com.seventh.lotobig.service.dto.MembershipConfDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MembershipConf} and its DTO {@link MembershipConfDTO}.
 */
@Mapper(componentModel = "spring")
public interface MembershipConfMapper extends EntityMapper<MembershipConfDTO, MembershipConf> {}
