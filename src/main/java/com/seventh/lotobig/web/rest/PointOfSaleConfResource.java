package com.seventh.lotobig.web.rest;

import com.seventh.lotobig.repository.PointOfSaleConfRepository;
import com.seventh.lotobig.service.PointOfSaleConfService;
import com.seventh.lotobig.service.dto.PointOfSaleConfDTO;
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
 * REST controller for managing {@link com.seventh.lotobig.domain.PointOfSaleConf}.
 */
@RestController
@RequestMapping("/api")
public class PointOfSaleConfResource {

    private final Logger log = LoggerFactory.getLogger(PointOfSaleConfResource.class);

    private static final String ENTITY_NAME = "pointOfSaleConf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PointOfSaleConfService pointOfSaleConfService;

    private final PointOfSaleConfRepository pointOfSaleConfRepository;

    public PointOfSaleConfResource(PointOfSaleConfService pointOfSaleConfService, PointOfSaleConfRepository pointOfSaleConfRepository) {
        this.pointOfSaleConfService = pointOfSaleConfService;
        this.pointOfSaleConfRepository = pointOfSaleConfRepository;
    }

    /**
     * {@code POST  /point-of-sale-confs} : Create a new pointOfSaleConf.
     *
     * @param pointOfSaleConfDTO the pointOfSaleConfDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pointOfSaleConfDTO, or with status {@code 400 (Bad Request)} if the pointOfSaleConf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/point-of-sale-confs")
    public ResponseEntity<PointOfSaleConfDTO> createPointOfSaleConf(@Valid @RequestBody PointOfSaleConfDTO pointOfSaleConfDTO)
        throws URISyntaxException {
        log.debug("REST request to save PointOfSaleConf : {}", pointOfSaleConfDTO);
        if (pointOfSaleConfDTO.getId() != null) {
            throw new BadRequestAlertException("A new pointOfSaleConf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PointOfSaleConfDTO result = pointOfSaleConfService.save(pointOfSaleConfDTO);
        return ResponseEntity
            .created(new URI("/api/point-of-sale-confs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /point-of-sale-confs/:id} : Updates an existing pointOfSaleConf.
     *
     * @param id the id of the pointOfSaleConfDTO to save.
     * @param pointOfSaleConfDTO the pointOfSaleConfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointOfSaleConfDTO,
     * or with status {@code 400 (Bad Request)} if the pointOfSaleConfDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pointOfSaleConfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/point-of-sale-confs/{id}")
    public ResponseEntity<PointOfSaleConfDTO> updatePointOfSaleConf(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PointOfSaleConfDTO pointOfSaleConfDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PointOfSaleConf : {}, {}", id, pointOfSaleConfDTO);
        if (pointOfSaleConfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointOfSaleConfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pointOfSaleConfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PointOfSaleConfDTO result = pointOfSaleConfService.update(pointOfSaleConfDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pointOfSaleConfDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /point-of-sale-confs/:id} : Partial updates given fields of an existing pointOfSaleConf, field will ignore if it is null
     *
     * @param id the id of the pointOfSaleConfDTO to save.
     * @param pointOfSaleConfDTO the pointOfSaleConfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointOfSaleConfDTO,
     * or with status {@code 400 (Bad Request)} if the pointOfSaleConfDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pointOfSaleConfDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pointOfSaleConfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/point-of-sale-confs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PointOfSaleConfDTO> partialUpdatePointOfSaleConf(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PointOfSaleConfDTO pointOfSaleConfDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PointOfSaleConf partially : {}, {}", id, pointOfSaleConfDTO);
        if (pointOfSaleConfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointOfSaleConfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pointOfSaleConfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PointOfSaleConfDTO> result = pointOfSaleConfService.partialUpdate(pointOfSaleConfDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pointOfSaleConfDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /point-of-sale-confs} : get all the pointOfSaleConfs.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pointOfSaleConfs in body.
     */
    @GetMapping("/point-of-sale-confs")
    public ResponseEntity<List<PointOfSaleConfDTO>> getAllPointOfSaleConfs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("pointofsale-is-null".equals(filter)) {
            log.debug("REST request to get all PointOfSaleConfs where pointOfSale is null");
            return new ResponseEntity<>(pointOfSaleConfService.findAllWherePointOfSaleIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of PointOfSaleConfs");
        Page<PointOfSaleConfDTO> page = pointOfSaleConfService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /point-of-sale-confs/:id} : get the "id" pointOfSaleConf.
     *
     * @param id the id of the pointOfSaleConfDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pointOfSaleConfDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/point-of-sale-confs/{id}")
    public ResponseEntity<PointOfSaleConfDTO> getPointOfSaleConf(@PathVariable Long id) {
        log.debug("REST request to get PointOfSaleConf : {}", id);
        Optional<PointOfSaleConfDTO> pointOfSaleConfDTO = pointOfSaleConfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pointOfSaleConfDTO);
    }

    /**
     * {@code DELETE  /point-of-sale-confs/:id} : delete the "id" pointOfSaleConf.
     *
     * @param id the id of the pointOfSaleConfDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/point-of-sale-confs/{id}")
    public ResponseEntity<Void> deletePointOfSaleConf(@PathVariable Long id) {
        log.debug("REST request to delete PointOfSaleConf : {}", id);
        pointOfSaleConfService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
