package com.seventh.lotobig.web.rest;

import com.seventh.lotobig.repository.CreditSoldeRepository;
import com.seventh.lotobig.service.CreditSoldeService;
import com.seventh.lotobig.service.dto.CreditSoldeDTO;
import com.seventh.lotobig.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.seventh.lotobig.domain.CreditSolde}.
 */
@RestController
@RequestMapping("/api")
public class CreditSoldeResource {

    private final Logger log = LoggerFactory.getLogger(CreditSoldeResource.class);

    private static final String ENTITY_NAME = "creditSolde";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreditSoldeService creditSoldeService;

    private final CreditSoldeRepository creditSoldeRepository;

    public CreditSoldeResource(CreditSoldeService creditSoldeService, CreditSoldeRepository creditSoldeRepository) {
        this.creditSoldeService = creditSoldeService;
        this.creditSoldeRepository = creditSoldeRepository;
    }

    /**
     * {@code POST  /credit-soldes} : Create a new creditSolde.
     *
     * @param creditSoldeDTO the creditSoldeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new creditSoldeDTO, or with status {@code 400 (Bad Request)} if the creditSolde has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/credit-soldes")
    public ResponseEntity<CreditSoldeDTO> createCreditSolde(@Valid @RequestBody CreditSoldeDTO creditSoldeDTO) throws URISyntaxException {
        log.debug("REST request to save CreditSolde : {}", creditSoldeDTO);
        if (creditSoldeDTO.getId() != null) {
            throw new BadRequestAlertException("A new creditSolde cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditSoldeDTO result = creditSoldeService.save(creditSoldeDTO);
        return ResponseEntity
            .created(new URI("/api/credit-soldes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /credit-soldes/:id} : Updates an existing creditSolde.
     *
     * @param id the id of the creditSoldeDTO to save.
     * @param creditSoldeDTO the creditSoldeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditSoldeDTO,
     * or with status {@code 400 (Bad Request)} if the creditSoldeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the creditSoldeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/credit-soldes/{id}")
    public ResponseEntity<CreditSoldeDTO> updateCreditSolde(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CreditSoldeDTO creditSoldeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CreditSolde : {}, {}", id, creditSoldeDTO);
        if (creditSoldeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creditSoldeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creditSoldeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CreditSoldeDTO result = creditSoldeService.update(creditSoldeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creditSoldeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /credit-soldes/:id} : Partial updates given fields of an existing creditSolde, field will ignore if it is null
     *
     * @param id the id of the creditSoldeDTO to save.
     * @param creditSoldeDTO the creditSoldeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditSoldeDTO,
     * or with status {@code 400 (Bad Request)} if the creditSoldeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the creditSoldeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the creditSoldeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/credit-soldes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CreditSoldeDTO> partialUpdateCreditSolde(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CreditSoldeDTO creditSoldeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CreditSolde partially : {}, {}", id, creditSoldeDTO);
        if (creditSoldeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creditSoldeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creditSoldeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CreditSoldeDTO> result = creditSoldeService.partialUpdate(creditSoldeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creditSoldeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /credit-soldes} : get all the creditSoldes.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of creditSoldes in body.
     */
    @GetMapping("/credit-soldes")
    public ResponseEntity<List<CreditSoldeDTO>> getAllCreditSoldes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("utilisateur-is-null".equals(filter)) {
            log.debug("REST request to get all CreditSoldes where utilisateur is null");
            return new ResponseEntity<>(creditSoldeService.findAllWhereUtilisateurIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of CreditSoldes");
        Page<CreditSoldeDTO> page = creditSoldeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /credit-soldes/:id} : get the "id" creditSolde.
     *
     * @param id the id of the creditSoldeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the creditSoldeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/credit-soldes/{id}")
    public ResponseEntity<CreditSoldeDTO> getCreditSolde(@PathVariable Long id) {
        log.debug("REST request to get CreditSolde : {}", id);
        Optional<CreditSoldeDTO> creditSoldeDTO = creditSoldeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditSoldeDTO);
    }

    /**
     * {@code DELETE  /credit-soldes/:id} : delete the "id" creditSolde.
     *
     * @param id the id of the creditSoldeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/credit-soldes/{id}")
    public ResponseEntity<Void> deleteCreditSolde(@PathVariable Long id) {
        log.debug("REST request to delete CreditSolde : {}", id);
        creditSoldeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
