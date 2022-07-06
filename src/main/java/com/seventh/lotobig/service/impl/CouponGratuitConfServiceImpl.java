package com.seventh.lotobig.service.impl;

import com.seventh.lotobig.domain.CouponGratuitConf;
import com.seventh.lotobig.repository.CouponGratuitConfRepository;
import com.seventh.lotobig.service.CouponGratuitConfService;
import com.seventh.lotobig.service.dto.CouponGratuitConfDTO;
import com.seventh.lotobig.service.mapper.CouponGratuitConfMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CouponGratuitConf}.
 */
@Service
@Transactional
public class CouponGratuitConfServiceImpl implements CouponGratuitConfService {

    private final Logger log = LoggerFactory.getLogger(CouponGratuitConfServiceImpl.class);

    private final CouponGratuitConfRepository couponGratuitConfRepository;

    private final CouponGratuitConfMapper couponGratuitConfMapper;

    public CouponGratuitConfServiceImpl(
        CouponGratuitConfRepository couponGratuitConfRepository,
        CouponGratuitConfMapper couponGratuitConfMapper
    ) {
        this.couponGratuitConfRepository = couponGratuitConfRepository;
        this.couponGratuitConfMapper = couponGratuitConfMapper;
    }

    @Override
    public CouponGratuitConfDTO save(CouponGratuitConfDTO couponGratuitConfDTO) {
        log.debug("Request to save CouponGratuitConf : {}", couponGratuitConfDTO);
        CouponGratuitConf couponGratuitConf = couponGratuitConfMapper.toEntity(couponGratuitConfDTO);
        couponGratuitConf = couponGratuitConfRepository.save(couponGratuitConf);
        return couponGratuitConfMapper.toDto(couponGratuitConf);
    }

    @Override
    public CouponGratuitConfDTO update(CouponGratuitConfDTO couponGratuitConfDTO) {
        log.debug("Request to save CouponGratuitConf : {}", couponGratuitConfDTO);
        CouponGratuitConf couponGratuitConf = couponGratuitConfMapper.toEntity(couponGratuitConfDTO);
        couponGratuitConf = couponGratuitConfRepository.save(couponGratuitConf);
        return couponGratuitConfMapper.toDto(couponGratuitConf);
    }

    @Override
    public Optional<CouponGratuitConfDTO> partialUpdate(CouponGratuitConfDTO couponGratuitConfDTO) {
        log.debug("Request to partially update CouponGratuitConf : {}", couponGratuitConfDTO);

        return couponGratuitConfRepository
            .findById(couponGratuitConfDTO.getId())
            .map(existingCouponGratuitConf -> {
                couponGratuitConfMapper.partialUpdate(existingCouponGratuitConf, couponGratuitConfDTO);

                return existingCouponGratuitConf;
            })
            .map(couponGratuitConfRepository::save)
            .map(couponGratuitConfMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponGratuitConfDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CouponGratuitConfs");
        return couponGratuitConfRepository.findAll(pageable).map(couponGratuitConfMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CouponGratuitConfDTO> findOne(Long id) {
        log.debug("Request to get CouponGratuitConf : {}", id);
        return couponGratuitConfRepository.findById(id).map(couponGratuitConfMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CouponGratuitConf : {}", id);
        couponGratuitConfRepository.deleteById(id);
    }
}
