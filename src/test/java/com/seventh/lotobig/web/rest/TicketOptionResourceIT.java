package com.seventh.lotobig.web.rest;

import static com.seventh.lotobig.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.TicketOption;
import com.seventh.lotobig.domain.enumeration.StatutFiche;
import com.seventh.lotobig.domain.enumeration.TypeOption;
import com.seventh.lotobig.repository.TicketOptionRepository;
import com.seventh.lotobig.service.dto.TicketOptionDTO;
import com.seventh.lotobig.service.mapper.TicketOptionMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link TicketOptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TicketOptionResourceIT {

    private static final String DEFAULT_CONTENU = "AAAAAAA";
    private static final String UPDATED_CONTENU = "BBBBBBB";

    private static final BigDecimal DEFAULT_PLAY_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PLAY_AMOUNT = new BigDecimal(2);

    private static final TypeOption DEFAULT_TYPE_OPTION = TypeOption.MARIAGE;
    private static final TypeOption UPDATED_TYPE_OPTION = TypeOption.LOTO3CHIF;

    private static final StatutFiche DEFAULT_STATUT_OPTION = StatutFiche.ACTIVE;
    private static final StatutFiche UPDATED_STATUT_OPTION = StatutFiche.ANNULED;

    private static final Integer DEFAULT_MULTIPLICATEUR = 1;
    private static final Integer UPDATED_MULTIPLICATEUR = 2;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/ticket-options";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TicketOptionRepository ticketOptionRepository;

    @Autowired
    private TicketOptionMapper ticketOptionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTicketOptionMockMvc;

    private TicketOption ticketOption;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketOption createEntity(EntityManager em) {
        TicketOption ticketOption = new TicketOption()
            .contenu(DEFAULT_CONTENU)
            .playAmount(DEFAULT_PLAY_AMOUNT)
            .typeOption(DEFAULT_TYPE_OPTION)
            .statutOption(DEFAULT_STATUT_OPTION)
            .multiplicateur(DEFAULT_MULTIPLICATEUR)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return ticketOption;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketOption createUpdatedEntity(EntityManager em) {
        TicketOption ticketOption = new TicketOption()
            .contenu(UPDATED_CONTENU)
            .playAmount(UPDATED_PLAY_AMOUNT)
            .typeOption(UPDATED_TYPE_OPTION)
            .statutOption(UPDATED_STATUT_OPTION)
            .multiplicateur(UPDATED_MULTIPLICATEUR)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return ticketOption;
    }

    @BeforeEach
    public void initTest() {
        ticketOption = createEntity(em);
    }

    @Test
    @Transactional
    void createTicketOption() throws Exception {
        int databaseSizeBeforeCreate = ticketOptionRepository.findAll().size();
        // Create the TicketOption
        TicketOptionDTO ticketOptionDTO = ticketOptionMapper.toDto(ticketOption);
        restTicketOptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketOptionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TicketOption in the database
        List<TicketOption> ticketOptionList = ticketOptionRepository.findAll();
        assertThat(ticketOptionList).hasSize(databaseSizeBeforeCreate + 1);
        TicketOption testTicketOption = ticketOptionList.get(ticketOptionList.size() - 1);
        assertThat(testTicketOption.getContenu()).isEqualTo(DEFAULT_CONTENU);
        assertThat(testTicketOption.getPlayAmount()).isEqualByComparingTo(DEFAULT_PLAY_AMOUNT);
        assertThat(testTicketOption.getTypeOption()).isEqualTo(DEFAULT_TYPE_OPTION);
        assertThat(testTicketOption.getStatutOption()).isEqualTo(DEFAULT_STATUT_OPTION);
        assertThat(testTicketOption.getMultiplicateur()).isEqualTo(DEFAULT_MULTIPLICATEUR);
        assertThat(testTicketOption.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTicketOption.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTicketOption.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testTicketOption.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createTicketOptionWithExistingId() throws Exception {
        // Create the TicketOption with an existing ID
        ticketOption.setId(1L);
        TicketOptionDTO ticketOptionDTO = ticketOptionMapper.toDto(ticketOption);

        int databaseSizeBeforeCreate = ticketOptionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketOptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketOption in the database
        List<TicketOption> ticketOptionList = ticketOptionRepository.findAll();
        assertThat(ticketOptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPlayAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketOptionRepository.findAll().size();
        // set the field null
        ticketOption.setPlayAmount(null);

        // Create the TicketOption, which fails.
        TicketOptionDTO ticketOptionDTO = ticketOptionMapper.toDto(ticketOption);

        restTicketOptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketOptionDTO))
            )
            .andExpect(status().isBadRequest());

        List<TicketOption> ticketOptionList = ticketOptionRepository.findAll();
        assertThat(ticketOptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeOptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketOptionRepository.findAll().size();
        // set the field null
        ticketOption.setTypeOption(null);

        // Create the TicketOption, which fails.
        TicketOptionDTO ticketOptionDTO = ticketOptionMapper.toDto(ticketOption);

        restTicketOptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketOptionDTO))
            )
            .andExpect(status().isBadRequest());

        List<TicketOption> ticketOptionList = ticketOptionRepository.findAll();
        assertThat(ticketOptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatutOptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketOptionRepository.findAll().size();
        // set the field null
        ticketOption.setStatutOption(null);

        // Create the TicketOption, which fails.
        TicketOptionDTO ticketOptionDTO = ticketOptionMapper.toDto(ticketOption);

        restTicketOptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketOptionDTO))
            )
            .andExpect(status().isBadRequest());

        List<TicketOption> ticketOptionList = ticketOptionRepository.findAll();
        assertThat(ticketOptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMultiplicateurIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketOptionRepository.findAll().size();
        // set the field null
        ticketOption.setMultiplicateur(null);

        // Create the TicketOption, which fails.
        TicketOptionDTO ticketOptionDTO = ticketOptionMapper.toDto(ticketOption);

        restTicketOptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketOptionDTO))
            )
            .andExpect(status().isBadRequest());

        List<TicketOption> ticketOptionList = ticketOptionRepository.findAll();
        assertThat(ticketOptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketOptionRepository.findAll().size();
        // set the field null
        ticketOption.setCreatedBy(null);

        // Create the TicketOption, which fails.
        TicketOptionDTO ticketOptionDTO = ticketOptionMapper.toDto(ticketOption);

        restTicketOptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketOptionDTO))
            )
            .andExpect(status().isBadRequest());

        List<TicketOption> ticketOptionList = ticketOptionRepository.findAll();
        assertThat(ticketOptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketOptionRepository.findAll().size();
        // set the field null
        ticketOption.setCreatedDate(null);

        // Create the TicketOption, which fails.
        TicketOptionDTO ticketOptionDTO = ticketOptionMapper.toDto(ticketOption);

        restTicketOptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketOptionDTO))
            )
            .andExpect(status().isBadRequest());

        List<TicketOption> ticketOptionList = ticketOptionRepository.findAll();
        assertThat(ticketOptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTicketOptions() throws Exception {
        // Initialize the database
        ticketOptionRepository.saveAndFlush(ticketOption);

        // Get all the ticketOptionList
        restTicketOptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].contenu").value(hasItem(DEFAULT_CONTENU)))
            .andExpect(jsonPath("$.[*].playAmount").value(hasItem(sameNumber(DEFAULT_PLAY_AMOUNT))))
            .andExpect(jsonPath("$.[*].typeOption").value(hasItem(DEFAULT_TYPE_OPTION.toString())))
            .andExpect(jsonPath("$.[*].statutOption").value(hasItem(DEFAULT_STATUT_OPTION.toString())))
            .andExpect(jsonPath("$.[*].multiplicateur").value(hasItem(DEFAULT_MULTIPLICATEUR)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getTicketOption() throws Exception {
        // Initialize the database
        ticketOptionRepository.saveAndFlush(ticketOption);

        // Get the ticketOption
        restTicketOptionMockMvc
            .perform(get(ENTITY_API_URL_ID, ticketOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ticketOption.getId().intValue()))
            .andExpect(jsonPath("$.contenu").value(DEFAULT_CONTENU))
            .andExpect(jsonPath("$.playAmount").value(sameNumber(DEFAULT_PLAY_AMOUNT)))
            .andExpect(jsonPath("$.typeOption").value(DEFAULT_TYPE_OPTION.toString()))
            .andExpect(jsonPath("$.statutOption").value(DEFAULT_STATUT_OPTION.toString()))
            .andExpect(jsonPath("$.multiplicateur").value(DEFAULT_MULTIPLICATEUR))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTicketOption() throws Exception {
        // Get the ticketOption
        restTicketOptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTicketOption() throws Exception {
        // Initialize the database
        ticketOptionRepository.saveAndFlush(ticketOption);

        int databaseSizeBeforeUpdate = ticketOptionRepository.findAll().size();

        // Update the ticketOption
        TicketOption updatedTicketOption = ticketOptionRepository.findById(ticketOption.getId()).get();
        // Disconnect from session so that the updates on updatedTicketOption are not directly saved in db
        em.detach(updatedTicketOption);
        updatedTicketOption
            .contenu(UPDATED_CONTENU)
            .playAmount(UPDATED_PLAY_AMOUNT)
            .typeOption(UPDATED_TYPE_OPTION)
            .statutOption(UPDATED_STATUT_OPTION)
            .multiplicateur(UPDATED_MULTIPLICATEUR)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        TicketOptionDTO ticketOptionDTO = ticketOptionMapper.toDto(updatedTicketOption);

        restTicketOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ticketOptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ticketOptionDTO))
            )
            .andExpect(status().isOk());

        // Validate the TicketOption in the database
        List<TicketOption> ticketOptionList = ticketOptionRepository.findAll();
        assertThat(ticketOptionList).hasSize(databaseSizeBeforeUpdate);
        TicketOption testTicketOption = ticketOptionList.get(ticketOptionList.size() - 1);
        assertThat(testTicketOption.getContenu()).isEqualTo(UPDATED_CONTENU);
        assertThat(testTicketOption.getPlayAmount()).isEqualByComparingTo(UPDATED_PLAY_AMOUNT);
        assertThat(testTicketOption.getTypeOption()).isEqualTo(UPDATED_TYPE_OPTION);
        assertThat(testTicketOption.getStatutOption()).isEqualTo(UPDATED_STATUT_OPTION);
        assertThat(testTicketOption.getMultiplicateur()).isEqualTo(UPDATED_MULTIPLICATEUR);
        assertThat(testTicketOption.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTicketOption.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTicketOption.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTicketOption.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTicketOption() throws Exception {
        int databaseSizeBeforeUpdate = ticketOptionRepository.findAll().size();
        ticketOption.setId(count.incrementAndGet());

        // Create the TicketOption
        TicketOptionDTO ticketOptionDTO = ticketOptionMapper.toDto(ticketOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ticketOptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ticketOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketOption in the database
        List<TicketOption> ticketOptionList = ticketOptionRepository.findAll();
        assertThat(ticketOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTicketOption() throws Exception {
        int databaseSizeBeforeUpdate = ticketOptionRepository.findAll().size();
        ticketOption.setId(count.incrementAndGet());

        // Create the TicketOption
        TicketOptionDTO ticketOptionDTO = ticketOptionMapper.toDto(ticketOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ticketOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketOption in the database
        List<TicketOption> ticketOptionList = ticketOptionRepository.findAll();
        assertThat(ticketOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTicketOption() throws Exception {
        int databaseSizeBeforeUpdate = ticketOptionRepository.findAll().size();
        ticketOption.setId(count.incrementAndGet());

        // Create the TicketOption
        TicketOptionDTO ticketOptionDTO = ticketOptionMapper.toDto(ticketOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketOptionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketOptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TicketOption in the database
        List<TicketOption> ticketOptionList = ticketOptionRepository.findAll();
        assertThat(ticketOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTicketOptionWithPatch() throws Exception {
        // Initialize the database
        ticketOptionRepository.saveAndFlush(ticketOption);

        int databaseSizeBeforeUpdate = ticketOptionRepository.findAll().size();

        // Update the ticketOption using partial update
        TicketOption partialUpdatedTicketOption = new TicketOption();
        partialUpdatedTicketOption.setId(ticketOption.getId());

        partialUpdatedTicketOption
            .typeOption(UPDATED_TYPE_OPTION)
            .multiplicateur(UPDATED_MULTIPLICATEUR)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restTicketOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTicketOption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTicketOption))
            )
            .andExpect(status().isOk());

        // Validate the TicketOption in the database
        List<TicketOption> ticketOptionList = ticketOptionRepository.findAll();
        assertThat(ticketOptionList).hasSize(databaseSizeBeforeUpdate);
        TicketOption testTicketOption = ticketOptionList.get(ticketOptionList.size() - 1);
        assertThat(testTicketOption.getContenu()).isEqualTo(DEFAULT_CONTENU);
        assertThat(testTicketOption.getPlayAmount()).isEqualByComparingTo(DEFAULT_PLAY_AMOUNT);
        assertThat(testTicketOption.getTypeOption()).isEqualTo(UPDATED_TYPE_OPTION);
        assertThat(testTicketOption.getStatutOption()).isEqualTo(DEFAULT_STATUT_OPTION);
        assertThat(testTicketOption.getMultiplicateur()).isEqualTo(UPDATED_MULTIPLICATEUR);
        assertThat(testTicketOption.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTicketOption.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTicketOption.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTicketOption.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTicketOptionWithPatch() throws Exception {
        // Initialize the database
        ticketOptionRepository.saveAndFlush(ticketOption);

        int databaseSizeBeforeUpdate = ticketOptionRepository.findAll().size();

        // Update the ticketOption using partial update
        TicketOption partialUpdatedTicketOption = new TicketOption();
        partialUpdatedTicketOption.setId(ticketOption.getId());

        partialUpdatedTicketOption
            .contenu(UPDATED_CONTENU)
            .playAmount(UPDATED_PLAY_AMOUNT)
            .typeOption(UPDATED_TYPE_OPTION)
            .statutOption(UPDATED_STATUT_OPTION)
            .multiplicateur(UPDATED_MULTIPLICATEUR)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restTicketOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTicketOption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTicketOption))
            )
            .andExpect(status().isOk());

        // Validate the TicketOption in the database
        List<TicketOption> ticketOptionList = ticketOptionRepository.findAll();
        assertThat(ticketOptionList).hasSize(databaseSizeBeforeUpdate);
        TicketOption testTicketOption = ticketOptionList.get(ticketOptionList.size() - 1);
        assertThat(testTicketOption.getContenu()).isEqualTo(UPDATED_CONTENU);
        assertThat(testTicketOption.getPlayAmount()).isEqualByComparingTo(UPDATED_PLAY_AMOUNT);
        assertThat(testTicketOption.getTypeOption()).isEqualTo(UPDATED_TYPE_OPTION);
        assertThat(testTicketOption.getStatutOption()).isEqualTo(UPDATED_STATUT_OPTION);
        assertThat(testTicketOption.getMultiplicateur()).isEqualTo(UPDATED_MULTIPLICATEUR);
        assertThat(testTicketOption.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTicketOption.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTicketOption.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTicketOption.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTicketOption() throws Exception {
        int databaseSizeBeforeUpdate = ticketOptionRepository.findAll().size();
        ticketOption.setId(count.incrementAndGet());

        // Create the TicketOption
        TicketOptionDTO ticketOptionDTO = ticketOptionMapper.toDto(ticketOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ticketOptionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ticketOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketOption in the database
        List<TicketOption> ticketOptionList = ticketOptionRepository.findAll();
        assertThat(ticketOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTicketOption() throws Exception {
        int databaseSizeBeforeUpdate = ticketOptionRepository.findAll().size();
        ticketOption.setId(count.incrementAndGet());

        // Create the TicketOption
        TicketOptionDTO ticketOptionDTO = ticketOptionMapper.toDto(ticketOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ticketOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketOption in the database
        List<TicketOption> ticketOptionList = ticketOptionRepository.findAll();
        assertThat(ticketOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTicketOption() throws Exception {
        int databaseSizeBeforeUpdate = ticketOptionRepository.findAll().size();
        ticketOption.setId(count.incrementAndGet());

        // Create the TicketOption
        TicketOptionDTO ticketOptionDTO = ticketOptionMapper.toDto(ticketOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketOptionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ticketOptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TicketOption in the database
        List<TicketOption> ticketOptionList = ticketOptionRepository.findAll();
        assertThat(ticketOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTicketOption() throws Exception {
        // Initialize the database
        ticketOptionRepository.saveAndFlush(ticketOption);

        int databaseSizeBeforeDelete = ticketOptionRepository.findAll().size();

        // Delete the ticketOption
        restTicketOptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, ticketOption.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TicketOption> ticketOptionList = ticketOptionRepository.findAll();
        assertThat(ticketOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
