package com.seventh.lotobig.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.LimitConfPoint;
import com.seventh.lotobig.domain.enumeration.TypeOption;
import com.seventh.lotobig.repository.LimitConfPointRepository;
import com.seventh.lotobig.service.dto.LimitConfPointDTO;
import com.seventh.lotobig.service.mapper.LimitConfPointMapper;
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
 * Integration tests for the {@link LimitConfPointResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LimitConfPointResourceIT {

    private static final String DEFAULT_NOMBRE_VALUE = "AAAAA";
    private static final String UPDATED_NOMBRE_VALUE = "BBBBB";

    private static final Long DEFAULT_LIMIT = 1L;
    private static final Long UPDATED_LIMIT = 2L;

    private static final TypeOption DEFAULT_LIMIT_STATUT = TypeOption.MARIAGE;
    private static final TypeOption UPDATED_LIMIT_STATUT = TypeOption.LOTO3CHIF;

    private static final String ENTITY_API_URL = "/api/limit-conf-points";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LimitConfPointRepository limitConfPointRepository;

    @Autowired
    private LimitConfPointMapper limitConfPointMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLimitConfPointMockMvc;

    private LimitConfPoint limitConfPoint;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LimitConfPoint createEntity(EntityManager em) {
        LimitConfPoint limitConfPoint = new LimitConfPoint()
            .nombreValue(DEFAULT_NOMBRE_VALUE)
            .limit(DEFAULT_LIMIT)
            .limitStatut(DEFAULT_LIMIT_STATUT);
        return limitConfPoint;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LimitConfPoint createUpdatedEntity(EntityManager em) {
        LimitConfPoint limitConfPoint = new LimitConfPoint()
            .nombreValue(UPDATED_NOMBRE_VALUE)
            .limit(UPDATED_LIMIT)
            .limitStatut(UPDATED_LIMIT_STATUT);
        return limitConfPoint;
    }

    @BeforeEach
    public void initTest() {
        limitConfPoint = createEntity(em);
    }

    @Test
    @Transactional
    void createLimitConfPoint() throws Exception {
        int databaseSizeBeforeCreate = limitConfPointRepository.findAll().size();
        // Create the LimitConfPoint
        LimitConfPointDTO limitConfPointDTO = limitConfPointMapper.toDto(limitConfPoint);
        restLimitConfPointMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(limitConfPointDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LimitConfPoint in the database
        List<LimitConfPoint> limitConfPointList = limitConfPointRepository.findAll();
        assertThat(limitConfPointList).hasSize(databaseSizeBeforeCreate + 1);
        LimitConfPoint testLimitConfPoint = limitConfPointList.get(limitConfPointList.size() - 1);
        assertThat(testLimitConfPoint.getNombreValue()).isEqualTo(DEFAULT_NOMBRE_VALUE);
        assertThat(testLimitConfPoint.getLimit()).isEqualTo(DEFAULT_LIMIT);
        assertThat(testLimitConfPoint.getLimitStatut()).isEqualTo(DEFAULT_LIMIT_STATUT);
    }

    @Test
    @Transactional
    void createLimitConfPointWithExistingId() throws Exception {
        // Create the LimitConfPoint with an existing ID
        limitConfPoint.setId(1L);
        LimitConfPointDTO limitConfPointDTO = limitConfPointMapper.toDto(limitConfPoint);

        int databaseSizeBeforeCreate = limitConfPointRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLimitConfPointMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(limitConfPointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LimitConfPoint in the database
        List<LimitConfPoint> limitConfPointList = limitConfPointRepository.findAll();
        assertThat(limitConfPointList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLimitIsRequired() throws Exception {
        int databaseSizeBeforeTest = limitConfPointRepository.findAll().size();
        // set the field null
        limitConfPoint.setLimit(null);

        // Create the LimitConfPoint, which fails.
        LimitConfPointDTO limitConfPointDTO = limitConfPointMapper.toDto(limitConfPoint);

        restLimitConfPointMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(limitConfPointDTO))
            )
            .andExpect(status().isBadRequest());

        List<LimitConfPoint> limitConfPointList = limitConfPointRepository.findAll();
        assertThat(limitConfPointList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLimitConfPoints() throws Exception {
        // Initialize the database
        limitConfPointRepository.saveAndFlush(limitConfPoint);

        // Get all the limitConfPointList
        restLimitConfPointMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(limitConfPoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreValue").value(hasItem(DEFAULT_NOMBRE_VALUE)))
            .andExpect(jsonPath("$.[*].limit").value(hasItem(DEFAULT_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].limitStatut").value(hasItem(DEFAULT_LIMIT_STATUT.toString())));
    }

    @Test
    @Transactional
    void getLimitConfPoint() throws Exception {
        // Initialize the database
        limitConfPointRepository.saveAndFlush(limitConfPoint);

        // Get the limitConfPoint
        restLimitConfPointMockMvc
            .perform(get(ENTITY_API_URL_ID, limitConfPoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(limitConfPoint.getId().intValue()))
            .andExpect(jsonPath("$.nombreValue").value(DEFAULT_NOMBRE_VALUE))
            .andExpect(jsonPath("$.limit").value(DEFAULT_LIMIT.intValue()))
            .andExpect(jsonPath("$.limitStatut").value(DEFAULT_LIMIT_STATUT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLimitConfPoint() throws Exception {
        // Get the limitConfPoint
        restLimitConfPointMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLimitConfPoint() throws Exception {
        // Initialize the database
        limitConfPointRepository.saveAndFlush(limitConfPoint);

        int databaseSizeBeforeUpdate = limitConfPointRepository.findAll().size();

        // Update the limitConfPoint
        LimitConfPoint updatedLimitConfPoint = limitConfPointRepository.findById(limitConfPoint.getId()).get();
        // Disconnect from session so that the updates on updatedLimitConfPoint are not directly saved in db
        em.detach(updatedLimitConfPoint);
        updatedLimitConfPoint.nombreValue(UPDATED_NOMBRE_VALUE).limit(UPDATED_LIMIT).limitStatut(UPDATED_LIMIT_STATUT);
        LimitConfPointDTO limitConfPointDTO = limitConfPointMapper.toDto(updatedLimitConfPoint);

        restLimitConfPointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, limitConfPointDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(limitConfPointDTO))
            )
            .andExpect(status().isOk());

        // Validate the LimitConfPoint in the database
        List<LimitConfPoint> limitConfPointList = limitConfPointRepository.findAll();
        assertThat(limitConfPointList).hasSize(databaseSizeBeforeUpdate);
        LimitConfPoint testLimitConfPoint = limitConfPointList.get(limitConfPointList.size() - 1);
        assertThat(testLimitConfPoint.getNombreValue()).isEqualTo(UPDATED_NOMBRE_VALUE);
        assertThat(testLimitConfPoint.getLimit()).isEqualTo(UPDATED_LIMIT);
        assertThat(testLimitConfPoint.getLimitStatut()).isEqualTo(UPDATED_LIMIT_STATUT);
    }

    @Test
    @Transactional
    void putNonExistingLimitConfPoint() throws Exception {
        int databaseSizeBeforeUpdate = limitConfPointRepository.findAll().size();
        limitConfPoint.setId(count.incrementAndGet());

        // Create the LimitConfPoint
        LimitConfPointDTO limitConfPointDTO = limitConfPointMapper.toDto(limitConfPoint);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLimitConfPointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, limitConfPointDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(limitConfPointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LimitConfPoint in the database
        List<LimitConfPoint> limitConfPointList = limitConfPointRepository.findAll();
        assertThat(limitConfPointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLimitConfPoint() throws Exception {
        int databaseSizeBeforeUpdate = limitConfPointRepository.findAll().size();
        limitConfPoint.setId(count.incrementAndGet());

        // Create the LimitConfPoint
        LimitConfPointDTO limitConfPointDTO = limitConfPointMapper.toDto(limitConfPoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLimitConfPointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(limitConfPointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LimitConfPoint in the database
        List<LimitConfPoint> limitConfPointList = limitConfPointRepository.findAll();
        assertThat(limitConfPointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLimitConfPoint() throws Exception {
        int databaseSizeBeforeUpdate = limitConfPointRepository.findAll().size();
        limitConfPoint.setId(count.incrementAndGet());

        // Create the LimitConfPoint
        LimitConfPointDTO limitConfPointDTO = limitConfPointMapper.toDto(limitConfPoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLimitConfPointMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(limitConfPointDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LimitConfPoint in the database
        List<LimitConfPoint> limitConfPointList = limitConfPointRepository.findAll();
        assertThat(limitConfPointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLimitConfPointWithPatch() throws Exception {
        // Initialize the database
        limitConfPointRepository.saveAndFlush(limitConfPoint);

        int databaseSizeBeforeUpdate = limitConfPointRepository.findAll().size();

        // Update the limitConfPoint using partial update
        LimitConfPoint partialUpdatedLimitConfPoint = new LimitConfPoint();
        partialUpdatedLimitConfPoint.setId(limitConfPoint.getId());

        partialUpdatedLimitConfPoint.nombreValue(UPDATED_NOMBRE_VALUE);

        restLimitConfPointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLimitConfPoint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLimitConfPoint))
            )
            .andExpect(status().isOk());

        // Validate the LimitConfPoint in the database
        List<LimitConfPoint> limitConfPointList = limitConfPointRepository.findAll();
        assertThat(limitConfPointList).hasSize(databaseSizeBeforeUpdate);
        LimitConfPoint testLimitConfPoint = limitConfPointList.get(limitConfPointList.size() - 1);
        assertThat(testLimitConfPoint.getNombreValue()).isEqualTo(UPDATED_NOMBRE_VALUE);
        assertThat(testLimitConfPoint.getLimit()).isEqualTo(DEFAULT_LIMIT);
        assertThat(testLimitConfPoint.getLimitStatut()).isEqualTo(DEFAULT_LIMIT_STATUT);
    }

    @Test
    @Transactional
    void fullUpdateLimitConfPointWithPatch() throws Exception {
        // Initialize the database
        limitConfPointRepository.saveAndFlush(limitConfPoint);

        int databaseSizeBeforeUpdate = limitConfPointRepository.findAll().size();

        // Update the limitConfPoint using partial update
        LimitConfPoint partialUpdatedLimitConfPoint = new LimitConfPoint();
        partialUpdatedLimitConfPoint.setId(limitConfPoint.getId());

        partialUpdatedLimitConfPoint.nombreValue(UPDATED_NOMBRE_VALUE).limit(UPDATED_LIMIT).limitStatut(UPDATED_LIMIT_STATUT);

        restLimitConfPointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLimitConfPoint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLimitConfPoint))
            )
            .andExpect(status().isOk());

        // Validate the LimitConfPoint in the database
        List<LimitConfPoint> limitConfPointList = limitConfPointRepository.findAll();
        assertThat(limitConfPointList).hasSize(databaseSizeBeforeUpdate);
        LimitConfPoint testLimitConfPoint = limitConfPointList.get(limitConfPointList.size() - 1);
        assertThat(testLimitConfPoint.getNombreValue()).isEqualTo(UPDATED_NOMBRE_VALUE);
        assertThat(testLimitConfPoint.getLimit()).isEqualTo(UPDATED_LIMIT);
        assertThat(testLimitConfPoint.getLimitStatut()).isEqualTo(UPDATED_LIMIT_STATUT);
    }

    @Test
    @Transactional
    void patchNonExistingLimitConfPoint() throws Exception {
        int databaseSizeBeforeUpdate = limitConfPointRepository.findAll().size();
        limitConfPoint.setId(count.incrementAndGet());

        // Create the LimitConfPoint
        LimitConfPointDTO limitConfPointDTO = limitConfPointMapper.toDto(limitConfPoint);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLimitConfPointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, limitConfPointDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(limitConfPointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LimitConfPoint in the database
        List<LimitConfPoint> limitConfPointList = limitConfPointRepository.findAll();
        assertThat(limitConfPointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLimitConfPoint() throws Exception {
        int databaseSizeBeforeUpdate = limitConfPointRepository.findAll().size();
        limitConfPoint.setId(count.incrementAndGet());

        // Create the LimitConfPoint
        LimitConfPointDTO limitConfPointDTO = limitConfPointMapper.toDto(limitConfPoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLimitConfPointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(limitConfPointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LimitConfPoint in the database
        List<LimitConfPoint> limitConfPointList = limitConfPointRepository.findAll();
        assertThat(limitConfPointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLimitConfPoint() throws Exception {
        int databaseSizeBeforeUpdate = limitConfPointRepository.findAll().size();
        limitConfPoint.setId(count.incrementAndGet());

        // Create the LimitConfPoint
        LimitConfPointDTO limitConfPointDTO = limitConfPointMapper.toDto(limitConfPoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLimitConfPointMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(limitConfPointDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LimitConfPoint in the database
        List<LimitConfPoint> limitConfPointList = limitConfPointRepository.findAll();
        assertThat(limitConfPointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLimitConfPoint() throws Exception {
        // Initialize the database
        limitConfPointRepository.saveAndFlush(limitConfPoint);

        int databaseSizeBeforeDelete = limitConfPointRepository.findAll().size();

        // Delete the limitConfPoint
        restLimitConfPointMockMvc
            .perform(delete(ENTITY_API_URL_ID, limitConfPoint.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LimitConfPoint> limitConfPointList = limitConfPointRepository.findAll();
        assertThat(limitConfPointList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
