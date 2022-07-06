package com.seventh.lotobig.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.UserRole;
import com.seventh.lotobig.domain.enumeration.UserRoleName;
import com.seventh.lotobig.repository.UserRoleRepository;
import com.seventh.lotobig.service.dto.UserRoleDTO;
import com.seventh.lotobig.service.mapper.UserRoleMapper;
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
 * Integration tests for the {@link UserRoleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserRoleResourceIT {

    private static final UserRoleName DEFAULT_ROLE = UserRoleName.CASHIER;
    private static final UserRoleName UPDATED_ROLE = UserRoleName.RESPONSABLE_POINT;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EXCEPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_EXCEPTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/user-roles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserRoleMockMvc;

    private UserRole userRole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRole createEntity(EntityManager em) {
        UserRole userRole = new UserRole()
            .role(DEFAULT_ROLE)
            .description(DEFAULT_DESCRIPTION)
            .exceptions(DEFAULT_EXCEPTIONS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return userRole;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRole createUpdatedEntity(EntityManager em) {
        UserRole userRole = new UserRole()
            .role(UPDATED_ROLE)
            .description(UPDATED_DESCRIPTION)
            .exceptions(UPDATED_EXCEPTIONS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return userRole;
    }

    @BeforeEach
    public void initTest() {
        userRole = createEntity(em);
    }

    @Test
    @Transactional
    void createUserRole() throws Exception {
        int databaseSizeBeforeCreate = userRoleRepository.findAll().size();
        // Create the UserRole
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(userRole);
        restUserRoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userRoleDTO)))
            .andExpect(status().isCreated());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeCreate + 1);
        UserRole testUserRole = userRoleList.get(userRoleList.size() - 1);
        assertThat(testUserRole.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testUserRole.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUserRole.getExceptions()).isEqualTo(DEFAULT_EXCEPTIONS);
        assertThat(testUserRole.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testUserRole.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserRole.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testUserRole.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createUserRoleWithExistingId() throws Exception {
        // Create the UserRole with an existing ID
        userRole.setId(1L);
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(userRole);

        int databaseSizeBeforeCreate = userRoleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserRoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRoleRepository.findAll().size();
        // set the field null
        userRole.setRole(null);

        // Create the UserRole, which fails.
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(userRole);

        restUserRoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userRoleDTO)))
            .andExpect(status().isBadRequest());

        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRoleRepository.findAll().size();
        // set the field null
        userRole.setCreatedBy(null);

        // Create the UserRole, which fails.
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(userRole);

        restUserRoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userRoleDTO)))
            .andExpect(status().isBadRequest());

        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRoleRepository.findAll().size();
        // set the field null
        userRole.setCreatedDate(null);

        // Create the UserRole, which fails.
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(userRole);

        restUserRoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userRoleDTO)))
            .andExpect(status().isBadRequest());

        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserRoles() throws Exception {
        // Initialize the database
        userRoleRepository.saveAndFlush(userRole);

        // Get all the userRoleList
        restUserRoleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].exceptions").value(hasItem(DEFAULT_EXCEPTIONS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getUserRole() throws Exception {
        // Initialize the database
        userRoleRepository.saveAndFlush(userRole);

        // Get the userRole
        restUserRoleMockMvc
            .perform(get(ENTITY_API_URL_ID, userRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userRole.getId().intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.exceptions").value(DEFAULT_EXCEPTIONS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserRole() throws Exception {
        // Get the userRole
        restUserRoleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserRole() throws Exception {
        // Initialize the database
        userRoleRepository.saveAndFlush(userRole);

        int databaseSizeBeforeUpdate = userRoleRepository.findAll().size();

        // Update the userRole
        UserRole updatedUserRole = userRoleRepository.findById(userRole.getId()).get();
        // Disconnect from session so that the updates on updatedUserRole are not directly saved in db
        em.detach(updatedUserRole);
        updatedUserRole
            .role(UPDATED_ROLE)
            .description(UPDATED_DESCRIPTION)
            .exceptions(UPDATED_EXCEPTIONS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(updatedUserRole);

        restUserRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userRoleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRoleDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeUpdate);
        UserRole testUserRole = userRoleList.get(userRoleList.size() - 1);
        assertThat(testUserRole.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testUserRole.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUserRole.getExceptions()).isEqualTo(UPDATED_EXCEPTIONS);
        assertThat(testUserRole.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserRole.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserRole.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testUserRole.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingUserRole() throws Exception {
        int databaseSizeBeforeUpdate = userRoleRepository.findAll().size();
        userRole.setId(count.incrementAndGet());

        // Create the UserRole
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(userRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userRoleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserRole() throws Exception {
        int databaseSizeBeforeUpdate = userRoleRepository.findAll().size();
        userRole.setId(count.incrementAndGet());

        // Create the UserRole
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(userRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserRole() throws Exception {
        int databaseSizeBeforeUpdate = userRoleRepository.findAll().size();
        userRole.setId(count.incrementAndGet());

        // Create the UserRole
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(userRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRoleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userRoleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserRoleWithPatch() throws Exception {
        // Initialize the database
        userRoleRepository.saveAndFlush(userRole);

        int databaseSizeBeforeUpdate = userRoleRepository.findAll().size();

        // Update the userRole using partial update
        UserRole partialUpdatedUserRole = new UserRole();
        partialUpdatedUserRole.setId(userRole.getId());

        partialUpdatedUserRole.createdBy(UPDATED_CREATED_BY).lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restUserRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserRole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserRole))
            )
            .andExpect(status().isOk());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeUpdate);
        UserRole testUserRole = userRoleList.get(userRoleList.size() - 1);
        assertThat(testUserRole.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testUserRole.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUserRole.getExceptions()).isEqualTo(DEFAULT_EXCEPTIONS);
        assertThat(testUserRole.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserRole.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserRole.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testUserRole.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateUserRoleWithPatch() throws Exception {
        // Initialize the database
        userRoleRepository.saveAndFlush(userRole);

        int databaseSizeBeforeUpdate = userRoleRepository.findAll().size();

        // Update the userRole using partial update
        UserRole partialUpdatedUserRole = new UserRole();
        partialUpdatedUserRole.setId(userRole.getId());

        partialUpdatedUserRole
            .role(UPDATED_ROLE)
            .description(UPDATED_DESCRIPTION)
            .exceptions(UPDATED_EXCEPTIONS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restUserRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserRole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserRole))
            )
            .andExpect(status().isOk());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeUpdate);
        UserRole testUserRole = userRoleList.get(userRoleList.size() - 1);
        assertThat(testUserRole.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testUserRole.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUserRole.getExceptions()).isEqualTo(UPDATED_EXCEPTIONS);
        assertThat(testUserRole.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserRole.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserRole.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testUserRole.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingUserRole() throws Exception {
        int databaseSizeBeforeUpdate = userRoleRepository.findAll().size();
        userRole.setId(count.incrementAndGet());

        // Create the UserRole
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(userRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userRoleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserRole() throws Exception {
        int databaseSizeBeforeUpdate = userRoleRepository.findAll().size();
        userRole.setId(count.incrementAndGet());

        // Create the UserRole
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(userRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserRole() throws Exception {
        int databaseSizeBeforeUpdate = userRoleRepository.findAll().size();
        userRole.setId(count.incrementAndGet());

        // Create the UserRole
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(userRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRoleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userRoleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserRole() throws Exception {
        // Initialize the database
        userRoleRepository.saveAndFlush(userRole);

        int databaseSizeBeforeDelete = userRoleRepository.findAll().size();

        // Delete the userRole
        restUserRoleMockMvc
            .perform(delete(ENTITY_API_URL_ID, userRole.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
