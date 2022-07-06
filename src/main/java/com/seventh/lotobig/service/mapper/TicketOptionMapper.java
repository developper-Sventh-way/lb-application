package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.Ticket;
import com.seventh.lotobig.domain.TicketOption;
import com.seventh.lotobig.service.dto.TicketDTO;
import com.seventh.lotobig.service.dto.TicketOptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TicketOption} and its DTO {@link TicketOptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TicketOptionMapper extends EntityMapper<TicketOptionDTO, TicketOption> {
    @Mapping(target = "ticket", source = "ticket", qualifiedByName = "ticketId")
    TicketOptionDTO toDto(TicketOption s);

    @Named("ticketId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TicketDTO toDtoTicketId(Ticket ticket);
}
