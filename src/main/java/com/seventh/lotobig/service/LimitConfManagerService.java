package com.seventh.lotobig.service;

import com.seventh.lotobig.service.dto.LimitConfManagerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.seventh.lotobig.domain.LimitConfManager}.
 */
public interface LimitConfManagerService {
    /**
     * Save a limitConfManager.
     *
     * @param limitConfManagerDTO the entity to save.
     * @return the persisted entity.
     */
    LimitConfManagerDTO save(LimitConfManagerDTO limitConfManagerDTO);

    /**
     * Updates a limitConfManager.
     *
     * @param limitConfManagerDTO the entity to update.
     * @return the persisted entity.
     */
    LimitConfManagerDTO update(LimitConfManagerDTO limitConfManagerDTO);

    /**
     * Partially updates a limitConfManager.
     *
     * @param limitConfManagerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LimitConfManagerDTO> partialUpdate(LimitConfManagerDTO limitConfManagerDTO);

    /**
     * Get all the limitConfManagers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LimitConfManagerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" limitConfManager.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LimitConfManagerDTO> findOne(Long id);

    /**
     * Delete the "id" limitConfManager.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
