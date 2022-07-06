package com.seventh.lotobig.web.rest;

import static com.seventh.lotobig.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.CouponGratuitConf;
import com.seventh.lotobig.domain.enumeration.TypeOption;
import com.seventh.lotobig.domain.enumeration.UserStatut;
import com.seventh.lotobig.repository.CouponGratuitConfRepository;
import com.seventh.lotobig.service.dto.CouponGratuitConfDTO;
import com.seventh.lotobig.service.mapper.CouponGratuitConfMapper;
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
 * Integration tests for the {@link CouponGratuitConfResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CouponGratuitConfResourceIT {

    private static final TypeOption DEFAULT_TYPE_OPTION = TypeOption.MARIAGE;
    private static final TypeOption UPDATED_TYPE_OPTION = TypeOption.LOTO3CHIF;

    private static final Long DEFAULT_MAXIMUM_COUNT = 1L;
    private static final Long UPDATED_MAXIMUM_COUNT = 2L;

    private static final BigDecimal DEFAULT_OBSTINATE_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_OBSTINATE_AMOUNT = new BigDecimal(2);

    private static final UserStatut DEFAULT_STATUT = UserStatut.ACTIVE;
    private static final UserStatut UPDATED_STATUT = UserStatut.BLOQUE;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/coupon-gratuit-confs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CouponGratuitConfRepository couponGratuitConfRepository;

    @Autowired
    private CouponGratuitConfMapper couponGratuitConfMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCouponGratuitConfMockMvc;

    private CouponGratuitConf couponGratuitConf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CouponGratuitConf createEntity(EntityManager em) {
        CouponGratuitConf couponGratuitConf = new CouponGratuitConf()
            .typeOption(DEFAULT_TYPE_OPTION)
            .maximumCount(DEFAULT_MAXIMUM_COUNT)
            .obstinateAmount(DEFAULT_OBSTINATE_AMOUNT)
            .statut(DEFAULT_STATUT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return couponGratuitConf;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CouponGratuitConf createUpdatedEntity(EntityManager em) {
        CouponGratuitConf couponGratuitConf = new CouponGratuitConf()
            .typeOption(UPDATED_TYPE_OPTION)
            .maximumCount(UPDATED_MAXIMUM_COUNT)
            .obstinateAmount(UPDATED_OBSTINATE_AMOUNT)
            .statut(UPDATED_STATUT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return couponGratuitConf;
    }

    @BeforeEach
    public void initTest() {
        couponGratuitConf = createEntity(em);
    }

    @Test
    @Transactional
    void createCouponGratuitConf() throws Exception {
        int databaseSizeBeforeCreate = couponGratuitConfRepository.findAll().size();
        // Create the CouponGratuitConf
        CouponGratuitConfDTO couponGratuitConfDTO = couponGratuitConfMapper.toDto(couponGratuitConf);
        restCouponGratuitConfMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(couponGratuitConfDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CouponGratuitConf in the database
        List<CouponGratuitConf> couponGratuitConfList = couponGratuitConfRepository.findAll();
        assertThat(couponGratuitConfList).hasSize(databaseSizeBeforeCreate + 1);
        CouponGratuitConf testCouponGratuitConf = couponGratuitConfList.get(couponGratuitConfList.size() - 1);
        assertThat(testCouponGratuitConf.getTypeOption()).isEqualTo(DEFAULT_TYPE_OPTION);
        assertThat(testCouponGratuitConf.getMaximumCount()).isEqualTo(DEFAULT_MAXIMUM_COUNT);
        assertThat(testCouponGratuitConf.getObstinateAmount()).isEqualByComparingTo(DEFAULT_OBSTINATE_AMOUNT);
        assertThat(testCouponGratuitConf.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testCouponGratuitConf.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCouponGratuitConf.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCouponGratuitConf.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testCouponGratuitConf.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createCouponGratuitConfWithExistingId() throws Exception {
        // Create the CouponGratuitConf with an existing ID
        couponGratuitConf.setId(1L);
        CouponGratuitConfDTO couponGratuitConfDTO = couponGratuitConfMapper.toDto(couponGratuitConf);

        int databaseSizeBeforeCreate = couponGratuitConfRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCouponGratuitConfMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(couponGratuitConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CouponGratuitConf in the database
        List<CouponGratuitConf> couponGratuitConfList = couponGratuitConfRepository.findAll();
        assertThat(couponGratuitConfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeOptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = couponGratuitConfRepository.findAll().size();
        // set the field null
        couponGratuitConf.setTypeOption(null);

        // Create the CouponGratuitConf, which fails.
        CouponGratuitConfDTO couponGratuitConfDTO = couponGratuitConfMapper.toDto(couponGratuitConf);

        restCouponGratuitConfMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(couponGratuitConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<CouponGratuitConf> couponGratuitConfList = couponGratuitConfRepository.findAll();
        assertThat(couponGratuitConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaximumCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = couponGratuitConfRepository.findAll().size();
        // set the field null
        couponGratuitConf.setMaximumCount(null);

        // Create the CouponGratuitConf, which fails.
        CouponGratuitConfDTO couponGratuitConfDTO = couponGratuitConfMapper.toDto(couponGratuitConf);

        restCouponGratuitConfMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(couponGratuitConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<CouponGratuitConf> couponGratuitConfList = couponGratuitConfRepository.findAll();
        assertThat(couponGratuitConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkObstinateAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = couponGratuitConfRepository.findAll().size();
        // set the field null
        couponGratuitConf.setObstinateAmount(null);

        // Create the CouponGratuitConf, which fails.
        CouponGratuitConfDTO couponGratuitConfDTO = couponGratuitConfMapper.toDto(couponGratuitConf);

        restCouponGratuitConfMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(couponGratuitConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<CouponGratuitConf> couponGratuitConfList = couponGratuitConfRepository.findAll();
        assertThat(couponGratuitConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = couponGratuitConfRepository.findAll().size();
        // set the field null
        couponGratuitConf.setStatut(null);

        // Create the CouponGratuitConf, which fails.
        CouponGratuitConfDTO couponGratuitConfDTO = couponGratuitConfMapper.toDto(couponGratuitConf);

        restCouponGratuitConfMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(couponGratuitConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<CouponGratuitConf> couponGratuitConfList = couponGratuitConfRepository.findAll();
        assertThat(couponGratuitConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = couponGratuitConfRepository.findAll().size();
        // set the field null
        couponGratuitConf.setCreatedDate(null);

        // Create the CouponGratuitConf, which fails.
        CouponGratuitConfDTO couponGratuitConfDTO = couponGratuitConfMapper.toDto(couponGratuitConf);

        restCouponGratuitConfMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(couponGratuitConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<CouponGratuitConf> couponGratuitConfList = couponGratuitConfRepository.findAll();
        assertThat(couponGratuitConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCouponGratuitConfs() throws Exception {
        // Initialize the database
        couponGratuitConfRepository.saveAndFlush(couponGratuitConf);

        // Get all the couponGratuitConfList
        restCouponGratuitConfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(couponGratuitConf.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeOption").value(hasItem(DEFAULT_TYPE_OPTION.toString())))
            .andExpect(jsonPath("$.[*].maximumCount").value(hasItem(DEFAULT_MAXIMUM_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].obstinateAmount").value(hasItem(sameNumber(DEFAULT_OBSTINATE_AMOUNT))))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getCouponGratuitConf() throws Exception {
        // Initialize the database
        couponGratuitConfRepository.saveAndFlush(couponGratuitConf);

        // Get the couponGratuitConf
        restCouponGratuitConfMockMvc
            .perform(get(ENTITY_API_URL_ID, couponGratuitConf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(couponGratuitConf.getId().intValue()))
            .andExpect(jsonPath("$.typeOption").value(DEFAULT_TYPE_OPTION.toString()))
            .andExpect(jsonPath("$.maximumCount").value(DEFAULT_MAXIMUM_COUNT.intValue()))
            .andExpect(jsonPath("$.obstinateAmount").value(sameNumber(DEFAULT_OBSTINATE_AMOUNT)))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCouponGratuitConf() throws Exception {
        // Get the couponGratuitConf
        restCouponGratuitConfMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCouponGratuitConf() throws Exception {
        // Initialize the database
        couponGratuitConfRepository.saveAndFlush(couponGratuitConf);

        int databaseSizeBeforeUpdate = couponGratuitConfRepository.findAll().size();

        // Update the couponGratuitConf
        CouponGratuitConf updatedCouponGratuitConf = couponGratuitConfRepository.findById(couponGratuitConf.getId()).get();
        // Disconnect from session so that the updates on updatedCouponGratuitConf are not directly saved in db
        em.detach(updatedCouponGratuitConf);
        updatedCouponGratuitConf
            .typeOption(UPDATED_TYPE_OPTION)
            .maximumCount(UPDATED_MAXIMUM_COUNT)
            .obstinateAmount(UPDATED_OBSTINATE_AMOUNT)
            .statut(UPDATED_STATUT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        CouponGratuitConfDTO couponGratuitConfDTO = couponGratuitConfMapper.toDto(updatedCouponGratuitConf);

        restCouponGratuitConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, couponGratuitConfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(couponGratuitConfDTO))
            )
            .andExpect(status().isOk());

        // Validate the CouponGratuitConf in the database
        List<CouponGratuitConf> couponGratuitConfList = couponGratuitConfRepository.findAll();
        assertThat(couponGratuitConfList).hasSize(databaseSizeBeforeUpdate);
        CouponGratuitConf testCouponGratuitConf = couponGratuitConfList.get(couponGratuitConfList.size() - 1);
        assertThat(testCouponGratuitConf.getTypeOption()).isEqualTo(UPDATED_TYPE_OPTION);
        assertThat(testCouponGratuitConf.getMaximumCount()).isEqualTo(UPDATED_MAXIMUM_COUNT);
        assertThat(testCouponGratuitConf.getObstinateAmount()).isEqualByComparingTo(UPDATED_OBSTINATE_AMOUNT);
        assertThat(testCouponGratuitConf.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testCouponGratuitConf.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCouponGratuitConf.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCouponGratuitConf.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testCouponGratuitConf.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingCouponGratuitConf() throws Exception {
        int databaseSizeBeforeUpdate = couponGratuitConfRepository.findAll().size();
        couponGratuitConf.setId(count.incrementAndGet());

        // Create the CouponGratuitConf
        CouponGratuitConfDTO couponGratuitConfDTO = couponGratuitConfMapper.toDto(couponGratuitConf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCouponGratuitConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, couponGratuitConfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(couponGratuitConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CouponGratuitConf in the database
        List<CouponGratuitConf> couponGratuitConfList = couponGratuitConfRepository.findAll();
        assertThat(couponGratuitConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCouponGratuitConf() throws Exception {
        int databaseSizeBeforeUpdate = couponGratuitConfRepository.findAll().size();
        couponGratuitConf.setId(count.incrementAndGet());

        // Create the CouponGratuitConf
        CouponGratuitConfDTO couponGratuitConfDTO = couponGratuitConfMapper.toDto(couponGratuitConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCouponGratuitConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(couponGratuitConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CouponGratuitConf in the database
        List<CouponGratuitConf> couponGratuitConfList = couponGratuitConfRepository.findAll();
        assertThat(couponGratuitConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCouponGratuitConf() throws Exception {
        int databaseSizeBeforeUpdate = couponGratuitConfRepository.findAll().size();
        couponGratuitConf.setId(count.incrementAndGet());

        // Create the CouponGratuitConf
        CouponGratuitConfDTO couponGratuitConfDTO = couponGratuitConfMapper.toDto(couponGratuitConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCouponGratuitConfMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(couponGratuitConfDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CouponGratuitConf in the database
        List<CouponGratuitConf> couponGratuitConfList = couponGratuitConfRepository.findAll();
        assertThat(couponGratuitConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCouponGratuitConfWithPatch() throws Exception {
        // Initialize the database
        couponGratuitConfRepository.saveAndFlush(couponGratuitConf);

        int databaseSizeBeforeUpdate = couponGratuitConfRepository.findAll().size();

        // Update the couponGratuitConf using partial update
        CouponGratuitConf partialUpdatedCouponGratuitConf = new CouponGratuitConf();
        partialUpdatedCouponGratuitConf.setId(couponGratuitConf.getId());

        partialUpdatedCouponGratuitConf
            .statut(UPDATED_STATUT)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restCouponGratuitConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCouponGratuitConf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCouponGratuitConf))
            )
            .andExpect(status().isOk());

        // Validate the CouponGratuitConf in the database
        List<CouponGratuitConf> couponGratuitConfList = couponGratuitConfRepository.findAll();
        assertThat(couponGratuitConfList).hasSize(databaseSizeBeforeUpdate);
        CouponGratuitConf testCouponGratuitConf = couponGratuitConfList.get(couponGratuitConfList.size() - 1);
        assertThat(testCouponGratuitConf.getTypeOption()).isEqualTo(DEFAULT_TYPE_OPTION);
        assertThat(testCouponGratuitConf.getMaximumCount()).isEqualTo(DEFAULT_MAXIMUM_COUNT);
        assertThat(testCouponGratuitConf.getObstinateAmount()).isEqualByComparingTo(DEFAULT_OBSTINATE_AMOUNT);
        assertThat(testCouponGratuitConf.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testCouponGratuitConf.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCouponGratuitConf.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCouponGratuitConf.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testCouponGratuitConf.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateCouponGratuitConfWithPatch() throws Exception {
        // Initialize the database
        couponGratuitConfRepository.saveAndFlush(couponGratuitConf);

        int databaseSizeBeforeUpdate = couponGratuitConfRepository.findAll().size();

        // Update the couponGratuitConf using partial update
        CouponGratuitConf partialUpdatedCouponGratuitConf = new CouponGratuitConf();
        partialUpdatedCouponGratuitConf.setId(couponGratuitConf.getId());

        partialUpdatedCouponGratuitConf
            .typeOption(UPDATED_TYPE_OPTION)
            .maximumCount(UPDATED_MAXIMUM_COUNT)
            .obstinateAmount(UPDATED_OBSTINATE_AMOUNT)
            .statut(UPDATED_STATUT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restCouponGratuitConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCouponGratuitConf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCouponGratuitConf))
            )
            .andExpect(status().isOk());

        // Validate the CouponGratuitConf in the database
        List<CouponGratuitConf> couponGratuitConfList = couponGratuitConfRepository.findAll();
        assertThat(couponGratuitConfList).hasSize(databaseSizeBeforeUpdate);
        CouponGratuitConf testCouponGratuitConf = couponGratuitConfList.get(couponGratuitConfList.size() - 1);
        assertThat(testCouponGratuitConf.getTypeOption()).isEqualTo(UPDATED_TYPE_OPTION);
        assertThat(testCouponGratuitConf.getMaximumCount()).isEqualTo(UPDATED_MAXIMUM_COUNT);
        assertThat(testCouponGratuitConf.getObstinateAmount()).isEqualByComparingTo(UPDATED_OBSTINATE_AMOUNT);
        assertThat(testCouponGratuitConf.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testCouponGratuitConf.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCouponGratuitConf.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCouponGratuitConf.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testCouponGratuitConf.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingCouponGratuitConf() throws Exception {
        int databaseSizeBeforeUpdate = couponGratuitConfRepository.findAll().size();
        couponGratuitConf.setId(count.incrementAndGet());

        // Create the CouponGratuitConf
        CouponGratuitConfDTO couponGratuitConfDTO = couponGratuitConfMapper.toDto(couponGratuitConf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCouponGratuitConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, couponGratuitConfDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(couponGratuitConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CouponGratuitConf in the database
        List<CouponGratuitConf> couponGratuitConfList = couponGratuitConfRepository.findAll();
        assertThat(couponGratuitConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCouponGratuitConf() throws Exception {
        int databaseSizeBeforeUpdate = couponGratuitConfRepository.findAll().size();
        couponGratuitConf.setId(count.incrementAndGet());

        // Create the CouponGratuitConf
        CouponGratuitConfDTO couponGratuitConfDTO = couponGratuitConfMapper.toDto(couponGratuitConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCouponGratuitConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(couponGratuitConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CouponGratuitConf in the database
        List<CouponGratuitConf> couponGratuitConfList = couponGratuitConfRepository.findAll();
        assertThat(couponGratuitConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCouponGratuitConf() throws Exception {
        int databaseSizeBeforeUpdate = couponGratuitConfRepository.findAll().size();
        couponGratuitConf.setId(count.incrementAndGet());

        // Create the CouponGratuitConf
        CouponGratuitConfDTO couponGratuitConfDTO = couponGratuitConfMapper.toDto(couponGratuitConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCouponGratuitConfMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(couponGratuitConfDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CouponGratuitConf in the database
        List<CouponGratuitConf> couponGratuitConfList = couponGratuitConfRepository.findAll();
        assertThat(couponGratuitConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCouponGratuitConf() throws Exception {
        // Initialize the database
        couponGratuitConfRepository.saveAndFlush(couponGratuitConf);

        int databaseSizeBeforeDelete = couponGratuitConfRepository.findAll().size();

        // Delete the couponGratuitConf
        restCouponGratuitConfMockMvc
            .perform(delete(ENTITY_API_URL_ID, couponGratuitConf.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CouponGratuitConf> couponGratuitConfList = couponGratuitConfRepository.findAll();
        assertThat(couponGratuitConfList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
