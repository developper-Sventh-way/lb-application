package com.seventh.lotobig.service;

import com.seventh.lotobig.service.dto.PointOfSaleConfDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.seventh.lotobig.domain.PointOfSaleConf}.
 */
public interface PointOfSaleConfService {
    /**
     * Save a pointOfSaleConf.
     *
     * @param pointOfSaleConfDTO the entity to save.
     * @return the persisted entity.
     */
    PointOfSaleConfDTO save(PointOfSaleConfDTO pointOfSaleConfDTO);

    /**
     * Updates a pointOfSaleConf.
     *
     * @param pointOfSaleConfDTO the entity to update.
     * @return the persisted entity.
     */
    PointOfSaleConfDTO update(PointOfSaleConfDTO pointOfSaleConfDTO);

    /**
     * Partially updates a pointOfSaleConf.
     *
     * @param pointOfSaleConfDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PointOfSaleConfDTO> partialUpdate(PointOfSaleConfDTO pointOfSaleConfDTO);

    /**
     * Get all the pointOfSaleConfs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PointOfSaleConfDTO> findAll(Pageable pageable);
    /**
     * Get all the PointOfSaleConfDTO where PointOfSale is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<PointOfSaleConfDTO> findAllWherePointOfSaleIsNull();

    /**
     * Get the "id" pointOfSaleConf.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PointOfSaleConfDTO> findOne(Long id);

    /**
     * Delete the "id" pointOfSaleConf.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
