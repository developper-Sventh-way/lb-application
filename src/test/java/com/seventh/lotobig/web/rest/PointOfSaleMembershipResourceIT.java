package com.seventh.lotobig.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.PointOfSaleMembership;
import com.seventh.lotobig.domain.enumeration.TypeBanque;
import com.seventh.lotobig.repository.PointOfSaleMembershipRepository;
import com.seventh.lotobig.service.dto.PointOfSaleMembershipDTO;
import com.seventh.lotobig.service.mapper.PointOfSaleMembershipMapper;
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
 * Integration tests for the {@link PointOfSaleMembershipResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PointOfSaleMembershipResourceIT {

    private static final TypeBanque DEFAULT_TYPE_POINT = TypeBanque.INTERNE;
    private static final TypeBanque UPDATED_TYPE_POINT = TypeBanque.PARTENARIAT;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/point-of-sale-memberships";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PointOfSaleMembershipRepository pointOfSaleMembershipRepository;

    @Autowired
    private PointOfSaleMembershipMapper pointOfSaleMembershipMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPointOfSaleMembershipMockMvc;

    private PointOfSaleMembership pointOfSaleMembership;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointOfSaleMembership createEntity(EntityManager em) {
        PointOfSaleMembership pointOfSaleMembership = new PointOfSaleMembership()
            .typePoint(DEFAULT_TYPE_POINT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return pointOfSaleMembership;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointOfSaleMembership createUpdatedEntity(EntityManager em) {
        PointOfSaleMembership pointOfSaleMembership = new PointOfSaleMembership()
            .typePoint(UPDATED_TYPE_POINT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return pointOfSaleMembership;
    }

    @BeforeEach
    public void initTest() {
        pointOfSaleMembership = createEntity(em);
    }

    @Test
    @Transactional
    void createPointOfSaleMembership() throws Exception {
        int databaseSizeBeforeCreate = pointOfSaleMembershipRepository.findAll().size();
        // Create the PointOfSaleMembership
        PointOfSaleMembershipDTO pointOfSaleMembershipDTO = pointOfSaleMembershipMapper.toDto(pointOfSaleMembership);
        restPointOfSaleMembershipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleMembershipDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PointOfSaleMembership in the database
        List<PointOfSaleMembership> pointOfSaleMembershipList = pointOfSaleMembershipRepository.findAll();
        assertThat(pointOfSaleMembershipList).hasSize(databaseSizeBeforeCreate + 1);
        PointOfSaleMembership testPointOfSaleMembership = pointOfSaleMembershipList.get(pointOfSaleMembershipList.size() - 1);
        assertThat(testPointOfSaleMembership.getTypePoint()).isEqualTo(DEFAULT_TYPE_POINT);
        assertThat(testPointOfSaleMembership.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPointOfSaleMembership.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPointOfSaleMembership.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPointOfSaleMembership.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createPointOfSaleMembershipWithExistingId() throws Exception {
        // Create the PointOfSaleMembership with an existing ID
        pointOfSaleMembership.setId(1L);
        PointOfSaleMembershipDTO pointOfSaleMembershipDTO = pointOfSaleMembershipMapper.toDto(pointOfSaleMembership);

        int databaseSizeBeforeCreate = pointOfSaleMembershipRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPointOfSaleMembershipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleMembershipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfSaleMembership in the database
        List<PointOfSaleMembership> pointOfSaleMembershipList = pointOfSaleMembershipRepository.findAll();
        assertThat(pointOfSaleMembershipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypePointIsRequired() throws Exception {
        int databaseSizeBeforeTest = pointOfSaleMembershipRepository.findAll().size();
        // set the field null
        pointOfSaleMembership.setTypePoint(null);

        // Create the PointOfSaleMembership, which fails.
        PointOfSaleMembershipDTO pointOfSaleMembershipDTO = pointOfSaleMembershipMapper.toDto(pointOfSaleMembership);

        restPointOfSaleMembershipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleMembershipDTO))
            )
            .andExpect(status().isBadRequest());

        List<PointOfSaleMembership> pointOfSaleMembershipList = pointOfSaleMembershipRepository.findAll();
        assertThat(pointOfSaleMembershipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = pointOfSaleMembershipRepository.findAll().size();
        // set the field null
        pointOfSaleMembership.setCreatedBy(null);

        // Create the PointOfSaleMembership, which fails.
        PointOfSaleMembershipDTO pointOfSaleMembershipDTO = pointOfSaleMembershipMapper.toDto(pointOfSaleMembership);

        restPointOfSaleMembershipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleMembershipDTO))
            )
            .andExpect(status().isBadRequest());

        List<PointOfSaleMembership> pointOfSaleMembershipList = pointOfSaleMembershipRepository.findAll();
        assertThat(pointOfSaleMembershipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pointOfSaleMembershipRepository.findAll().size();
        // set the field null
        pointOfSaleMembership.setCreatedDate(null);

        // Create the PointOfSaleMembership, which fails.
        PointOfSaleMembershipDTO pointOfSaleMembershipDTO = pointOfSaleMembershipMapper.toDto(pointOfSaleMembership);

        restPointOfSaleMembershipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleMembershipDTO))
            )
            .andExpect(status().isBadRequest());

        List<PointOfSaleMembership> pointOfSaleMembershipList = pointOfSaleMembershipRepository.findAll();
        assertThat(pointOfSaleMembershipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPointOfSaleMemberships() throws Exception {
        // Initialize the database
        pointOfSaleMembershipRepository.saveAndFlush(pointOfSaleMembership);

        // Get all the pointOfSaleMembershipList
        restPointOfSaleMembershipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pointOfSaleMembership.getId().intValue())))
            .andExpect(jsonPath("$.[*].typePoint").value(hasItem(DEFAULT_TYPE_POINT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getPointOfSaleMembership() throws Exception {
        // Initialize the database
        pointOfSaleMembershipRepository.saveAndFlush(pointOfSaleMembership);

        // Get the pointOfSaleMembership
        restPointOfSaleMembershipMockMvc
            .perform(get(ENTITY_API_URL_ID, pointOfSaleMembership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pointOfSaleMembership.getId().intValue()))
            .andExpect(jsonPath("$.typePoint").value(DEFAULT_TYPE_POINT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPointOfSaleMembership() throws Exception {
        // Get the pointOfSaleMembership
        restPointOfSaleMembershipMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPointOfSaleMembership() throws Exception {
        // Initialize the database
        pointOfSaleMembershipRepository.saveAndFlush(pointOfSaleMembership);

        int databaseSizeBeforeUpdate = pointOfSaleMembershipRepository.findAll().size();

        // Update the pointOfSaleMembership
        PointOfSaleMembership updatedPointOfSaleMembership = pointOfSaleMembershipRepository.findById(pointOfSaleMembership.getId()).get();
        // Disconnect from session so that the updates on updatedPointOfSaleMembership are not directly saved in db
        em.detach(updatedPointOfSaleMembership);
        updatedPointOfSaleMembership
            .typePoint(UPDATED_TYPE_POINT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        PointOfSaleMembershipDTO pointOfSaleMembershipDTO = pointOfSaleMembershipMapper.toDto(updatedPointOfSaleMembership);

        restPointOfSaleMembershipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointOfSaleMembershipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleMembershipDTO))
            )
            .andExpect(status().isOk());

        // Validate the PointOfSaleMembership in the database
        List<PointOfSaleMembership> pointOfSaleMembershipList = pointOfSaleMembershipRepository.findAll();
        assertThat(pointOfSaleMembershipList).hasSize(databaseSizeBeforeUpdate);
        PointOfSaleMembership testPointOfSaleMembership = pointOfSaleMembershipList.get(pointOfSaleMembershipList.size() - 1);
        assertThat(testPointOfSaleMembership.getTypePoint()).isEqualTo(UPDATED_TYPE_POINT);
        assertThat(testPointOfSaleMembership.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPointOfSaleMembership.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPointOfSaleMembership.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPointOfSaleMembership.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPointOfSaleMembership() throws Exception {
        int databaseSizeBeforeUpdate = pointOfSaleMembershipRepository.findAll().size();
        pointOfSaleMembership.setId(count.incrementAndGet());

        // Create the PointOfSaleMembership
        PointOfSaleMembershipDTO pointOfSaleMembershipDTO = pointOfSaleMembershipMapper.toDto(pointOfSaleMembership);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointOfSaleMembershipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointOfSaleMembershipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleMembershipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfSaleMembership in the database
        List<PointOfSaleMembership> pointOfSaleMembershipList = pointOfSaleMembershipRepository.findAll();
        assertThat(pointOfSaleMembershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPointOfSaleMembership() throws Exception {
        int databaseSizeBeforeUpdate = pointOfSaleMembershipRepository.findAll().size();
        pointOfSaleMembership.setId(count.incrementAndGet());

        // Create the PointOfSaleMembership
        PointOfSaleMembershipDTO pointOfSaleMembershipDTO = pointOfSaleMembershipMapper.toDto(pointOfSaleMembership);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointOfSaleMembershipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleMembershipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfSaleMembership in the database
        List<PointOfSaleMembership> pointOfSaleMembershipList = pointOfSaleMembershipRepository.findAll();
        assertThat(pointOfSaleMembershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPointOfSaleMembership() throws Exception {
        int databaseSizeBeforeUpdate = pointOfSaleMembershipRepository.findAll().size();
        pointOfSaleMembership.setId(count.incrementAndGet());

        // Create the PointOfSaleMembership
        PointOfSaleMembershipDTO pointOfSaleMembershipDTO = pointOfSaleMembershipMapper.toDto(pointOfSaleMembership);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointOfSaleMembershipMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleMembershipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointOfSaleMembership in the database
        List<PointOfSaleMembership> pointOfSaleMembershipList = pointOfSaleMembershipRepository.findAll();
        assertThat(pointOfSaleMembershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePointOfSaleMembershipWithPatch() throws Exception {
        // Initialize the database
        pointOfSaleMembershipRepository.saveAndFlush(pointOfSaleMembership);

        int databaseSizeBeforeUpdate = pointOfSaleMembershipRepository.findAll().size();

        // Update the pointOfSaleMembership using partial update
        PointOfSaleMembership partialUpdatedPointOfSaleMembership = new PointOfSaleMembership();
        partialUpdatedPointOfSaleMembership.setId(pointOfSaleMembership.getId());

        partialUpdatedPointOfSaleMembership
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restPointOfSaleMembershipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointOfSaleMembership.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPointOfSaleMembership))
            )
            .andExpect(status().isOk());

        // Validate the PointOfSaleMembership in the database
        List<PointOfSaleMembership> pointOfSaleMembershipList = pointOfSaleMembershipRepository.findAll();
        assertThat(pointOfSaleMembershipList).hasSize(databaseSizeBeforeUpdate);
        PointOfSaleMembership testPointOfSaleMembership = pointOfSaleMembershipList.get(pointOfSaleMembershipList.size() - 1);
        assertThat(testPointOfSaleMembership.getTypePoint()).isEqualTo(DEFAULT_TYPE_POINT);
        assertThat(testPointOfSaleMembership.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPointOfSaleMembership.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPointOfSaleMembership.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPointOfSaleMembership.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePointOfSaleMembershipWithPatch() throws Exception {
        // Initialize the database
        pointOfSaleMembershipRepository.saveAndFlush(pointOfSaleMembership);

        int databaseSizeBeforeUpdate = pointOfSaleMembershipRepository.findAll().size();

        // Update the pointOfSaleMembership using partial update
        PointOfSaleMembership partialUpdatedPointOfSaleMembership = new PointOfSaleMembership();
        partialUpdatedPointOfSaleMembership.setId(pointOfSaleMembership.getId());

        partialUpdatedPointOfSaleMembership
            .typePoint(UPDATED_TYPE_POINT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restPointOfSaleMembershipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointOfSaleMembership.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPointOfSaleMembership))
            )
            .andExpect(status().isOk());

        // Validate the PointOfSaleMembership in the database
        List<PointOfSaleMembership> pointOfSaleMembershipList = pointOfSaleMembershipRepository.findAll();
        assertThat(pointOfSaleMembershipList).hasSize(databaseSizeBeforeUpdate);
        PointOfSaleMembership testPointOfSaleMembership = pointOfSaleMembershipList.get(pointOfSaleMembershipList.size() - 1);
        assertThat(testPointOfSaleMembership.getTypePoint()).isEqualTo(UPDATED_TYPE_POINT);
        assertThat(testPointOfSaleMembership.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPointOfSaleMembership.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPointOfSaleMembership.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPointOfSaleMembership.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPointOfSaleMembership() throws Exception {
        int databaseSizeBeforeUpdate = pointOfSaleMembershipRepository.findAll().size();
        pointOfSaleMembership.setId(count.incrementAndGet());

        // Create the PointOfSaleMembership
        PointOfSaleMembershipDTO pointOfSaleMembershipDTO = pointOfSaleMembershipMapper.toDto(pointOfSaleMembership);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointOfSaleMembershipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pointOfSaleMembershipDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleMembershipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfSaleMembership in the database
        List<PointOfSaleMembership> pointOfSaleMembershipList = pointOfSaleMembershipRepository.findAll();
        assertThat(pointOfSaleMembershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPointOfSaleMembership() throws Exception {
        int databaseSizeBeforeUpdate = pointOfSaleMembershipRepository.findAll().size();
        pointOfSaleMembership.setId(count.incrementAndGet());

        // Create the PointOfSaleMembership
        PointOfSaleMembershipDTO pointOfSaleMembershipDTO = pointOfSaleMembershipMapper.toDto(pointOfSaleMembership);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointOfSaleMembershipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleMembershipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfSaleMembership in the database
        List<PointOfSaleMembership> pointOfSaleMembershipList = pointOfSaleMembershipRepository.findAll();
        assertThat(pointOfSaleMembershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPointOfSaleMembership() throws Exception {
        int databaseSizeBeforeUpdate = pointOfSaleMembershipRepository.findAll().size();
        pointOfSaleMembership.setId(count.incrementAndGet());

        // Create the PointOfSaleMembership
        PointOfSaleMembershipDTO pointOfSaleMembershipDTO = pointOfSaleMembershipMapper.toDto(pointOfSaleMembership);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointOfSaleMembershipMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleMembershipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointOfSaleMembership in the database
        List<PointOfSaleMembership> pointOfSaleMembershipList = pointOfSaleMembershipRepository.findAll();
        assertThat(pointOfSaleMembershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePointOfSaleMembership() throws Exception {
        // Initialize the database
        pointOfSaleMembershipRepository.saveAndFlush(pointOfSaleMembership);

        int databaseSizeBeforeDelete = pointOfSaleMembershipRepository.findAll().size();

        // Delete the pointOfSaleMembership
        restPointOfSaleMembershipMockMvc
            .perform(delete(ENTITY_API_URL_ID, pointOfSaleMembership.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PointOfSaleMembership> pointOfSaleMembershipList = pointOfSaleMembershipRepository.findAll();
        assertThat(pointOfSaleMembershipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
