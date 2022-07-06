package com.seventh.lotobig.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.LimitConfManager;
import com.seventh.lotobig.domain.enumeration.TypeOption;
import com.seventh.lotobig.repository.LimitConfManagerRepository;
import com.seventh.lotobig.service.dto.LimitConfManagerDTO;
import com.seventh.lotobig.service.mapper.LimitConfManagerMapper;
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
 * Integration tests for the {@link LimitConfManagerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LimitConfManagerResourceIT {

    private static final String DEFAULT_NOMBRE_VALUE = "AAAAA";
    private static final String UPDATED_NOMBRE_VALUE = "BBBBB";

    private static final Long DEFAULT_LIMIT = 1L;
    private static final Long UPDATED_LIMIT = 2L;

    private static final TypeOption DEFAULT_LIMIT_STATUT = TypeOption.MARIAGE;
    private static final TypeOption UPDATED_LIMIT_STATUT = TypeOption.LOTO3CHIF;

    private static final String ENTITY_API_URL = "/api/limit-conf-managers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LimitConfManagerRepository limitConfManagerRepository;

    @Autowired
    private LimitConfManagerMapper limitConfManagerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLimitConfManagerMockMvc;

    private LimitConfManager limitConfManager;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LimitConfManager createEntity(EntityManager em) {
        LimitConfManager limitConfManager = new LimitConfManager()
            .nombreValue(DEFAULT_NOMBRE_VALUE)
            .limit(DEFAULT_LIMIT)
            .limitStatut(DEFAULT_LIMIT_STATUT);
        return limitConfManager;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LimitConfManager createUpdatedEntity(EntityManager em) {
        LimitConfManager limitConfManager = new LimitConfManager()
            .nombreValue(UPDATED_NOMBRE_VALUE)
            .limit(UPDATED_LIMIT)
            .limitStatut(UPDATED_LIMIT_STATUT);
        return limitConfManager;
    }

    @BeforeEach
    public void initTest() {
        limitConfManager = createEntity(em);
    }

    @Test
    @Transactional
    void createLimitConfManager() throws Exception {
        int databaseSizeBeforeCreate = limitConfManagerRepository.findAll().size();
        // Create the LimitConfManager
        LimitConfManagerDTO limitConfManagerDTO = limitConfManagerMapper.toDto(limitConfManager);
        restLimitConfManagerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(limitConfManagerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LimitConfManager in the database
        List<LimitConfManager> limitConfManagerList = limitConfManagerRepository.findAll();
        assertThat(limitConfManagerList).hasSize(databaseSizeBeforeCreate + 1);
        LimitConfManager testLimitConfManager = limitConfManagerList.get(limitConfManagerList.size() - 1);
        assertThat(testLimitConfManager.getNombreValue()).isEqualTo(DEFAULT_NOMBRE_VALUE);
        assertThat(testLimitConfManager.getLimit()).isEqualTo(DEFAULT_LIMIT);
        assertThat(testLimitConfManager.getLimitStatut()).isEqualTo(DEFAULT_LIMIT_STATUT);
    }

    @Test
    @Transactional
    void createLimitConfManagerWithExistingId() throws Exception {
        // Create the LimitConfManager with an existing ID
        limitConfManager.setId(1L);
        LimitConfManagerDTO limitConfManagerDTO = limitConfManagerMapper.toDto(limitConfManager);

        int databaseSizeBeforeCreate = limitConfManagerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLimitConfManagerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(limitConfManagerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LimitConfManager in the database
        List<LimitConfManager> limitConfManagerList = limitConfManagerRepository.findAll();
        assertThat(limitConfManagerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLimitIsRequired() throws Exception {
        int databaseSizeBeforeTest = limitConfManagerRepository.findAll().size();
        // set the field null
        limitConfManager.setLimit(null);

        // Create the LimitConfManager, which fails.
        LimitConfManagerDTO limitConfManagerDTO = limitConfManagerMapper.toDto(limitConfManager);

        restLimitConfManagerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(limitConfManagerDTO))
            )
            .andExpect(status().isBadRequest());

        List<LimitConfManager> limitConfManagerList = limitConfManagerRepository.findAll();
        assertThat(limitConfManagerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLimitConfManagers() throws Exception {
        // Initialize the database
        limitConfManagerRepository.saveAndFlush(limitConfManager);

        // Get all the limitConfManagerList
        restLimitConfManagerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(limitConfManager.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreValue").value(hasItem(DEFAULT_NOMBRE_VALUE)))
            .andExpect(jsonPath("$.[*].limit").value(hasItem(DEFAULT_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].limitStatut").value(hasItem(DEFAULT_LIMIT_STATUT.toString())));
    }

    @Test
    @Transactional
    void getLimitConfManager() throws Exception {
        // Initialize the database
        limitConfManagerRepository.saveAndFlush(limitConfManager);

        // Get the limitConfManager
        restLimitConfManagerMockMvc
            .perform(get(ENTITY_API_URL_ID, limitConfManager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(limitConfManager.getId().intValue()))
            .andExpect(jsonPath("$.nombreValue").value(DEFAULT_NOMBRE_VALUE))
            .andExpect(jsonPath("$.limit").value(DEFAULT_LIMIT.intValue()))
            .andExpect(jsonPath("$.limitStatut").value(DEFAULT_LIMIT_STATUT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLimitConfManager() throws Exception {
        // Get the limitConfManager
        restLimitConfManagerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLimitConfManager() throws Exception {
        // Initialize the database
        limitConfManagerRepository.saveAndFlush(limitConfManager);

        int databaseSizeBeforeUpdate = limitConfManagerRepository.findAll().size();

        // Update the limitConfManager
        LimitConfManager updatedLimitConfManager = limitConfManagerRepository.findById(limitConfManager.getId()).get();
        // Disconnect from session so that the updates on updatedLimitConfManager are not directly saved in db
        em.detach(updatedLimitConfManager);
        updatedLimitConfManager.nombreValue(UPDATED_NOMBRE_VALUE).limit(UPDATED_LIMIT).limitStatut(UPDATED_LIMIT_STATUT);
        LimitConfManagerDTO limitConfManagerDTO = limitConfManagerMapper.toDto(updatedLimitConfManager);

        restLimitConfManagerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, limitConfManagerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(limitConfManagerDTO))
            )
            .andExpect(status().isOk());

        // Validate the LimitConfManager in the database
        List<LimitConfManager> limitConfManagerList = limitConfManagerRepository.findAll();
        assertThat(limitConfManagerList).hasSize(databaseSizeBeforeUpdate);
        LimitConfManager testLimitConfManager = limitConfManagerList.get(limitConfManagerList.size() - 1);
        assertThat(testLimitConfManager.getNombreValue()).isEqualTo(UPDATED_NOMBRE_VALUE);
        assertThat(testLimitConfManager.getLimit()).isEqualTo(UPDATED_LIMIT);
        assertThat(testLimitConfManager.getLimitStatut()).isEqualTo(UPDATED_LIMIT_STATUT);
    }

    @Test
    @Transactional
    void putNonExistingLimitConfManager() throws Exception {
        int databaseSizeBeforeUpdate = limitConfManagerRepository.findAll().size();
        limitConfManager.setId(count.incrementAndGet());

        // Create the LimitConfManager
        LimitConfManagerDTO limitConfManagerDTO = limitConfManagerMapper.toDto(limitConfManager);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLimitConfManagerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, limitConfManagerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(limitConfManagerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LimitConfManager in the database
        List<LimitConfManager> limitConfManagerList = limitConfManagerRepository.findAll();
        assertThat(limitConfManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLimitConfManager() throws Exception {
        int databaseSizeBeforeUpdate = limitConfManagerRepository.findAll().size();
        limitConfManager.setId(count.incrementAndGet());

        // Create the LimitConfManager
        LimitConfManagerDTO limitConfManagerDTO = limitConfManagerMapper.toDto(limitConfManager);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLimitConfManagerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(limitConfManagerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LimitConfManager in the database
        List<LimitConfManager> limitConfManagerList = limitConfManagerRepository.findAll();
        assertThat(limitConfManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLimitConfManager() throws Exception {
        int databaseSizeBeforeUpdate = limitConfManagerRepository.findAll().size();
        limitConfManager.setId(count.incrementAndGet());

        // Create the LimitConfManager
        LimitConfManagerDTO limitConfManagerDTO = limitConfManagerMapper.toDto(limitConfManager);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLimitConfManagerMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(limitConfManagerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LimitConfManager in the database
        List<LimitConfManager> limitConfManagerList = limitConfManagerRepository.findAll();
        assertThat(limitConfManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLimitConfManagerWithPatch() throws Exception {
        // Initialize the database
        limitConfManagerRepository.saveAndFlush(limitConfManager);

        int databaseSizeBeforeUpdate = limitConfManagerRepository.findAll().size();

        // Update the limitConfManager using partial update
        LimitConfManager partialUpdatedLimitConfManager = new LimitConfManager();
        partialUpdatedLimitConfManager.setId(limitConfManager.getId());

        partialUpdatedLimitConfManager.nombreValue(UPDATED_NOMBRE_VALUE).limitStatut(UPDATED_LIMIT_STATUT);

        restLimitConfManagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLimitConfManager.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLimitConfManager))
            )
            .andExpect(status().isOk());

        // Validate the LimitConfManager in the database
        List<LimitConfManager> limitConfManagerList = limitConfManagerRepository.findAll();
        assertThat(limitConfManagerList).hasSize(databaseSizeBeforeUpdate);
        LimitConfManager testLimitConfManager = limitConfManagerList.get(limitConfManagerList.size() - 1);
        assertThat(testLimitConfManager.getNombreValue()).isEqualTo(UPDATED_NOMBRE_VALUE);
        assertThat(testLimitConfManager.getLimit()).isEqualTo(DEFAULT_LIMIT);
        assertThat(testLimitConfManager.getLimitStatut()).isEqualTo(UPDATED_LIMIT_STATUT);
    }

    @Test
    @Transactional
    void fullUpdateLimitConfManagerWithPatch() throws Exception {
        // Initialize the database
        limitConfManagerRepository.saveAndFlush(limitConfManager);

        int databaseSizeBeforeUpdate = limitConfManagerRepository.findAll().size();

        // Update the limitConfManager using partial update
        LimitConfManager partialUpdatedLimitConfManager = new LimitConfManager();
        partialUpdatedLimitConfManager.setId(limitConfManager.getId());

        partialUpdatedLimitConfManager.nombreValue(UPDATED_NOMBRE_VALUE).limit(UPDATED_LIMIT).limitStatut(UPDATED_LIMIT_STATUT);

        restLimitConfManagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLimitConfManager.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLimitConfManager))
            )
            .andExpect(status().isOk());

        // Validate the LimitConfManager in the database
        List<LimitConfManager> limitConfManagerList = limitConfManagerRepository.findAll();
        assertThat(limitConfManagerList).hasSize(databaseSizeBeforeUpdate);
        LimitConfManager testLimitConfManager = limitConfManagerList.get(limitConfManagerList.size() - 1);
        assertThat(testLimitConfManager.getNombreValue()).isEqualTo(UPDATED_NOMBRE_VALUE);
        assertThat(testLimitConfManager.getLimit()).isEqualTo(UPDATED_LIMIT);
        assertThat(testLimitConfManager.getLimitStatut()).isEqualTo(UPDATED_LIMIT_STATUT);
    }

    @Test
    @Transactional
    void patchNonExistingLimitConfManager() throws Exception {
        int databaseSizeBeforeUpdate = limitConfManagerRepository.findAll().size();
        limitConfManager.setId(count.incrementAndGet());

        // Create the LimitConfManager
        LimitConfManagerDTO limitConfManagerDTO = limitConfManagerMapper.toDto(limitConfManager);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLimitConfManagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, limitConfManagerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(limitConfManagerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LimitConfManager in the database
        List<LimitConfManager> limitConfManagerList = limitConfManagerRepository.findAll();
        assertThat(limitConfManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLimitConfManager() throws Exception {
        int databaseSizeBeforeUpdate = limitConfManagerRepository.findAll().size();
        limitConfManager.setId(count.incrementAndGet());

        // Create the LimitConfManager
        LimitConfManagerDTO limitConfManagerDTO = limitConfManagerMapper.toDto(limitConfManager);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLimitConfManagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(limitConfManagerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LimitConfManager in the database
        List<LimitConfManager> limitConfManagerList = limitConfManagerRepository.findAll();
        assertThat(limitConfManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLimitConfManager() throws Exception {
        int databaseSizeBeforeUpdate = limitConfManagerRepository.findAll().size();
        limitConfManager.setId(count.incrementAndGet());

        // Create the LimitConfManager
        LimitConfManagerDTO limitConfManagerDTO = limitConfManagerMapper.toDto(limitConfManager);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLimitConfManagerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(limitConfManagerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LimitConfManager in the database
        List<LimitConfManager> limitConfManagerList = limitConfManagerRepository.findAll();
        assertThat(limitConfManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLimitConfManager() throws Exception {
        // Initialize the database
        limitConfManagerRepository.saveAndFlush(limitConfManager);

        int databaseSizeBeforeDelete = limitConfManagerRepository.findAll().size();

        // Delete the limitConfManager
        restLimitConfManagerMockMvc
            .perform(delete(ENTITY_API_URL_ID, limitConfManager.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LimitConfManager> limitConfManagerList = limitConfManagerRepository.findAll();
        assertThat(limitConfManagerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
