package com.seventh.lotobig.service;

import com.seventh.lotobig.service.dto.BorletteConfDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.seventh.lotobig.domain.BorletteConf}.
 */
public interface BorletteConfService {
    /**
     * Save a borletteConf.
     *
     * @param borletteConfDTO the entity to save.
     * @return the persisted entity.
     */
    BorletteConfDTO save(BorletteConfDTO borletteConfDTO);

    /**
     * Updates a borletteConf.
     *
     * @param borletteConfDTO the entity to update.
     * @return the persisted entity.
     */
    BorletteConfDTO update(BorletteConfDTO borletteConfDTO);

    /**
     * Partially updates a borletteConf.
     *
     * @param borletteConfDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BorletteConfDTO> partialUpdate(BorletteConfDTO borletteConfDTO);

    /**
     * Get all the borletteConfs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BorletteConfDTO> findAll(Pageable pageable);

    /**
     * Get the "id" borletteConf.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BorletteConfDTO> findOne(Long id);

    /**
     * Delete the "id" borletteConf.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
