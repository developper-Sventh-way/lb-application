package com.seventh.lotobig.web.rest;

import com.seventh.lotobig.repository.LimitConfPointRepository;
import com.seventh.lotobig.service.LimitConfPointService;
import com.seventh.lotobig.service.dto.LimitConfPointDTO;
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
 * REST controller for managing {@link com.seventh.lotobig.domain.LimitConfPoint}.
 */
@RestController
@RequestMapping("/api")
public class LimitConfPointResource {

    private final Logger log = LoggerFactory.getLogger(LimitConfPointResource.class);

    private static final String ENTITY_NAME = "limitConfPoint";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LimitConfPointService limitConfPointService;

    private final LimitConfPointRepository limitConfPointRepository;

    public LimitConfPointResource(LimitConfPointService limitConfPointService, LimitConfPointRepository limitConfPointRepository) {
        this.limitConfPointService = limitConfPointService;
        this.limitConfPointRepository = limitConfPointRepository;
    }

    /**
     * {@code POST  /limit-conf-points} : Create a new limitConfPoint.
     *
     * @param limitConfPointDTO the limitConfPointDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new limitConfPointDTO, or with status {@code 400 (Bad Request)} if the limitConfPoint has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/limit-conf-points")
    public ResponseEntity<LimitConfPointDTO> createLimitConfPoint(@Valid @RequestBody LimitConfPointDTO limitConfPointDTO)
        throws URISyntaxException {
        log.debug("REST request to save LimitConfPoint : {}", limitConfPointDTO);
        if (limitConfPointDTO.getId() != null) {
            throw new BadRequestAlertException("A new limitConfPoint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LimitConfPointDTO result = limitConfPointService.save(limitConfPointDTO);
        return ResponseEntity
            .created(new URI("/api/limit-conf-points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /limit-conf-points/:id} : Updates an existing limitConfPoint.
     *
     * @param id the id of the limitConfPointDTO to save.
     * @param limitConfPointDTO the limitConfPointDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated limitConfPointDTO,
     * or with status {@code 400 (Bad Request)} if the limitConfPointDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the limitConfPointDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/limit-conf-points/{id}")
    public ResponseEntity<LimitConfPointDTO> updateLimitConfPoint(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LimitConfPointDTO limitConfPointDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LimitConfPoint : {}, {}", id, limitConfPointDTO);
        if (limitConfPointDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, limitConfPointDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!limitConfPointRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LimitConfPointDTO result = limitConfPointService.update(limitConfPointDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, limitConfPointDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /limit-conf-points/:id} : Partial updates given fields of an existing limitConfPoint, field will ignore if it is null
     *
     * @param id the id of the limitConfPointDTO to save.
     * @param limitConfPointDTO the limitConfPointDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated limitConfPointDTO,
     * or with status {@code 400 (Bad Request)} if the limitConfPointDTO is not valid,
     * or with status {@code 404 (Not Found)} if the limitConfPointDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the limitConfPointDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/limit-conf-points/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LimitConfPointDTO> partialUpdateLimitConfPoint(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LimitConfPointDTO limitConfPointDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LimitConfPoint partially : {}, {}", id, limitConfPointDTO);
        if (limitConfPointDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, limitConfPointDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!limitConfPointRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LimitConfPointDTO> result = limitConfPointService.partialUpdate(limitConfPointDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, limitConfPointDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /limit-conf-points} : get all the limitConfPoints.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of limitConfPoints in body.
     */
    @GetMapping("/limit-conf-points")
    public ResponseEntity<List<LimitConfPointDTO>> getAllLimitConfPoints(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of LimitConfPoints");
        Page<LimitConfPointDTO> page = limitConfPointService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /limit-conf-points/:id} : get the "id" limitConfPoint.
     *
     * @param id the id of the limitConfPointDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the limitConfPointDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/limit-conf-points/{id}")
    public ResponseEntity<LimitConfPointDTO> getLimitConfPoint(@PathVariable Long id) {
        log.debug("REST request to get LimitConfPoint : {}", id);
        Optional<LimitConfPointDTO> limitConfPointDTO = limitConfPointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(limitConfPointDTO);
    }

    /**
     * {@code DELETE  /limit-conf-points/:id} : delete the "id" limitConfPoint.
     *
     * @param id the id of the limitConfPointDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/limit-conf-points/{id}")
    public ResponseEntity<Void> deleteLimitConfPoint(@PathVariable Long id) {
        log.debug("REST request to delete LimitConfPoint : {}", id);
        limitConfPointService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
