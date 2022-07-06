package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.MembershipConf;
import com.seventh.lotobig.domain.PointOfSaleMembership;
import com.seventh.lotobig.domain.Utilisateur;
import com.seventh.lotobig.service.dto.MembershipConfDTO;
import com.seventh.lotobig.service.dto.PointOfSaleMembershipDTO;
import com.seventh.lotobig.service.dto.UtilisateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PointOfSaleMembership} and its DTO {@link PointOfSaleMembershipDTO}.
 */
@Mapper(componentModel = "spring")
public interface PointOfSaleMembershipMapper extends EntityMapper<PointOfSaleMembershipDTO, PointOfSaleMembership> {
    @Mapping(target = "membershipConf", source = "membershipConf", qualifiedByName = "membershipConfId")
    @Mapping(target = "utilisateur", source = "utilisateur", qualifiedByName = "utilisateurId")
    PointOfSaleMembershipDTO toDto(PointOfSaleMembership s);

    @Named("membershipConfId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MembershipConfDTO toDtoMembershipConfId(MembershipConf membershipConf);

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);
}
