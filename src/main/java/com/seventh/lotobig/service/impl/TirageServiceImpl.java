package com.seventh.lotobig.service.impl;

import com.seventh.lotobig.domain.Tirage;
import com.seventh.lotobig.repository.TirageRepository;
import com.seventh.lotobig.service.TirageService;
import com.seventh.lotobig.service.dto.TirageDTO;
import com.seventh.lotobig.service.mapper.TirageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tirage}.
 */
@Service
@Transactional
public class TirageServiceImpl implements TirageService {

    private final Logger log = LoggerFactory.getLogger(TirageServiceImpl.class);

    private final TirageRepository tirageRepository;

    private final TirageMapper tirageMapper;

    public TirageServiceImpl(TirageRepository tirageRepository, TirageMapper tirageMapper) {
        this.tirageRepository = tirageRepository;
        this.tirageMapper = tirageMapper;
    }

    @Override
    public TirageDTO save(TirageDTO tirageDTO) {
        log.debug("Request to save Tirage : {}", tirageDTO);
        Tirage tirage = tirageMapper.toEntity(tirageDTO);
        tirage = tirageRepository.save(tirage);
        return tirageMapper.toDto(tirage);
    }

    @Override
    public TirageDTO update(TirageDTO tirageDTO) {
        log.debug("Request to save Tirage : {}", tirageDTO);
        Tirage tirage = tirageMapper.toEntity(tirageDTO);
        tirage = tirageRepository.save(tirage);
        return tirageMapper.toDto(tirage);
    }

    @Override
    public Optional<TirageDTO> partialUpdate(TirageDTO tirageDTO) {
        log.debug("Request to partially update Tirage : {}", tirageDTO);

        return tirageRepository
            .findById(tirageDTO.getId())
            .map(existingTirage -> {
                tirageMapper.partialUpdate(existingTirage, tirageDTO);

                return existingTirage;
            })
            .map(tirageRepository::save)
            .map(tirageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TirageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tirages");
        return tirageRepository.findAll(pageable).map(tirageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TirageDTO> findOne(Long id) {
        log.debug("Request to get Tirage : {}", id);
        return tirageRepository.findById(id).map(tirageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tirage : {}", id);
        tirageRepository.deleteById(id);
    }
}
