package com.seventh.lotobig.web.rest;

import com.seventh.lotobig.repository.MembershipConfRepository;
import com.seventh.lotobig.service.MembershipConfService;
import com.seventh.lotobig.service.dto.MembershipConfDTO;
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
 * REST controller for managing {@link com.seventh.lotobig.domain.MembershipConf}.
 */
@RestController
@RequestMapping("/api")
public class MembershipConfResource {

    private final Logger log = LoggerFactory.getLogger(MembershipConfResource.class);

    private static final String ENTITY_NAME = "membershipConf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MembershipConfService membershipConfService;

    private final MembershipConfRepository membershipConfRepository;

    public MembershipConfResource(MembershipConfService membershipConfService, MembershipConfRepository membershipConfRepository) {
        this.membershipConfService = membershipConfService;
        this.membershipConfRepository = membershipConfRepository;
    }

    /**
     * {@code POST  /membership-confs} : Create a new membershipConf.
     *
     * @param membershipConfDTO the membershipConfDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new membershipConfDTO, or with status {@code 400 (Bad Request)} if the membershipConf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/membership-confs")
    public ResponseEntity<MembershipConfDTO> createMembershipConf(@Valid @RequestBody MembershipConfDTO membershipConfDTO)
        throws URISyntaxException {
        log.debug("REST request to save MembershipConf : {}", membershipConfDTO);
        if (membershipConfDTO.getId() != null) {
            throw new BadRequestAlertException("A new membershipConf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MembershipConfDTO result = membershipConfService.save(membershipConfDTO);
        return ResponseEntity
            .created(new URI("/api/membership-confs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /membership-confs/:id} : Updates an existing membershipConf.
     *
     * @param id the id of the membershipConfDTO to save.
     * @param membershipConfDTO the membershipConfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated membershipConfDTO,
     * or with status {@code 400 (Bad Request)} if the membershipConfDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the membershipConfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/membership-confs/{id}")
    public ResponseEntity<MembershipConfDTO> updateMembershipConf(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MembershipConfDTO membershipConfDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MembershipConf : {}, {}", id, membershipConfDTO);
        if (membershipConfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, membershipConfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!membershipConfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MembershipConfDTO result = membershipConfService.update(membershipConfDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, membershipConfDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /membership-confs/:id} : Partial updates given fields of an existing membershipConf, field will ignore if it is null
     *
     * @param id the id of the membershipConfDTO to save.
     * @param membershipConfDTO the membershipConfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated membershipConfDTO,
     * or with status {@code 400 (Bad Request)} if the membershipConfDTO is not valid,
     * or with status {@code 404 (Not Found)} if the membershipConfDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the membershipConfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/membership-confs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MembershipConfDTO> partialUpdateMembershipConf(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MembershipConfDTO membershipConfDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MembershipConf partially : {}, {}", id, membershipConfDTO);
        if (membershipConfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, membershipConfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!membershipConfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MembershipConfDTO> result = membershipConfService.partialUpdate(membershipConfDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, membershipConfDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /membership-confs} : get all the membershipConfs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of membershipConfs in body.
     */
    @GetMapping("/membership-confs")
    public ResponseEntity<List<MembershipConfDTO>> getAllMembershipConfs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of MembershipConfs");
        Page<MembershipConfDTO> page = membershipConfService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /membership-confs/:id} : get the "id" membershipConf.
     *
     * @param id the id of the membershipConfDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the membershipConfDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/membership-confs/{id}")
    public ResponseEntity<MembershipConfDTO> getMembershipConf(@PathVariable Long id) {
        log.debug("REST request to get MembershipConf : {}", id);
        Optional<MembershipConfDTO> membershipConfDTO = membershipConfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(membershipConfDTO);
    }

    /**
     * {@code DELETE  /membership-confs/:id} : delete the "id" membershipConf.
     *
     * @param id the id of the membershipConfDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/membership-confs/{id}")
    public ResponseEntity<Void> deleteMembershipConf(@PathVariable Long id) {
        log.debug("REST request to delete MembershipConf : {}", id);
        membershipConfService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
