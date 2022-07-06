package com.seventh.lotobig.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.LimitConfBorlette;
import com.seventh.lotobig.domain.enumeration.TypeOption;
import com.seventh.lotobig.repository.LimitConfBorletteRepository;
import com.seventh.lotobig.service.dto.LimitConfBorletteDTO;
import com.seventh.lotobig.service.mapper.LimitConfBorletteMapper;
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
 * Integration tests for the {@link LimitConfBorletteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LimitConfBorletteResourceIT {

    private static final String DEFAULT_NOMBRE_VALUE = "AAAAA";
    private static final String UPDATED_NOMBRE_VALUE = "BBBBB";

    private static final Long DEFAULT_LIMIT = 1L;
    private static final Long UPDATED_LIMIT = 2L;

    private static final TypeOption DEFAULT_LIMIT_STATUT = TypeOption.MARIAGE;
    private static final TypeOption UPDATED_LIMIT_STATUT = TypeOption.LOTO3CHIF;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/limit-conf-borlettes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LimitConfBorletteRepository limitConfBorletteRepository;

    @Autowired
    private LimitConfBorletteMapper limitConfBorletteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLimitConfBorletteMockMvc;

    private LimitConfBorlette limitConfBorlette;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LimitConfBorlette createEntity(EntityManager em) {
        LimitConfBorlette limitConfBorlette = new LimitConfBorlette()
            .nombreValue(DEFAULT_NOMBRE_VALUE)
            .limit(DEFAULT_LIMIT)
            .limitStatut(DEFAULT_LIMIT_STATUT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return limitConfBorlette;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LimitConfBorlette createUpdatedEntity(EntityManager em) {
        LimitConfBorlette limitConfBorlette = new LimitConfBorlette()
            .nombreValue(UPDATED_NOMBRE_VALUE)
            .limit(UPDATED_LIMIT)
            .limitStatut(UPDATED_LIMIT_STATUT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return limitConfBorlette;
    }

    @BeforeEach
    public void initTest() {
        limitConfBorlette = createEntity(em);
    }

    @Test
    @Transactional
    void createLimitConfBorlette() throws Exception {
        int databaseSizeBeforeCreate = limitConfBorletteRepository.findAll().size();
        // Create the LimitConfBorlette
        LimitConfBorletteDTO limitConfBorletteDTO = limitConfBorletteMapper.toDto(limitConfBorlette);
        restLimitConfBorletteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(limitConfBorletteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LimitConfBorlette in the database
        List<LimitConfBorlette> limitConfBorletteList = limitConfBorletteRepository.findAll();
        assertThat(limitConfBorletteList).hasSize(databaseSizeBeforeCreate + 1);
        LimitConfBorlette testLimitConfBorlette = limitConfBorletteList.get(limitConfBorletteList.size() - 1);
        assertThat(testLimitConfBorlette.getNombreValue()).isEqualTo(DEFAULT_NOMBRE_VALUE);
        assertThat(testLimitConfBorlette.getLimit()).isEqualTo(DEFAULT_LIMIT);
        assertThat(testLimitConfBorlette.getLimitStatut()).isEqualTo(DEFAULT_LIMIT_STATUT);
        assertThat(testLimitConfBorlette.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLimitConfBorlette.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testLimitConfBorlette.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLimitConfBorlette.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createLimitConfBorletteWithExistingId() throws Exception {
        // Create the LimitConfBorlette with an existing ID
        limitConfBorlette.setId(1L);
        LimitConfBorletteDTO limitConfBorletteDTO = limitConfBorletteMapper.toDto(limitConfBorlette);

        int databaseSizeBeforeCreate = limitConfBorletteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLimitConfBorletteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(limitConfBorletteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LimitConfBorlette in the database
        List<LimitConfBorlette> limitConfBorletteList = limitConfBorletteRepository.findAll();
        assertThat(limitConfBorletteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLimitIsRequired() throws Exception {
        int databaseSizeBeforeTest = limitConfBorletteRepository.findAll().size();
        // set the field null
        limitConfBorlette.setLimit(null);

        // Create the LimitConfBorlette, which fails.
        LimitConfBorletteDTO limitConfBorletteDTO = limitConfBorletteMapper.toDto(limitConfBorlette);

        restLimitConfBorletteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(limitConfBorletteDTO))
            )
            .andExpect(status().isBadRequest());

        List<LimitConfBorlette> limitConfBorletteList = limitConfBorletteRepository.findAll();
        assertThat(limitConfBorletteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLimitConfBorlettes() throws Exception {
        // Initialize the database
        limitConfBorletteRepository.saveAndFlush(limitConfBorlette);

        // Get all the limitConfBorletteList
        restLimitConfBorletteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(limitConfBorlette.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreValue").value(hasItem(DEFAULT_NOMBRE_VALUE)))
            .andExpect(jsonPath("$.[*].limit").value(hasItem(DEFAULT_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].limitStatut").value(hasItem(DEFAULT_LIMIT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getLimitConfBorlette() throws Exception {
        // Initialize the database
        limitConfBorletteRepository.saveAndFlush(limitConfBorlette);

        // Get the limitConfBorlette
        restLimitConfBorletteMockMvc
            .perform(get(ENTITY_API_URL_ID, limitConfBorlette.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(limitConfBorlette.getId().intValue()))
            .andExpect(jsonPath("$.nombreValue").value(DEFAULT_NOMBRE_VALUE))
            .andExpect(jsonPath("$.limit").value(DEFAULT_LIMIT.intValue()))
            .andExpect(jsonPath("$.limitStatut").value(DEFAULT_LIMIT_STATUT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLimitConfBorlette() throws Exception {
        // Get the limitConfBorlette
        restLimitConfBorletteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLimitConfBorlette() throws Exception {
        // Initialize the database
        limitConfBorletteRepository.saveAndFlush(limitConfBorlette);

        int databaseSizeBeforeUpdate = limitConfBorletteRepository.findAll().size();

        // Update the limitConfBorlette
        LimitConfBorlette updatedLimitConfBorlette = limitConfBorletteRepository.findById(limitConfBorlette.getId()).get();
        // Disconnect from session so that the updates on updatedLimitConfBorlette are not directly saved in db
        em.detach(updatedLimitConfBorlette);
        updatedLimitConfBorlette
            .nombreValue(UPDATED_NOMBRE_VALUE)
            .limit(UPDATED_LIMIT)
            .limitStatut(UPDATED_LIMIT_STATUT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        LimitConfBorletteDTO limitConfBorletteDTO = limitConfBorletteMapper.toDto(updatedLimitConfBorlette);

        restLimitConfBorletteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, limitConfBorletteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(limitConfBorletteDTO))
            )
            .andExpect(status().isOk());

        // Validate the LimitConfBorlette in the database
        List<LimitConfBorlette> limitConfBorletteList = limitConfBorletteRepository.findAll();
        assertThat(limitConfBorletteList).hasSize(databaseSizeBeforeUpdate);
        LimitConfBorlette testLimitConfBorlette = limitConfBorletteList.get(limitConfBorletteList.size() - 1);
        assertThat(testLimitConfBorlette.getNombreValue()).isEqualTo(UPDATED_NOMBRE_VALUE);
        assertThat(testLimitConfBorlette.getLimit()).isEqualTo(UPDATED_LIMIT);
        assertThat(testLimitConfBorlette.getLimitStatut()).isEqualTo(UPDATED_LIMIT_STATUT);
        assertThat(testLimitConfBorlette.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLimitConfBorlette.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testLimitConfBorlette.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLimitConfBorlette.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingLimitConfBorlette() throws Exception {
        int databaseSizeBeforeUpdate = limitConfBorletteRepository.findAll().size();
        limitConfBorlette.setId(count.incrementAndGet());

        // Create the LimitConfBorlette
        LimitConfBorletteDTO limitConfBorletteDTO = limitConfBorletteMapper.toDto(limitConfBorlette);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLimitConfBorletteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, limitConfBorletteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(limitConfBorletteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LimitConfBorlette in the database
        List<LimitConfBorlette> limitConfBorletteList = limitConfBorletteRepository.findAll();
        assertThat(limitConfBorletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLimitConfBorlette() throws Exception {
        int databaseSizeBeforeUpdate = limitConfBorletteRepository.findAll().size();
        limitConfBorlette.setId(count.incrementAndGet());

        // Create the LimitConfBorlette
        LimitConfBorletteDTO limitConfBorletteDTO = limitConfBorletteMapper.toDto(limitConfBorlette);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLimitConfBorletteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(limitConfBorletteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LimitConfBorlette in the database
        List<LimitConfBorlette> limitConfBorletteList = limitConfBorletteRepository.findAll();
        assertThat(limitConfBorletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLimitConfBorlette() throws Exception {
        int databaseSizeBeforeUpdate = limitConfBorletteRepository.findAll().size();
        limitConfBorlette.setId(count.incrementAndGet());

        // Create the LimitConfBorlette
        LimitConfBorletteDTO limitConfBorletteDTO = limitConfBorletteMapper.toDto(limitConfBorlette);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLimitConfBorletteMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(limitConfBorletteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LimitConfBorlette in the database
        List<LimitConfBorlette> limitConfBorletteList = limitConfBorletteRepository.findAll();
        assertThat(limitConfBorletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLimitConfBorletteWithPatch() throws Exception {
        // Initialize the database
        limitConfBorletteRepository.saveAndFlush(limitConfBorlette);

        int databaseSizeBeforeUpdate = limitConfBorletteRepository.findAll().size();

        // Update the limitConfBorlette using partial update
        LimitConfBorlette partialUpdatedLimitConfBorlette = new LimitConfBorlette();
        partialUpdatedLimitConfBorlette.setId(limitConfBorlette.getId());

        partialUpdatedLimitConfBorlette
            .limitStatut(UPDATED_LIMIT_STATUT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restLimitConfBorletteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLimitConfBorlette.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLimitConfBorlette))
            )
            .andExpect(status().isOk());

        // Validate the LimitConfBorlette in the database
        List<LimitConfBorlette> limitConfBorletteList = limitConfBorletteRepository.findAll();
        assertThat(limitConfBorletteList).hasSize(databaseSizeBeforeUpdate);
        LimitConfBorlette testLimitConfBorlette = limitConfBorletteList.get(limitConfBorletteList.size() - 1);
        assertThat(testLimitConfBorlette.getNombreValue()).isEqualTo(DEFAULT_NOMBRE_VALUE);
        assertThat(testLimitConfBorlette.getLimit()).isEqualTo(DEFAULT_LIMIT);
        assertThat(testLimitConfBorlette.getLimitStatut()).isEqualTo(UPDATED_LIMIT_STATUT);
        assertThat(testLimitConfBorlette.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLimitConfBorlette.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testLimitConfBorlette.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLimitConfBorlette.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateLimitConfBorletteWithPatch() throws Exception {
        // Initialize the database
        limitConfBorletteRepository.saveAndFlush(limitConfBorlette);

        int databaseSizeBeforeUpdate = limitConfBorletteRepository.findAll().size();

        // Update the limitConfBorlette using partial update
        LimitConfBorlette partialUpdatedLimitConfBorlette = new LimitConfBorlette();
        partialUpdatedLimitConfBorlette.setId(limitConfBorlette.getId());

        partialUpdatedLimitConfBorlette
            .nombreValue(UPDATED_NOMBRE_VALUE)
            .limit(UPDATED_LIMIT)
            .limitStatut(UPDATED_LIMIT_STATUT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restLimitConfBorletteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLimitConfBorlette.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLimitConfBorlette))
            )
            .andExpect(status().isOk());

        // Validate the LimitConfBorlette in the database
        List<LimitConfBorlette> limitConfBorletteList = limitConfBorletteRepository.findAll();
        assertThat(limitConfBorletteList).hasSize(databaseSizeBeforeUpdate);
        LimitConfBorlette testLimitConfBorlette = limitConfBorletteList.get(limitConfBorletteList.size() - 1);
        assertThat(testLimitConfBorlette.getNombreValue()).isEqualTo(UPDATED_NOMBRE_VALUE);
        assertThat(testLimitConfBorlette.getLimit()).isEqualTo(UPDATED_LIMIT);
        assertThat(testLimitConfBorlette.getLimitStatut()).isEqualTo(UPDATED_LIMIT_STATUT);
        assertThat(testLimitConfBorlette.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLimitConfBorlette.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testLimitConfBorlette.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLimitConfBorlette.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingLimitConfBorlette() throws Exception {
        int databaseSizeBeforeUpdate = limitConfBorletteRepository.findAll().size();
        limitConfBorlette.setId(count.incrementAndGet());

        // Create the LimitConfBorlette
        LimitConfBorletteDTO limitConfBorletteDTO = limitConfBorletteMapper.toDto(limitConfBorlette);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLimitConfBorletteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, limitConfBorletteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(limitConfBorletteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LimitConfBorlette in the database
        List<LimitConfBorlette> limitConfBorletteList = limitConfBorletteRepository.findAll();
        assertThat(limitConfBorletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLimitConfBorlette() throws Exception {
        int databaseSizeBeforeUpdate = limitConfBorletteRepository.findAll().size();
        limitConfBorlette.setId(count.incrementAndGet());

        // Create the LimitConfBorlette
        LimitConfBorletteDTO limitConfBorletteDTO = limitConfBorletteMapper.toDto(limitConfBorlette);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLimitConfBorletteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(limitConfBorletteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LimitConfBorlette in the database
        List<LimitConfBorlette> limitConfBorletteList = limitConfBorletteRepository.findAll();
        assertThat(limitConfBorletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLimitConfBorlette() throws Exception {
        int databaseSizeBeforeUpdate = limitConfBorletteRepository.findAll().size();
        limitConfBorlette.setId(count.incrementAndGet());

        // Create the LimitConfBorlette
        LimitConfBorletteDTO limitConfBorletteDTO = limitConfBorletteMapper.toDto(limitConfBorlette);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLimitConfBorletteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(limitConfBorletteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LimitConfBorlette in the database
        List<LimitConfBorlette> limitConfBorletteList = limitConfBorletteRepository.findAll();
        assertThat(limitConfBorletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLimitConfBorlette() throws Exception {
        // Initialize the database
        limitConfBorletteRepository.saveAndFlush(limitConfBorlette);

        int databaseSizeBeforeDelete = limitConfBorletteRepository.findAll().size();

        // Delete the limitConfBorlette
        restLimitConfBorletteMockMvc
            .perform(delete(ENTITY_API_URL_ID, limitConfBorlette.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LimitConfBorlette> limitConfBorletteList = limitConfBorletteRepository.findAll();
        assertThat(limitConfBorletteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
