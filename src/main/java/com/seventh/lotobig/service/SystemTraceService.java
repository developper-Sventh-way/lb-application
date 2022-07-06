package com.seventh.lotobig.service;

import com.seventh.lotobig.service.dto.SystemTraceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.seventh.lotobig.domain.SystemTrace}.
 */
public interface SystemTraceService {
    /**
     * Save a systemTrace.
     *
     * @param systemTraceDTO the entity to save.
     * @return the persisted entity.
     */
    SystemTraceDTO save(SystemTraceDTO systemTraceDTO);

    /**
     * Updates a systemTrace.
     *
     * @param systemTraceDTO the entity to update.
     * @return the persisted entity.
     */
    SystemTraceDTO update(SystemTraceDTO systemTraceDTO);

    /**
     * Partially updates a systemTrace.
     *
     * @param systemTraceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SystemTraceDTO> partialUpdate(SystemTraceDTO systemTraceDTO);

    /**
     * Get all the systemTraces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SystemTraceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" systemTrace.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SystemTraceDTO> findOne(Long id);

    /**
     * Delete the "id" systemTrace.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
