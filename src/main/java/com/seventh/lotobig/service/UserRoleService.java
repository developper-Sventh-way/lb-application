package com.seventh.lotobig.service;

import com.seventh.lotobig.service.dto.UserRoleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.seventh.lotobig.domain.UserRole}.
 */
public interface UserRoleService {
    /**
     * Save a userRole.
     *
     * @param userRoleDTO the entity to save.
     * @return the persisted entity.
     */
    UserRoleDTO save(UserRoleDTO userRoleDTO);

    /**
     * Updates a userRole.
     *
     * @param userRoleDTO the entity to update.
     * @return the persisted entity.
     */
    UserRoleDTO update(UserRoleDTO userRoleDTO);

    /**
     * Partially updates a userRole.
     *
     * @param userRoleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserRoleDTO> partialUpdate(UserRoleDTO userRoleDTO);

    /**
     * Get all the userRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserRoleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userRole.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserRoleDTO> findOne(Long id);

    /**
     * Delete the "id" userRole.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
