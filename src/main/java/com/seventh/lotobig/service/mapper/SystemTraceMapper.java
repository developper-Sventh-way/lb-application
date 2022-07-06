package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.SystemTrace;
import com.seventh.lotobig.service.dto.SystemTraceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SystemTrace} and its DTO {@link SystemTraceDTO}.
 */
@Mapper(componentModel = "spring")
public interface SystemTraceMapper extends EntityMapper<SystemTraceDTO, SystemTrace> {}
