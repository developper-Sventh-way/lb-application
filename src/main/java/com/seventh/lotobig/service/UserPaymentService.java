package com.seventh.lotobig.service;

import com.seventh.lotobig.service.dto.UserPaymentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.seventh.lotobig.domain.UserPayment}.
 */
public interface UserPaymentService {
    /**
     * Save a userPayment.
     *
     * @param userPaymentDTO the entity to save.
     * @return the persisted entity.
     */
    UserPaymentDTO save(UserPaymentDTO userPaymentDTO);

    /**
     * Updates a userPayment.
     *
     * @param userPaymentDTO the entity to update.
     * @return the persisted entity.
     */
    UserPaymentDTO update(UserPaymentDTO userPaymentDTO);

    /**
     * Partially updates a userPayment.
     *
     * @param userPaymentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserPaymentDTO> partialUpdate(UserPaymentDTO userPaymentDTO);

    /**
     * Get all the userPayments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserPaymentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userPayment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserPaymentDTO> findOne(Long id);

    /**
     * Delete the "id" userPayment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
