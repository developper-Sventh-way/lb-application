package com.seventh.lotobig.web.rest;

import com.seventh.lotobig.repository.SystemTraceRepository;
import com.seventh.lotobig.service.SystemTraceService;
import com.seventh.lotobig.service.dto.SystemTraceDTO;
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
 * REST controller for managing {@link com.seventh.lotobig.domain.SystemTrace}.
 */
@RestController
@RequestMapping("/api")
public class SystemTraceResource {

    private final Logger log = LoggerFactory.getLogger(SystemTraceResource.class);

    private static final String ENTITY_NAME = "systemTrace";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemTraceService systemTraceService;

    private final SystemTraceRepository systemTraceRepository;

    public SystemTraceResource(SystemTraceService systemTraceService, SystemTraceRepository systemTraceRepository) {
        this.systemTraceService = systemTraceService;
        this.systemTraceRepository = systemTraceRepository;
    }

    /**
     * {@code POST  /system-traces} : Create a new systemTrace.
     *
     * @param systemTraceDTO the systemTraceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new systemTraceDTO, or with status {@code 400 (Bad Request)} if the systemTrace has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/system-traces")
    public ResponseEntity<SystemTraceDTO> createSystemTrace(@Valid @RequestBody SystemTraceDTO systemTraceDTO) throws URISyntaxException {
        log.debug("REST request to save SystemTrace : {}", systemTraceDTO);
        if (systemTraceDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemTrace cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemTraceDTO result = systemTraceService.save(systemTraceDTO);
        return ResponseEntity
            .created(new URI("/api/system-traces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /system-traces/:id} : Updates an existing systemTrace.
     *
     * @param id the id of the systemTraceDTO to save.
     * @param systemTraceDTO the systemTraceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemTraceDTO,
     * or with status {@code 400 (Bad Request)} if the systemTraceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the systemTraceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/system-traces/{id}")
    public ResponseEntity<SystemTraceDTO> updateSystemTrace(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SystemTraceDTO systemTraceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SystemTrace : {}, {}", id, systemTraceDTO);
        if (systemTraceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, systemTraceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!systemTraceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SystemTraceDTO result = systemTraceService.update(systemTraceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, systemTraceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /system-traces/:id} : Partial updates given fields of an existing systemTrace, field will ignore if it is null
     *
     * @param id the id of the systemTraceDTO to save.
     * @param systemTraceDTO the systemTraceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemTraceDTO,
     * or with status {@code 400 (Bad Request)} if the systemTraceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the systemTraceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the systemTraceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/system-traces/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SystemTraceDTO> partialUpdateSystemTrace(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SystemTraceDTO systemTraceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SystemTrace partially : {}, {}", id, systemTraceDTO);
        if (systemTraceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, systemTraceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!systemTraceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SystemTraceDTO> result = systemTraceService.partialUpdate(systemTraceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, systemTraceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /system-traces} : get all the systemTraces.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systemTraces in body.
     */
    @GetMapping("/system-traces")
    public ResponseEntity<List<SystemTraceDTO>> getAllSystemTraces(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SystemTraces");
        Page<SystemTraceDTO> page = systemTraceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /system-traces/:id} : get the "id" systemTrace.
     *
     * @param id the id of the systemTraceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the systemTraceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/system-traces/{id}")
    public ResponseEntity<SystemTraceDTO> getSystemTrace(@PathVariable Long id) {
        log.debug("REST request to get SystemTrace : {}", id);
        Optional<SystemTraceDTO> systemTraceDTO = systemTraceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemTraceDTO);
    }

    /**
     * {@code DELETE  /system-traces/:id} : delete the "id" systemTrace.
     *
     * @param id the id of the systemTraceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/system-traces/{id}")
    public ResponseEntity<Void> deleteSystemTrace(@PathVariable Long id) {
        log.debug("REST request to delete SystemTrace : {}", id);
        systemTraceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
