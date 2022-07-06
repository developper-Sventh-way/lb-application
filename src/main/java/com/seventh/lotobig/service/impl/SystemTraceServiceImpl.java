package com.seventh.lotobig.service.impl;

import com.seventh.lotobig.domain.SystemTrace;
import com.seventh.lotobig.repository.SystemTraceRepository;
import com.seventh.lotobig.service.SystemTraceService;
import com.seventh.lotobig.service.dto.SystemTraceDTO;
import com.seventh.lotobig.service.mapper.SystemTraceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SystemTrace}.
 */
@Service
@Transactional
public class SystemTraceServiceImpl implements SystemTraceService {

    private final Logger log = LoggerFactory.getLogger(SystemTraceServiceImpl.class);

    private final SystemTraceRepository systemTraceRepository;

    private final SystemTraceMapper systemTraceMapper;

    public SystemTraceServiceImpl(SystemTraceRepository systemTraceRepository, SystemTraceMapper systemTraceMapper) {
        this.systemTraceRepository = systemTraceRepository;
        this.systemTraceMapper = systemTraceMapper;
    }

    @Override
    public SystemTraceDTO save(SystemTraceDTO systemTraceDTO) {
        log.debug("Request to save SystemTrace : {}", systemTraceDTO);
        SystemTrace systemTrace = systemTraceMapper.toEntity(systemTraceDTO);
        systemTrace = systemTraceRepository.save(systemTrace);
        return systemTraceMapper.toDto(systemTrace);
    }

    @Override
    public SystemTraceDTO update(SystemTraceDTO systemTraceDTO) {
        log.debug("Request to save SystemTrace : {}", systemTraceDTO);
        SystemTrace systemTrace = systemTraceMapper.toEntity(systemTraceDTO);
        systemTrace = systemTraceRepository.save(systemTrace);
        return systemTraceMapper.toDto(systemTrace);
    }

    @Override
    public Optional<SystemTraceDTO> partialUpdate(SystemTraceDTO systemTraceDTO) {
        log.debug("Request to partially update SystemTrace : {}", systemTraceDTO);

        return systemTraceRepository
            .findById(systemTraceDTO.getId())
            .map(existingSystemTrace -> {
                systemTraceMapper.partialUpdate(existingSystemTrace, systemTraceDTO);

                return existingSystemTrace;
            })
            .map(systemTraceRepository::save)
            .map(systemTraceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SystemTraceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SystemTraces");
        return systemTraceRepository.findAll(pageable).map(systemTraceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SystemTraceDTO> findOne(Long id) {
        log.debug("Request to get SystemTrace : {}", id);
        return systemTraceRepository.findById(id).map(systemTraceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SystemTrace : {}", id);
        systemTraceRepository.deleteById(id);
    }
}
