package com.seventh.lotobig.web.rest;

import static com.seventh.lotobig.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.UserPayment;
import com.seventh.lotobig.repository.UserPaymentRepository;
import com.seventh.lotobig.service.dto.UserPaymentDTO;
import com.seventh.lotobig.service.mapper.UserPaymentMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link UserPaymentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserPaymentResourceIT {

    private static final BigDecimal DEFAULT_PAY_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAY_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/user-payments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserPaymentRepository userPaymentRepository;

    @Autowired
    private UserPaymentMapper userPaymentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserPaymentMockMvc;

    private UserPayment userPayment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPayment createEntity(EntityManager em) {
        UserPayment userPayment = new UserPayment()
            .payAmount(DEFAULT_PAY_AMOUNT)
            .balance(DEFAULT_BALANCE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return userPayment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPayment createUpdatedEntity(EntityManager em) {
        UserPayment userPayment = new UserPayment()
            .payAmount(UPDATED_PAY_AMOUNT)
            .balance(UPDATED_BALANCE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return userPayment;
    }

    @BeforeEach
    public void initTest() {
        userPayment = createEntity(em);
    }

    @Test
    @Transactional
    void createUserPayment() throws Exception {
        int databaseSizeBeforeCreate = userPaymentRepository.findAll().size();
        // Create the UserPayment
        UserPaymentDTO userPaymentDTO = userPaymentMapper.toDto(userPayment);
        restUserPaymentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPaymentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserPayment in the database
        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeCreate + 1);
        UserPayment testUserPayment = userPaymentList.get(userPaymentList.size() - 1);
        assertThat(testUserPayment.getPayAmount()).isEqualByComparingTo(DEFAULT_PAY_AMOUNT);
        assertThat(testUserPayment.getBalance()).isEqualByComparingTo(DEFAULT_BALANCE);
        assertThat(testUserPayment.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testUserPayment.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testUserPayment.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testUserPayment.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserPayment.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testUserPayment.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createUserPaymentWithExistingId() throws Exception {
        // Create the UserPayment with an existing ID
        userPayment.setId(1L);
        UserPaymentDTO userPaymentDTO = userPaymentMapper.toDto(userPayment);

        int databaseSizeBeforeCreate = userPaymentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserPaymentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserPayment in the database
        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPayAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPaymentRepository.findAll().size();
        // set the field null
        userPayment.setPayAmount(null);

        // Create the UserPayment, which fails.
        UserPaymentDTO userPaymentDTO = userPaymentMapper.toDto(userPayment);

        restUserPaymentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPaymentRepository.findAll().size();
        // set the field null
        userPayment.setBalance(null);

        // Create the UserPayment, which fails.
        UserPaymentDTO userPaymentDTO = userPaymentMapper.toDto(userPayment);

        restUserPaymentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPaymentRepository.findAll().size();
        // set the field null
        userPayment.setCreatedBy(null);

        // Create the UserPayment, which fails.
        UserPaymentDTO userPaymentDTO = userPaymentMapper.toDto(userPayment);

        restUserPaymentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPaymentRepository.findAll().size();
        // set the field null
        userPayment.setCreatedDate(null);

        // Create the UserPayment, which fails.
        UserPaymentDTO userPaymentDTO = userPaymentMapper.toDto(userPayment);

        restUserPaymentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserPayments() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList
        restUserPaymentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].payAmount").value(hasItem(sameNumber(DEFAULT_PAY_AMOUNT))))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(sameNumber(DEFAULT_BALANCE))))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getUserPayment() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get the userPayment
        restUserPaymentMockMvc
            .perform(get(ENTITY_API_URL_ID, userPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userPayment.getId().intValue()))
            .andExpect(jsonPath("$.payAmount").value(sameNumber(DEFAULT_PAY_AMOUNT)))
            .andExpect(jsonPath("$.balance").value(sameNumber(DEFAULT_BALANCE)))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserPayment() throws Exception {
        // Get the userPayment
        restUserPaymentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserPayment() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        int databaseSizeBeforeUpdate = userPaymentRepository.findAll().size();

        // Update the userPayment
        UserPayment updatedUserPayment = userPaymentRepository.findById(userPayment.getId()).get();
        // Disconnect from session so that the updates on updatedUserPayment are not directly saved in db
        em.detach(updatedUserPayment);
        updatedUserPayment
            .payAmount(UPDATED_PAY_AMOUNT)
            .balance(UPDATED_BALANCE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        UserPaymentDTO userPaymentDTO = userPaymentMapper.toDto(updatedUserPayment);

        restUserPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userPaymentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userPaymentDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserPayment in the database
        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeUpdate);
        UserPayment testUserPayment = userPaymentList.get(userPaymentList.size() - 1);
        assertThat(testUserPayment.getPayAmount()).isEqualByComparingTo(UPDATED_PAY_AMOUNT);
        assertThat(testUserPayment.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testUserPayment.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testUserPayment.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserPayment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserPayment.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserPayment.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testUserPayment.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingUserPayment() throws Exception {
        int databaseSizeBeforeUpdate = userPaymentRepository.findAll().size();
        userPayment.setId(count.incrementAndGet());

        // Create the UserPayment
        UserPaymentDTO userPaymentDTO = userPaymentMapper.toDto(userPayment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userPaymentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserPayment in the database
        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserPayment() throws Exception {
        int databaseSizeBeforeUpdate = userPaymentRepository.findAll().size();
        userPayment.setId(count.incrementAndGet());

        // Create the UserPayment
        UserPaymentDTO userPaymentDTO = userPaymentMapper.toDto(userPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserPayment in the database
        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserPayment() throws Exception {
        int databaseSizeBeforeUpdate = userPaymentRepository.findAll().size();
        userPayment.setId(count.incrementAndGet());

        // Create the UserPayment
        UserPaymentDTO userPaymentDTO = userPaymentMapper.toDto(userPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserPaymentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPaymentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserPayment in the database
        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserPaymentWithPatch() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        int databaseSizeBeforeUpdate = userPaymentRepository.findAll().size();

        // Update the userPayment using partial update
        UserPayment partialUpdatedUserPayment = new UserPayment();
        partialUpdatedUserPayment.setId(userPayment.getId());

        partialUpdatedUserPayment
            .balance(UPDATED_BALANCE)
            .endDate(UPDATED_END_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restUserPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserPayment))
            )
            .andExpect(status().isOk());

        // Validate the UserPayment in the database
        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeUpdate);
        UserPayment testUserPayment = userPaymentList.get(userPaymentList.size() - 1);
        assertThat(testUserPayment.getPayAmount()).isEqualByComparingTo(DEFAULT_PAY_AMOUNT);
        assertThat(testUserPayment.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testUserPayment.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testUserPayment.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserPayment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserPayment.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserPayment.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testUserPayment.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateUserPaymentWithPatch() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        int databaseSizeBeforeUpdate = userPaymentRepository.findAll().size();

        // Update the userPayment using partial update
        UserPayment partialUpdatedUserPayment = new UserPayment();
        partialUpdatedUserPayment.setId(userPayment.getId());

        partialUpdatedUserPayment
            .payAmount(UPDATED_PAY_AMOUNT)
            .balance(UPDATED_BALANCE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restUserPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserPayment))
            )
            .andExpect(status().isOk());

        // Validate the UserPayment in the database
        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeUpdate);
        UserPayment testUserPayment = userPaymentList.get(userPaymentList.size() - 1);
        assertThat(testUserPayment.getPayAmount()).isEqualByComparingTo(UPDATED_PAY_AMOUNT);
        assertThat(testUserPayment.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testUserPayment.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testUserPayment.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserPayment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserPayment.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserPayment.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testUserPayment.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingUserPayment() throws Exception {
        int databaseSizeBeforeUpdate = userPaymentRepository.findAll().size();
        userPayment.setId(count.incrementAndGet());

        // Create the UserPayment
        UserPaymentDTO userPaymentDTO = userPaymentMapper.toDto(userPayment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userPaymentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserPayment in the database
        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserPayment() throws Exception {
        int databaseSizeBeforeUpdate = userPaymentRepository.findAll().size();
        userPayment.setId(count.incrementAndGet());

        // Create the UserPayment
        UserPaymentDTO userPaymentDTO = userPaymentMapper.toDto(userPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserPayment in the database
        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserPayment() throws Exception {
        int databaseSizeBeforeUpdate = userPaymentRepository.findAll().size();
        userPayment.setId(count.incrementAndGet());

        // Create the UserPayment
        UserPaymentDTO userPaymentDTO = userPaymentMapper.toDto(userPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userPaymentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserPayment in the database
        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserPayment() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        int databaseSizeBeforeDelete = userPaymentRepository.findAll().size();

        // Delete the userPayment
        restUserPaymentMockMvc
            .perform(delete(ENTITY_API_URL_ID, userPayment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
