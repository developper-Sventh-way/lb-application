package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.UserPayment;
import com.seventh.lotobig.domain.Utilisateur;
import com.seventh.lotobig.service.dto.UserPaymentDTO;
import com.seventh.lotobig.service.dto.UtilisateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserPayment} and its DTO {@link UserPaymentDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserPaymentMapper extends EntityMapper<UserPaymentDTO, UserPayment> {
    @Mapping(target = "utilisateur", source = "utilisateur", qualifiedByName = "utilisateurId")
    UserPaymentDTO toDto(UserPayment s);

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);
}
