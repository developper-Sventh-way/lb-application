package com.seventh.lotobig.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.PointOfSale;
import com.seventh.lotobig.domain.enumeration.StatutBanque;
import com.seventh.lotobig.repository.PointOfSaleRepository;
import com.seventh.lotobig.service.dto.PointOfSaleDTO;
import com.seventh.lotobig.service.mapper.PointOfSaleMapper;
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
 * Integration tests for the {@link PointOfSaleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PointOfSaleResourceIT {

    private static final String DEFAULT_ADRESSE_POINT = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_POINT = "BBBBBBBBBB";

    private static final StatutBanque DEFAULT_STATUT = StatutBanque.OPEN;
    private static final StatutBanque UPDATED_STATUT = StatutBanque.CLOSE;

    private static final String DEFAULT_PHONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_2 = "BBBBBBBBBB";

    private static final Integer DEFAULT_POURCENTAGE_POINT = 1;
    private static final Integer UPDATED_POURCENTAGE_POINT = 2;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/point-of-sales";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PointOfSaleRepository pointOfSaleRepository;

    @Autowired
    private PointOfSaleMapper pointOfSaleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPointOfSaleMockMvc;

    private PointOfSale pointOfSale;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointOfSale createEntity(EntityManager em) {
        PointOfSale pointOfSale = new PointOfSale()
            .adressePoint(DEFAULT_ADRESSE_POINT)
            .statut(DEFAULT_STATUT)
            .phone1(DEFAULT_PHONE_1)
            .phone2(DEFAULT_PHONE_2)
            .pourcentagePoint(DEFAULT_POURCENTAGE_POINT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return pointOfSale;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointOfSale createUpdatedEntity(EntityManager em) {
        PointOfSale pointOfSale = new PointOfSale()
            .adressePoint(UPDATED_ADRESSE_POINT)
            .statut(UPDATED_STATUT)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .pourcentagePoint(UPDATED_POURCENTAGE_POINT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return pointOfSale;
    }

    @BeforeEach
    public void initTest() {
        pointOfSale = createEntity(em);
    }

    @Test
    @Transactional
    void createPointOfSale() throws Exception {
        int databaseSizeBeforeCreate = pointOfSaleRepository.findAll().size();
        // Create the PointOfSale
        PointOfSaleDTO pointOfSaleDTO = pointOfSaleMapper.toDto(pointOfSale);
        restPointOfSaleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointOfSaleDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PointOfSale in the database
        List<PointOfSale> pointOfSaleList = pointOfSaleRepository.findAll();
        assertThat(pointOfSaleList).hasSize(databaseSizeBeforeCreate + 1);
        PointOfSale testPointOfSale = pointOfSaleList.get(pointOfSaleList.size() - 1);
        assertThat(testPointOfSale.getAdressePoint()).isEqualTo(DEFAULT_ADRESSE_POINT);
        assertThat(testPointOfSale.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testPointOfSale.getPhone1()).isEqualTo(DEFAULT_PHONE_1);
        assertThat(testPointOfSale.getPhone2()).isEqualTo(DEFAULT_PHONE_2);
        assertThat(testPointOfSale.getPourcentagePoint()).isEqualTo(DEFAULT_POURCENTAGE_POINT);
        assertThat(testPointOfSale.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPointOfSale.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPointOfSale.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPointOfSale.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createPointOfSaleWithExistingId() throws Exception {
        // Create the PointOfSale with an existing ID
        pointOfSale.setId(1L);
        PointOfSaleDTO pointOfSaleDTO = pointOfSaleMapper.toDto(pointOfSale);

        int databaseSizeBeforeCreate = pointOfSaleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPointOfSaleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointOfSaleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfSale in the database
        List<PointOfSale> pointOfSaleList = pointOfSaleRepository.findAll();
        assertThat(pointOfSaleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = pointOfSaleRepository.findAll().size();
        // set the field null
        pointOfSale.setStatut(null);

        // Create the PointOfSale, which fails.
        PointOfSaleDTO pointOfSaleDTO = pointOfSaleMapper.toDto(pointOfSale);

        restPointOfSaleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointOfSaleDTO))
            )
            .andExpect(status().isBadRequest());

        List<PointOfSale> pointOfSaleList = pointOfSaleRepository.findAll();
        assertThat(pointOfSaleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPourcentagePointIsRequired() throws Exception {
        int databaseSizeBeforeTest = pointOfSaleRepository.findAll().size();
        // set the field null
        pointOfSale.setPourcentagePoint(null);

        // Create the PointOfSale, which fails.
        PointOfSaleDTO pointOfSaleDTO = pointOfSaleMapper.toDto(pointOfSale);

        restPointOfSaleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointOfSaleDTO))
            )
            .andExpect(status().isBadRequest());

        List<PointOfSale> pointOfSaleList = pointOfSaleRepository.findAll();
        assertThat(pointOfSaleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = pointOfSaleRepository.findAll().size();
        // set the field null
        pointOfSale.setCreatedBy(null);

        // Create the PointOfSale, which fails.
        PointOfSaleDTO pointOfSaleDTO = pointOfSaleMapper.toDto(pointOfSale);

        restPointOfSaleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointOfSaleDTO))
            )
            .andExpect(status().isBadRequest());

        List<PointOfSale> pointOfSaleList = pointOfSaleRepository.findAll();
        assertThat(pointOfSaleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pointOfSaleRepository.findAll().size();
        // set the field null
        pointOfSale.setCreatedDate(null);

        // Create the PointOfSale, which fails.
        PointOfSaleDTO pointOfSaleDTO = pointOfSaleMapper.toDto(pointOfSale);

        restPointOfSaleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointOfSaleDTO))
            )
            .andExpect(status().isBadRequest());

        List<PointOfSale> pointOfSaleList = pointOfSaleRepository.findAll();
        assertThat(pointOfSaleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPointOfSales() throws Exception {
        // Initialize the database
        pointOfSaleRepository.saveAndFlush(pointOfSale);

        // Get all the pointOfSaleList
        restPointOfSaleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pointOfSale.getId().intValue())))
            .andExpect(jsonPath("$.[*].adressePoint").value(hasItem(DEFAULT_ADRESSE_POINT)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1)))
            .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2)))
            .andExpect(jsonPath("$.[*].pourcentagePoint").value(hasItem(DEFAULT_POURCENTAGE_POINT)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getPointOfSale() throws Exception {
        // Initialize the database
        pointOfSaleRepository.saveAndFlush(pointOfSale);

        // Get the pointOfSale
        restPointOfSaleMockMvc
            .perform(get(ENTITY_API_URL_ID, pointOfSale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pointOfSale.getId().intValue()))
            .andExpect(jsonPath("$.adressePoint").value(DEFAULT_ADRESSE_POINT))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.phone1").value(DEFAULT_PHONE_1))
            .andExpect(jsonPath("$.phone2").value(DEFAULT_PHONE_2))
            .andExpect(jsonPath("$.pourcentagePoint").value(DEFAULT_POURCENTAGE_POINT))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPointOfSale() throws Exception {
        // Get the pointOfSale
        restPointOfSaleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPointOfSale() throws Exception {
        // Initialize the database
        pointOfSaleRepository.saveAndFlush(pointOfSale);

        int databaseSizeBeforeUpdate = pointOfSaleRepository.findAll().size();

        // Update the pointOfSale
        PointOfSale updatedPointOfSale = pointOfSaleRepository.findById(pointOfSale.getId()).get();
        // Disconnect from session so that the updates on updatedPointOfSale are not directly saved in db
        em.detach(updatedPointOfSale);
        updatedPointOfSale
            .adressePoint(UPDATED_ADRESSE_POINT)
            .statut(UPDATED_STATUT)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .pourcentagePoint(UPDATED_POURCENTAGE_POINT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        PointOfSaleDTO pointOfSaleDTO = pointOfSaleMapper.toDto(updatedPointOfSale);

        restPointOfSaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointOfSaleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleDTO))
            )
            .andExpect(status().isOk());

        // Validate the PointOfSale in the database
        List<PointOfSale> pointOfSaleList = pointOfSaleRepository.findAll();
        assertThat(pointOfSaleList).hasSize(databaseSizeBeforeUpdate);
        PointOfSale testPointOfSale = pointOfSaleList.get(pointOfSaleList.size() - 1);
        assertThat(testPointOfSale.getAdressePoint()).isEqualTo(UPDATED_ADRESSE_POINT);
        assertThat(testPointOfSale.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testPointOfSale.getPhone1()).isEqualTo(UPDATED_PHONE_1);
        assertThat(testPointOfSale.getPhone2()).isEqualTo(UPDATED_PHONE_2);
        assertThat(testPointOfSale.getPourcentagePoint()).isEqualTo(UPDATED_POURCENTAGE_POINT);
        assertThat(testPointOfSale.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPointOfSale.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPointOfSale.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPointOfSale.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPointOfSale() throws Exception {
        int databaseSizeBeforeUpdate = pointOfSaleRepository.findAll().size();
        pointOfSale.setId(count.incrementAndGet());

        // Create the PointOfSale
        PointOfSaleDTO pointOfSaleDTO = pointOfSaleMapper.toDto(pointOfSale);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointOfSaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointOfSaleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfSale in the database
        List<PointOfSale> pointOfSaleList = pointOfSaleRepository.findAll();
        assertThat(pointOfSaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPointOfSale() throws Exception {
        int databaseSizeBeforeUpdate = pointOfSaleRepository.findAll().size();
        pointOfSale.setId(count.incrementAndGet());

        // Create the PointOfSale
        PointOfSaleDTO pointOfSaleDTO = pointOfSaleMapper.toDto(pointOfSale);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointOfSaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfSale in the database
        List<PointOfSale> pointOfSaleList = pointOfSaleRepository.findAll();
        assertThat(pointOfSaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPointOfSale() throws Exception {
        int databaseSizeBeforeUpdate = pointOfSaleRepository.findAll().size();
        pointOfSale.setId(count.incrementAndGet());

        // Create the PointOfSale
        PointOfSaleDTO pointOfSaleDTO = pointOfSaleMapper.toDto(pointOfSale);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointOfSaleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointOfSaleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointOfSale in the database
        List<PointOfSale> pointOfSaleList = pointOfSaleRepository.findAll();
        assertThat(pointOfSaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePointOfSaleWithPatch() throws Exception {
        // Initialize the database
        pointOfSaleRepository.saveAndFlush(pointOfSale);

        int databaseSizeBeforeUpdate = pointOfSaleRepository.findAll().size();

        // Update the pointOfSale using partial update
        PointOfSale partialUpdatedPointOfSale = new PointOfSale();
        partialUpdatedPointOfSale.setId(pointOfSale.getId());

        partialUpdatedPointOfSale
            .statut(UPDATED_STATUT)
            .phone2(UPDATED_PHONE_2)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restPointOfSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointOfSale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPointOfSale))
            )
            .andExpect(status().isOk());

        // Validate the PointOfSale in the database
        List<PointOfSale> pointOfSaleList = pointOfSaleRepository.findAll();
        assertThat(pointOfSaleList).hasSize(databaseSizeBeforeUpdate);
        PointOfSale testPointOfSale = pointOfSaleList.get(pointOfSaleList.size() - 1);
        assertThat(testPointOfSale.getAdressePoint()).isEqualTo(DEFAULT_ADRESSE_POINT);
        assertThat(testPointOfSale.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testPointOfSale.getPhone1()).isEqualTo(DEFAULT_PHONE_1);
        assertThat(testPointOfSale.getPhone2()).isEqualTo(UPDATED_PHONE_2);
        assertThat(testPointOfSale.getPourcentagePoint()).isEqualTo(DEFAULT_POURCENTAGE_POINT);
        assertThat(testPointOfSale.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPointOfSale.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPointOfSale.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPointOfSale.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePointOfSaleWithPatch() throws Exception {
        // Initialize the database
        pointOfSaleRepository.saveAndFlush(pointOfSale);

        int databaseSizeBeforeUpdate = pointOfSaleRepository.findAll().size();

        // Update the pointOfSale using partial update
        PointOfSale partialUpdatedPointOfSale = new PointOfSale();
        partialUpdatedPointOfSale.setId(pointOfSale.getId());

        partialUpdatedPointOfSale
            .adressePoint(UPDATED_ADRESSE_POINT)
            .statut(UPDATED_STATUT)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .pourcentagePoint(UPDATED_POURCENTAGE_POINT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restPointOfSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointOfSale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPointOfSale))
            )
            .andExpect(status().isOk());

        // Validate the PointOfSale in the database
        List<PointOfSale> pointOfSaleList = pointOfSaleRepository.findAll();
        assertThat(pointOfSaleList).hasSize(databaseSizeBeforeUpdate);
        PointOfSale testPointOfSale = pointOfSaleList.get(pointOfSaleList.size() - 1);
        assertThat(testPointOfSale.getAdressePoint()).isEqualTo(UPDATED_ADRESSE_POINT);
        assertThat(testPointOfSale.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testPointOfSale.getPhone1()).isEqualTo(UPDATED_PHONE_1);
        assertThat(testPointOfSale.getPhone2()).isEqualTo(UPDATED_PHONE_2);
        assertThat(testPointOfSale.getPourcentagePoint()).isEqualTo(UPDATED_POURCENTAGE_POINT);
        assertThat(testPointOfSale.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPointOfSale.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPointOfSale.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPointOfSale.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPointOfSale() throws Exception {
        int databaseSizeBeforeUpdate = pointOfSaleRepository.findAll().size();
        pointOfSale.setId(count.incrementAndGet());

        // Create the PointOfSale
        PointOfSaleDTO pointOfSaleDTO = pointOfSaleMapper.toDto(pointOfSale);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointOfSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pointOfSaleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfSale in the database
        List<PointOfSale> pointOfSaleList = pointOfSaleRepository.findAll();
        assertThat(pointOfSaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPointOfSale() throws Exception {
        int databaseSizeBeforeUpdate = pointOfSaleRepository.findAll().size();
        pointOfSale.setId(count.incrementAndGet());

        // Create the PointOfSale
        PointOfSaleDTO pointOfSaleDTO = pointOfSaleMapper.toDto(pointOfSale);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointOfSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointOfSaleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfSale in the database
        List<PointOfSale> pointOfSaleList = pointOfSaleRepository.findAll();
        assertThat(pointOfSaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPointOfSale() throws Exception {
        int databaseSizeBeforeUpdate = pointOfSaleRepository.findAll().size();
        pointOfSale.setId(count.incrementAndGet());

        // Create the PointOfSale
        PointOfSaleDTO pointOfSaleDTO = pointOfSaleMapper.toDto(pointOfSale);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointOfSaleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pointOfSaleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointOfSale in the database
        List<PointOfSale> pointOfSaleList = pointOfSaleRepository.findAll();
        assertThat(pointOfSaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePointOfSale() throws Exception {
        // Initialize the database
        pointOfSaleRepository.saveAndFlush(pointOfSale);

        int databaseSizeBeforeDelete = pointOfSaleRepository.findAll().size();

        // Delete the pointOfSale
        restPointOfSaleMockMvc
            .perform(delete(ENTITY_API_URL_ID, pointOfSale.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PointOfSale> pointOfSaleList = pointOfSaleRepository.findAll();
        assertThat(pointOfSaleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
