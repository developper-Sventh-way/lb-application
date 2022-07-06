package com.seventh.lotobig.web.rest;

import com.seventh.lotobig.repository.CouponGratuitConfRepository;
import com.seventh.lotobig.service.CouponGratuitConfService;
import com.seventh.lotobig.service.dto.CouponGratuitConfDTO;
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
 * REST controller for managing {@link com.seventh.lotobig.domain.CouponGratuitConf}.
 */
@RestController
@RequestMapping("/api")
public class CouponGratuitConfResource {

    private final Logger log = LoggerFactory.getLogger(CouponGratuitConfResource.class);

    private static final String ENTITY_NAME = "couponGratuitConf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CouponGratuitConfService couponGratuitConfService;

    private final CouponGratuitConfRepository couponGratuitConfRepository;

    public CouponGratuitConfResource(
        CouponGratuitConfService couponGratuitConfService,
        CouponGratuitConfRepository couponGratuitConfRepository
    ) {
        this.couponGratuitConfService = couponGratuitConfService;
        this.couponGratuitConfRepository = couponGratuitConfRepository;
    }

    /**
     * {@code POST  /coupon-gratuit-confs} : Create a new couponGratuitConf.
     *
     * @param couponGratuitConfDTO the couponGratuitConfDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new couponGratuitConfDTO, or with status {@code 400 (Bad Request)} if the couponGratuitConf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coupon-gratuit-confs")
    public ResponseEntity<CouponGratuitConfDTO> createCouponGratuitConf(@Valid @RequestBody CouponGratuitConfDTO couponGratuitConfDTO)
        throws URISyntaxException {
        log.debug("REST request to save CouponGratuitConf : {}", couponGratuitConfDTO);
        if (couponGratuitConfDTO.getId() != null) {
            throw new BadRequestAlertException("A new couponGratuitConf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CouponGratuitConfDTO result = couponGratuitConfService.save(couponGratuitConfDTO);
        return ResponseEntity
            .created(new URI("/api/coupon-gratuit-confs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coupon-gratuit-confs/:id} : Updates an existing couponGratuitConf.
     *
     * @param id the id of the couponGratuitConfDTO to save.
     * @param couponGratuitConfDTO the couponGratuitConfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated couponGratuitConfDTO,
     * or with status {@code 400 (Bad Request)} if the couponGratuitConfDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the couponGratuitConfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coupon-gratuit-confs/{id}")
    public ResponseEntity<CouponGratuitConfDTO> updateCouponGratuitConf(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CouponGratuitConfDTO couponGratuitConfDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CouponGratuitConf : {}, {}", id, couponGratuitConfDTO);
        if (couponGratuitConfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, couponGratuitConfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!couponGratuitConfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CouponGratuitConfDTO result = couponGratuitConfService.update(couponGratuitConfDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, couponGratuitConfDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /coupon-gratuit-confs/:id} : Partial updates given fields of an existing couponGratuitConf, field will ignore if it is null
     *
     * @param id the id of the couponGratuitConfDTO to save.
     * @param couponGratuitConfDTO the couponGratuitConfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated couponGratuitConfDTO,
     * or with status {@code 400 (Bad Request)} if the couponGratuitConfDTO is not valid,
     * or with status {@code 404 (Not Found)} if the couponGratuitConfDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the couponGratuitConfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/coupon-gratuit-confs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CouponGratuitConfDTO> partialUpdateCouponGratuitConf(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CouponGratuitConfDTO couponGratuitConfDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CouponGratuitConf partially : {}, {}", id, couponGratuitConfDTO);
        if (couponGratuitConfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, couponGratuitConfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!couponGratuitConfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CouponGratuitConfDTO> result = couponGratuitConfService.partialUpdate(couponGratuitConfDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, couponGratuitConfDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /coupon-gratuit-confs} : get all the couponGratuitConfs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of couponGratuitConfs in body.
     */
    @GetMapping("/coupon-gratuit-confs")
    public ResponseEntity<List<CouponGratuitConfDTO>> getAllCouponGratuitConfs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CouponGratuitConfs");
        Page<CouponGratuitConfDTO> page = couponGratuitConfService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /coupon-gratuit-confs/:id} : get the "id" couponGratuitConf.
     *
     * @param id the id of the couponGratuitConfDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the couponGratuitConfDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coupon-gratuit-confs/{id}")
    public ResponseEntity<CouponGratuitConfDTO> getCouponGratuitConf(@PathVariable Long id) {
        log.debug("REST request to get CouponGratuitConf : {}", id);
        Optional<CouponGratuitConfDTO> couponGratuitConfDTO = couponGratuitConfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(couponGratuitConfDTO);
    }

    /**
     * {@code DELETE  /coupon-gratuit-confs/:id} : delete the "id" couponGratuitConf.
     *
     * @param id the id of the couponGratuitConfDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coupon-gratuit-confs/{id}")
    public ResponseEntity<Void> deleteCouponGratuitConf(@PathVariable Long id) {
        log.debug("REST request to delete CouponGratuitConf : {}", id);
        couponGratuitConfService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
