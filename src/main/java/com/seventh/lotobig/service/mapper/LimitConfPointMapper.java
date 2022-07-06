package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.LimitConfPoint;
import com.seventh.lotobig.domain.PointOfSale;
import com.seventh.lotobig.service.dto.LimitConfPointDTO;
import com.seventh.lotobig.service.dto.PointOfSaleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LimitConfPoint} and its DTO {@link LimitConfPointDTO}.
 */
@Mapper(componentModel = "spring")
public interface LimitConfPointMapper extends EntityMapper<LimitConfPointDTO, LimitConfPoint> {
    @Mapping(target = "pointOfSale", source = "pointOfSale", qualifiedByName = "pointOfSaleId")
    LimitConfPointDTO toDto(LimitConfPoint s);

    @Named("pointOfSaleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PointOfSaleDTO toDtoPointOfSaleId(PointOfSale pointOfSale);
}
