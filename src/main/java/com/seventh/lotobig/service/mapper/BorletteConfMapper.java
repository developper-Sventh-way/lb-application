package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.BorletteConf;
import com.seventh.lotobig.service.dto.BorletteConfDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BorletteConf} and its DTO {@link BorletteConfDTO}.
 */
@Mapper(componentModel = "spring")
public interface BorletteConfMapper extends EntityMapper<BorletteConfDTO, BorletteConf> {}
