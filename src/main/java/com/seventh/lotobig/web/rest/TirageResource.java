package com.seventh.lotobig.web.rest;

import com.seventh.lotobig.repository.TirageRepository;
import com.seventh.lotobig.service.TirageService;
import com.seventh.lotobig.service.dto.TirageDTO;
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
 * REST controller for managing {@link com.seventh.lotobig.domain.Tirage}.
 */
@RestController
@RequestMapping("/api")
public class TirageResource {

    private final Logger log = LoggerFactory.getLogger(TirageResource.class);

    private static final String ENTITY_NAME = "tirage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TirageService tirageService;

    private final TirageRepository tirageRepository;

    public TirageResource(TirageService tirageService, TirageRepository tirageRepository) {
        this.tirageService = tirageService;
        this.tirageRepository = tirageRepository;
    }

    /**
     * {@code POST  /tirages} : Create a new tirage.
     *
     * @param tirageDTO the tirageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tirageDTO, or with status {@code 400 (Bad Request)} if the tirage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tirages")
    public ResponseEntity<TirageDTO> createTirage(@Valid @RequestBody TirageDTO tirageDTO) throws URISyntaxException {
        log.debug("REST request to save Tirage : {}", tirageDTO);
        if (tirageDTO.getId() != null) {
            throw new BadRequestAlertException("A new tirage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TirageDTO result = tirageService.save(tirageDTO);
        return ResponseEntity
            .created(new URI("/api/tirages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tirages/:id} : Updates an existing tirage.
     *
     * @param id the id of the tirageDTO to save.
     * @param tirageDTO the tirageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tirageDTO,
     * or with status {@code 400 (Bad Request)} if the tirageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tirageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tirages/{id}")
    public ResponseEntity<TirageDTO> updateTirage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TirageDTO tirageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Tirage : {}, {}", id, tirageDTO);
        if (tirageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tirageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tirageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TirageDTO result = tirageService.update(tirageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tirageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tirages/:id} : Partial updates given fields of an existing tirage, field will ignore if it is null
     *
     * @param id the id of the tirageDTO to save.
     * @param tirageDTO the tirageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tirageDTO,
     * or with status {@code 400 (Bad Request)} if the tirageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tirageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tirageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tirages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TirageDTO> partialUpdateTirage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TirageDTO tirageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tirage partially : {}, {}", id, tirageDTO);
        if (tirageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tirageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tirageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TirageDTO> result = tirageService.partialUpdate(tirageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tirageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tirages} : get all the tirages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tirages in body.
     */
    @GetMapping("/tirages")
    public ResponseEntity<List<TirageDTO>> getAllTirages(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Tirages");
        Page<TirageDTO> page = tirageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tirages/:id} : get the "id" tirage.
     *
     * @param id the id of the tirageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tirageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tirages/{id}")
    public ResponseEntity<TirageDTO> getTirage(@PathVariable Long id) {
        log.debug("REST request to get Tirage : {}", id);
        Optional<TirageDTO> tirageDTO = tirageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tirageDTO);
    }

    /**
     * {@code DELETE  /tirages/:id} : delete the "id" tirage.
     *
     * @param id the id of the tirageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tirages/{id}")
    public ResponseEntity<Void> deleteTirage(@PathVariable Long id) {
        log.debug("REST request to delete Tirage : {}", id);
        tirageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
