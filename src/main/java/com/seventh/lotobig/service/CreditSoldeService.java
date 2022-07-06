package com.seventh.lotobig.service;

import com.seventh.lotobig.service.dto.CreditSoldeDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.seventh.lotobig.domain.CreditSolde}.
 */
public interface CreditSoldeService {
    /**
     * Save a creditSolde.
     *
     * @param creditSoldeDTO the entity to save.
     * @return the persisted entity.
     */
    CreditSoldeDTO save(CreditSoldeDTO creditSoldeDTO);

    /**
     * Updates a creditSolde.
     *
     * @param creditSoldeDTO the entity to update.
     * @return the persisted entity.
     */
    CreditSoldeDTO update(CreditSoldeDTO creditSoldeDTO);

    /**
     * Partially updates a creditSolde.
     *
     * @param creditSoldeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CreditSoldeDTO> partialUpdate(CreditSoldeDTO creditSoldeDTO);

    /**
     * Get all the creditSoldes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CreditSoldeDTO> findAll(Pageable pageable);
    /**
     * Get all the CreditSoldeDTO where Utilisateur is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CreditSoldeDTO> findAllWhereUtilisateurIsNull();

    /**
     * Get the "id" creditSolde.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CreditSoldeDTO> findOne(Long id);

    /**
     * Delete the "id" creditSolde.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
