package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.BorletteConf;
import com.seventh.lotobig.domain.Tirage;
import com.seventh.lotobig.service.dto.BorletteConfDTO;
import com.seventh.lotobig.service.dto.TirageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tirage} and its DTO {@link TirageDTO}.
 */
@Mapper(componentModel = "spring")
public interface TirageMapper extends EntityMapper<TirageDTO, Tirage> {
    @Mapping(target = "borletteConf", source = "borletteConf", qualifiedByName = "borletteConfId")
    TirageDTO toDto(Tirage s);

    @Named("borletteConfId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BorletteConfDTO toDtoBorletteConfId(BorletteConf borletteConf);
}
