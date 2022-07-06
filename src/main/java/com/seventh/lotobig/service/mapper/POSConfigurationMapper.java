package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.POSConfiguration;
import com.seventh.lotobig.service.dto.POSConfigurationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link POSConfiguration} and its DTO {@link POSConfigurationDTO}.
 */
@Mapper(componentModel = "spring")
public interface POSConfigurationMapper extends EntityMapper<POSConfigurationDTO, POSConfiguration> {}
