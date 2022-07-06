package com.seventh.lotobig.web.rest;

import com.seventh.lotobig.repository.LimitConfBorletteRepository;
import com.seventh.lotobig.service.LimitConfBorletteService;
import com.seventh.lotobig.service.dto.LimitConfBorletteDTO;
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
 * REST controller for managing {@link com.seventh.lotobig.domain.LimitConfBorlette}.
 */
@RestController
@RequestMapping("/api")
public class LimitConfBorletteResource {

    private final Logger log = LoggerFactory.getLogger(LimitConfBorletteResource.class);

    private static final String ENTITY_NAME = "limitConfBorlette";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LimitConfBorletteService limitConfBorletteService;

    private final LimitConfBorletteRepository limitConfBorletteRepository;

    public LimitConfBorletteResource(
        LimitConfBorletteService limitConfBorletteService,
        LimitConfBorletteRepository limitConfBorletteRepository
    ) {
        this.limitConfBorletteService = limitConfBorletteService;
        this.limitConfBorletteRepository = limitConfBorletteRepository;
    }

    /**
     * {@code POST  /limit-conf-borlettes} : Create a new limitConfBorlette.
     *
     * @param limitConfBorletteDTO the limitConfBorletteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new limitConfBorletteDTO, or with status {@code 400 (Bad Request)} if the limitConfBorlette has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/limit-conf-borlettes")
    public ResponseEntity<LimitConfBorletteDTO> createLimitConfBorlette(@Valid @RequestBody LimitConfBorletteDTO limitConfBorletteDTO)
        throws URISyntaxException {
        log.debug("REST request to save LimitConfBorlette : {}", limitConfBorletteDTO);
        if (limitConfBorletteDTO.getId() != null) {
            throw new BadRequestAlertException("A new limitConfBorlette cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LimitConfBorletteDTO result = limitConfBorletteService.save(limitConfBorletteDTO);
        return ResponseEntity
            .created(new URI("/api/limit-conf-borlettes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /limit-conf-borlettes/:id} : Updates an existing limitConfBorlette.
     *
     * @param id the id of the limitConfBorletteDTO to save.
     * @param limitConfBorletteDTO the limitConfBorletteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated limitConfBorletteDTO,
     * or with status {@code 400 (Bad Request)} if the limitConfBorletteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the limitConfBorletteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/limit-conf-borlettes/{id}")
    public ResponseEntity<LimitConfBorletteDTO> updateLimitConfBorlette(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LimitConfBorletteDTO limitConfBorletteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LimitConfBorlette : {}, {}", id, limitConfBorletteDTO);
        if (limitConfBorletteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, limitConfBorletteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!limitConfBorletteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LimitConfBorletteDTO result = limitConfBorletteService.update(limitConfBorletteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, limitConfBorletteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /limit-conf-borlettes/:id} : Partial updates given fields of an existing limitConfBorlette, field will ignore if it is null
     *
     * @param id the id of the limitConfBorletteDTO to save.
     * @param limitConfBorletteDTO the limitConfBorletteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated limitConfBorletteDTO,
     * or with status {@code 400 (Bad Request)} if the limitConfBorletteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the limitConfBorletteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the limitConfBorletteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/limit-conf-borlettes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LimitConfBorletteDTO> partialUpdateLimitConfBorlette(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LimitConfBorletteDTO limitConfBorletteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LimitConfBorlette partially : {}, {}", id, limitConfBorletteDTO);
        if (limitConfBorletteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, limitConfBorletteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!limitConfBorletteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LimitConfBorletteDTO> result = limitConfBorletteService.partialUpdate(limitConfBorletteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, limitConfBorletteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /limit-conf-borlettes} : get all the limitConfBorlettes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of limitConfBorlettes in body.
     */
    @GetMapping("/limit-conf-borlettes")
    public ResponseEntity<List<LimitConfBorletteDTO>> getAllLimitConfBorlettes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of LimitConfBorlettes");
        Page<LimitConfBorletteDTO> page = limitConfBorletteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /limit-conf-borlettes/:id} : get the "id" limitConfBorlette.
     *
     * @param id the id of the limitConfBorletteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the limitConfBorletteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/limit-conf-borlettes/{id}")
    public ResponseEntity<LimitConfBorletteDTO> getLimitConfBorlette(@PathVariable Long id) {
        log.debug("REST request to get LimitConfBorlette : {}", id);
        Optional<LimitConfBorletteDTO> limitConfBorletteDTO = limitConfBorletteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(limitConfBorletteDTO);
    }

    /**
     * {@code DELETE  /limit-conf-borlettes/:id} : delete the "id" limitConfBorlette.
     *
     * @param id the id of the limitConfBorletteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/limit-conf-borlettes/{id}")
    public ResponseEntity<Void> deleteLimitConfBorlette(@PathVariable Long id) {
        log.debug("REST request to delete LimitConfBorlette : {}", id);
        limitConfBorletteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
