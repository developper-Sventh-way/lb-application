package com.seventh.lotobig.service.impl;

import com.seventh.lotobig.domain.TransactionHistories;
import com.seventh.lotobig.repository.TransactionHistoriesRepository;
import com.seventh.lotobig.service.TransactionHistoriesService;
import com.seventh.lotobig.service.dto.TransactionHistoriesDTO;
import com.seventh.lotobig.service.mapper.TransactionHistoriesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TransactionHistories}.
 */
@Service
@Transactional
public class TransactionHistoriesServiceImpl implements TransactionHistoriesService {

    private final Logger log = LoggerFactory.getLogger(TransactionHistoriesServiceImpl.class);

    private final TransactionHistoriesRepository transactionHistoriesRepository;

    private final TransactionHistoriesMapper transactionHistoriesMapper;

    public TransactionHistoriesServiceImpl(
        TransactionHistoriesRepository transactionHistoriesRepository,
        TransactionHistoriesMapper transactionHistoriesMapper
    ) {
        this.transactionHistoriesRepository = transactionHistoriesRepository;
        this.transactionHistoriesMapper = transactionHistoriesMapper;
    }

    @Override
    public TransactionHistoriesDTO save(TransactionHistoriesDTO transactionHistoriesDTO) {
        log.debug("Request to save TransactionHistories : {}", transactionHistoriesDTO);
        TransactionHistories transactionHistories = transactionHistoriesMapper.toEntity(transactionHistoriesDTO);
        transactionHistories = transactionHistoriesRepository.save(transactionHistories);
        return transactionHistoriesMapper.toDto(transactionHistories);
    }

    @Override
    public TransactionHistoriesDTO update(TransactionHistoriesDTO transactionHistoriesDTO) {
        log.debug("Request to save TransactionHistories : {}", transactionHistoriesDTO);
        TransactionHistories transactionHistories = transactionHistoriesMapper.toEntity(transactionHistoriesDTO);
        transactionHistories = transactionHistoriesRepository.save(transactionHistories);
        return transactionHistoriesMapper.toDto(transactionHistories);
    }

    @Override
    public Optional<TransactionHistoriesDTO> partialUpdate(TransactionHistoriesDTO transactionHistoriesDTO) {
        log.debug("Request to partially update TransactionHistories : {}", transactionHistoriesDTO);

        return transactionHistoriesRepository
            .findById(transactionHistoriesDTO.getId())
            .map(existingTransactionHistories -> {
                transactionHistoriesMapper.partialUpdate(existingTransactionHistories, transactionHistoriesDTO);

                return existingTransactionHistories;
            })
            .map(transactionHistoriesRepository::save)
            .map(transactionHistoriesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionHistoriesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransactionHistories");
        return transactionHistoriesRepository.findAll(pageable).map(transactionHistoriesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionHistoriesDTO> findOne(Long id) {
        log.debug("Request to get TransactionHistories : {}", id);
        return transactionHistoriesRepository.findById(id).map(transactionHistoriesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionHistories : {}", id);
        transactionHistoriesRepository.deleteById(id);
    }
}
