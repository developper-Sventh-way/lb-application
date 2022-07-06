package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.PaiementBanque;
import com.seventh.lotobig.domain.PointOfSale;
import com.seventh.lotobig.domain.Utilisateur;
import com.seventh.lotobig.service.dto.PaiementBanqueDTO;
import com.seventh.lotobig.service.dto.PointOfSaleDTO;
import com.seventh.lotobig.service.dto.UtilisateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaiementBanque} and its DTO {@link PaiementBanqueDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaiementBanqueMapper extends EntityMapper<PaiementBanqueDTO, PaiementBanque> {
    @Mapping(target = "pointOfSale", source = "pointOfSale", qualifiedByName = "pointOfSaleId")
    @Mapping(target = "utilisateur", source = "utilisateur", qualifiedByName = "utilisateurId")
    PaiementBanqueDTO toDto(PaiementBanque s);

    @Named("pointOfSaleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PointOfSaleDTO toDtoPointOfSaleId(PointOfSale pointOfSale);

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);
}
