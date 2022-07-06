package com.seventh.lotobig.service;

import com.seventh.lotobig.service.dto.TransactionHistoriesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.seventh.lotobig.domain.TransactionHistories}.
 */
public interface TransactionHistoriesService {
    /**
     * Save a transactionHistories.
     *
     * @param transactionHistoriesDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionHistoriesDTO save(TransactionHistoriesDTO transactionHistoriesDTO);

    /**
     * Updates a transactionHistories.
     *
     * @param transactionHistoriesDTO the entity to update.
     * @return the persisted entity.
     */
    TransactionHistoriesDTO update(TransactionHistoriesDTO transactionHistoriesDTO);

    /**
     * Partially updates a transactionHistories.
     *
     * @param transactionHistoriesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TransactionHistoriesDTO> partialUpdate(TransactionHistoriesDTO transactionHistoriesDTO);

    /**
     * Get all the transactionHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransactionHistoriesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" transactionHistories.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionHistoriesDTO> findOne(Long id);

    /**
     * Delete the "id" transactionHistories.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
