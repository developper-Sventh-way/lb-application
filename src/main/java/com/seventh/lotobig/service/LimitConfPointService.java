package com.seventh.lotobig.service;

import com.seventh.lotobig.service.dto.LimitConfPointDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.seventh.lotobig.domain.LimitConfPoint}.
 */
public interface LimitConfPointService {
    /**
     * Save a limitConfPoint.
     *
     * @param limitConfPointDTO the entity to save.
     * @return the persisted entity.
     */
    LimitConfPointDTO save(LimitConfPointDTO limitConfPointDTO);

    /**
     * Updates a limitConfPoint.
     *
     * @param limitConfPointDTO the entity to update.
     * @return the persisted entity.
     */
    LimitConfPointDTO update(LimitConfPointDTO limitConfPointDTO);

    /**
     * Partially updates a limitConfPoint.
     *
     * @param limitConfPointDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LimitConfPointDTO> partialUpdate(LimitConfPointDTO limitConfPointDTO);

    /**
     * Get all the limitConfPoints.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LimitConfPointDTO> findAll(Pageable pageable);

    /**
     * Get the "id" limitConfPoint.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LimitConfPointDTO> findOne(Long id);

    /**
     * Delete the "id" limitConfPoint.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
