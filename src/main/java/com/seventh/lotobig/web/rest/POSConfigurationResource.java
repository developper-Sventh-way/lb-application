package com.seventh.lotobig.web.rest;

import com.seventh.lotobig.repository.POSConfigurationRepository;
import com.seventh.lotobig.service.POSConfigurationService;
import com.seventh.lotobig.service.dto.POSConfigurationDTO;
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
 * REST controller for managing {@link com.seventh.lotobig.domain.POSConfiguration}.
 */
@RestController
@RequestMapping("/api")
public class POSConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(POSConfigurationResource.class);

    private static final String ENTITY_NAME = "pOSConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final POSConfigurationService pOSConfigurationService;

    private final POSConfigurationRepository pOSConfigurationRepository;

    public POSConfigurationResource(
        POSConfigurationService pOSConfigurationService,
        POSConfigurationRepository pOSConfigurationRepository
    ) {
        this.pOSConfigurationService = pOSConfigurationService;
        this.pOSConfigurationRepository = pOSConfigurationRepository;
    }

    /**
     * {@code POST  /pos-configurations} : Create a new pOSConfiguration.
     *
     * @param pOSConfigurationDTO the pOSConfigurationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pOSConfigurationDTO, or with status {@code 400 (Bad Request)} if the pOSConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pos-configurations")
    public ResponseEntity<POSConfigurationDTO> createPOSConfiguration(@Valid @RequestBody POSConfigurationDTO pOSConfigurationDTO)
        throws URISyntaxException {
        log.debug("REST request to save POSConfiguration : {}", pOSConfigurationDTO);
        if (pOSConfigurationDTO.getId() != null) {
            throw new BadRequestAlertException("A new pOSConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        POSConfigurationDTO result = pOSConfigurationService.save(pOSConfigurationDTO);
        return ResponseEntity
            .created(new URI("/api/pos-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pos-configurations/:id} : Updates an existing pOSConfiguration.
     *
     * @param id the id of the pOSConfigurationDTO to save.
     * @param pOSConfigurationDTO the pOSConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pOSConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the pOSConfigurationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pOSConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pos-configurations/{id}")
    public ResponseEntity<POSConfigurationDTO> updatePOSConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody POSConfigurationDTO pOSConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update POSConfiguration : {}, {}", id, pOSConfigurationDTO);
        if (pOSConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pOSConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pOSConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        POSConfigurationDTO result = pOSConfigurationService.update(pOSConfigurationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pOSConfigurationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pos-configurations/:id} : Partial updates given fields of an existing pOSConfiguration, field will ignore if it is null
     *
     * @param id the id of the pOSConfigurationDTO to save.
     * @param pOSConfigurationDTO the pOSConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pOSConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the pOSConfigurationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pOSConfigurationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pOSConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pos-configurations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<POSConfigurationDTO> partialUpdatePOSConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody POSConfigurationDTO pOSConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update POSConfiguration partially : {}, {}", id, pOSConfigurationDTO);
        if (pOSConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pOSConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pOSConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<POSConfigurationDTO> result = pOSConfigurationService.partialUpdate(pOSConfigurationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pOSConfigurationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pos-configurations} : get all the pOSConfigurations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pOSConfigurations in body.
     */
    @GetMapping("/pos-configurations")
    public ResponseEntity<List<POSConfigurationDTO>> getAllPOSConfigurations(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of POSConfigurations");
        Page<POSConfigurationDTO> page = pOSConfigurationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pos-configurations/:id} : get the "id" pOSConfiguration.
     *
     * @param id the id of the pOSConfigurationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pOSConfigurationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pos-configurations/{id}")
    public ResponseEntity<POSConfigurationDTO> getPOSConfiguration(@PathVariable Long id) {
        log.debug("REST request to get POSConfiguration : {}", id);
        Optional<POSConfigurationDTO> pOSConfigurationDTO = pOSConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pOSConfigurationDTO);
    }

    /**
     * {@code DELETE  /pos-configurations/:id} : delete the "id" pOSConfiguration.
     *
     * @param id the id of the pOSConfigurationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pos-configurations/{id}")
    public ResponseEntity<Void> deletePOSConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete POSConfiguration : {}", id);
        pOSConfigurationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
