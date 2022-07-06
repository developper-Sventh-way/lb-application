package com.seventh.lotobig.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.POSConfiguration;
import com.seventh.lotobig.domain.enumeration.DeviceStatut;
import com.seventh.lotobig.repository.POSConfigurationRepository;
import com.seventh.lotobig.service.dto.POSConfigurationDTO;
import com.seventh.lotobig.service.mapper.POSConfigurationMapper;
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
 * Integration tests for the {@link POSConfigurationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class POSConfigurationResourceIT {

    private static final String DEFAULT_P_OS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_P_OS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_I_MEI = "AAAAAAAAAA";
    private static final String UPDATED_I_MEI = "BBBBBBBBBB";

    private static final DeviceStatut DEFAULT_DEVICE_STATUT = DeviceStatut.ACTIVE;
    private static final DeviceStatut UPDATED_DEVICE_STATUT = DeviceStatut.BLOQUER;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/pos-configurations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private POSConfigurationRepository pOSConfigurationRepository;

    @Autowired
    private POSConfigurationMapper pOSConfigurationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPOSConfigurationMockMvc;

    private POSConfiguration pOSConfiguration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static POSConfiguration createEntity(EntityManager em) {
        POSConfiguration pOSConfiguration = new POSConfiguration()
            .pOSName(DEFAULT_P_OS_NAME)
            .iMEI(DEFAULT_I_MEI)
            .deviceStatut(DEFAULT_DEVICE_STATUT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return pOSConfiguration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static POSConfiguration createUpdatedEntity(EntityManager em) {
        POSConfiguration pOSConfiguration = new POSConfiguration()
            .pOSName(UPDATED_P_OS_NAME)
            .iMEI(UPDATED_I_MEI)
            .deviceStatut(UPDATED_DEVICE_STATUT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return pOSConfiguration;
    }

    @BeforeEach
    public void initTest() {
        pOSConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    void createPOSConfiguration() throws Exception {
        int databaseSizeBeforeCreate = pOSConfigurationRepository.findAll().size();
        // Create the POSConfiguration
        POSConfigurationDTO pOSConfigurationDTO = pOSConfigurationMapper.toDto(pOSConfiguration);
        restPOSConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pOSConfigurationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the POSConfiguration in the database
        List<POSConfiguration> pOSConfigurationList = pOSConfigurationRepository.findAll();
        assertThat(pOSConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        POSConfiguration testPOSConfiguration = pOSConfigurationList.get(pOSConfigurationList.size() - 1);
        assertThat(testPOSConfiguration.getpOSName()).isEqualTo(DEFAULT_P_OS_NAME);
        assertThat(testPOSConfiguration.getiMEI()).isEqualTo(DEFAULT_I_MEI);
        assertThat(testPOSConfiguration.getDeviceStatut()).isEqualTo(DEFAULT_DEVICE_STATUT);
        assertThat(testPOSConfiguration.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPOSConfiguration.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPOSConfiguration.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPOSConfiguration.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createPOSConfigurationWithExistingId() throws Exception {
        // Create the POSConfiguration with an existing ID
        pOSConfiguration.setId(1L);
        POSConfigurationDTO pOSConfigurationDTO = pOSConfigurationMapper.toDto(pOSConfiguration);

        int databaseSizeBeforeCreate = pOSConfigurationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPOSConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pOSConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the POSConfiguration in the database
        List<POSConfiguration> pOSConfigurationList = pOSConfigurationRepository.findAll();
        assertThat(pOSConfigurationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkpOSNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pOSConfigurationRepository.findAll().size();
        // set the field null
        pOSConfiguration.setpOSName(null);

        // Create the POSConfiguration, which fails.
        POSConfigurationDTO pOSConfigurationDTO = pOSConfigurationMapper.toDto(pOSConfiguration);

        restPOSConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pOSConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<POSConfiguration> pOSConfigurationList = pOSConfigurationRepository.findAll();
        assertThat(pOSConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkiMEIIsRequired() throws Exception {
        int databaseSizeBeforeTest = pOSConfigurationRepository.findAll().size();
        // set the field null
        pOSConfiguration.setiMEI(null);

        // Create the POSConfiguration, which fails.
        POSConfigurationDTO pOSConfigurationDTO = pOSConfigurationMapper.toDto(pOSConfiguration);

        restPOSConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pOSConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<POSConfiguration> pOSConfigurationList = pOSConfigurationRepository.findAll();
        assertThat(pOSConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeviceStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = pOSConfigurationRepository.findAll().size();
        // set the field null
        pOSConfiguration.setDeviceStatut(null);

        // Create the POSConfiguration, which fails.
        POSConfigurationDTO pOSConfigurationDTO = pOSConfigurationMapper.toDto(pOSConfiguration);

        restPOSConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pOSConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<POSConfiguration> pOSConfigurationList = pOSConfigurationRepository.findAll();
        assertThat(pOSConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = pOSConfigurationRepository.findAll().size();
        // set the field null
        pOSConfiguration.setCreatedBy(null);

        // Create the POSConfiguration, which fails.
        POSConfigurationDTO pOSConfigurationDTO = pOSConfigurationMapper.toDto(pOSConfiguration);

        restPOSConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pOSConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<POSConfiguration> pOSConfigurationList = pOSConfigurationRepository.findAll();
        assertThat(pOSConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pOSConfigurationRepository.findAll().size();
        // set the field null
        pOSConfiguration.setCreatedDate(null);

        // Create the POSConfiguration, which fails.
        POSConfigurationDTO pOSConfigurationDTO = pOSConfigurationMapper.toDto(pOSConfiguration);

        restPOSConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pOSConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<POSConfiguration> pOSConfigurationList = pOSConfigurationRepository.findAll();
        assertThat(pOSConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPOSConfigurations() throws Exception {
        // Initialize the database
        pOSConfigurationRepository.saveAndFlush(pOSConfiguration);

        // Get all the pOSConfigurationList
        restPOSConfigurationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pOSConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].pOSName").value(hasItem(DEFAULT_P_OS_NAME)))
            .andExpect(jsonPath("$.[*].iMEI").value(hasItem(DEFAULT_I_MEI)))
            .andExpect(jsonPath("$.[*].deviceStatut").value(hasItem(DEFAULT_DEVICE_STATUT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getPOSConfiguration() throws Exception {
        // Initialize the database
        pOSConfigurationRepository.saveAndFlush(pOSConfiguration);

        // Get the pOSConfiguration
        restPOSConfigurationMockMvc
            .perform(get(ENTITY_API_URL_ID, pOSConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pOSConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.pOSName").value(DEFAULT_P_OS_NAME))
            .andExpect(jsonPath("$.iMEI").value(DEFAULT_I_MEI))
            .andExpect(jsonPath("$.deviceStatut").value(DEFAULT_DEVICE_STATUT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPOSConfiguration() throws Exception {
        // Get the pOSConfiguration
        restPOSConfigurationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPOSConfiguration() throws Exception {
        // Initialize the database
        pOSConfigurationRepository.saveAndFlush(pOSConfiguration);

        int databaseSizeBeforeUpdate = pOSConfigurationRepository.findAll().size();

        // Update the pOSConfiguration
        POSConfiguration updatedPOSConfiguration = pOSConfigurationRepository.findById(pOSConfiguration.getId()).get();
        // Disconnect from session so that the updates on updatedPOSConfiguration are not directly saved in db
        em.detach(updatedPOSConfiguration);
        updatedPOSConfiguration
            .pOSName(UPDATED_P_OS_NAME)
            .iMEI(UPDATED_I_MEI)
            .deviceStatut(UPDATED_DEVICE_STATUT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        POSConfigurationDTO pOSConfigurationDTO = pOSConfigurationMapper.toDto(updatedPOSConfiguration);

        restPOSConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pOSConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pOSConfigurationDTO))
            )
            .andExpect(status().isOk());

        // Validate the POSConfiguration in the database
        List<POSConfiguration> pOSConfigurationList = pOSConfigurationRepository.findAll();
        assertThat(pOSConfigurationList).hasSize(databaseSizeBeforeUpdate);
        POSConfiguration testPOSConfiguration = pOSConfigurationList.get(pOSConfigurationList.size() - 1);
        assertThat(testPOSConfiguration.getpOSName()).isEqualTo(UPDATED_P_OS_NAME);
        assertThat(testPOSConfiguration.getiMEI()).isEqualTo(UPDATED_I_MEI);
        assertThat(testPOSConfiguration.getDeviceStatut()).isEqualTo(UPDATED_DEVICE_STATUT);
        assertThat(testPOSConfiguration.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPOSConfiguration.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPOSConfiguration.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPOSConfiguration.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPOSConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = pOSConfigurationRepository.findAll().size();
        pOSConfiguration.setId(count.incrementAndGet());

        // Create the POSConfiguration
        POSConfigurationDTO pOSConfigurationDTO = pOSConfigurationMapper.toDto(pOSConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPOSConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pOSConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pOSConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the POSConfiguration in the database
        List<POSConfiguration> pOSConfigurationList = pOSConfigurationRepository.findAll();
        assertThat(pOSConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPOSConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = pOSConfigurationRepository.findAll().size();
        pOSConfiguration.setId(count.incrementAndGet());

        // Create the POSConfiguration
        POSConfigurationDTO pOSConfigurationDTO = pOSConfigurationMapper.toDto(pOSConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPOSConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pOSConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the POSConfiguration in the database
        List<POSConfiguration> pOSConfigurationList = pOSConfigurationRepository.findAll();
        assertThat(pOSConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPOSConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = pOSConfigurationRepository.findAll().size();
        pOSConfiguration.setId(count.incrementAndGet());

        // Create the POSConfiguration
        POSConfigurationDTO pOSConfigurationDTO = pOSConfigurationMapper.toDto(pOSConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPOSConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pOSConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the POSConfiguration in the database
        List<POSConfiguration> pOSConfigurationList = pOSConfigurationRepository.findAll();
        assertThat(pOSConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePOSConfigurationWithPatch() throws Exception {
        // Initialize the database
        pOSConfigurationRepository.saveAndFlush(pOSConfiguration);

        int databaseSizeBeforeUpdate = pOSConfigurationRepository.findAll().size();

        // Update the pOSConfiguration using partial update
        POSConfiguration partialUpdatedPOSConfiguration = new POSConfiguration();
        partialUpdatedPOSConfiguration.setId(pOSConfiguration.getId());

        partialUpdatedPOSConfiguration
            .iMEI(UPDATED_I_MEI)
            .deviceStatut(UPDATED_DEVICE_STATUT)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restPOSConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPOSConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPOSConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the POSConfiguration in the database
        List<POSConfiguration> pOSConfigurationList = pOSConfigurationRepository.findAll();
        assertThat(pOSConfigurationList).hasSize(databaseSizeBeforeUpdate);
        POSConfiguration testPOSConfiguration = pOSConfigurationList.get(pOSConfigurationList.size() - 1);
        assertThat(testPOSConfiguration.getpOSName()).isEqualTo(DEFAULT_P_OS_NAME);
        assertThat(testPOSConfiguration.getiMEI()).isEqualTo(UPDATED_I_MEI);
        assertThat(testPOSConfiguration.getDeviceStatut()).isEqualTo(UPDATED_DEVICE_STATUT);
        assertThat(testPOSConfiguration.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPOSConfiguration.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPOSConfiguration.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPOSConfiguration.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePOSConfigurationWithPatch() throws Exception {
        // Initialize the database
        pOSConfigurationRepository.saveAndFlush(pOSConfiguration);

        int databaseSizeBeforeUpdate = pOSConfigurationRepository.findAll().size();

        // Update the pOSConfiguration using partial update
        POSConfiguration partialUpdatedPOSConfiguration = new POSConfiguration();
        partialUpdatedPOSConfiguration.setId(pOSConfiguration.getId());

        partialUpdatedPOSConfiguration
            .pOSName(UPDATED_P_OS_NAME)
            .iMEI(UPDATED_I_MEI)
            .deviceStatut(UPDATED_DEVICE_STATUT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restPOSConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPOSConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPOSConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the POSConfiguration in the database
        List<POSConfiguration> pOSConfigurationList = pOSConfigurationRepository.findAll();
        assertThat(pOSConfigurationList).hasSize(databaseSizeBeforeUpdate);
        POSConfiguration testPOSConfiguration = pOSConfigurationList.get(pOSConfigurationList.size() - 1);
        assertThat(testPOSConfiguration.getpOSName()).isEqualTo(UPDATED_P_OS_NAME);
        assertThat(testPOSConfiguration.getiMEI()).isEqualTo(UPDATED_I_MEI);
        assertThat(testPOSConfiguration.getDeviceStatut()).isEqualTo(UPDATED_DEVICE_STATUT);
        assertThat(testPOSConfiguration.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPOSConfiguration.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPOSConfiguration.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPOSConfiguration.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPOSConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = pOSConfigurationRepository.findAll().size();
        pOSConfiguration.setId(count.incrementAndGet());

        // Create the POSConfiguration
        POSConfigurationDTO pOSConfigurationDTO = pOSConfigurationMapper.toDto(pOSConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPOSConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pOSConfigurationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pOSConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the POSConfiguration in the database
        List<POSConfiguration> pOSConfigurationList = pOSConfigurationRepository.findAll();
        assertThat(pOSConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPOSConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = pOSConfigurationRepository.findAll().size();
        pOSConfiguration.setId(count.incrementAndGet());

        // Create the POSConfiguration
        POSConfigurationDTO pOSConfigurationDTO = pOSConfigurationMapper.toDto(pOSConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPOSConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pOSConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the POSConfiguration in the database
        List<POSConfiguration> pOSConfigurationList = pOSConfigurationRepository.findAll();
        assertThat(pOSConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPOSConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = pOSConfigurationRepository.findAll().size();
        pOSConfiguration.setId(count.incrementAndGet());

        // Create the POSConfiguration
        POSConfigurationDTO pOSConfigurationDTO = pOSConfigurationMapper.toDto(pOSConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPOSConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pOSConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the POSConfiguration in the database
        List<POSConfiguration> pOSConfigurationList = pOSConfigurationRepository.findAll();
        assertThat(pOSConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePOSConfiguration() throws Exception {
        // Initialize the database
        pOSConfigurationRepository.saveAndFlush(pOSConfiguration);

        int databaseSizeBeforeDelete = pOSConfigurationRepository.findAll().size();

        // Delete the pOSConfiguration
        restPOSConfigurationMockMvc
            .perform(delete(ENTITY_API_URL_ID, pOSConfiguration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<POSConfiguration> pOSConfigurationList = pOSConfigurationRepository.findAll();
        assertThat(pOSConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
