package com.seventh.lotobig.service;

import com.seventh.lotobig.service.dto.MembershipConfDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.seventh.lotobig.domain.MembershipConf}.
 */
public interface MembershipConfService {
    /**
     * Save a membershipConf.
     *
     * @param membershipConfDTO the entity to save.
     * @return the persisted entity.
     */
    MembershipConfDTO save(MembershipConfDTO membershipConfDTO);

    /**
     * Updates a membershipConf.
     *
     * @param membershipConfDTO the entity to update.
     * @return the persisted entity.
     */
    MembershipConfDTO update(MembershipConfDTO membershipConfDTO);

    /**
     * Partially updates a membershipConf.
     *
     * @param membershipConfDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MembershipConfDTO> partialUpdate(MembershipConfDTO membershipConfDTO);

    /**
     * Get all the membershipConfs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MembershipConfDTO> findAll(Pageable pageable);

    /**
     * Get the "id" membershipConf.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MembershipConfDTO> findOne(Long id);

    /**
     * Delete the "id" membershipConf.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
