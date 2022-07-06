package com.seventh.lotobig.web.rest;

import com.seventh.lotobig.repository.TicketOptionRepository;
import com.seventh.lotobig.service.TicketOptionService;
import com.seventh.lotobig.service.dto.TicketOptionDTO;
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
 * REST controller for managing {@link com.seventh.lotobig.domain.TicketOption}.
 */
@RestController
@RequestMapping("/api")
public class TicketOptionResource {

    private final Logger log = LoggerFactory.getLogger(TicketOptionResource.class);

    private static final String ENTITY_NAME = "ticketOption";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TicketOptionService ticketOptionService;

    private final TicketOptionRepository ticketOptionRepository;

    public TicketOptionResource(TicketOptionService ticketOptionService, TicketOptionRepository ticketOptionRepository) {
        this.ticketOptionService = ticketOptionService;
        this.ticketOptionRepository = ticketOptionRepository;
    }

    /**
     * {@code POST  /ticket-options} : Create a new ticketOption.
     *
     * @param ticketOptionDTO the ticketOptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketOptionDTO, or with status {@code 400 (Bad Request)} if the ticketOption has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ticket-options")
    public ResponseEntity<TicketOptionDTO> createTicketOption(@Valid @RequestBody TicketOptionDTO ticketOptionDTO)
        throws URISyntaxException {
        log.debug("REST request to save TicketOption : {}", ticketOptionDTO);
        if (ticketOptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new ticketOption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TicketOptionDTO result = ticketOptionService.save(ticketOptionDTO);
        return ResponseEntity
            .created(new URI("/api/ticket-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ticket-options/:id} : Updates an existing ticketOption.
     *
     * @param id the id of the ticketOptionDTO to save.
     * @param ticketOptionDTO the ticketOptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketOptionDTO,
     * or with status {@code 400 (Bad Request)} if the ticketOptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ticketOptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ticket-options/{id}")
    public ResponseEntity<TicketOptionDTO> updateTicketOption(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TicketOptionDTO ticketOptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TicketOption : {}, {}", id, ticketOptionDTO);
        if (ticketOptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ticketOptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ticketOptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TicketOptionDTO result = ticketOptionService.update(ticketOptionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ticketOptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ticket-options/:id} : Partial updates given fields of an existing ticketOption, field will ignore if it is null
     *
     * @param id the id of the ticketOptionDTO to save.
     * @param ticketOptionDTO the ticketOptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketOptionDTO,
     * or with status {@code 400 (Bad Request)} if the ticketOptionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ticketOptionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ticketOptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ticket-options/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TicketOptionDTO> partialUpdateTicketOption(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TicketOptionDTO ticketOptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TicketOption partially : {}, {}", id, ticketOptionDTO);
        if (ticketOptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ticketOptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ticketOptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TicketOptionDTO> result = ticketOptionService.partialUpdate(ticketOptionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ticketOptionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ticket-options} : get all the ticketOptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ticketOptions in body.
     */
    @GetMapping("/ticket-options")
    public ResponseEntity<List<TicketOptionDTO>> getAllTicketOptions(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TicketOptions");
        Page<TicketOptionDTO> page = ticketOptionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ticket-options/:id} : get the "id" ticketOption.
     *
     * @param id the id of the ticketOptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketOptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ticket-options/{id}")
    public ResponseEntity<TicketOptionDTO> getTicketOption(@PathVariable Long id) {
        log.debug("REST request to get TicketOption : {}", id);
        Optional<TicketOptionDTO> ticketOptionDTO = ticketOptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ticketOptionDTO);
    }

    /**
     * {@code DELETE  /ticket-options/:id} : delete the "id" ticketOption.
     *
     * @param id the id of the ticketOptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ticket-options/{id}")
    public ResponseEntity<Void> deleteTicketOption(@PathVariable Long id) {
        log.debug("REST request to delete TicketOption : {}", id);
        ticketOptionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
