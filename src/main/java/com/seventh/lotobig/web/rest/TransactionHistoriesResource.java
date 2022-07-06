package com.seventh.lotobig.web.rest;

import com.seventh.lotobig.repository.TransactionHistoriesRepository;
import com.seventh.lotobig.service.TransactionHistoriesService;
import com.seventh.lotobig.service.dto.TransactionHistoriesDTO;
import com.seventh.lotobig.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.seventh.lotobig.domain.TransactionHistories}.
 */
@RestController
@RequestMapping("/api")
public class TransactionHistoriesResource {

    private final Logger log = LoggerFactory.getLogger(TransactionHistoriesResource.class);

    private static final String ENTITY_NAME = "transactionHistories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionHistoriesService transactionHistoriesService;

    private final TransactionHistoriesRepository transactionHistoriesRepository;

    public TransactionHistoriesResource(
        TransactionHistoriesService transactionHistoriesService,
        TransactionHistoriesRepository transactionHistoriesRepository
    ) {
        this.transactionHistoriesService = transactionHistoriesService;
        this.transactionHistoriesRepository = transactionHistoriesRepository;
    }

    /**
     * {@code POST  /transaction-histories} : Create a new transactionHistories.
     *
     * @param transactionHistoriesDTO the transactionHistoriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionHistoriesDTO, or with status {@code 400 (Bad Request)} if the transactionHistories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-histories")
    public ResponseEntity<TransactionHistoriesDTO> createTransactionHistories(
        @Valid @RequestBody TransactionHistoriesDTO transactionHistoriesDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TransactionHistories : {}", transactionHistoriesDTO);
        if (transactionHistoriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionHistories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionHistoriesDTO result = transactionHistoriesService.save(transactionHistoriesDTO);
        return ResponseEntity
            .created(new URI("/api/transaction-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-histories/:id} : Updates an existing transactionHistories.
     *
     * @param id the id of the transactionHistoriesDTO to save.
     * @param transactionHistoriesDTO the transactionHistoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionHistoriesDTO,
     * or with status {@code 400 (Bad Request)} if the transactionHistoriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionHistoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-histories/{id}")
    public ResponseEntity<TransactionHistoriesDTO> updateTransactionHistories(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TransactionHistoriesDTO transactionHistoriesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TransactionHistories : {}, {}", id, transactionHistoriesDTO);
        if (transactionHistoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transactionHistoriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transactionHistoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransactionHistoriesDTO result = transactionHistoriesService.update(transactionHistoriesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionHistoriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transaction-histories/:id} : Partial updates given fields of an existing transactionHistories, field will ignore if it is null
     *
     * @param id the id of the transactionHistoriesDTO to save.
     * @param transactionHistoriesDTO the transactionHistoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionHistoriesDTO,
     * or with status {@code 400 (Bad Request)} if the transactionHistoriesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transactionHistoriesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transactionHistoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transaction-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransactionHistoriesDTO> partialUpdateTransactionHistories(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TransactionHistoriesDTO transactionHistoriesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TransactionHistories partially : {}, {}", id, transactionHistoriesDTO);
        if (transactionHistoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transactionHistoriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transactionHistoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransactionHistoriesDTO> result = transactionHistoriesService.partialUpdate(transactionHistoriesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionHistoriesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /transaction-histories} : get all the transactionHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionHistories in body.
     */
    @GetMapping("/transaction-histories")
    public ResponseEntity<List<TransactionHistoriesDTO>> getAllTransactionHistories(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of TransactionHistories");
        Page<TransactionHistoriesDTO> page = transactionHistoriesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transaction-histories/:id} : get the "id" transactionHistories.
     *
     * @param id the id of the transactionHistoriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionHistoriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-histories/{id}")
    public ResponseEntity<TransactionHistoriesDTO> getTransactionHistories(@PathVariable Long id) {
        log.debug("REST request to get TransactionHistories : {}", id);
        Optional<TransactionHistoriesDTO> transactionHistoriesDTO = transactionHistoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionHistoriesDTO);
    }

    /**
     * {@code DELETE  /transaction-histories/:id} : delete the "id" transactionHistories.
     *
     * @param id the id of the transactionHistoriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-histories/{id}")
    public ResponseEntity<Void> deleteTransactionHistories(@PathVariable Long id) {
        log.debug("REST request to delete TransactionHistories : {}", id);
        transactionHistoriesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
