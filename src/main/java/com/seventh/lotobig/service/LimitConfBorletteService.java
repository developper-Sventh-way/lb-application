package com.seventh.lotobig.service;

import com.seventh.lotobig.service.dto.LimitConfBorletteDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.seventh.lotobig.domain.LimitConfBorlette}.
 */
public interface LimitConfBorletteService {
    /**
     * Save a limitConfBorlette.
     *
     * @param limitConfBorletteDTO the entity to save.
     * @return the persisted entity.
     */
    LimitConfBorletteDTO save(LimitConfBorletteDTO limitConfBorletteDTO);

    /**
     * Updates a limitConfBorlette.
     *
     * @param limitConfBorletteDTO the entity to update.
     * @return the persisted entity.
     */
    LimitConfBorletteDTO update(LimitConfBorletteDTO limitConfBorletteDTO);

    /**
     * Partially updates a limitConfBorlette.
     *
     * @param limitConfBorletteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LimitConfBorletteDTO> partialUpdate(LimitConfBorletteDTO limitConfBorletteDTO);

    /**
     * Get all the limitConfBorlettes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LimitConfBorletteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" limitConfBorlette.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LimitConfBorletteDTO> findOne(Long id);

    /**
     * Delete the "id" limitConfBorlette.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
