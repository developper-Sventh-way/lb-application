package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.LimitConfManager;
import com.seventh.lotobig.domain.MembershipConf;
import com.seventh.lotobig.service.dto.LimitConfManagerDTO;
import com.seventh.lotobig.service.dto.MembershipConfDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LimitConfManager} and its DTO {@link LimitConfManagerDTO}.
 */
@Mapper(componentModel = "spring")
public interface LimitConfManagerMapper extends EntityMapper<LimitConfManagerDTO, LimitConfManager> {
    @Mapping(target = "membershipConf", source = "membershipConf", qualifiedByName = "membershipConfId")
    LimitConfManagerDTO toDto(LimitConfManager s);

    @Named("membershipConfId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MembershipConfDTO toDtoMembershipConfId(MembershipConf membershipConf);
}
