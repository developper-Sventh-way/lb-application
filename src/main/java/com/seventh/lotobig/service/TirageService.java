package com.seventh.lotobig.service;

import com.seventh.lotobig.service.dto.TirageDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.seventh.lotobig.domain.Tirage}.
 */
public interface TirageService {
    /**
     * Save a tirage.
     *
     * @param tirageDTO the entity to save.
     * @return the persisted entity.
     */
    TirageDTO save(TirageDTO tirageDTO);

    /**
     * Updates a tirage.
     *
     * @param tirageDTO the entity to update.
     * @return the persisted entity.
     */
    TirageDTO update(TirageDTO tirageDTO);

    /**
     * Partially updates a tirage.
     *
     * @param tirageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TirageDTO> partialUpdate(TirageDTO tirageDTO);

    /**
     * Get all the tirages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TirageDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tirage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TirageDTO> findOne(Long id);

    /**
     * Delete the "id" tirage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
