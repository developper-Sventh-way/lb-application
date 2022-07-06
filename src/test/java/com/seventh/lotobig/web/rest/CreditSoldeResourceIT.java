package com.seventh.lotobig.web.rest;

import static com.seventh.lotobig.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.CreditSolde;
import com.seventh.lotobig.repository.CreditSoldeRepository;
import com.seventh.lotobig.service.dto.CreditSoldeDTO;
import com.seventh.lotobig.service.mapper.CreditSoldeMapper;
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
 * Integration tests for the {@link CreditSoldeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CreditSoldeResourceIT {

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

    private static final String ENTITY_API_URL = "/api/credit-soldes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CreditSoldeRepository creditSoldeRepository;

    @Autowired
    private CreditSoldeMapper creditSoldeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCreditSoldeMockMvc;

    private CreditSolde creditSolde;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditSolde createEntity(EntityManager em) {
        CreditSolde creditSolde = new CreditSolde()
            .montant(DEFAULT_MONTANT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return creditSolde;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditSolde createUpdatedEntity(EntityManager em) {
        CreditSolde creditSolde = new CreditSolde()
            .montant(UPDATED_MONTANT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return creditSolde;
    }

    @BeforeEach
    public void initTest() {
        creditSolde = createEntity(em);
    }

    @Test
    @Transactional
    void createCreditSolde() throws Exception {
        int databaseSizeBeforeCreate = creditSoldeRepository.findAll().size();
        // Create the CreditSolde
        CreditSoldeDTO creditSoldeDTO = creditSoldeMapper.toDto(creditSolde);
        restCreditSoldeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creditSoldeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CreditSolde in the database
        List<CreditSolde> creditSoldeList = creditSoldeRepository.findAll();
        assertThat(creditSoldeList).hasSize(databaseSizeBeforeCreate + 1);
        CreditSolde testCreditSolde = creditSoldeList.get(creditSoldeList.size() - 1);
        assertThat(testCreditSolde.getMontant()).isEqualByComparingTo(DEFAULT_MONTANT);
        assertThat(testCreditSolde.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCreditSolde.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCreditSolde.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testCreditSolde.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createCreditSoldeWithExistingId() throws Exception {
        // Create the CreditSolde with an existing ID
        creditSolde.setId(1L);
        CreditSoldeDTO creditSoldeDTO = creditSoldeMapper.toDto(creditSolde);

        int databaseSizeBeforeCreate = creditSoldeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditSoldeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creditSoldeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditSolde in the database
        List<CreditSolde> creditSoldeList = creditSoldeRepository.findAll();
        assertThat(creditSoldeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditSoldeRepository.findAll().size();
        // set the field null
        creditSolde.setMontant(null);

        // Create the CreditSolde, which fails.
        CreditSoldeDTO creditSoldeDTO = creditSoldeMapper.toDto(creditSolde);

        restCreditSoldeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creditSoldeDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreditSolde> creditSoldeList = creditSoldeRepository.findAll();
        assertThat(creditSoldeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditSoldeRepository.findAll().size();
        // set the field null
        creditSolde.setCreatedDate(null);

        // Create the CreditSolde, which fails.
        CreditSoldeDTO creditSoldeDTO = creditSoldeMapper.toDto(creditSolde);

        restCreditSoldeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creditSoldeDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreditSolde> creditSoldeList = creditSoldeRepository.findAll();
        assertThat(creditSoldeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCreditSoldes() throws Exception {
        // Initialize the database
        creditSoldeRepository.saveAndFlush(creditSolde);

        // Get all the creditSoldeList
        restCreditSoldeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditSolde.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(sameNumber(DEFAULT_MONTANT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getCreditSolde() throws Exception {
        // Initialize the database
        creditSoldeRepository.saveAndFlush(creditSolde);

        // Get the creditSolde
        restCreditSoldeMockMvc
            .perform(get(ENTITY_API_URL_ID, creditSolde.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(creditSolde.getId().intValue()))
            .andExpect(jsonPath("$.montant").value(sameNumber(DEFAULT_MONTANT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCreditSolde() throws Exception {
        // Get the creditSolde
        restCreditSoldeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCreditSolde() throws Exception {
        // Initialize the database
        creditSoldeRepository.saveAndFlush(creditSolde);

        int databaseSizeBeforeUpdate = creditSoldeRepository.findAll().size();

        // Update the creditSolde
        CreditSolde updatedCreditSolde = creditSoldeRepository.findById(creditSolde.getId()).get();
        // Disconnect from session so that the updates on updatedCreditSolde are not directly saved in db
        em.detach(updatedCreditSolde);
        updatedCreditSolde
            .montant(UPDATED_MONTANT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        CreditSoldeDTO creditSoldeDTO = creditSoldeMapper.toDto(updatedCreditSolde);

        restCreditSoldeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creditSoldeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditSoldeDTO))
            )
            .andExpect(status().isOk());

        // Validate the CreditSolde in the database
        List<CreditSolde> creditSoldeList = creditSoldeRepository.findAll();
        assertThat(creditSoldeList).hasSize(databaseSizeBeforeUpdate);
        CreditSolde testCreditSolde = creditSoldeList.get(creditSoldeList.size() - 1);
        assertThat(testCreditSolde.getMontant()).isEqualByComparingTo(UPDATED_MONTANT);
        assertThat(testCreditSolde.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCreditSolde.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCreditSolde.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testCreditSolde.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingCreditSolde() throws Exception {
        int databaseSizeBeforeUpdate = creditSoldeRepository.findAll().size();
        creditSolde.setId(count.incrementAndGet());

        // Create the CreditSolde
        CreditSoldeDTO creditSoldeDTO = creditSoldeMapper.toDto(creditSolde);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditSoldeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creditSoldeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditSoldeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditSolde in the database
        List<CreditSolde> creditSoldeList = creditSoldeRepository.findAll();
        assertThat(creditSoldeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCreditSolde() throws Exception {
        int databaseSizeBeforeUpdate = creditSoldeRepository.findAll().size();
        creditSolde.setId(count.incrementAndGet());

        // Create the CreditSolde
        CreditSoldeDTO creditSoldeDTO = creditSoldeMapper.toDto(creditSolde);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditSoldeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditSoldeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditSolde in the database
        List<CreditSolde> creditSoldeList = creditSoldeRepository.findAll();
        assertThat(creditSoldeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCreditSolde() throws Exception {
        int databaseSizeBeforeUpdate = creditSoldeRepository.findAll().size();
        creditSolde.setId(count.incrementAndGet());

        // Create the CreditSolde
        CreditSoldeDTO creditSoldeDTO = creditSoldeMapper.toDto(creditSolde);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditSoldeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creditSoldeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreditSolde in the database
        List<CreditSolde> creditSoldeList = creditSoldeRepository.findAll();
        assertThat(creditSoldeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCreditSoldeWithPatch() throws Exception {
        // Initialize the database
        creditSoldeRepository.saveAndFlush(creditSolde);

        int databaseSizeBeforeUpdate = creditSoldeRepository.findAll().size();

        // Update the creditSolde using partial update
        CreditSolde partialUpdatedCreditSolde = new CreditSolde();
        partialUpdatedCreditSolde.setId(creditSolde.getId());

        partialUpdatedCreditSolde.montant(UPDATED_MONTANT).createdBy(UPDATED_CREATED_BY).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restCreditSoldeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreditSolde.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreditSolde))
            )
            .andExpect(status().isOk());

        // Validate the CreditSolde in the database
        List<CreditSolde> creditSoldeList = creditSoldeRepository.findAll();
        assertThat(creditSoldeList).hasSize(databaseSizeBeforeUpdate);
        CreditSolde testCreditSolde = creditSoldeList.get(creditSoldeList.size() - 1);
        assertThat(testCreditSolde.getMontant()).isEqualByComparingTo(UPDATED_MONTANT);
        assertThat(testCreditSolde.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCreditSolde.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCreditSolde.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testCreditSolde.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateCreditSoldeWithPatch() throws Exception {
        // Initialize the database
        creditSoldeRepository.saveAndFlush(creditSolde);

        int databaseSizeBeforeUpdate = creditSoldeRepository.findAll().size();

        // Update the creditSolde using partial update
        CreditSolde partialUpdatedCreditSolde = new CreditSolde();
        partialUpdatedCreditSolde.setId(creditSolde.getId());

        partialUpdatedCreditSolde
            .montant(UPDATED_MONTANT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restCreditSoldeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreditSolde.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreditSolde))
            )
            .andExpect(status().isOk());

        // Validate the CreditSolde in the database
        List<CreditSolde> creditSoldeList = creditSoldeRepository.findAll();
        assertThat(creditSoldeList).hasSize(databaseSizeBeforeUpdate);
        CreditSolde testCreditSolde = creditSoldeList.get(creditSoldeList.size() - 1);
        assertThat(testCreditSolde.getMontant()).isEqualByComparingTo(UPDATED_MONTANT);
        assertThat(testCreditSolde.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCreditSolde.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCreditSolde.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testCreditSolde.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingCreditSolde() throws Exception {
        int databaseSizeBeforeUpdate = creditSoldeRepository.findAll().size();
        creditSolde.setId(count.incrementAndGet());

        // Create the CreditSolde
        CreditSoldeDTO creditSoldeDTO = creditSoldeMapper.toDto(creditSolde);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditSoldeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, creditSoldeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditSoldeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditSolde in the database
        List<CreditSolde> creditSoldeList = creditSoldeRepository.findAll();
        assertThat(creditSoldeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCreditSolde() throws Exception {
        int databaseSizeBeforeUpdate = creditSoldeRepository.findAll().size();
        creditSolde.setId(count.incrementAndGet());

        // Create the CreditSolde
        CreditSoldeDTO creditSoldeDTO = creditSoldeMapper.toDto(creditSolde);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditSoldeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditSoldeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditSolde in the database
        List<CreditSolde> creditSoldeList = creditSoldeRepository.findAll();
        assertThat(creditSoldeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCreditSolde() throws Exception {
        int databaseSizeBeforeUpdate = creditSoldeRepository.findAll().size();
        creditSolde.setId(count.incrementAndGet());

        // Create the CreditSolde
        CreditSoldeDTO creditSoldeDTO = creditSoldeMapper.toDto(creditSolde);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditSoldeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(creditSoldeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreditSolde in the database
        List<CreditSolde> creditSoldeList = creditSoldeRepository.findAll();
        assertThat(creditSoldeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCreditSolde() throws Exception {
        // Initialize the database
        creditSoldeRepository.saveAndFlush(creditSolde);

        int databaseSizeBeforeDelete = creditSoldeRepository.findAll().size();

        // Delete the creditSolde
        restCreditSoldeMockMvc
            .perform(delete(ENTITY_API_URL_ID, creditSolde.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CreditSolde> creditSoldeList = creditSoldeRepository.findAll();
        assertThat(creditSoldeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
