package com.seventh.lotobig.service;

import com.seventh.lotobig.service.dto.CouponGratuitConfDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.seventh.lotobig.domain.CouponGratuitConf}.
 */
public interface CouponGratuitConfService {
    /**
     * Save a couponGratuitConf.
     *
     * @param couponGratuitConfDTO the entity to save.
     * @return the persisted entity.
     */
    CouponGratuitConfDTO save(CouponGratuitConfDTO couponGratuitConfDTO);

    /**
     * Updates a couponGratuitConf.
     *
     * @param couponGratuitConfDTO the entity to update.
     * @return the persisted entity.
     */
    CouponGratuitConfDTO update(CouponGratuitConfDTO couponGratuitConfDTO);

    /**
     * Partially updates a couponGratuitConf.
     *
     * @param couponGratuitConfDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CouponGratuitConfDTO> partialUpdate(CouponGratuitConfDTO couponGratuitConfDTO);

    /**
     * Get all the couponGratuitConfs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CouponGratuitConfDTO> findAll(Pageable pageable);

    /**
     * Get the "id" couponGratuitConf.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CouponGratuitConfDTO> findOne(Long id);

    /**
     * Delete the "id" couponGratuitConf.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
