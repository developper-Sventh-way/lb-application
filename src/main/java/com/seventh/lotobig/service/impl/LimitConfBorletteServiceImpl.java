package com.seventh.lotobig.service.impl;

import com.seventh.lotobig.domain.LimitConfBorlette;
import com.seventh.lotobig.repository.LimitConfBorletteRepository;
import com.seventh.lotobig.service.LimitConfBorletteService;
import com.seventh.lotobig.service.dto.LimitConfBorletteDTO;
import com.seventh.lotobig.service.mapper.LimitConfBorletteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LimitConfBorlette}.
 */
@Service
@Transactional
public class LimitConfBorletteServiceImpl implements LimitConfBorletteService {

    private final Logger log = LoggerFactory.getLogger(LimitConfBorletteServiceImpl.class);

    private final LimitConfBorletteRepository limitConfBorletteRepository;

    private final LimitConfBorletteMapper limitConfBorletteMapper;

    public LimitConfBorletteServiceImpl(
        LimitConfBorletteRepository limitConfBorletteRepository,
        LimitConfBorletteMapper limitConfBorletteMapper
    ) {
        this.limitConfBorletteRepository = limitConfBorletteRepository;
        this.limitConfBorletteMapper = limitConfBorletteMapper;
    }

    @Override
    public LimitConfBorletteDTO save(LimitConfBorletteDTO limitConfBorletteDTO) {
        log.debug("Request to save LimitConfBorlette : {}", limitConfBorletteDTO);
        LimitConfBorlette limitConfBorlette = limitConfBorletteMapper.toEntity(limitConfBorletteDTO);
        limitConfBorlette = limitConfBorletteRepository.save(limitConfBorlette);
        return limitConfBorletteMapper.toDto(limitConfBorlette);
    }

    @Override
    public LimitConfBorletteDTO update(LimitConfBorletteDTO limitConfBorletteDTO) {
        log.debug("Request to save LimitConfBorlette : {}", limitConfBorletteDTO);
        LimitConfBorlette limitConfBorlette = limitConfBorletteMapper.toEntity(limitConfBorletteDTO);
        limitConfBorlette = limitConfBorletteRepository.save(limitConfBorlette);
        return limitConfBorletteMapper.toDto(limitConfBorlette);
    }

    @Override
    public Optional<LimitConfBorletteDTO> partialUpdate(LimitConfBorletteDTO limitConfBorletteDTO) {
        log.debug("Request to partially update LimitConfBorlette : {}", limitConfBorletteDTO);

        return limitConfBorletteRepository
            .findById(limitConfBorletteDTO.getId())
            .map(existingLimitConfBorlette -> {
                limitConfBorletteMapper.partialUpdate(existingLimitConfBorlette, limitConfBorletteDTO);

                return existingLimitConfBorlette;
            })
            .map(limitConfBorletteRepository::save)
            .map(limitConfBorletteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LimitConfBorletteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LimitConfBorlettes");
        return limitConfBorletteRepository.findAll(pageable).map(limitConfBorletteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LimitConfBorletteDTO> findOne(Long id) {
        log.debug("Request to get LimitConfBorlette : {}", id);
        return limitConfBorletteRepository.findById(id).map(limitConfBorletteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LimitConfBorlette : {}", id);
        limitConfBorletteRepository.deleteById(id);
    }
}
