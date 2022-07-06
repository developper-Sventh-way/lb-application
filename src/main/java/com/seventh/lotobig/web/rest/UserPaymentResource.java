package com.seventh.lotobig.web.rest;

import com.seventh.lotobig.repository.UserPaymentRepository;
import com.seventh.lotobig.service.UserPaymentService;
import com.seventh.lotobig.service.dto.UserPaymentDTO;
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
 * REST controller for managing {@link com.seventh.lotobig.domain.UserPayment}.
 */
@RestController
@RequestMapping("/api")
public class UserPaymentResource {

    private final Logger log = LoggerFactory.getLogger(UserPaymentResource.class);

    private static final String ENTITY_NAME = "userPayment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserPaymentService userPaymentService;

    private final UserPaymentRepository userPaymentRepository;

    public UserPaymentResource(UserPaymentService userPaymentService, UserPaymentRepository userPaymentRepository) {
        this.userPaymentService = userPaymentService;
        this.userPaymentRepository = userPaymentRepository;
    }

    /**
     * {@code POST  /user-payments} : Create a new userPayment.
     *
     * @param userPaymentDTO the userPaymentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userPaymentDTO, or with status {@code 400 (Bad Request)} if the userPayment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-payments")
    public ResponseEntity<UserPaymentDTO> createUserPayment(@Valid @RequestBody UserPaymentDTO userPaymentDTO) throws URISyntaxException {
        log.debug("REST request to save UserPayment : {}", userPaymentDTO);
        if (userPaymentDTO.getId() != null) {
            throw new BadRequestAlertException("A new userPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserPaymentDTO result = userPaymentService.save(userPaymentDTO);
        return ResponseEntity
            .created(new URI("/api/user-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-payments/:id} : Updates an existing userPayment.
     *
     * @param id the id of the userPaymentDTO to save.
     * @param userPaymentDTO the userPaymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userPaymentDTO,
     * or with status {@code 400 (Bad Request)} if the userPaymentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userPaymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-payments/{id}")
    public ResponseEntity<UserPaymentDTO> updateUserPayment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserPaymentDTO userPaymentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserPayment : {}, {}", id, userPaymentDTO);
        if (userPaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userPaymentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userPaymentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserPaymentDTO result = userPaymentService.update(userPaymentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userPaymentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-payments/:id} : Partial updates given fields of an existing userPayment, field will ignore if it is null
     *
     * @param id the id of the userPaymentDTO to save.
     * @param userPaymentDTO the userPaymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userPaymentDTO,
     * or with status {@code 400 (Bad Request)} if the userPaymentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userPaymentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userPaymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-payments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserPaymentDTO> partialUpdateUserPayment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserPaymentDTO userPaymentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserPayment partially : {}, {}", id, userPaymentDTO);
        if (userPaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userPaymentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userPaymentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserPaymentDTO> result = userPaymentService.partialUpdate(userPaymentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userPaymentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-payments} : get all the userPayments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userPayments in body.
     */
    @GetMapping("/user-payments")
    public ResponseEntity<List<UserPaymentDTO>> getAllUserPayments(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of UserPayments");
        Page<UserPaymentDTO> page = userPaymentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-payments/:id} : get the "id" userPayment.
     *
     * @param id the id of the userPaymentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userPaymentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-payments/{id}")
    public ResponseEntity<UserPaymentDTO> getUserPayment(@PathVariable Long id) {
        log.debug("REST request to get UserPayment : {}", id);
        Optional<UserPaymentDTO> userPaymentDTO = userPaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userPaymentDTO);
    }

    /**
     * {@code DELETE  /user-payments/:id} : delete the "id" userPayment.
     *
     * @param id the id of the userPaymentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-payments/{id}")
    public ResponseEntity<Void> deleteUserPayment(@PathVariable Long id) {
        log.debug("REST request to delete UserPayment : {}", id);
        userPaymentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
