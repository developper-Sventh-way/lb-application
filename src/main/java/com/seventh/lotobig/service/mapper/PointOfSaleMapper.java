package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.PointOfSale;
import com.seventh.lotobig.domain.PointOfSaleConf;
import com.seventh.lotobig.service.dto.PointOfSaleConfDTO;
import com.seventh.lotobig.service.dto.PointOfSaleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PointOfSale} and its DTO {@link PointOfSaleDTO}.
 */
@Mapper(componentModel = "spring")
public interface PointOfSaleMapper extends EntityMapper<PointOfSaleDTO, PointOfSale> {
    @Mapping(target = "pointOfSaleConf", source = "pointOfSaleConf", qualifiedByName = "pointOfSaleConfId")
    PointOfSaleDTO toDto(PointOfSale s);

    @Named("pointOfSaleConfId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PointOfSaleConfDTO toDtoPointOfSaleConfId(PointOfSaleConf pointOfSaleConf);
}
