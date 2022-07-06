package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.UserRole;
import com.seventh.lotobig.domain.Utilisateur;
import com.seventh.lotobig.service.dto.UserRoleDTO;
import com.seventh.lotobig.service.dto.UtilisateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserRole} and its DTO {@link UserRoleDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserRoleMapper extends EntityMapper<UserRoleDTO, UserRole> {
    @Mapping(target = "utilisateur", source = "utilisateur", qualifiedByName = "utilisateurId")
    UserRoleDTO toDto(UserRole s);

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);
}
