package com.seventh.lotobig.service.mapper;

import com.seventh.lotobig.domain.CouponGratuitConf;
import com.seventh.lotobig.service.dto.CouponGratuitConfDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CouponGratuitConf} and its DTO {@link CouponGratuitConfDTO}.
 */
@Mapper(componentModel = "spring")
public interface CouponGratuitConfMapper extends EntityMapper<CouponGratuitConfDTO, CouponGratuitConf> {}
