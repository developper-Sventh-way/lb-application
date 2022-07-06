package com.seventh.lotobig.web.rest;

import static com.seventh.lotobig.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.UserSaleAccount;
import com.seventh.lotobig.domain.enumeration.UserSaleAccountStatut;
import com.seventh.lotobig.repository.UserSaleAccountRepository;
import com.seventh.lotobig.service.dto.UserSaleAccountDTO;
import com.seventh.lotobig.service.mapper.UserSaleAccountMapper;
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
 * Integration tests for the {@link UserSaleAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserSaleAccountResourceIT {

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LAST_PAYMENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LAST_PAYMENT = new BigDecimal(2);

    private static final UserSaleAccountStatut DEFAULT_STATUT = UserSaleAccountStatut.ACTIVE;
    private static final UserSaleAccountStatut UPDATED_STATUT = UserSaleAccountStatut.CLOSED;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/user-sale-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserSaleAccountRepository userSaleAccountRepository;

    @Autowired
    private UserSaleAccountMapper userSaleAccountMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserSaleAccountMockMvc;

    private UserSaleAccount userSaleAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSaleAccount createEntity(EntityManager em) {
        UserSaleAccount userSaleAccount = new UserSaleAccount()
            .balance(DEFAULT_BALANCE)
            .lastPayment(DEFAULT_LAST_PAYMENT)
            .statut(DEFAULT_STATUT)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return userSaleAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSaleAccount createUpdatedEntity(EntityManager em) {
        UserSaleAccount userSaleAccount = new UserSaleAccount()
            .balance(UPDATED_BALANCE)
            .lastPayment(UPDATED_LAST_PAYMENT)
            .statut(UPDATED_STATUT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return userSaleAccount;
    }

    @BeforeEach
    public void initTest() {
        userSaleAccount = createEntity(em);
    }

    @Test
    @Transactional
    void createUserSaleAccount() throws Exception {
        int databaseSizeBeforeCreate = userSaleAccountRepository.findAll().size();
        // Create the UserSaleAccount
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);
        restUserSaleAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeCreate + 1);
        UserSaleAccount testUserSaleAccount = userSaleAccountList.get(userSaleAccountList.size() - 1);
        assertThat(testUserSaleAccount.getBalance()).isEqualByComparingTo(DEFAULT_BALANCE);
        assertThat(testUserSaleAccount.getLastPayment()).isEqualByComparingTo(DEFAULT_LAST_PAYMENT);
        assertThat(testUserSaleAccount.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testUserSaleAccount.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testUserSaleAccount.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testUserSaleAccount.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testUserSaleAccount.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserSaleAccount.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testUserSaleAccount.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createUserSaleAccountWithExistingId() throws Exception {
        // Create the UserSaleAccount with an existing ID
        userSaleAccount.setId(1L);
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        int databaseSizeBeforeCreate = userSaleAccountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserSaleAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSaleAccountRepository.findAll().size();
        // set the field null
        userSaleAccount.setBalance(null);

        // Create the UserSaleAccount, which fails.
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        restUserSaleAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastPaymentIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSaleAccountRepository.findAll().size();
        // set the field null
        userSaleAccount.setLastPayment(null);

        // Create the UserSaleAccount, which fails.
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        restUserSaleAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSaleAccountRepository.findAll().size();
        // set the field null
        userSaleAccount.setStartDate(null);

        // Create the UserSaleAccount, which fails.
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        restUserSaleAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSaleAccountRepository.findAll().size();
        // set the field null
        userSaleAccount.setEndDate(null);

        // Create the UserSaleAccount, which fails.
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        restUserSaleAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSaleAccountRepository.findAll().size();
        // set the field null
        userSaleAccount.setCreatedBy(null);

        // Create the UserSaleAccount, which fails.
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        restUserSaleAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSaleAccountRepository.findAll().size();
        // set the field null
        userSaleAccount.setCreatedDate(null);

        // Create the UserSaleAccount, which fails.
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        restUserSaleAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserSaleAccounts() throws Exception {
        // Initialize the database
        userSaleAccountRepository.saveAndFlush(userSaleAccount);

        // Get all the userSaleAccountList
        restUserSaleAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userSaleAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(sameNumber(DEFAULT_BALANCE))))
            .andExpect(jsonPath("$.[*].lastPayment").value(hasItem(sameNumber(DEFAULT_LAST_PAYMENT))))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getUserSaleAccount() throws Exception {
        // Initialize the database
        userSaleAccountRepository.saveAndFlush(userSaleAccount);

        // Get the userSaleAccount
        restUserSaleAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, userSaleAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userSaleAccount.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(sameNumber(DEFAULT_BALANCE)))
            .andExpect(jsonPath("$.lastPayment").value(sameNumber(DEFAULT_LAST_PAYMENT)))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserSaleAccount() throws Exception {
        // Get the userSaleAccount
        restUserSaleAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserSaleAccount() throws Exception {
        // Initialize the database
        userSaleAccountRepository.saveAndFlush(userSaleAccount);

        int databaseSizeBeforeUpdate = userSaleAccountRepository.findAll().size();

        // Update the userSaleAccount
        UserSaleAccount updatedUserSaleAccount = userSaleAccountRepository.findById(userSaleAccount.getId()).get();
        // Disconnect from session so that the updates on updatedUserSaleAccount are not directly saved in db
        em.detach(updatedUserSaleAccount);
        updatedUserSaleAccount
            .balance(UPDATED_BALANCE)
            .lastPayment(UPDATED_LAST_PAYMENT)
            .statut(UPDATED_STATUT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(updatedUserSaleAccount);

        restUserSaleAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userSaleAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeUpdate);
        UserSaleAccount testUserSaleAccount = userSaleAccountList.get(userSaleAccountList.size() - 1);
        assertThat(testUserSaleAccount.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testUserSaleAccount.getLastPayment()).isEqualByComparingTo(UPDATED_LAST_PAYMENT);
        assertThat(testUserSaleAccount.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testUserSaleAccount.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testUserSaleAccount.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserSaleAccount.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserSaleAccount.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserSaleAccount.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testUserSaleAccount.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingUserSaleAccount() throws Exception {
        int databaseSizeBeforeUpdate = userSaleAccountRepository.findAll().size();
        userSaleAccount.setId(count.incrementAndGet());

        // Create the UserSaleAccount
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserSaleAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userSaleAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserSaleAccount() throws Exception {
        int databaseSizeBeforeUpdate = userSaleAccountRepository.findAll().size();
        userSaleAccount.setId(count.incrementAndGet());

        // Create the UserSaleAccount
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSaleAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserSaleAccount() throws Exception {
        int databaseSizeBeforeUpdate = userSaleAccountRepository.findAll().size();
        userSaleAccount.setId(count.incrementAndGet());

        // Create the UserSaleAccount
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSaleAccountMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserSaleAccountWithPatch() throws Exception {
        // Initialize the database
        userSaleAccountRepository.saveAndFlush(userSaleAccount);

        int databaseSizeBeforeUpdate = userSaleAccountRepository.findAll().size();

        // Update the userSaleAccount using partial update
        UserSaleAccount partialUpdatedUserSaleAccount = new UserSaleAccount();
        partialUpdatedUserSaleAccount.setId(userSaleAccount.getId());

        partialUpdatedUserSaleAccount
            .balance(UPDATED_BALANCE)
            .lastPayment(UPDATED_LAST_PAYMENT)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restUserSaleAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserSaleAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserSaleAccount))
            )
            .andExpect(status().isOk());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeUpdate);
        UserSaleAccount testUserSaleAccount = userSaleAccountList.get(userSaleAccountList.size() - 1);
        assertThat(testUserSaleAccount.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testUserSaleAccount.getLastPayment()).isEqualByComparingTo(UPDATED_LAST_PAYMENT);
        assertThat(testUserSaleAccount.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testUserSaleAccount.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testUserSaleAccount.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testUserSaleAccount.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserSaleAccount.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserSaleAccount.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testUserSaleAccount.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateUserSaleAccountWithPatch() throws Exception {
        // Initialize the database
        userSaleAccountRepository.saveAndFlush(userSaleAccount);

        int databaseSizeBeforeUpdate = userSaleAccountRepository.findAll().size();

        // Update the userSaleAccount using partial update
        UserSaleAccount partialUpdatedUserSaleAccount = new UserSaleAccount();
        partialUpdatedUserSaleAccount.setId(userSaleAccount.getId());

        partialUpdatedUserSaleAccount
            .balance(UPDATED_BALANCE)
            .lastPayment(UPDATED_LAST_PAYMENT)
            .statut(UPDATED_STATUT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restUserSaleAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserSaleAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserSaleAccount))
            )
            .andExpect(status().isOk());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeUpdate);
        UserSaleAccount testUserSaleAccount = userSaleAccountList.get(userSaleAccountList.size() - 1);
        assertThat(testUserSaleAccount.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testUserSaleAccount.getLastPayment()).isEqualByComparingTo(UPDATED_LAST_PAYMENT);
        assertThat(testUserSaleAccount.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testUserSaleAccount.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testUserSaleAccount.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserSaleAccount.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserSaleAccount.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserSaleAccount.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testUserSaleAccount.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingUserSaleAccount() throws Exception {
        int databaseSizeBeforeUpdate = userSaleAccountRepository.findAll().size();
        userSaleAccount.setId(count.incrementAndGet());

        // Create the UserSaleAccount
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserSaleAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userSaleAccountDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserSaleAccount() throws Exception {
        int databaseSizeBeforeUpdate = userSaleAccountRepository.findAll().size();
        userSaleAccount.setId(count.incrementAndGet());

        // Create the UserSaleAccount
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSaleAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserSaleAccount() throws Exception {
        int databaseSizeBeforeUpdate = userSaleAccountRepository.findAll().size();
        userSaleAccount.setId(count.incrementAndGet());

        // Create the UserSaleAccount
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSaleAccountMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserSaleAccount() throws Exception {
        // Initialize the database
        userSaleAccountRepository.saveAndFlush(userSaleAccount);

        int databaseSizeBeforeDelete = userSaleAccountRepository.findAll().size();

        // Delete the userSaleAccount
        restUserSaleAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, userSaleAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
