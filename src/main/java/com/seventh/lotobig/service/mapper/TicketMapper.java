package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.PointOfSale;
import com.seventh.lotobig.domain.Ticket;
import com.seventh.lotobig.domain.Tirage;
import com.seventh.lotobig.domain.UserSaleAccount;
import com.seventh.lotobig.service.dto.PointOfSaleDTO;
import com.seventh.lotobig.service.dto.TicketDTO;
import com.seventh.lotobig.service.dto.TirageDTO;
import com.seventh.lotobig.service.dto.UserSaleAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ticket} and its DTO {@link TicketDTO}.
 */
@Mapper(componentModel = "spring")
public interface TicketMapper extends EntityMapper<TicketDTO, Ticket> {
    @Mapping(target = "pointOfSale", source = "pointOfSale", qualifiedByName = "pointOfSaleId")
    @Mapping(target = "tirage", source = "tirage", qualifiedByName = "tirageId")
    @Mapping(target = "userSaleAccount", source = "userSaleAccount", qualifiedByName = "userSaleAccountId")
    TicketDTO toDto(Ticket s);

    @Named("pointOfSaleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PointOfSaleDTO toDtoPointOfSaleId(PointOfSale pointOfSale);

    @Named("tirageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TirageDTO toDtoTirageId(Tirage tirage);

    @Named("userSaleAccountId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserSaleAccountDTO toDtoUserSaleAccountId(UserSaleAccount userSaleAccount);
}
