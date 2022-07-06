package com.seventh.lotobig.web.rest;

import static com.seventh.lotobig.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.TransactionHistories;
import com.seventh.lotobig.domain.enumeration.TransactionType;
import com.seventh.lotobig.repository.TransactionHistoriesRepository;
import com.seventh.lotobig.service.dto.TransactionHistoriesDTO;
import com.seventh.lotobig.service.mapper.TransactionHistoriesMapper;
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
 * Integration tests for the {@link TransactionHistoriesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransactionHistoriesResourceIT {

    private static final TransactionType DEFAULT_TYPE = TransactionType.DEPOT;
    private static final TransactionType UPDATED_TYPE = TransactionType.RETRAIT;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MONTANT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT = new BigDecimal(2);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/transaction-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransactionHistoriesRepository transactionHistoriesRepository;

    @Autowired
    private TransactionHistoriesMapper transactionHistoriesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionHistoriesMockMvc;

    private TransactionHistories transactionHistories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionHistories createEntity(EntityManager em) {
        TransactionHistories transactionHistories = new TransactionHistories()
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .montant(DEFAULT_MONTANT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return transactionHistories;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionHistories createUpdatedEntity(EntityManager em) {
        TransactionHistories transactionHistories = new TransactionHistories()
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .montant(UPDATED_MONTANT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return transactionHistories;
    }

    @BeforeEach
    public void initTest() {
        transactionHistories = createEntity(em);
    }

    @Test
    @Transactional
    void createTransactionHistories() throws Exception {
        int databaseSizeBeforeCreate = transactionHistoriesRepository.findAll().size();
        // Create the TransactionHistories
        TransactionHistoriesDTO transactionHistoriesDTO = transactionHistoriesMapper.toDto(transactionHistories);
        restTransactionHistoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoriesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TransactionHistories in the database
        List<TransactionHistories> transactionHistoriesList = transactionHistoriesRepository.findAll();
        assertThat(transactionHistoriesList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionHistories testTransactionHistories = transactionHistoriesList.get(transactionHistoriesList.size() - 1);
        assertThat(testTransactionHistories.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTransactionHistories.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTransactionHistories.getMontant()).isEqualByComparingTo(DEFAULT_MONTANT);
        assertThat(testTransactionHistories.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTransactionHistories.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTransactionHistories.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testTransactionHistories.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createTransactionHistoriesWithExistingId() throws Exception {
        // Create the TransactionHistories with an existing ID
        transactionHistories.setId(1L);
        TransactionHistoriesDTO transactionHistoriesDTO = transactionHistoriesMapper.toDto(transactionHistories);

        int databaseSizeBeforeCreate = transactionHistoriesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionHistoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionHistories in the database
        List<TransactionHistories> transactionHistoriesList = transactionHistoriesRepository.findAll();
        assertThat(transactionHistoriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionHistoriesRepository.findAll().size();
        // set the field null
        transactionHistories.setType(null);

        // Create the TransactionHistories, which fails.
        TransactionHistoriesDTO transactionHistoriesDTO = transactionHistoriesMapper.toDto(transactionHistories);

        restTransactionHistoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoriesDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransactionHistories> transactionHistoriesList = transactionHistoriesRepository.findAll();
        assertThat(transactionHistoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionHistoriesRepository.findAll().size();
        // set the field null
        transactionHistories.setMontant(null);

        // Create the TransactionHistories, which fails.
        TransactionHistoriesDTO transactionHistoriesDTO = transactionHistoriesMapper.toDto(transactionHistories);

        restTransactionHistoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoriesDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransactionHistories> transactionHistoriesList = transactionHistoriesRepository.findAll();
        assertThat(transactionHistoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionHistoriesRepository.findAll().size();
        // set the field null
        transactionHistories.setCreatedBy(null);

        // Create the TransactionHistories, which fails.
        TransactionHistoriesDTO transactionHistoriesDTO = transactionHistoriesMapper.toDto(transactionHistories);

        restTransactionHistoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoriesDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransactionHistories> transactionHistoriesList = transactionHistoriesRepository.findAll();
        assertThat(transactionHistoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionHistoriesRepository.findAll().size();
        // set the field null
        transactionHistories.setCreatedDate(null);

        // Create the TransactionHistories, which fails.
        TransactionHistoriesDTO transactionHistoriesDTO = transactionHistoriesMapper.toDto(transactionHistories);

        restTransactionHistoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoriesDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransactionHistories> transactionHistoriesList = transactionHistoriesRepository.findAll();
        assertThat(transactionHistoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTransactionHistories() throws Exception {
        // Initialize the database
        transactionHistoriesRepository.saveAndFlush(transactionHistories);

        // Get all the transactionHistoriesList
        restTransactionHistoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionHistories.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(sameNumber(DEFAULT_MONTANT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getTransactionHistories() throws Exception {
        // Initialize the database
        transactionHistoriesRepository.saveAndFlush(transactionHistories);

        // Get the transactionHistories
        restTransactionHistoriesMockMvc
            .perform(get(ENTITY_API_URL_ID, transactionHistories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transactionHistories.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.montant").value(sameNumber(DEFAULT_MONTANT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTransactionHistories() throws Exception {
        // Get the transactionHistories
        restTransactionHistoriesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTransactionHistories() throws Exception {
        // Initialize the database
        transactionHistoriesRepository.saveAndFlush(transactionHistories);

        int databaseSizeBeforeUpdate = transactionHistoriesRepository.findAll().size();

        // Update the transactionHistories
        TransactionHistories updatedTransactionHistories = transactionHistoriesRepository.findById(transactionHistories.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionHistories are not directly saved in db
        em.detach(updatedTransactionHistories);
        updatedTransactionHistories
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .montant(UPDATED_MONTANT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        TransactionHistoriesDTO transactionHistoriesDTO = transactionHistoriesMapper.toDto(updatedTransactionHistories);

        restTransactionHistoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transactionHistoriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoriesDTO))
            )
            .andExpect(status().isOk());

        // Validate the TransactionHistories in the database
        List<TransactionHistories> transactionHistoriesList = transactionHistoriesRepository.findAll();
        assertThat(transactionHistoriesList).hasSize(databaseSizeBeforeUpdate);
        TransactionHistories testTransactionHistories = transactionHistoriesList.get(transactionHistoriesList.size() - 1);
        assertThat(testTransactionHistories.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTransactionHistories.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTransactionHistories.getMontant()).isEqualByComparingTo(UPDATED_MONTANT);
        assertThat(testTransactionHistories.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTransactionHistories.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTransactionHistories.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTransactionHistories.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTransactionHistories() throws Exception {
        int databaseSizeBeforeUpdate = transactionHistoriesRepository.findAll().size();
        transactionHistories.setId(count.incrementAndGet());

        // Create the TransactionHistories
        TransactionHistoriesDTO transactionHistoriesDTO = transactionHistoriesMapper.toDto(transactionHistories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionHistoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transactionHistoriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionHistories in the database
        List<TransactionHistories> transactionHistoriesList = transactionHistoriesRepository.findAll();
        assertThat(transactionHistoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransactionHistories() throws Exception {
        int databaseSizeBeforeUpdate = transactionHistoriesRepository.findAll().size();
        transactionHistories.setId(count.incrementAndGet());

        // Create the TransactionHistories
        TransactionHistoriesDTO transactionHistoriesDTO = transactionHistoriesMapper.toDto(transactionHistories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionHistoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionHistories in the database
        List<TransactionHistories> transactionHistoriesList = transactionHistoriesRepository.findAll();
        assertThat(transactionHistoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransactionHistories() throws Exception {
        int databaseSizeBeforeUpdate = transactionHistoriesRepository.findAll().size();
        transactionHistories.setId(count.incrementAndGet());

        // Create the TransactionHistories
        TransactionHistoriesDTO transactionHistoriesDTO = transactionHistoriesMapper.toDto(transactionHistories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionHistoriesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoriesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransactionHistories in the database
        List<TransactionHistories> transactionHistoriesList = transactionHistoriesRepository.findAll();
        assertThat(transactionHistoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransactionHistoriesWithPatch() throws Exception {
        // Initialize the database
        transactionHistoriesRepository.saveAndFlush(transactionHistories);

        int databaseSizeBeforeUpdate = transactionHistoriesRepository.findAll().size();

        // Update the transactionHistories using partial update
        TransactionHistories partialUpdatedTransactionHistories = new TransactionHistories();
        partialUpdatedTransactionHistories.setId(transactionHistories.getId());

        partialUpdatedTransactionHistories
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .montant(UPDATED_MONTANT)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restTransactionHistoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransactionHistories.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransactionHistories))
            )
            .andExpect(status().isOk());

        // Validate the TransactionHistories in the database
        List<TransactionHistories> transactionHistoriesList = transactionHistoriesRepository.findAll();
        assertThat(transactionHistoriesList).hasSize(databaseSizeBeforeUpdate);
        TransactionHistories testTransactionHistories = transactionHistoriesList.get(transactionHistoriesList.size() - 1);
        assertThat(testTransactionHistories.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTransactionHistories.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTransactionHistories.getMontant()).isEqualByComparingTo(UPDATED_MONTANT);
        assertThat(testTransactionHistories.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTransactionHistories.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTransactionHistories.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTransactionHistories.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTransactionHistoriesWithPatch() throws Exception {
        // Initialize the database
        transactionHistoriesRepository.saveAndFlush(transactionHistories);

        int databaseSizeBeforeUpdate = transactionHistoriesRepository.findAll().size();

        // Update the transactionHistories using partial update
        TransactionHistories partialUpdatedTransactionHistories = new TransactionHistories();
        partialUpdatedTransactionHistories.setId(transactionHistories.getId());

        partialUpdatedTransactionHistories
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .montant(UPDATED_MONTANT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restTransactionHistoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransactionHistories.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransactionHistories))
            )
            .andExpect(status().isOk());

        // Validate the TransactionHistories in the database
        List<TransactionHistories> transactionHistoriesList = transactionHistoriesRepository.findAll();
        assertThat(transactionHistoriesList).hasSize(databaseSizeBeforeUpdate);
        TransactionHistories testTransactionHistories = transactionHistoriesList.get(transactionHistoriesList.size() - 1);
        assertThat(testTransactionHistories.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTransactionHistories.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTransactionHistories.getMontant()).isEqualByComparingTo(UPDATED_MONTANT);
        assertThat(testTransactionHistories.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTransactionHistories.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTransactionHistories.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTransactionHistories.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTransactionHistories() throws Exception {
        int databaseSizeBeforeUpdate = transactionHistoriesRepository.findAll().size();
        transactionHistories.setId(count.incrementAndGet());

        // Create the TransactionHistories
        TransactionHistoriesDTO transactionHistoriesDTO = transactionHistoriesMapper.toDto(transactionHistories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionHistoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transactionHistoriesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionHistories in the database
        List<TransactionHistories> transactionHistoriesList = transactionHistoriesRepository.findAll();
        assertThat(transactionHistoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransactionHistories() throws Exception {
        int databaseSizeBeforeUpdate = transactionHistoriesRepository.findAll().size();
        transactionHistories.setId(count.incrementAndGet());

        // Create the TransactionHistories
        TransactionHistoriesDTO transactionHistoriesDTO = transactionHistoriesMapper.toDto(transactionHistories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionHistoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionHistories in the database
        List<TransactionHistories> transactionHistoriesList = transactionHistoriesRepository.findAll();
        assertThat(transactionHistoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransactionHistories() throws Exception {
        int databaseSizeBeforeUpdate = transactionHistoriesRepository.findAll().size();
        transactionHistories.setId(count.incrementAndGet());

        // Create the TransactionHistories
        TransactionHistoriesDTO transactionHistoriesDTO = transactionHistoriesMapper.toDto(transactionHistories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionHistoriesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionHistoriesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransactionHistories in the database
        List<TransactionHistories> transactionHistoriesList = transactionHistoriesRepository.findAll();
        assertThat(transactionHistoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransactionHistories() throws Exception {
        // Initialize the database
        transactionHistoriesRepository.saveAndFlush(transactionHistories);

        int databaseSizeBeforeDelete = transactionHistoriesRepository.findAll().size();

        // Delete the transactionHistories
        restTransactionHistoriesMockMvc
            .perform(delete(ENTITY_API_URL_ID, transactionHistories.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionHistories> transactionHistoriesList = transactionHistoriesRepository.findAll();
        assertThat(transactionHistoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
