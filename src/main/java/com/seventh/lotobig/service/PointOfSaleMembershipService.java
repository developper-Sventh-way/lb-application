package com.seventh.lotobig.service;

import com.seventh.lotobig.service.dto.PointOfSaleMembershipDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.seventh.lotobig.domain.PointOfSaleMembership}.
 */
public interface PointOfSaleMembershipService {
    /**
     * Save a pointOfSaleMembership.
     *
     * @param pointOfSaleMembershipDTO the entity to save.
     * @return the persisted entity.
     */
    PointOfSaleMembershipDTO save(PointOfSaleMembershipDTO pointOfSaleMembershipDTO);

    /**
     * Updates a pointOfSaleMembership.
     *
     * @param pointOfSaleMembershipDTO the entity to update.
     * @return the persisted entity.
     */
    PointOfSaleMembershipDTO update(PointOfSaleMembershipDTO pointOfSaleMembershipDTO);

    /**
     * Partially updates a pointOfSaleMembership.
     *
     * @param pointOfSaleMembershipDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PointOfSaleMembershipDTO> partialUpdate(PointOfSaleMembershipDTO pointOfSaleMembershipDTO);

    /**
     * Get all the pointOfSaleMemberships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PointOfSaleMembershipDTO> findAll(Pageable pageable);

    /**
     * Get the "id" pointOfSaleMembership.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PointOfSaleMembershipDTO> findOne(Long id);

    /**
     * Delete the "id" pointOfSaleMembership.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
