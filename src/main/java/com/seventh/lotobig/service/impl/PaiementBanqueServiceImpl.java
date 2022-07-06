package com.seventh.lotobig.service.impl;

import com.seventh.lotobig.domain.PaiementBanque;
import com.seventh.lotobig.repository.PaiementBanqueRepository;
import com.seventh.lotobig.service.PaiementBanqueService;
import com.seventh.lotobig.service.dto.PaiementBanqueDTO;
import com.seventh.lotobig.service.mapper.PaiementBanqueMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PaiementBanque}.
 */
@Service
@Transactional
public class PaiementBanqueServiceImpl implements PaiementBanqueService {

    private final Logger log = LoggerFactory.getLogger(PaiementBanqueServiceImpl.class);

    private final PaiementBanqueRepository paiementBanqueRepository;

    private final PaiementBanqueMapper paiementBanqueMapper;

    public PaiementBanqueServiceImpl(PaiementBanqueRepository paiementBanqueRepository, PaiementBanqueMapper paiementBanqueMapper) {
        this.paiementBanqueRepository = paiementBanqueRepository;
        this.paiementBanqueMapper = paiementBanqueMapper;
    }

    @Override
    public PaiementBanqueDTO save(PaiementBanqueDTO paiementBanqueDTO) {
        log.debug("Request to save PaiementBanque : {}", paiementBanqueDTO);
        PaiementBanque paiementBanque = paiementBanqueMapper.toEntity(paiementBanqueDTO);
        paiementBanque = paiementBanqueRepository.save(paiementBanque);
        return paiementBanqueMapper.toDto(paiementBanque);
    }

    @Override
    public PaiementBanqueDTO update(PaiementBanqueDTO paiementBanqueDTO) {
        log.debug("Request to save PaiementBanque : {}", paiementBanqueDTO);
        PaiementBanque paiementBanque = paiementBanqueMapper.toEntity(paiementBanqueDTO);
        paiementBanque = paiementBanqueRepository.save(paiementBanque);
        return paiementBanqueMapper.toDto(paiementBanque);
    }

    @Override
    public Optional<PaiementBanqueDTO> partialUpdate(PaiementBanqueDTO paiementBanqueDTO) {
        log.debug("Request to partially update PaiementBanque : {}", paiementBanqueDTO);

        return paiementBanqueRepository
            .findById(paiementBanqueDTO.getId())
            .map(existingPaiementBanque -> {
                paiementBanqueMapper.partialUpdate(existingPaiementBanque, paiementBanqueDTO);

                return existingPaiementBanque;
            })
            .map(paiementBanqueRepository::save)
            .map(paiementBanqueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaiementBanqueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaiementBanques");
        return paiementBanqueRepository.findAll(pageable).map(paiementBanqueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaiementBanqueDTO> findOne(Long id) {
        log.debug("Request to get PaiementBanque : {}", id);
        return paiementBanqueRepository.findById(id).map(paiementBanqueMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaiementBanque : {}", id);
        paiementBanqueRepository.deleteById(id);
    }
}
