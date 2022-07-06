package com.seventh.lotobig.service.impl;

import com.seventh.lotobig.domain.POSConfiguration;
import com.seventh.lotobig.repository.POSConfigurationRepository;
import com.seventh.lotobig.service.POSConfigurationService;
import com.seventh.lotobig.service.dto.POSConfigurationDTO;
import com.seventh.lotobig.service.mapper.POSConfigurationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link POSConfiguration}.
 */
@Service
@Transactional
public class POSConfigurationServiceImpl implements POSConfigurationService {

    private final Logger log = LoggerFactory.getLogger(POSConfigurationServiceImpl.class);

    private final POSConfigurationRepository pOSConfigurationRepository;

    private final POSConfigurationMapper pOSConfigurationMapper;

    public POSConfigurationServiceImpl(
        POSConfigurationRepository pOSConfigurationRepository,
        POSConfigurationMapper pOSConfigurationMapper
    ) {
        this.pOSConfigurationRepository = pOSConfigurationRepository;
        this.pOSConfigurationMapper = pOSConfigurationMapper;
    }

    @Override
    public POSConfigurationDTO save(POSConfigurationDTO pOSConfigurationDTO) {
        log.debug("Request to save POSConfiguration : {}", pOSConfigurationDTO);
        POSConfiguration pOSConfiguration = pOSConfigurationMapper.toEntity(pOSConfigurationDTO);
        pOSConfiguration = pOSConfigurationRepository.save(pOSConfiguration);
        return pOSConfigurationMapper.toDto(pOSConfiguration);
    }

    @Override
    public POSConfigurationDTO update(POSConfigurationDTO pOSConfigurationDTO) {
        log.debug("Request to save POSConfiguration : {}", pOSConfigurationDTO);
        POSConfiguration pOSConfiguration = pOSConfigurationMapper.toEntity(pOSConfigurationDTO);
        pOSConfiguration = pOSConfigurationRepository.save(pOSConfiguration);
        return pOSConfigurationMapper.toDto(pOSConfiguration);
    }

    @Override
    public Optional<POSConfigurationDTO> partialUpdate(POSConfigurationDTO pOSConfigurationDTO) {
        log.debug("Request to partially update POSConfiguration : {}", pOSConfigurationDTO);

        return pOSConfigurationRepository
            .findById(pOSConfigurationDTO.getId())
            .map(existingPOSConfiguration -> {
                pOSConfigurationMapper.partialUpdate(existingPOSConfiguration, pOSConfigurationDTO);

                return existingPOSConfiguration;
            })
            .map(pOSConfigurationRepository::save)
            .map(pOSConfigurationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<POSConfigurationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all POSConfigurations");
        return pOSConfigurationRepository.findAll(pageable).map(pOSConfigurationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<POSConfigurationDTO> findOne(Long id) {
        log.debug("Request to get POSConfiguration : {}", id);
        return pOSConfigurationRepository.findById(id).map(pOSConfigurationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete POSConfiguration : {}", id);
        pOSConfigurationRepository.deleteById(id);
    }
}
