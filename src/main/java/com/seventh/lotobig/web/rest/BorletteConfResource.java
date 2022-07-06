package com.seventh.lotobig.web.rest;

import com.seventh.lotobig.repository.BorletteConfRepository;
import com.seventh.lotobig.service.BorletteConfService;
import com.seventh.lotobig.service.dto.BorletteConfDTO;
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
 * REST controller for managing {@link com.seventh.lotobig.domain.BorletteConf}.
 */
@RestController
@RequestMapping("/api")
public class BorletteConfResource {

    private final Logger log = LoggerFactory.getLogger(BorletteConfResource.class);

    private static final String ENTITY_NAME = "borletteConf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BorletteConfService borletteConfService;

    private final BorletteConfRepository borletteConfRepository;

    public BorletteConfResource(BorletteConfService borletteConfService, BorletteConfRepository borletteConfRepository) {
        this.borletteConfService = borletteConfService;
        this.borletteConfRepository = borletteConfRepository;
    }

    /**
     * {@code POST  /borlette-confs} : Create a new borletteConf.
     *
     * @param borletteConfDTO the borletteConfDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new borletteConfDTO, or with status {@code 400 (Bad Request)} if the borletteConf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/borlette-confs")
    public ResponseEntity<BorletteConfDTO> createBorletteConf(@Valid @RequestBody BorletteConfDTO borletteConfDTO)
        throws URISyntaxException {
        log.debug("REST request to save BorletteConf : {}", borletteConfDTO);
        if (borletteConfDTO.getId() != null) {
            throw new BadRequestAlertException("A new borletteConf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BorletteConfDTO result = borletteConfService.save(borletteConfDTO);
        return ResponseEntity
            .created(new URI("/api/borlette-confs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /borlette-confs/:id} : Updates an existing borletteConf.
     *
     * @param id the id of the borletteConfDTO to save.
     * @param borletteConfDTO the borletteConfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated borletteConfDTO,
     * or with status {@code 400 (Bad Request)} if the borletteConfDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the borletteConfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/borlette-confs/{id}")
    public ResponseEntity<BorletteConfDTO> updateBorletteConf(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BorletteConfDTO borletteConfDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BorletteConf : {}, {}", id, borletteConfDTO);
        if (borletteConfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, borletteConfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!borletteConfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BorletteConfDTO result = borletteConfService.update(borletteConfDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, borletteConfDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /borlette-confs/:id} : Partial updates given fields of an existing borletteConf, field will ignore if it is null
     *
     * @param id the id of the borletteConfDTO to save.
     * @param borletteConfDTO the borletteConfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated borletteConfDTO,
     * or with status {@code 400 (Bad Request)} if the borletteConfDTO is not valid,
     * or with status {@code 404 (Not Found)} if the borletteConfDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the borletteConfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/borlette-confs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BorletteConfDTO> partialUpdateBorletteConf(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BorletteConfDTO borletteConfDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BorletteConf partially : {}, {}", id, borletteConfDTO);
        if (borletteConfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, borletteConfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!borletteConfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BorletteConfDTO> result = borletteConfService.partialUpdate(borletteConfDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, borletteConfDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /borlette-confs} : get all the borletteConfs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of borletteConfs in body.
     */
    @GetMapping("/borlette-confs")
    public ResponseEntity<List<BorletteConfDTO>> getAllBorletteConfs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BorletteConfs");
        Page<BorletteConfDTO> page = borletteConfService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /borlette-confs/:id} : get the "id" borletteConf.
     *
     * @param id the id of the borletteConfDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the borletteConfDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/borlette-confs/{id}")
    public ResponseEntity<BorletteConfDTO> getBorletteConf(@PathVariable Long id) {
        log.debug("REST request to get BorletteConf : {}", id);
        Optional<BorletteConfDTO> borletteConfDTO = borletteConfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(borletteConfDTO);
    }

    /**
     * {@code DELETE  /borlette-confs/:id} : delete the "id" borletteConf.
     *
     * @param id the id of the borletteConfDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/borlette-confs/{id}")
    public ResponseEntity<Void> deleteBorletteConf(@PathVariable Long id) {
        log.debug("REST request to delete BorletteConf : {}", id);
        borletteConfService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
