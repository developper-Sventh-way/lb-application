package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.BorletteConf;
import com.seventh.lotobig.domain.LimitConfBorlette;
import com.seventh.lotobig.service.dto.BorletteConfDTO;
import com.seventh.lotobig.service.dto.LimitConfBorletteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LimitConfBorlette} and its DTO {@link LimitConfBorletteDTO}.
 */
@Mapper(componentModel = "spring")
public interface LimitConfBorletteMapper extends EntityMapper<LimitConfBorletteDTO, LimitConfBorlette> {
    @Mapping(target = "borletteConf", source = "borletteConf", qualifiedByName = "borletteConfId")
    LimitConfBorletteDTO toDto(LimitConfBorlette s);

    @Named("borletteConfId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BorletteConfDTO toDtoBorletteConfId(BorletteConf borletteConf);
}
