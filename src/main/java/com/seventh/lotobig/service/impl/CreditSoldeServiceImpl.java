package com.seventh.lotobig.service.impl;

import com.seventh.lotobig.domain.CreditSolde;
import com.seventh.lotobig.repository.CreditSoldeRepository;
import com.seventh.lotobig.service.CreditSoldeService;
import com.seventh.lotobig.service.dto.CreditSoldeDTO;
import com.seventh.lotobig.service.mapper.CreditSoldeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CreditSolde}.
 */
@Service
@Transactional
public class CreditSoldeServiceImpl implements CreditSoldeService {

    private final Logger log = LoggerFactory.getLogger(CreditSoldeServiceImpl.class);

    private final CreditSoldeRepository creditSoldeRepository;

    private final CreditSoldeMapper creditSoldeMapper;

    public CreditSoldeServiceImpl(CreditSoldeRepository creditSoldeRepository, CreditSoldeMapper creditSoldeMapper) {
        this.creditSoldeRepository = creditSoldeRepository;
        this.creditSoldeMapper = creditSoldeMapper;
    }

    @Override
    public CreditSoldeDTO save(CreditSoldeDTO creditSoldeDTO) {
        log.debug("Request to save CreditSolde : {}", creditSoldeDTO);
        CreditSolde creditSolde = creditSoldeMapper.toEntity(creditSoldeDTO);
        creditSolde = creditSoldeRepository.save(creditSolde);
        return creditSoldeMapper.toDto(creditSolde);
    }

    @Override
    public CreditSoldeDTO update(CreditSoldeDTO creditSoldeDTO) {
        log.debug("Request to save CreditSolde : {}", creditSoldeDTO);
        CreditSolde creditSolde = creditSoldeMapper.toEntity(creditSoldeDTO);
        creditSolde = creditSoldeRepository.save(creditSolde);
        return creditSoldeMapper.toDto(creditSolde);
    }

    @Override
    public Optional<CreditSoldeDTO> partialUpdate(CreditSoldeDTO creditSoldeDTO) {
        log.debug("Request to partially update CreditSolde : {}", creditSoldeDTO);

        return creditSoldeRepository
            .findById(creditSoldeDTO.getId())
            .map(existingCreditSolde -> {
                creditSoldeMapper.partialUpdate(existingCreditSolde, creditSoldeDTO);

                return existingCreditSolde;
            })
            .map(creditSoldeRepository::save)
            .map(creditSoldeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CreditSoldeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CreditSoldes");
        return creditSoldeRepository.findAll(pageable).map(creditSoldeMapper::toDto);
    }

    /**
     *  Get all the creditSoldes where Utilisateur is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CreditSoldeDTO> findAllWhereUtilisateurIsNull() {
        log.debug("Request to get all creditSoldes where Utilisateur is null");
        return StreamSupport
            .stream(creditSoldeRepository.findAll().spliterator(), false)
            .filter(creditSolde -> creditSolde.getUtilisateur() == null)
            .map(creditSoldeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CreditSoldeDTO> findOne(Long id) {
        log.debug("Request to get CreditSolde : {}", id);
        return creditSoldeRepository.findById(id).map(creditSoldeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CreditSolde : {}", id);
        creditSoldeRepository.deleteById(id);
    }
}
