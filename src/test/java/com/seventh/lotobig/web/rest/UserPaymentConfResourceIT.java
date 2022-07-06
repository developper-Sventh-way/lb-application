package com.seventh.lotobig.web.rest;

import static com.seventh.lotobig.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.UserPaymentConf;
import com.seventh.lotobig.domain.enumeration.UserPaymentType;
import com.seventh.lotobig.repository.UserPaymentConfRepository;
import com.seventh.lotobig.service.dto.UserPaymentConfDTO;
import com.seventh.lotobig.service.mapper.UserPaymentConfMapper;
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
 * Integration tests for the {@link UserPaymentConfResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserPaymentConfResourceIT {

    private static final UserPaymentType DEFAULT_TYPE = UserPaymentType.FIX;
    private static final UserPaymentType UPDATED_TYPE = UserPaymentType.POURCENTAGE;

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/user-payment-confs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserPaymentConfRepository userPaymentConfRepository;

    @Autowired
    private UserPaymentConfMapper userPaymentConfMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserPaymentConfMockMvc;

    private UserPaymentConf userPaymentConf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPaymentConf createEntity(EntityManager em) {
        UserPaymentConf userPaymentConf = new UserPaymentConf()
            .type(DEFAULT_TYPE)
            .total(DEFAULT_TOTAL)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return userPaymentConf;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPaymentConf createUpdatedEntity(EntityManager em) {
        UserPaymentConf userPaymentConf = new UserPaymentConf()
            .type(UPDATED_TYPE)
            .total(UPDATED_TOTAL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return userPaymentConf;
    }

    @BeforeEach
    public void initTest() {
        userPaymentConf = createEntity(em);
    }

    @Test
    @Transactional
    void createUserPaymentConf() throws Exception {
        int databaseSizeBeforeCreate = userPaymentConfRepository.findAll().size();
        // Create the UserPaymentConf
        UserPaymentConfDTO userPaymentConfDTO = userPaymentConfMapper.toDto(userPaymentConf);
        restUserPaymentConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPaymentConfDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserPaymentConf in the database
        List<UserPaymentConf> userPaymentConfList = userPaymentConfRepository.findAll();
        assertThat(userPaymentConfList).hasSize(databaseSizeBeforeCreate + 1);
        UserPaymentConf testUserPaymentConf = userPaymentConfList.get(userPaymentConfList.size() - 1);
        assertThat(testUserPaymentConf.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testUserPaymentConf.getTotal()).isEqualByComparingTo(DEFAULT_TOTAL);
        assertThat(testUserPaymentConf.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testUserPaymentConf.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserPaymentConf.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testUserPaymentConf.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createUserPaymentConfWithExistingId() throws Exception {
        // Create the UserPaymentConf with an existing ID
        userPaymentConf.setId(1L);
        UserPaymentConfDTO userPaymentConfDTO = userPaymentConfMapper.toDto(userPaymentConf);

        int databaseSizeBeforeCreate = userPaymentConfRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserPaymentConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPaymentConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserPaymentConf in the database
        List<UserPaymentConf> userPaymentConfList = userPaymentConfRepository.findAll();
        assertThat(userPaymentConfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPaymentConfRepository.findAll().size();
        // set the field null
        userPaymentConf.setType(null);

        // Create the UserPaymentConf, which fails.
        UserPaymentConfDTO userPaymentConfDTO = userPaymentConfMapper.toDto(userPaymentConf);

        restUserPaymentConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPaymentConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserPaymentConf> userPaymentConfList = userPaymentConfRepository.findAll();
        assertThat(userPaymentConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPaymentConfRepository.findAll().size();
        // set the field null
        userPaymentConf.setTotal(null);

        // Create the UserPaymentConf, which fails.
        UserPaymentConfDTO userPaymentConfDTO = userPaymentConfMapper.toDto(userPaymentConf);

        restUserPaymentConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPaymentConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserPaymentConf> userPaymentConfList = userPaymentConfRepository.findAll();
        assertThat(userPaymentConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPaymentConfRepository.findAll().size();
        // set the field null
        userPaymentConf.setCreatedBy(null);

        // Create the UserPaymentConf, which fails.
        UserPaymentConfDTO userPaymentConfDTO = userPaymentConfMapper.toDto(userPaymentConf);

        restUserPaymentConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPaymentConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserPaymentConf> userPaymentConfList = userPaymentConfRepository.findAll();
        assertThat(userPaymentConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPaymentConfRepository.findAll().size();
        // set the field null
        userPaymentConf.setCreatedDate(null);

        // Create the UserPaymentConf, which fails.
        UserPaymentConfDTO userPaymentConfDTO = userPaymentConfMapper.toDto(userPaymentConf);

        restUserPaymentConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPaymentConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserPaymentConf> userPaymentConfList = userPaymentConfRepository.findAll();
        assertThat(userPaymentConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserPaymentConfs() throws Exception {
        // Initialize the database
        userPaymentConfRepository.saveAndFlush(userPaymentConf);

        // Get all the userPaymentConfList
        restUserPaymentConfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPaymentConf.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(sameNumber(DEFAULT_TOTAL))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getUserPaymentConf() throws Exception {
        // Initialize the database
        userPaymentConfRepository.saveAndFlush(userPaymentConf);

        // Get the userPaymentConf
        restUserPaymentConfMockMvc
            .perform(get(ENTITY_API_URL_ID, userPaymentConf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userPaymentConf.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.total").value(sameNumber(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserPaymentConf() throws Exception {
        // Get the userPaymentConf
        restUserPaymentConfMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserPaymentConf() throws Exception {
        // Initialize the database
        userPaymentConfRepository.saveAndFlush(userPaymentConf);

        int databaseSizeBeforeUpdate = userPaymentConfRepository.findAll().size();

        // Update the userPaymentConf
        UserPaymentConf updatedUserPaymentConf = userPaymentConfRepository.findById(userPaymentConf.getId()).get();
        // Disconnect from session so that the updates on updatedUserPaymentConf are not directly saved in db
        em.detach(updatedUserPaymentConf);
        updatedUserPaymentConf
            .type(UPDATED_TYPE)
            .total(UPDATED_TOTAL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        UserPaymentConfDTO userPaymentConfDTO = userPaymentConfMapper.toDto(updatedUserPaymentConf);

        restUserPaymentConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userPaymentConfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userPaymentConfDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserPaymentConf in the database
        List<UserPaymentConf> userPaymentConfList = userPaymentConfRepository.findAll();
        assertThat(userPaymentConfList).hasSize(databaseSizeBeforeUpdate);
        UserPaymentConf testUserPaymentConf = userPaymentConfList.get(userPaymentConfList.size() - 1);
        assertThat(testUserPaymentConf.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testUserPaymentConf.getTotal()).isEqualByComparingTo(UPDATED_TOTAL);
        assertThat(testUserPaymentConf.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserPaymentConf.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserPaymentConf.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testUserPaymentConf.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingUserPaymentConf() throws Exception {
        int databaseSizeBeforeUpdate = userPaymentConfRepository.findAll().size();
        userPaymentConf.setId(count.incrementAndGet());

        // Create the UserPaymentConf
        UserPaymentConfDTO userPaymentConfDTO = userPaymentConfMapper.toDto(userPaymentConf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserPaymentConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userPaymentConfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userPaymentConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserPaymentConf in the database
        List<UserPaymentConf> userPaymentConfList = userPaymentConfRepository.findAll();
        assertThat(userPaymentConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserPaymentConf() throws Exception {
        int databaseSizeBeforeUpdate = userPaymentConfRepository.findAll().size();
        userPaymentConf.setId(count.incrementAndGet());

        // Create the UserPaymentConf
        UserPaymentConfDTO userPaymentConfDTO = userPaymentConfMapper.toDto(userPaymentConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserPaymentConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userPaymentConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserPaymentConf in the database
        List<UserPaymentConf> userPaymentConfList = userPaymentConfRepository.findAll();
        assertThat(userPaymentConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserPaymentConf() throws Exception {
        int databaseSizeBeforeUpdate = userPaymentConfRepository.findAll().size();
        userPaymentConf.setId(count.incrementAndGet());

        // Create the UserPaymentConf
        UserPaymentConfDTO userPaymentConfDTO = userPaymentConfMapper.toDto(userPaymentConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserPaymentConfMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPaymentConfDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserPaymentConf in the database
        List<UserPaymentConf> userPaymentConfList = userPaymentConfRepository.findAll();
        assertThat(userPaymentConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserPaymentConfWithPatch() throws Exception {
        // Initialize the database
        userPaymentConfRepository.saveAndFlush(userPaymentConf);

        int databaseSizeBeforeUpdate = userPaymentConfRepository.findAll().size();

        // Update the userPaymentConf using partial update
        UserPaymentConf partialUpdatedUserPaymentConf = new UserPaymentConf();
        partialUpdatedUserPaymentConf.setId(userPaymentConf.getId());

        partialUpdatedUserPaymentConf.type(UPDATED_TYPE).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restUserPaymentConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserPaymentConf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserPaymentConf))
            )
            .andExpect(status().isOk());

        // Validate the UserPaymentConf in the database
        List<UserPaymentConf> userPaymentConfList = userPaymentConfRepository.findAll();
        assertThat(userPaymentConfList).hasSize(databaseSizeBeforeUpdate);
        UserPaymentConf testUserPaymentConf = userPaymentConfList.get(userPaymentConfList.size() - 1);
        assertThat(testUserPaymentConf.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testUserPaymentConf.getTotal()).isEqualByComparingTo(DEFAULT_TOTAL);
        assertThat(testUserPaymentConf.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testUserPaymentConf.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserPaymentConf.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testUserPaymentConf.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateUserPaymentConfWithPatch() throws Exception {
        // Initialize the database
        userPaymentConfRepository.saveAndFlush(userPaymentConf);

        int databaseSizeBeforeUpdate = userPaymentConfRepository.findAll().size();

        // Update the userPaymentConf using partial update
        UserPaymentConf partialUpdatedUserPaymentConf = new UserPaymentConf();
        partialUpdatedUserPaymentConf.setId(userPaymentConf.getId());

        partialUpdatedUserPaymentConf
            .type(UPDATED_TYPE)
            .total(UPDATED_TOTAL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restUserPaymentConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserPaymentConf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserPaymentConf))
            )
            .andExpect(status().isOk());

        // Validate the UserPaymentConf in the database
        List<UserPaymentConf> userPaymentConfList = userPaymentConfRepository.findAll();
        assertThat(userPaymentConfList).hasSize(databaseSizeBeforeUpdate);
        UserPaymentConf testUserPaymentConf = userPaymentConfList.get(userPaymentConfList.size() - 1);
        assertThat(testUserPaymentConf.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testUserPaymentConf.getTotal()).isEqualByComparingTo(UPDATED_TOTAL);
        assertThat(testUserPaymentConf.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserPaymentConf.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserPaymentConf.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testUserPaymentConf.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingUserPaymentConf() throws Exception {
        int databaseSizeBeforeUpdate = userPaymentConfRepository.findAll().size();
        userPaymentConf.setId(count.incrementAndGet());

        // Create the UserPaymentConf
        UserPaymentConfDTO userPaymentConfDTO = userPaymentConfMapper.toDto(userPaymentConf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserPaymentConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userPaymentConfDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userPaymentConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserPaymentConf in the database
        List<UserPaymentConf> userPaymentConfList = userPaymentConfRepository.findAll();
        assertThat(userPaymentConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserPaymentConf() throws Exception {
        int databaseSizeBeforeUpdate = userPaymentConfRepository.findAll().size();
        userPaymentConf.setId(count.incrementAndGet());

        // Create the UserPaymentConf
        UserPaymentConfDTO userPaymentConfDTO = userPaymentConfMapper.toDto(userPaymentConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserPaymentConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userPaymentConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserPaymentConf in the database
        List<UserPaymentConf> userPaymentConfList = userPaymentConfRepository.findAll();
        assertThat(userPaymentConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserPaymentConf() throws Exception {
        int databaseSizeBeforeUpdate = userPaymentConfRepository.findAll().size();
        userPaymentConf.setId(count.incrementAndGet());

        // Create the UserPaymentConf
        UserPaymentConfDTO userPaymentConfDTO = userPaymentConfMapper.toDto(userPaymentConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserPaymentConfMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userPaymentConfDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserPaymentConf in the database
        List<UserPaymentConf> userPaymentConfList = userPaymentConfRepository.findAll();
        assertThat(userPaymentConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserPaymentConf() throws Exception {
        // Initialize the database
        userPaymentConfRepository.saveAndFlush(userPaymentConf);

        int databaseSizeBeforeDelete = userPaymentConfRepository.findAll().size();

        // Delete the userPaymentConf
        restUserPaymentConfMockMvc
            .perform(delete(ENTITY_API_URL_ID, userPaymentConf.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserPaymentConf> userPaymentConfList = userPaymentConfRepository.findAll();
        assertThat(userPaymentConfList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
