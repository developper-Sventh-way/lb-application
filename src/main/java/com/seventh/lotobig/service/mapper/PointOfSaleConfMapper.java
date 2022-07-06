package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.PointOfSaleConf;
import com.seventh.lotobig.service.dto.PointOfSaleConfDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PointOfSaleConf} and its DTO {@link PointOfSaleConfDTO}.
 */
@Mapper(componentModel = "spring")
public interface PointOfSaleConfMapper extends EntityMapper<PointOfSaleConfDTO, PointOfSaleConf> {}
