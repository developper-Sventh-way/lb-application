package com.seventh.lotobig.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.PointOfSaleConf;
import com.seventh.lotobig.repository.PointOfSaleConfRepository;
import com.seventh.lotobig.service.dto.PointOfSaleConfDTO;
import com.seventh.lotobig.service.mapper.PointOfSaleConfMapper;
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
 * Integration tests for the {@link PointOfSaleConfResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PointOfSaleConfResourceIT {

    private static final Integer DEFAULT_POURCENTAGE_CAISSIER = 1;
    private static final Integer UPDATED_POURCENTAGE_CAISSIER = 2;

    private static final Integer DEFAULT_POURCENTAGE_RESPONSABLE = 1;
    private static final Integer UPDATED_POURCENTAGE_RESPONSABLE = 2;

    private static final String DEFAULT_START_TIME = "AAAAA";
    private static final String UPDATED_START_TIME = "BBBBB";

    private static final String DEFAULT_END_TIME = "AAAAA";
    private static final String UPDATED_END_TIME = "BBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/point-of-sale-confs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PointOfSaleConfRepository pointOfSaleConfRepository;

    @Autowired
    private PointOfSaleConfMapper pointOfSaleConfMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPointOfSaleConfMockMvc;

    private PointOfSaleConf pointOfSaleConf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointOfSaleConf createEntity(EntityManager em) {
        PointOfSaleConf pointOfSaleConf = new PointOfSaleConf()
            .pourcentageCaissier(DEFAULT_POURCENTAGE_CAISSIER)
            .pourcentageResponsable(DEFAULT_POURCENTAGE_RESPONSABLE)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return pointOfSaleConf;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointOfSaleConf createUpdatedEntity(EntityManager em) {
        PointOfSaleConf pointOfSaleConf = new PointOfSaleConf()
            .pourcentageCaissier(UPDATED_POURCENTAGE_CAISSIER)
            .pourcentageResponsable(UPDATED_POURCENTAGE_RESPONSABLE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return pointOfSaleConf;
    }

    @BeforeEach
    public void initTest() {
        pointOfSaleConf = createEntity(em);
    }

    @Test
    @Transactional
    void createPointOfSaleConf() throws Exception {
        int databaseSizeBeforeCreate = pointOfSaleConfRepository.findAll().size();
        // Create the PointOfSaleConf
        PointOfSaleConfDTO pointOfSaleConfDTO = pointOfSaleConfMapper.toDto(pointOfSaleConf);
        restPointOfSaleConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointOfSaleConfDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PointOfSaleConf in the database
        List<PointOfSaleConf> pointOfSaleConfList = pointOfSaleConfRepository.findAll();
        assertThat(pointOfSaleConfList).hasSize(databaseSizeBeforeCreate + 1);
        PointOfSaleConf testPointOfSaleConf = pointOfSaleConfList.get(pointOfSaleConfList.size() - 1);
        assertThat(testPointOfSaleConf.getPourcentageCaissier()).isEqualTo(DEFAULT_POURCENTAGE_CAISSIER);
        assertThat(testPointOfSaleConf.getPourcentageResponsable()).isEqualTo(DEFAULT_POURCENTAGE_RESPONSABLE);
        assertThat(testPointOfSaleConf.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testPointOfSaleConf.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testPointOfSaleConf.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPointOfSaleConf.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPointOfSaleConf.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPointOfSaleConf.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createPointOfSaleConfWithExistingId() throws Exception {
        // Create the PointOfSaleConf with an existing ID
        pointOfSaleConf.setId(1L);
        PointOfSaleConfDTO pointOfSaleConfDTO = pointOfSaleConfMapper.toDto(pointOfSaleConf);

        int databaseSizeBeforeCreate = pointOfSaleConfRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPointOfSaleConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointOfSaleConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfSaleConf in the database
        List<PointOfSaleConf> pointOfSaleConfList = pointOfSaleConfRepository.findAll();
        assertThat(pointOfSaleConfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = pointOfSaleConfRepository.findAll().size();
        // set the field null
        pointOfSaleConf.setCreatedBy(null);

        // Create the PointOfSaleConf, which fails.
        PointOfSaleConfDTO pointOfSaleConfDTO = pointOfSaleConfMapper.toDto(pointOfSaleConf);

        restPointOfSaleConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointOfSaleConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<PointOfSaleConf> pointOfSaleConfList = pointOfSaleConfRepository.findAll();
        assertThat(pointOfSaleConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pointOfSaleConfRepository.findAll().size();
        // set the field null
        pointOfSaleConf.setCreatedDate(null);

        // Create the PointOfSaleConf, which fails.
        PointOfSaleConfDTO pointOfSaleConfDTO = pointOfSaleConfMapper.toDto(pointOfSaleConf);

        restPointOfSaleConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointOfSaleConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<PointOfSaleConf> pointOfSaleConfList = pointOfSaleConfRepository.findAll();
        assertThat(pointOfSaleConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPointOfSaleConfs() throws Exception {
        // Initialize the database
        pointOfSaleConfRepository.saveAndFlush(pointOfSaleConf);

        // Get all the pointOfSaleConfList
        restPointOfSaleConfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pointOfSaleConf.getId().intValue())))
            .andExpect(jsonPath("$.[*].pourcentageCaissier").value(hasItem(DEFAULT_POURCENTAGE_CAISSIER)))
            .andExpect(jsonPath("$.[*].pourcentageResponsable").value(hasItem(DEFAULT_POURCENTAGE_RESPONSABLE)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getPointOfSaleConf() throws Exception {
        // Initialize the database
        pointOfSaleConfRepository.saveAndFlush(pointOfSaleConf);

        // Get the pointOfSaleConf
        restPointOfSaleConfMockMvc
            .perform(get(ENTITY_API_URL_ID, pointOfSaleConf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pointOfSaleConf.getId().intValue()))
            .andExpect(jsonPath("$.pourcentageCaissier").value(DEFAULT_POURCENTAGE_CAISSIER))
            .andExpect(jsonPath("$.pourcentageResponsable").value(DEFAULT_POURCENTAGE_RESPONSABLE))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPointOfSaleConf() throws Exception {
        // Get the pointOfSaleConf
        restPointOfSaleConfMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPointOfSaleConf() throws Exception {
        // Initialize the database
        pointOfSaleConfRepository.saveAndFlush(pointOfSaleConf);

        int databaseSizeBeforeUpdate = pointOfSaleConfRepository.findAll().size();

        // Update the pointOfSaleConf
        PointOfSaleConf updatedPointOfSaleConf = pointOfSaleConfRepository.findById(pointOfSaleConf.getId()).get();
        // Disconnect from session so that the updates on updatedPointOfSaleConf are not directly saved in db
        em.detach(updatedPointOfSaleConf);
        updatedPointOfSaleConf
            .pourcentageCaissier(UPDATED_POURCENTAGE_CAISSIER)
            .pourcentageResponsable(UPDATED_POURCENTAGE_RESPONSABLE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        PointOfSaleConfDTO pointOfSaleConfDTO = pointOfSaleConfMapper.toDto(updatedPointOfSaleConf);

        restPointOfSaleConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointOfSaleConfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleConfDTO))
            )
            .andExpect(status().isOk());

        // Validate the PointOfSaleConf in the database
        List<PointOfSaleConf> pointOfSaleConfList = pointOfSaleConfRepository.findAll();
        assertThat(pointOfSaleConfList).hasSize(databaseSizeBeforeUpdate);
        PointOfSaleConf testPointOfSaleConf = pointOfSaleConfList.get(pointOfSaleConfList.size() - 1);
        assertThat(testPointOfSaleConf.getPourcentageCaissier()).isEqualTo(UPDATED_POURCENTAGE_CAISSIER);
        assertThat(testPointOfSaleConf.getPourcentageResponsable()).isEqualTo(UPDATED_POURCENTAGE_RESPONSABLE);
        assertThat(testPointOfSaleConf.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testPointOfSaleConf.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testPointOfSaleConf.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPointOfSaleConf.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPointOfSaleConf.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPointOfSaleConf.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPointOfSaleConf() throws Exception {
        int databaseSizeBeforeUpdate = pointOfSaleConfRepository.findAll().size();
        pointOfSaleConf.setId(count.incrementAndGet());

        // Create the PointOfSaleConf
        PointOfSaleConfDTO pointOfSaleConfDTO = pointOfSaleConfMapper.toDto(pointOfSaleConf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointOfSaleConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointOfSaleConfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfSaleConf in the database
        List<PointOfSaleConf> pointOfSaleConfList = pointOfSaleConfRepository.findAll();
        assertThat(pointOfSaleConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPointOfSaleConf() throws Exception {
        int databaseSizeBeforeUpdate = pointOfSaleConfRepository.findAll().size();
        pointOfSaleConf.setId(count.incrementAndGet());

        // Create the PointOfSaleConf
        PointOfSaleConfDTO pointOfSaleConfDTO = pointOfSaleConfMapper.toDto(pointOfSaleConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointOfSaleConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfSaleConf in the database
        List<PointOfSaleConf> pointOfSaleConfList = pointOfSaleConfRepository.findAll();
        assertThat(pointOfSaleConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPointOfSaleConf() throws Exception {
        int databaseSizeBeforeUpdate = pointOfSaleConfRepository.findAll().size();
        pointOfSaleConf.setId(count.incrementAndGet());

        // Create the PointOfSaleConf
        PointOfSaleConfDTO pointOfSaleConfDTO = pointOfSaleConfMapper.toDto(pointOfSaleConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointOfSaleConfMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointOfSaleConfDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointOfSaleConf in the database
        List<PointOfSaleConf> pointOfSaleConfList = pointOfSaleConfRepository.findAll();
        assertThat(pointOfSaleConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePointOfSaleConfWithPatch() throws Exception {
        // Initialize the database
        pointOfSaleConfRepository.saveAndFlush(pointOfSaleConf);

        int databaseSizeBeforeUpdate = pointOfSaleConfRepository.findAll().size();

        // Update the pointOfSaleConf using partial update
        PointOfSaleConf partialUpdatedPointOfSaleConf = new PointOfSaleConf();
        partialUpdatedPointOfSaleConf.setId(pointOfSaleConf.getId());

        partialUpdatedPointOfSaleConf.pourcentageResponsable(UPDATED_POURCENTAGE_RESPONSABLE).createdBy(UPDATED_CREATED_BY);

        restPointOfSaleConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointOfSaleConf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPointOfSaleConf))
            )
            .andExpect(status().isOk());

        // Validate the PointOfSaleConf in the database
        List<PointOfSaleConf> pointOfSaleConfList = pointOfSaleConfRepository.findAll();
        assertThat(pointOfSaleConfList).hasSize(databaseSizeBeforeUpdate);
        PointOfSaleConf testPointOfSaleConf = pointOfSaleConfList.get(pointOfSaleConfList.size() - 1);
        assertThat(testPointOfSaleConf.getPourcentageCaissier()).isEqualTo(DEFAULT_POURCENTAGE_CAISSIER);
        assertThat(testPointOfSaleConf.getPourcentageResponsable()).isEqualTo(UPDATED_POURCENTAGE_RESPONSABLE);
        assertThat(testPointOfSaleConf.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testPointOfSaleConf.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testPointOfSaleConf.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPointOfSaleConf.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPointOfSaleConf.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPointOfSaleConf.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePointOfSaleConfWithPatch() throws Exception {
        // Initialize the database
        pointOfSaleConfRepository.saveAndFlush(pointOfSaleConf);

        int databaseSizeBeforeUpdate = pointOfSaleConfRepository.findAll().size();

        // Update the pointOfSaleConf using partial update
        PointOfSaleConf partialUpdatedPointOfSaleConf = new PointOfSaleConf();
        partialUpdatedPointOfSaleConf.setId(pointOfSaleConf.getId());

        partialUpdatedPointOfSaleConf
            .pourcentageCaissier(UPDATED_POURCENTAGE_CAISSIER)
            .pourcentageResponsable(UPDATED_POURCENTAGE_RESPONSABLE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restPointOfSaleConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointOfSaleConf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPointOfSaleConf))
            )
            .andExpect(status().isOk());

        // Validate the PointOfSaleConf in the database
        List<PointOfSaleConf> pointOfSaleConfList = pointOfSaleConfRepository.findAll();
        assertThat(pointOfSaleConfList).hasSize(databaseSizeBeforeUpdate);
        PointOfSaleConf testPointOfSaleConf = pointOfSaleConfList.get(pointOfSaleConfList.size() - 1);
        assertThat(testPointOfSaleConf.getPourcentageCaissier()).isEqualTo(UPDATED_POURCENTAGE_CAISSIER);
        assertThat(testPointOfSaleConf.getPourcentageResponsable()).isEqualTo(UPDATED_POURCENTAGE_RESPONSABLE);
        assertThat(testPointOfSaleConf.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testPointOfSaleConf.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testPointOfSaleConf.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPointOfSaleConf.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPointOfSaleConf.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPointOfSaleConf.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPointOfSaleConf() throws Exception {
        int databaseSizeBeforeUpdate = pointOfSaleConfRepository.findAll().size();
        pointOfSaleConf.setId(count.incrementAndGet());

        // Create the PointOfSaleConf
        PointOfSaleConfDTO pointOfSaleConfDTO = pointOfSaleConfMapper.toDto(pointOfSaleConf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointOfSaleConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pointOfSaleConfDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfSaleConf in the database
        List<PointOfSaleConf> pointOfSaleConfList = pointOfSaleConfRepository.findAll();
        assertThat(pointOfSaleConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPointOfSaleConf() throws Exception {
        int databaseSizeBeforeUpdate = pointOfSaleConfRepository.findAll().size();
        pointOfSaleConf.setId(count.incrementAndGet());

        // Create the PointOfSaleConf
        PointOfSaleConfDTO pointOfSaleConfDTO = pointOfSaleConfMapper.toDto(pointOfSaleConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointOfSaleConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfSaleConf in the database
        List<PointOfSaleConf> pointOfSaleConfList = pointOfSaleConfRepository.findAll();
        assertThat(pointOfSaleConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPointOfSaleConf() throws Exception {
        int databaseSizeBeforeUpdate = pointOfSaleConfRepository.findAll().size();
        pointOfSaleConf.setId(count.incrementAndGet());

        // Create the PointOfSaleConf
        PointOfSaleConfDTO pointOfSaleConfDTO = pointOfSaleConfMapper.toDto(pointOfSaleConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointOfSaleConfMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleConfDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointOfSaleConf in the database
        List<PointOfSaleConf> pointOfSaleConfList = pointOfSaleConfRepository.findAll();
        assertThat(pointOfSaleConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePointOfSaleConf() throws Exception {
        // Initialize the database
        pointOfSaleConfRepository.saveAndFlush(pointOfSaleConf);

        int databaseSizeBeforeDelete = pointOfSaleConfRepository.findAll().size();

        // Delete the pointOfSaleConf
        restPointOfSaleConfMockMvc
            .perform(delete(ENTITY_API_URL_ID, pointOfSaleConf.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PointOfSaleConf> pointOfSaleConfList = pointOfSaleConfRepository.findAll();
        assertThat(pointOfSaleConfList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
