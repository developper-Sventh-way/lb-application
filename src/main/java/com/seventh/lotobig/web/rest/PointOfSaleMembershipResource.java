package com.seventh.lotobig.web.rest;

import com.seventh.lotobig.repository.PointOfSaleMembershipRepository;
import com.seventh.lotobig.service.PointOfSaleMembershipService;
import com.seventh.lotobig.service.dto.PointOfSaleMembershipDTO;
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
 * REST controller for managing {@link com.seventh.lotobig.domain.PointOfSaleMembership}.
 */
@RestController
@RequestMapping("/api")
public class PointOfSaleMembershipResource {

    private final Logger log = LoggerFactory.getLogger(PointOfSaleMembershipResource.class);

    private static final String ENTITY_NAME = "pointOfSaleMembership";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PointOfSaleMembershipService pointOfSaleMembershipService;

    private final PointOfSaleMembershipRepository pointOfSaleMembershipRepository;

    public PointOfSaleMembershipResource(
        PointOfSaleMembershipService pointOfSaleMembershipService,
        PointOfSaleMembershipRepository pointOfSaleMembershipRepository
    ) {
        this.pointOfSaleMembershipService = pointOfSaleMembershipService;
        this.pointOfSaleMembershipRepository = pointOfSaleMembershipRepository;
    }

    /**
     * {@code POST  /point-of-sale-memberships} : Create a new pointOfSaleMembership.
     *
     * @param pointOfSaleMembershipDTO the pointOfSaleMembershipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pointOfSaleMembershipDTO, or with status {@code 400 (Bad Request)} if the pointOfSaleMembership has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/point-of-sale-memberships")
    public ResponseEntity<PointOfSaleMembershipDTO> createPointOfSaleMembership(
        @Valid @RequestBody PointOfSaleMembershipDTO pointOfSaleMembershipDTO
    ) throws URISyntaxException {
        log.debug("REST request to save PointOfSaleMembership : {}", pointOfSaleMembershipDTO);
        if (pointOfSaleMembershipDTO.getId() != null) {
            throw new BadRequestAlertException("A new pointOfSaleMembership cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PointOfSaleMembershipDTO result = pointOfSaleMembershipService.save(pointOfSaleMembershipDTO);
        return ResponseEntity
            .created(new URI("/api/point-of-sale-memberships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /point-of-sale-memberships/:id} : Updates an existing pointOfSaleMembership.
     *
     * @param id the id of the pointOfSaleMembershipDTO to save.
     * @param pointOfSaleMembershipDTO the pointOfSaleMembershipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointOfSaleMembershipDTO,
     * or with status {@code 400 (Bad Request)} if the pointOfSaleMembershipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pointOfSaleMembershipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/point-of-sale-memberships/{id}")
    public ResponseEntity<PointOfSaleMembershipDTO> updatePointOfSaleMembership(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PointOfSaleMembershipDTO pointOfSaleMembershipDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PointOfSaleMembership : {}, {}", id, pointOfSaleMembershipDTO);
        if (pointOfSaleMembershipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointOfSaleMembershipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pointOfSaleMembershipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PointOfSaleMembershipDTO result = pointOfSaleMembershipService.update(pointOfSaleMembershipDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pointOfSaleMembershipDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /point-of-sale-memberships/:id} : Partial updates given fields of an existing pointOfSaleMembership, field will ignore if it is null
     *
     * @param id the id of the pointOfSaleMembershipDTO to save.
     * @param pointOfSaleMembershipDTO the pointOfSaleMembershipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointOfSaleMembershipDTO,
     * or with status {@code 400 (Bad Request)} if the pointOfSaleMembershipDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pointOfSaleMembershipDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pointOfSaleMembershipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/point-of-sale-memberships/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PointOfSaleMembershipDTO> partialUpdatePointOfSaleMembership(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PointOfSaleMembershipDTO pointOfSaleMembershipDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PointOfSaleMembership partially : {}, {}", id, pointOfSaleMembershipDTO);
        if (pointOfSaleMembershipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointOfSaleMembershipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pointOfSaleMembershipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PointOfSaleMembershipDTO> result = pointOfSaleMembershipService.partialUpdate(pointOfSaleMembershipDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pointOfSaleMembershipDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /point-of-sale-memberships} : get all the pointOfSaleMemberships.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pointOfSaleMemberships in body.
     */
    @GetMapping("/point-of-sale-memberships")
    public ResponseEntity<List<PointOfSaleMembershipDTO>> getAllPointOfSaleMemberships(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PointOfSaleMemberships");
        Page<PointOfSaleMembershipDTO> page = pointOfSaleMembershipService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /point-of-sale-memberships/:id} : get the "id" pointOfSaleMembership.
     *
     * @param id the id of the pointOfSaleMembershipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pointOfSaleMembershipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/point-of-sale-memberships/{id}")
    public ResponseEntity<PointOfSaleMembershipDTO> getPointOfSaleMembership(@PathVariable Long id) {
        log.debug("REST request to get PointOfSaleMembership : {}", id);
        Optional<PointOfSaleMembershipDTO> pointOfSaleMembershipDTO = pointOfSaleMembershipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pointOfSaleMembershipDTO);
    }

    /**
     * {@code DELETE  /point-of-sale-memberships/:id} : delete the "id" pointOfSaleMembership.
     *
     * @param id the id of the pointOfSaleMembershipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/point-of-sale-memberships/{id}")
    public ResponseEntity<Void> deletePointOfSaleMembership(@PathVariable Long id) {
        log.debug("REST request to delete PointOfSaleMembership : {}", id);
        pointOfSaleMembershipService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
