package com.seventh.lotobig.web.rest;

import com.seventh.lotobig.repository.UserPaymentConfRepository;
import com.seventh.lotobig.service.UserPaymentConfService;
import com.seventh.lotobig.service.dto.UserPaymentConfDTO;
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
 * REST controller for managing {@link com.seventh.lotobig.domain.UserPaymentConf}.
 */
@RestController
@RequestMapping("/api")
public class UserPaymentConfResource {

    private final Logger log = LoggerFactory.getLogger(UserPaymentConfResource.class);

    private static final String ENTITY_NAME = "userPaymentConf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserPaymentConfService userPaymentConfService;

    private final UserPaymentConfRepository userPaymentConfRepository;

    public UserPaymentConfResource(UserPaymentConfService userPaymentConfService, UserPaymentConfRepository userPaymentConfRepository) {
        this.userPaymentConfService = userPaymentConfService;
        this.userPaymentConfRepository = userPaymentConfRepository;
    }

    /**
     * {@code POST  /user-payment-confs} : Create a new userPaymentConf.
     *
     * @param userPaymentConfDTO the userPaymentConfDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userPaymentConfDTO, or with status {@code 400 (Bad Request)} if the userPaymentConf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-payment-confs")
    public ResponseEntity<UserPaymentConfDTO> createUserPaymentConf(@Valid @RequestBody UserPaymentConfDTO userPaymentConfDTO)
        throws URISyntaxException {
        log.debug("REST request to save UserPaymentConf : {}", userPaymentConfDTO);
        if (userPaymentConfDTO.getId() != null) {
            throw new BadRequestAlertException("A new userPaymentConf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserPaymentConfDTO result = userPaymentConfService.save(userPaymentConfDTO);
        return ResponseEntity
            .created(new URI("/api/user-payment-confs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-payment-confs/:id} : Updates an existing userPaymentConf.
     *
     * @param id the id of the userPaymentConfDTO to save.
     * @param userPaymentConfDTO the userPaymentConfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userPaymentConfDTO,
     * or with status {@code 400 (Bad Request)} if the userPaymentConfDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userPaymentConfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-payment-confs/{id}")
    public ResponseEntity<UserPaymentConfDTO> updateUserPaymentConf(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserPaymentConfDTO userPaymentConfDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserPaymentConf : {}, {}", id, userPaymentConfDTO);
        if (userPaymentConfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userPaymentConfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userPaymentConfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserPaymentConfDTO result = userPaymentConfService.update(userPaymentConfDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userPaymentConfDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-payment-confs/:id} : Partial updates given fields of an existing userPaymentConf, field will ignore if it is null
     *
     * @param id the id of the userPaymentConfDTO to save.
     * @param userPaymentConfDTO the userPaymentConfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userPaymentConfDTO,
     * or with status {@code 400 (Bad Request)} if the userPaymentConfDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userPaymentConfDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userPaymentConfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-payment-confs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserPaymentConfDTO> partialUpdateUserPaymentConf(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserPaymentConfDTO userPaymentConfDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserPaymentConf partially : {}, {}", id, userPaymentConfDTO);
        if (userPaymentConfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userPaymentConfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userPaymentConfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserPaymentConfDTO> result = userPaymentConfService.partialUpdate(userPaymentConfDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userPaymentConfDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-payment-confs} : get all the userPaymentConfs.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userPaymentConfs in body.
     */
    @GetMapping("/user-payment-confs")
    public ResponseEntity<List<UserPaymentConfDTO>> getAllUserPaymentConfs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("utilisateur-is-null".equals(filter)) {
            log.debug("REST request to get all UserPaymentConfs where utilisateur is null");
            return new ResponseEntity<>(userPaymentConfService.findAllWhereUtilisateurIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of UserPaymentConfs");
        Page<UserPaymentConfDTO> page = userPaymentConfService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-payment-confs/:id} : get the "id" userPaymentConf.
     *
     * @param id the id of the userPaymentConfDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userPaymentConfDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-payment-confs/{id}")
    public ResponseEntity<UserPaymentConfDTO> getUserPaymentConf(@PathVariable Long id) {
        log.debug("REST request to get UserPaymentConf : {}", id);
        Optional<UserPaymentConfDTO> userPaymentConfDTO = userPaymentConfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userPaymentConfDTO);
    }

    /**
     * {@code DELETE  /user-payment-confs/:id} : delete the "id" userPaymentConf.
     *
     * @param id the id of the userPaymentConfDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-payment-confs/{id}")
    public ResponseEntity<Void> deleteUserPaymentConf(@PathVariable Long id) {
        log.debug("REST request to delete UserPaymentConf : {}", id);
        userPaymentConfService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
