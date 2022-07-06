package com.seventh.lotobig.service;

import com.seventh.lotobig.service.dto.UserPaymentConfDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.seventh.lotobig.domain.UserPaymentConf}.
 */
public interface UserPaymentConfService {
    /**
     * Save a userPaymentConf.
     *
     * @param userPaymentConfDTO the entity to save.
     * @return the persisted entity.
     */
    UserPaymentConfDTO save(UserPaymentConfDTO userPaymentConfDTO);

    /**
     * Updates a userPaymentConf.
     *
     * @param userPaymentConfDTO the entity to update.
     * @return the persisted entity.
     */
    UserPaymentConfDTO update(UserPaymentConfDTO userPaymentConfDTO);

    /**
     * Partially updates a userPaymentConf.
     *
     * @param userPaymentConfDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserPaymentConfDTO> partialUpdate(UserPaymentConfDTO userPaymentConfDTO);

    /**
     * Get all the userPaymentConfs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserPaymentConfDTO> findAll(Pageable pageable);
    /**
     * Get all the UserPaymentConfDTO where Utilisateur is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<UserPaymentConfDTO> findAllWhereUtilisateurIsNull();

    /**
     * Get the "id" userPaymentConf.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserPaymentConfDTO> findOne(Long id);

    /**
     * Delete the "id" userPaymentConf.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
