package com.seventh.lotobig.service.impl;

import com.seventh.lotobig.domain.LimitConfPoint;
import com.seventh.lotobig.repository.LimitConfPointRepository;
import com.seventh.lotobig.service.LimitConfPointService;
import com.seventh.lotobig.service.dto.LimitConfPointDTO;
import com.seventh.lotobig.service.mapper.LimitConfPointMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LimitConfPoint}.
 */
@Service
@Transactional
public class LimitConfPointServiceImpl implements LimitConfPointService {

    private final Logger log = LoggerFactory.getLogger(LimitConfPointServiceImpl.class);

    private final LimitConfPointRepository limitConfPointRepository;

    private final LimitConfPointMapper limitConfPointMapper;

    public LimitConfPointServiceImpl(LimitConfPointRepository limitConfPointRepository, LimitConfPointMapper limitConfPointMapper) {
        this.limitConfPointRepository = limitConfPointRepository;
        this.limitConfPointMapper = limitConfPointMapper;
    }

    @Override
    public LimitConfPointDTO save(LimitConfPointDTO limitConfPointDTO) {
        log.debug("Request to save LimitConfPoint : {}", limitConfPointDTO);
        LimitConfPoint limitConfPoint = limitConfPointMapper.toEntity(limitConfPointDTO);
        limitConfPoint = limitConfPointRepository.save(limitConfPoint);
        return limitConfPointMapper.toDto(limitConfPoint);
    }

    @Override
    public LimitConfPointDTO update(LimitConfPointDTO limitConfPointDTO) {
        log.debug("Request to save LimitConfPoint : {}", limitConfPointDTO);
        LimitConfPoint limitConfPoint = limitConfPointMapper.toEntity(limitConfPointDTO);
        limitConfPoint = limitConfPointRepository.save(limitConfPoint);
        return limitConfPointMapper.toDto(limitConfPoint);
    }

    @Override
    public Optional<LimitConfPointDTO> partialUpdate(LimitConfPointDTO limitConfPointDTO) {
        log.debug("Request to partially update LimitConfPoint : {}", limitConfPointDTO);

        return limitConfPointRepository
            .findById(limitConfPointDTO.getId())
            .map(existingLimitConfPoint -> {
                limitConfPointMapper.partialUpdate(existingLimitConfPoint, limitConfPointDTO);

                return existingLimitConfPoint;
            })
            .map(limitConfPointRepository::save)
            .map(limitConfPointMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LimitConfPointDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LimitConfPoints");
        return limitConfPointRepository.findAll(pageable).map(limitConfPointMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LimitConfPointDTO> findOne(Long id) {
        log.debug("Request to get LimitConfPoint : {}", id);
        return limitConfPointRepository.findById(id).map(limitConfPointMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LimitConfPoint : {}", id);
        limitConfPointRepository.deleteById(id);
    }
}
