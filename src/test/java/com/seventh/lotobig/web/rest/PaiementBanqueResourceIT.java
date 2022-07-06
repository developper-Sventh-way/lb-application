package com.seventh.lotobig.web.rest;

import static com.seventh.lotobig.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.PaiementBanque;
import com.seventh.lotobig.repository.PaiementBanqueRepository;
import com.seventh.lotobig.service.dto.PaiementBanqueDTO;
import com.seventh.lotobig.service.mapper.PaiementBanqueMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link PaiementBanqueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaiementBanqueResourceIT {

    private static final BigDecimal DEFAULT_MONTANT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/paiement-banques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaiementBanqueRepository paiementBanqueRepository;

    @Autowired
    private PaiementBanqueMapper paiementBanqueMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaiementBanqueMockMvc;

    private PaiementBanque paiementBanque;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaiementBanque createEntity(EntityManager em) {
        PaiementBanque paiementBanque = new PaiementBanque()
            .montant(DEFAULT_MONTANT)
            .balance(DEFAULT_BALANCE)
            .description(DEFAULT_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return paiementBanque;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaiementBanque createUpdatedEntity(EntityManager em) {
        PaiementBanque paiementBanque = new PaiementBanque()
            .montant(UPDATED_MONTANT)
            .balance(UPDATED_BALANCE)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return paiementBanque;
    }

    @BeforeEach
    public void initTest() {
        paiementBanque = createEntity(em);
    }

    @Test
    @Transactional
    void createPaiementBanque() throws Exception {
        int databaseSizeBeforeCreate = paiementBanqueRepository.findAll().size();
        // Create the PaiementBanque
        PaiementBanqueDTO paiementBanqueDTO = paiementBanqueMapper.toDto(paiementBanque);
        restPaiementBanqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paiementBanqueDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PaiementBanque in the database
        List<PaiementBanque> paiementBanqueList = paiementBanqueRepository.findAll();
        assertThat(paiementBanqueList).hasSize(databaseSizeBeforeCreate + 1);
        PaiementBanque testPaiementBanque = paiementBanqueList.get(paiementBanqueList.size() - 1);
        assertThat(testPaiementBanque.getMontant()).isEqualByComparingTo(DEFAULT_MONTANT);
        assertThat(testPaiementBanque.getBalance()).isEqualByComparingTo(DEFAULT_BALANCE);
        assertThat(testPaiementBanque.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPaiementBanque.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPaiementBanque.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPaiementBanque.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPaiementBanque.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPaiementBanque.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPaiementBanque.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createPaiementBanqueWithExistingId() throws Exception {
        // Create the PaiementBanque with an existing ID
        paiementBanque.setId(1L);
        PaiementBanqueDTO paiementBanqueDTO = paiementBanqueMapper.toDto(paiementBanque);

        int databaseSizeBeforeCreate = paiementBanqueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaiementBanqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paiementBanqueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaiementBanque in the database
        List<PaiementBanque> paiementBanqueList = paiementBanqueRepository.findAll();
        assertThat(paiementBanqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = paiementBanqueRepository.findAll().size();
        // set the field null
        paiementBanque.setMontant(null);

        // Create the PaiementBanque, which fails.
        PaiementBanqueDTO paiementBanqueDTO = paiementBanqueMapper.toDto(paiementBanque);

        restPaiementBanqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paiementBanqueDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaiementBanque> paiementBanqueList = paiementBanqueRepository.findAll();
        assertThat(paiementBanqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = paiementBanqueRepository.findAll().size();
        // set the field null
        paiementBanque.setBalance(null);

        // Create the PaiementBanque, which fails.
        PaiementBanqueDTO paiementBanqueDTO = paiementBanqueMapper.toDto(paiementBanque);

        restPaiementBanqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paiementBanqueDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaiementBanque> paiementBanqueList = paiementBanqueRepository.findAll();
        assertThat(paiementBanqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = paiementBanqueRepository.findAll().size();
        // set the field null
        paiementBanque.setCreatedDate(null);

        // Create the PaiementBanque, which fails.
        PaiementBanqueDTO paiementBanqueDTO = paiementBanqueMapper.toDto(paiementBanque);

        restPaiementBanqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paiementBanqueDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaiementBanque> paiementBanqueList = paiementBanqueRepository.findAll();
        assertThat(paiementBanqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaiementBanques() throws Exception {
        // Initialize the database
        paiementBanqueRepository.saveAndFlush(paiementBanque);

        // Get all the paiementBanqueList
        restPaiementBanqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paiementBanque.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(sameNumber(DEFAULT_MONTANT))))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(sameNumber(DEFAULT_BALANCE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getPaiementBanque() throws Exception {
        // Initialize the database
        paiementBanqueRepository.saveAndFlush(paiementBanque);

        // Get the paiementBanque
        restPaiementBanqueMockMvc
            .perform(get(ENTITY_API_URL_ID, paiementBanque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paiementBanque.getId().intValue()))
            .andExpect(jsonPath("$.montant").value(sameNumber(DEFAULT_MONTANT)))
            .andExpect(jsonPath("$.balance").value(sameNumber(DEFAULT_BALANCE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPaiementBanque() throws Exception {
        // Get the paiementBanque
        restPaiementBanqueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaiementBanque() throws Exception {
        // Initialize the database
        paiementBanqueRepository.saveAndFlush(paiementBanque);

        int databaseSizeBeforeUpdate = paiementBanqueRepository.findAll().size();

        // Update the paiementBanque
        PaiementBanque updatedPaiementBanque = paiementBanqueRepository.findById(paiementBanque.getId()).get();
        // Disconnect from session so that the updates on updatedPaiementBanque are not directly saved in db
        em.detach(updatedPaiementBanque);
        updatedPaiementBanque
            .montant(UPDATED_MONTANT)
            .balance(UPDATED_BALANCE)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        PaiementBanqueDTO paiementBanqueDTO = paiementBanqueMapper.toDto(updatedPaiementBanque);

        restPaiementBanqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paiementBanqueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paiementBanqueDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaiementBanque in the database
        List<PaiementBanque> paiementBanqueList = paiementBanqueRepository.findAll();
        assertThat(paiementBanqueList).hasSize(databaseSizeBeforeUpdate);
        PaiementBanque testPaiementBanque = paiementBanqueList.get(paiementBanqueList.size() - 1);
        assertThat(testPaiementBanque.getMontant()).isEqualByComparingTo(UPDATED_MONTANT);
        assertThat(testPaiementBanque.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testPaiementBanque.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPaiementBanque.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPaiementBanque.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPaiementBanque.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPaiementBanque.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPaiementBanque.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPaiementBanque.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPaiementBanque() throws Exception {
        int databaseSizeBeforeUpdate = paiementBanqueRepository.findAll().size();
        paiementBanque.setId(count.incrementAndGet());

        // Create the PaiementBanque
        PaiementBanqueDTO paiementBanqueDTO = paiementBanqueMapper.toDto(paiementBanque);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaiementBanqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paiementBanqueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paiementBanqueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaiementBanque in the database
        List<PaiementBanque> paiementBanqueList = paiementBanqueRepository.findAll();
        assertThat(paiementBanqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaiementBanque() throws Exception {
        int databaseSizeBeforeUpdate = paiementBanqueRepository.findAll().size();
        paiementBanque.setId(count.incrementAndGet());

        // Create the PaiementBanque
        PaiementBanqueDTO paiementBanqueDTO = paiementBanqueMapper.toDto(paiementBanque);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaiementBanqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paiementBanqueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaiementBanque in the database
        List<PaiementBanque> paiementBanqueList = paiementBanqueRepository.findAll();
        assertThat(paiementBanqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaiementBanque() throws Exception {
        int databaseSizeBeforeUpdate = paiementBanqueRepository.findAll().size();
        paiementBanque.setId(count.incrementAndGet());

        // Create the PaiementBanque
        PaiementBanqueDTO paiementBanqueDTO = paiementBanqueMapper.toDto(paiementBanque);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaiementBanqueMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paiementBanqueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaiementBanque in the database
        List<PaiementBanque> paiementBanqueList = paiementBanqueRepository.findAll();
        assertThat(paiementBanqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaiementBanqueWithPatch() throws Exception {
        // Initialize the database
        paiementBanqueRepository.saveAndFlush(paiementBanque);

        int databaseSizeBeforeUpdate = paiementBanqueRepository.findAll().size();

        // Update the paiementBanque using partial update
        PaiementBanque partialUpdatedPaiementBanque = new PaiementBanque();
        partialUpdatedPaiementBanque.setId(paiementBanque.getId());

        partialUpdatedPaiementBanque.balance(UPDATED_BALANCE).description(UPDATED_DESCRIPTION).startDate(UPDATED_START_DATE);

        restPaiementBanqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaiementBanque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaiementBanque))
            )
            .andExpect(status().isOk());

        // Validate the PaiementBanque in the database
        List<PaiementBanque> paiementBanqueList = paiementBanqueRepository.findAll();
        assertThat(paiementBanqueList).hasSize(databaseSizeBeforeUpdate);
        PaiementBanque testPaiementBanque = paiementBanqueList.get(paiementBanqueList.size() - 1);
        assertThat(testPaiementBanque.getMontant()).isEqualByComparingTo(DEFAULT_MONTANT);
        assertThat(testPaiementBanque.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testPaiementBanque.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPaiementBanque.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPaiementBanque.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPaiementBanque.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPaiementBanque.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPaiementBanque.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPaiementBanque.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePaiementBanqueWithPatch() throws Exception {
        // Initialize the database
        paiementBanqueRepository.saveAndFlush(paiementBanque);

        int databaseSizeBeforeUpdate = paiementBanqueRepository.findAll().size();

        // Update the paiementBanque using partial update
        PaiementBanque partialUpdatedPaiementBanque = new PaiementBanque();
        partialUpdatedPaiementBanque.setId(paiementBanque.getId());

        partialUpdatedPaiementBanque
            .montant(UPDATED_MONTANT)
            .balance(UPDATED_BALANCE)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restPaiementBanqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaiementBanque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaiementBanque))
            )
            .andExpect(status().isOk());

        // Validate the PaiementBanque in the database
        List<PaiementBanque> paiementBanqueList = paiementBanqueRepository.findAll();
        assertThat(paiementBanqueList).hasSize(databaseSizeBeforeUpdate);
        PaiementBanque testPaiementBanque = paiementBanqueList.get(paiementBanqueList.size() - 1);
        assertThat(testPaiementBanque.getMontant()).isEqualByComparingTo(UPDATED_MONTANT);
        assertThat(testPaiementBanque.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testPaiementBanque.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPaiementBanque.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPaiementBanque.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPaiementBanque.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPaiementBanque.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPaiementBanque.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPaiementBanque.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPaiementBanque() throws Exception {
        int databaseSizeBeforeUpdate = paiementBanqueRepository.findAll().size();
        paiementBanque.setId(count.incrementAndGet());

        // Create the PaiementBanque
        PaiementBanqueDTO paiementBanqueDTO = paiementBanqueMapper.toDto(paiementBanque);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaiementBanqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paiementBanqueDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paiementBanqueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaiementBanque in the database
        List<PaiementBanque> paiementBanqueList = paiementBanqueRepository.findAll();
        assertThat(paiementBanqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaiementBanque() throws Exception {
        int databaseSizeBeforeUpdate = paiementBanqueRepository.findAll().size();
        paiementBanque.setId(count.incrementAndGet());

        // Create the PaiementBanque
        PaiementBanqueDTO paiementBanqueDTO = paiementBanqueMapper.toDto(paiementBanque);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaiementBanqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paiementBanqueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaiementBanque in the database
        List<PaiementBanque> paiementBanqueList = paiementBanqueRepository.findAll();
        assertThat(paiementBanqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaiementBanque() throws Exception {
        int databaseSizeBeforeUpdate = paiementBanqueRepository.findAll().size();
        paiementBanque.setId(count.incrementAndGet());

        // Create the PaiementBanque
        PaiementBanqueDTO paiementBanqueDTO = paiementBanqueMapper.toDto(paiementBanque);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaiementBanqueMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paiementBanqueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaiementBanque in the database
        List<PaiementBanque> paiementBanqueList = paiementBanqueRepository.findAll();
        assertThat(paiementBanqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaiementBanque() throws Exception {
        // Initialize the database
        paiementBanqueRepository.saveAndFlush(paiementBanque);

        int databaseSizeBeforeDelete = paiementBanqueRepository.findAll().size();

        // Delete the paiementBanque
        restPaiementBanqueMockMvc
            .perform(delete(ENTITY_API_URL_ID, paiementBanque.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaiementBanque> paiementBanqueList = paiementBanqueRepository.findAll();
        assertThat(paiementBanqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
