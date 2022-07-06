package com.seventh.lotobig.service.impl;

import com.seventh.lotobig.domain.LimitConfManager;
import com.seventh.lotobig.repository.LimitConfManagerRepository;
import com.seventh.lotobig.service.LimitConfManagerService;
import com.seventh.lotobig.service.dto.LimitConfManagerDTO;
import com.seventh.lotobig.service.mapper.LimitConfManagerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LimitConfManager}.
 */
@Service
@Transactional
public class LimitConfManagerServiceImpl implements LimitConfManagerService {

    private final Logger log = LoggerFactory.getLogger(LimitConfManagerServiceImpl.class);

    private final LimitConfManagerRepository limitConfManagerRepository;

    private final LimitConfManagerMapper limitConfManagerMapper;

    public LimitConfManagerServiceImpl(
        LimitConfManagerRepository limitConfManagerRepository,
        LimitConfManagerMapper limitConfManagerMapper
    ) {
        this.limitConfManagerRepository = limitConfManagerRepository;
        this.limitConfManagerMapper = limitConfManagerMapper;
    }

    @Override
    public LimitConfManagerDTO save(LimitConfManagerDTO limitConfManagerDTO) {
        log.debug("Request to save LimitConfManager : {}", limitConfManagerDTO);
        LimitConfManager limitConfManager = limitConfManagerMapper.toEntity(limitConfManagerDTO);
        limitConfManager = limitConfManagerRepository.save(limitConfManager);
        return limitConfManagerMapper.toDto(limitConfManager);
    }

    @Override
    public LimitConfManagerDTO update(LimitConfManagerDTO limitConfManagerDTO) {
        log.debug("Request to save LimitConfManager : {}", limitConfManagerDTO);
        LimitConfManager limitConfManager = limitConfManagerMapper.toEntity(limitConfManagerDTO);
        limitConfManager = limitConfManagerRepository.save(limitConfManager);
        return limitConfManagerMapper.toDto(limitConfManager);
    }

    @Override
    public Optional<LimitConfManagerDTO> partialUpdate(LimitConfManagerDTO limitConfManagerDTO) {
        log.debug("Request to partially update LimitConfManager : {}", limitConfManagerDTO);

        return limitConfManagerRepository
            .findById(limitConfManagerDTO.getId())
            .map(existingLimitConfManager -> {
                limitConfManagerMapper.partialUpdate(existingLimitConfManager, limitConfManagerDTO);

                return existingLimitConfManager;
            })
            .map(limitConfManagerRepository::save)
            .map(limitConfManagerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LimitConfManagerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LimitConfManagers");
        return limitConfManagerRepository.findAll(pageable).map(limitConfManagerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LimitConfManagerDTO> findOne(Long id) {
        log.debug("Request to get LimitConfManager : {}", id);
        return limitConfManagerRepository.findById(id).map(limitConfManagerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LimitConfManager : {}", id);
        limitConfManagerRepository.deleteById(id);
    }
}
