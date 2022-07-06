package com.seventh.lotobig.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.Ticket;
import com.seventh.lotobig.domain.enumeration.StatutFiche;
import com.seventh.lotobig.repository.TicketRepository;
import com.seventh.lotobig.service.dto.TicketDTO;
import com.seventh.lotobig.service.mapper.TicketMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TicketResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TicketResourceIT {

    private static final String DEFAULT_TICKET_NO = "AAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_TICKET_NO = "BBBBBBBBBBBBBBBBBB";

    private static final StatutFiche DEFAULT_STATUT_FICHE = StatutFiche.ACTIVE;
    private static final StatutFiche UPDATED_STATUT_FICHE = StatutFiche.ANNULED;

    private static final String DEFAULT_CLOSE_BY = "AAAAAAAAAA";
    private static final String UPDATED_CLOSE_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CLOSE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CLOSE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_CLOSED = false;
    private static final Boolean UPDATED_IS_CLOSED = true;

    private static final Long DEFAULT_CLOSE_BY_ID = 1L;
    private static final Long UPDATED_CLOSE_BY_ID = 2L;

    private static final String DEFAULT_PAY_BY = "AAAAAAAAAA";
    private static final String UPDATED_PAY_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_PAY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_PAY = false;
    private static final Boolean UPDATED_IS_PAY = true;

    private static final Long DEFAULT_PAY_BY_ID = 1L;
    private static final Long UPDATED_PAY_BY_ID = 2L;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/tickets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTicketMockMvc;

    private Ticket ticket;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ticket createEntity(EntityManager em) {
        Ticket ticket = new Ticket()
            .ticketNo(DEFAULT_TICKET_NO)
            .statutFiche(DEFAULT_STATUT_FICHE)
            .closeBy(DEFAULT_CLOSE_BY)
            .closeDate(DEFAULT_CLOSE_DATE)
            .isClosed(DEFAULT_IS_CLOSED)
            .closeById(DEFAULT_CLOSE_BY_ID)
            .payBy(DEFAULT_PAY_BY)
            .payDate(DEFAULT_PAY_DATE)
            .isPay(DEFAULT_IS_PAY)
            .payById(DEFAULT_PAY_BY_ID)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return ticket;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ticket createUpdatedEntity(EntityManager em) {
        Ticket ticket = new Ticket()
            .ticketNo(UPDATED_TICKET_NO)
            .statutFiche(UPDATED_STATUT_FICHE)
            .closeBy(UPDATED_CLOSE_BY)
            .closeDate(UPDATED_CLOSE_DATE)
            .isClosed(UPDATED_IS_CLOSED)
            .closeById(UPDATED_CLOSE_BY_ID)
            .payBy(UPDATED_PAY_BY)
            .payDate(UPDATED_PAY_DATE)
            .isPay(UPDATED_IS_PAY)
            .payById(UPDATED_PAY_BY_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return ticket;
    }

    @BeforeEach
    public void initTest() {
        ticket = createEntity(em);
    }

    @Test
    @Transactional
    void createTicket() throws Exception {
        int databaseSizeBeforeCreate = ticketRepository.findAll().size();
        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);
        restTicketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketDTO)))
            .andExpect(status().isCreated());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeCreate + 1);
        Ticket testTicket = ticketList.get(ticketList.size() - 1);
        assertThat(testTicket.getTicketNo()).isEqualTo(DEFAULT_TICKET_NO);
        assertThat(testTicket.getStatutFiche()).isEqualTo(DEFAULT_STATUT_FICHE);
        assertThat(testTicket.getCloseBy()).isEqualTo(DEFAULT_CLOSE_BY);
        assertThat(testTicket.getCloseDate()).isEqualTo(DEFAULT_CLOSE_DATE);
        assertThat(testTicket.getIsClosed()).isEqualTo(DEFAULT_IS_CLOSED);
        assertThat(testTicket.getCloseById()).isEqualTo(DEFAULT_CLOSE_BY_ID);
        assertThat(testTicket.getPayBy()).isEqualTo(DEFAULT_PAY_BY);
        assertThat(testTicket.getPayDate()).isEqualTo(DEFAULT_PAY_DATE);
        assertThat(testTicket.getIsPay()).isEqualTo(DEFAULT_IS_PAY);
        assertThat(testTicket.getPayById()).isEqualTo(DEFAULT_PAY_BY_ID);
        assertThat(testTicket.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTicket.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTicket.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testTicket.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createTicketWithExistingId() throws Exception {
        // Create the Ticket with an existing ID
        ticket.setId(1L);
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        int databaseSizeBeforeCreate = ticketRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatutFicheIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketRepository.findAll().size();
        // set the field null
        ticket.setStatutFiche(null);

        // Create the Ticket, which fails.
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        restTicketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketDTO)))
            .andExpect(status().isBadRequest());

        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketRepository.findAll().size();
        // set the field null
        ticket.setCreatedBy(null);

        // Create the Ticket, which fails.
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        restTicketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketDTO)))
            .andExpect(status().isBadRequest());

        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketRepository.findAll().size();
        // set the field null
        ticket.setCreatedDate(null);

        // Create the Ticket, which fails.
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        restTicketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketDTO)))
            .andExpect(status().isBadRequest());

        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTickets() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList
        restTicketMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticket.getId().intValue())))
            .andExpect(jsonPath("$.[*].ticketNo").value(hasItem(DEFAULT_TICKET_NO)))
            .andExpect(jsonPath("$.[*].statutFiche").value(hasItem(DEFAULT_STATUT_FICHE.toString())))
            .andExpect(jsonPath("$.[*].closeBy").value(hasItem(DEFAULT_CLOSE_BY)))
            .andExpect(jsonPath("$.[*].closeDate").value(hasItem(DEFAULT_CLOSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].isClosed").value(hasItem(DEFAULT_IS_CLOSED.booleanValue())))
            .andExpect(jsonPath("$.[*].closeById").value(hasItem(DEFAULT_CLOSE_BY_ID.intValue())))
            .andExpect(jsonPath("$.[*].payBy").value(hasItem(DEFAULT_PAY_BY)))
            .andExpect(jsonPath("$.[*].payDate").value(hasItem(DEFAULT_PAY_DATE.toString())))
            .andExpect(jsonPath("$.[*].isPay").value(hasItem(DEFAULT_IS_PAY.booleanValue())))
            .andExpect(jsonPath("$.[*].payById").value(hasItem(DEFAULT_PAY_BY_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getTicket() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);

        // Get the ticket
        restTicketMockMvc
            .perform(get(ENTITY_API_URL_ID, ticket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ticket.getId().intValue()))
            .andExpect(jsonPath("$.ticketNo").value(DEFAULT_TICKET_NO))
            .andExpect(jsonPath("$.statutFiche").value(DEFAULT_STATUT_FICHE.toString()))
            .andExpect(jsonPath("$.closeBy").value(DEFAULT_CLOSE_BY))
            .andExpect(jsonPath("$.closeDate").value(DEFAULT_CLOSE_DATE.toString()))
            .andExpect(jsonPath("$.isClosed").value(DEFAULT_IS_CLOSED.booleanValue()))
            .andExpect(jsonPath("$.closeById").value(DEFAULT_CLOSE_BY_ID.intValue()))
            .andExpect(jsonPath("$.payBy").value(DEFAULT_PAY_BY))
            .andExpect(jsonPath("$.payDate").value(DEFAULT_PAY_DATE.toString()))
            .andExpect(jsonPath("$.isPay").value(DEFAULT_IS_PAY.booleanValue()))
            .andExpect(jsonPath("$.payById").value(DEFAULT_PAY_BY_ID.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTicket() throws Exception {
        // Get the ticket
        restTicketMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTicket() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);

        int databaseSizeBeforeUpdate = ticketRepository.findAll().size();

        // Update the ticket
        Ticket updatedTicket = ticketRepository.findById(ticket.getId()).get();
        // Disconnect from session so that the updates on updatedTicket are not directly saved in db
        em.detach(updatedTicket);
        updatedTicket
            .ticketNo(UPDATED_TICKET_NO)
            .statutFiche(UPDATED_STATUT_FICHE)
            .closeBy(UPDATED_CLOSE_BY)
            .closeDate(UPDATED_CLOSE_DATE)
            .isClosed(UPDATED_IS_CLOSED)
            .closeById(UPDATED_CLOSE_BY_ID)
            .payBy(UPDATED_PAY_BY)
            .payDate(UPDATED_PAY_DATE)
            .isPay(UPDATED_IS_PAY)
            .payById(UPDATED_PAY_BY_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        TicketDTO ticketDTO = ticketMapper.toDto(updatedTicket);

        restTicketMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ticketDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ticketDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
        Ticket testTicket = ticketList.get(ticketList.size() - 1);
        assertThat(testTicket.getTicketNo()).isEqualTo(UPDATED_TICKET_NO);
        assertThat(testTicket.getStatutFiche()).isEqualTo(UPDATED_STATUT_FICHE);
        assertThat(testTicket.getCloseBy()).isEqualTo(UPDATED_CLOSE_BY);
        assertThat(testTicket.getCloseDate()).isEqualTo(UPDATED_CLOSE_DATE);
        assertThat(testTicket.getIsClosed()).isEqualTo(UPDATED_IS_CLOSED);
        assertThat(testTicket.getCloseById()).isEqualTo(UPDATED_CLOSE_BY_ID);
        assertThat(testTicket.getPayBy()).isEqualTo(UPDATED_PAY_BY);
        assertThat(testTicket.getPayDate()).isEqualTo(UPDATED_PAY_DATE);
        assertThat(testTicket.getIsPay()).isEqualTo(UPDATED_IS_PAY);
        assertThat(testTicket.getPayById()).isEqualTo(UPDATED_PAY_BY_ID);
        assertThat(testTicket.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTicket.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTicket.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTicket.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTicket() throws Exception {
        int databaseSizeBeforeUpdate = ticketRepository.findAll().size();
        ticket.setId(count.incrementAndGet());

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ticketDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ticketDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTicket() throws Exception {
        int databaseSizeBeforeUpdate = ticketRepository.findAll().size();
        ticket.setId(count.incrementAndGet());

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ticketDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTicket() throws Exception {
        int databaseSizeBeforeUpdate = ticketRepository.findAll().size();
        ticket.setId(count.incrementAndGet());

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTicketWithPatch() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);

        int databaseSizeBeforeUpdate = ticketRepository.findAll().size();

        // Update the ticket using partial update
        Ticket partialUpdatedTicket = new Ticket();
        partialUpdatedTicket.setId(ticket.getId());

        partialUpdatedTicket
            .closeBy(UPDATED_CLOSE_BY)
            .payDate(UPDATED_PAY_DATE)
            .isPay(UPDATED_IS_PAY)
            .payById(UPDATED_PAY_BY_ID)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restTicketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTicket.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTicket))
            )
            .andExpect(status().isOk());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
        Ticket testTicket = ticketList.get(ticketList.size() - 1);
        assertThat(testTicket.getTicketNo()).isEqualTo(DEFAULT_TICKET_NO);
        assertThat(testTicket.getStatutFiche()).isEqualTo(DEFAULT_STATUT_FICHE);
        assertThat(testTicket.getCloseBy()).isEqualTo(UPDATED_CLOSE_BY);
        assertThat(testTicket.getCloseDate()).isEqualTo(DEFAULT_CLOSE_DATE);
        assertThat(testTicket.getIsClosed()).isEqualTo(DEFAULT_IS_CLOSED);
        assertThat(testTicket.getCloseById()).isEqualTo(DEFAULT_CLOSE_BY_ID);
        assertThat(testTicket.getPayBy()).isEqualTo(DEFAULT_PAY_BY);
        assertThat(testTicket.getPayDate()).isEqualTo(UPDATED_PAY_DATE);
        assertThat(testTicket.getIsPay()).isEqualTo(UPDATED_IS_PAY);
        assertThat(testTicket.getPayById()).isEqualTo(UPDATED_PAY_BY_ID);
        assertThat(testTicket.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTicket.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTicket.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTicket.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTicketWithPatch() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);

        int databaseSizeBeforeUpdate = ticketRepository.findAll().size();

        // Update the ticket using partial update
        Ticket partialUpdatedTicket = new Ticket();
        partialUpdatedTicket.setId(ticket.getId());

        partialUpdatedTicket
            .ticketNo(UPDATED_TICKET_NO)
            .statutFiche(UPDATED_STATUT_FICHE)
            .closeBy(UPDATED_CLOSE_BY)
            .closeDate(UPDATED_CLOSE_DATE)
            .isClosed(UPDATED_IS_CLOSED)
            .closeById(UPDATED_CLOSE_BY_ID)
            .payBy(UPDATED_PAY_BY)
            .payDate(UPDATED_PAY_DATE)
            .isPay(UPDATED_IS_PAY)
            .payById(UPDATED_PAY_BY_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restTicketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTicket.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTicket))
            )
            .andExpect(status().isOk());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
        Ticket testTicket = ticketList.get(ticketList.size() - 1);
        assertThat(testTicket.getTicketNo()).isEqualTo(UPDATED_TICKET_NO);
        assertThat(testTicket.getStatutFiche()).isEqualTo(UPDATED_STATUT_FICHE);
        assertThat(testTicket.getCloseBy()).isEqualTo(UPDATED_CLOSE_BY);
        assertThat(testTicket.getCloseDate()).isEqualTo(UPDATED_CLOSE_DATE);
        assertThat(testTicket.getIsClosed()).isEqualTo(UPDATED_IS_CLOSED);
        assertThat(testTicket.getCloseById()).isEqualTo(UPDATED_CLOSE_BY_ID);
        assertThat(testTicket.getPayBy()).isEqualTo(UPDATED_PAY_BY);
        assertThat(testTicket.getPayDate()).isEqualTo(UPDATED_PAY_DATE);
        assertThat(testTicket.getIsPay()).isEqualTo(UPDATED_IS_PAY);
        assertThat(testTicket.getPayById()).isEqualTo(UPDATED_PAY_BY_ID);
        assertThat(testTicket.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTicket.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTicket.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTicket.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTicket() throws Exception {
        int databaseSizeBeforeUpdate = ticketRepository.findAll().size();
        ticket.setId(count.incrementAndGet());

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ticketDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ticketDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTicket() throws Exception {
        int databaseSizeBeforeUpdate = ticketRepository.findAll().size();
        ticket.setId(count.incrementAndGet());

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ticketDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTicket() throws Exception {
        int databaseSizeBeforeUpdate = ticketRepository.findAll().size();
        ticket.setId(count.incrementAndGet());

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ticketDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTicket() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);

        int databaseSizeBeforeDelete = ticketRepository.findAll().size();

        // Delete the ticket
        restTicketMockMvc
            .perform(delete(ENTITY_API_URL_ID, ticket.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
