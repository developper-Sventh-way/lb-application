package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.UserSaleAccount;
import com.seventh.lotobig.domain.Utilisateur;
import com.seventh.lotobig.service.dto.UserSaleAccountDTO;
import com.seventh.lotobig.service.dto.UtilisateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserSaleAccount} and its DTO {@link UserSaleAccountDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserSaleAccountMapper extends EntityMapper<UserSaleAccountDTO, UserSaleAccount> {
    @Mapping(target = "utilisateur", source = "utilisateur", qualifiedByName = "utilisateurId")
    UserSaleAccountDTO toDto(UserSaleAccount s);

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);
}
