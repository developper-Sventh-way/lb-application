package com.seventh.lotobig.web.rest;

import com.seventh.lotobig.repository.LimitConfManagerRepository;
import com.seventh.lotobig.service.LimitConfManagerService;
import com.seventh.lotobig.service.dto.LimitConfManagerDTO;
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
 * REST controller for managing {@link com.seventh.lotobig.domain.LimitConfManager}.
 */
@RestController
@RequestMapping("/api")
public class LimitConfManagerResource {

    private final Logger log = LoggerFactory.getLogger(LimitConfManagerResource.class);

    private static final String ENTITY_NAME = "limitConfManager";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LimitConfManagerService limitConfManagerService;

    private final LimitConfManagerRepository limitConfManagerRepository;

    public LimitConfManagerResource(
        LimitConfManagerService limitConfManagerService,
        LimitConfManagerRepository limitConfManagerRepository
    ) {
        this.limitConfManagerService = limitConfManagerService;
        this.limitConfManagerRepository = limitConfManagerRepository;
    }

    /**
     * {@code POST  /limit-conf-managers} : Create a new limitConfManager.
     *
     * @param limitConfManagerDTO the limitConfManagerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new limitConfManagerDTO, or with status {@code 400 (Bad Request)} if the limitConfManager has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/limit-conf-managers")
    public ResponseEntity<LimitConfManagerDTO> createLimitConfManager(@Valid @RequestBody LimitConfManagerDTO limitConfManagerDTO)
        throws URISyntaxException {
        log.debug("REST request to save LimitConfManager : {}", limitConfManagerDTO);
        if (limitConfManagerDTO.getId() != null) {
            throw new BadRequestAlertException("A new limitConfManager cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LimitConfManagerDTO result = limitConfManagerService.save(limitConfManagerDTO);
        return ResponseEntity
            .created(new URI("/api/limit-conf-managers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /limit-conf-managers/:id} : Updates an existing limitConfManager.
     *
     * @param id the id of the limitConfManagerDTO to save.
     * @param limitConfManagerDTO the limitConfManagerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated limitConfManagerDTO,
     * or with status {@code 400 (Bad Request)} if the limitConfManagerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the limitConfManagerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/limit-conf-managers/{id}")
    public ResponseEntity<LimitConfManagerDTO> updateLimitConfManager(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LimitConfManagerDTO limitConfManagerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LimitConfManager : {}, {}", id, limitConfManagerDTO);
        if (limitConfManagerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, limitConfManagerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!limitConfManagerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LimitConfManagerDTO result = limitConfManagerService.update(limitConfManagerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, limitConfManagerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /limit-conf-managers/:id} : Partial updates given fields of an existing limitConfManager, field will ignore if it is null
     *
     * @param id the id of the limitConfManagerDTO to save.
     * @param limitConfManagerDTO the limitConfManagerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated limitConfManagerDTO,
     * or with status {@code 400 (Bad Request)} if the limitConfManagerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the limitConfManagerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the limitConfManagerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/limit-conf-managers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LimitConfManagerDTO> partialUpdateLimitConfManager(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LimitConfManagerDTO limitConfManagerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LimitConfManager partially : {}, {}", id, limitConfManagerDTO);
        if (limitConfManagerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, limitConfManagerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!limitConfManagerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LimitConfManagerDTO> result = limitConfManagerService.partialUpdate(limitConfManagerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, limitConfManagerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /limit-conf-managers} : get all the limitConfManagers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of limitConfManagers in body.
     */
    @GetMapping("/limit-conf-managers")
    public ResponseEntity<List<LimitConfManagerDTO>> getAllLimitConfManagers(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of LimitConfManagers");
        Page<LimitConfManagerDTO> page = limitConfManagerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /limit-conf-managers/:id} : get the "id" limitConfManager.
     *
     * @param id the id of the limitConfManagerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the limitConfManagerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/limit-conf-managers/{id}")
    public ResponseEntity<LimitConfManagerDTO> getLimitConfManager(@PathVariable Long id) {
        log.debug("REST request to get LimitConfManager : {}", id);
        Optional<LimitConfManagerDTO> limitConfManagerDTO = limitConfManagerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(limitConfManagerDTO);
    }

    /**
     * {@code DELETE  /limit-conf-managers/:id} : delete the "id" limitConfManager.
     *
     * @param id the id of the limitConfManagerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/limit-conf-managers/{id}")
    public ResponseEntity<Void> deleteLimitConfManager(@PathVariable Long id) {
        log.debug("REST request to delete LimitConfManager : {}", id);
        limitConfManagerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
