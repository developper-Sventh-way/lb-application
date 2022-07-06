package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.CreditSolde;
import com.seventh.lotobig.domain.PointOfSale;
import com.seventh.lotobig.domain.UserPaymentConf;
import com.seventh.lotobig.domain.Utilisateur;
import com.seventh.lotobig.service.dto.CreditSoldeDTO;
import com.seventh.lotobig.service.dto.PointOfSaleDTO;
import com.seventh.lotobig.service.dto.UserPaymentConfDTO;
import com.seventh.lotobig.service.dto.UtilisateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Utilisateur} and its DTO {@link UtilisateurDTO}.
 */
@Mapper(componentModel = "spring")
public interface UtilisateurMapper extends EntityMapper<UtilisateurDTO, Utilisateur> {
    @Mapping(target = "creditSolde", source = "creditSolde", qualifiedByName = "creditSoldeId")
    @Mapping(target = "userPaymentConf", source = "userPaymentConf", qualifiedByName = "userPaymentConfId")
    @Mapping(target = "pointOfSale", source = "pointOfSale", qualifiedByName = "pointOfSaleId")
    UtilisateurDTO toDto(Utilisateur s);

    @Named("creditSoldeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CreditSoldeDTO toDtoCreditSoldeId(CreditSolde creditSolde);

    @Named("userPaymentConfId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserPaymentConfDTO toDtoUserPaymentConfId(UserPaymentConf userPaymentConf);

    @Named("pointOfSaleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PointOfSaleDTO toDtoPointOfSaleId(PointOfSale pointOfSale);
}
