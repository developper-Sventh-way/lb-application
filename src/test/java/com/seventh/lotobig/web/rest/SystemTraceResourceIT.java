package com.seventh.lotobig.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.SystemTrace;
import com.seventh.lotobig.repository.SystemTraceRepository;
import com.seventh.lotobig.service.dto.SystemTraceDTO;
import com.seventh.lotobig.service.mapper.SystemTraceMapper;
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
 * Integration tests for the {@link SystemTraceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SystemTraceResourceIT {

    private static final String DEFAULT_TRACE_CONTENU = "AAAAAAAAAA";
    private static final String UPDATED_TRACE_CONTENU = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/system-traces";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SystemTraceRepository systemTraceRepository;

    @Autowired
    private SystemTraceMapper systemTraceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSystemTraceMockMvc;

    private SystemTrace systemTrace;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemTrace createEntity(EntityManager em) {
        SystemTrace systemTrace = new SystemTrace()
            .traceContenu(DEFAULT_TRACE_CONTENU)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return systemTrace;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemTrace createUpdatedEntity(EntityManager em) {
        SystemTrace systemTrace = new SystemTrace()
            .traceContenu(UPDATED_TRACE_CONTENU)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return systemTrace;
    }

    @BeforeEach
    public void initTest() {
        systemTrace = createEntity(em);
    }

    @Test
    @Transactional
    void createSystemTrace() throws Exception {
        int databaseSizeBeforeCreate = systemTraceRepository.findAll().size();
        // Create the SystemTrace
        SystemTraceDTO systemTraceDTO = systemTraceMapper.toDto(systemTrace);
        restSystemTraceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(systemTraceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SystemTrace in the database
        List<SystemTrace> systemTraceList = systemTraceRepository.findAll();
        assertThat(systemTraceList).hasSize(databaseSizeBeforeCreate + 1);
        SystemTrace testSystemTrace = systemTraceList.get(systemTraceList.size() - 1);
        assertThat(testSystemTrace.getTraceContenu()).isEqualTo(DEFAULT_TRACE_CONTENU);
        assertThat(testSystemTrace.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSystemTrace.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSystemTrace.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSystemTrace.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createSystemTraceWithExistingId() throws Exception {
        // Create the SystemTrace with an existing ID
        systemTrace.setId(1L);
        SystemTraceDTO systemTraceDTO = systemTraceMapper.toDto(systemTrace);

        int databaseSizeBeforeCreate = systemTraceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemTraceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(systemTraceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SystemTrace in the database
        List<SystemTrace> systemTraceList = systemTraceRepository.findAll();
        assertThat(systemTraceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTraceContenuIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemTraceRepository.findAll().size();
        // set the field null
        systemTrace.setTraceContenu(null);

        // Create the SystemTrace, which fails.
        SystemTraceDTO systemTraceDTO = systemTraceMapper.toDto(systemTrace);

        restSystemTraceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(systemTraceDTO))
            )
            .andExpect(status().isBadRequest());

        List<SystemTrace> systemTraceList = systemTraceRepository.findAll();
        assertThat(systemTraceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemTraceRepository.findAll().size();
        // set the field null
        systemTrace.setCreatedBy(null);

        // Create the SystemTrace, which fails.
        SystemTraceDTO systemTraceDTO = systemTraceMapper.toDto(systemTrace);

        restSystemTraceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(systemTraceDTO))
            )
            .andExpect(status().isBadRequest());

        List<SystemTrace> systemTraceList = systemTraceRepository.findAll();
        assertThat(systemTraceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemTraceRepository.findAll().size();
        // set the field null
        systemTrace.setCreatedDate(null);

        // Create the SystemTrace, which fails.
        SystemTraceDTO systemTraceDTO = systemTraceMapper.toDto(systemTrace);

        restSystemTraceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(systemTraceDTO))
            )
            .andExpect(status().isBadRequest());

        List<SystemTrace> systemTraceList = systemTraceRepository.findAll();
        assertThat(systemTraceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSystemTraces() throws Exception {
        // Initialize the database
        systemTraceRepository.saveAndFlush(systemTrace);

        // Get all the systemTraceList
        restSystemTraceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemTrace.getId().intValue())))
            .andExpect(jsonPath("$.[*].traceContenu").value(hasItem(DEFAULT_TRACE_CONTENU)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getSystemTrace() throws Exception {
        // Initialize the database
        systemTraceRepository.saveAndFlush(systemTrace);

        // Get the systemTrace
        restSystemTraceMockMvc
            .perform(get(ENTITY_API_URL_ID, systemTrace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(systemTrace.getId().intValue()))
            .andExpect(jsonPath("$.traceContenu").value(DEFAULT_TRACE_CONTENU))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSystemTrace() throws Exception {
        // Get the systemTrace
        restSystemTraceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSystemTrace() throws Exception {
        // Initialize the database
        systemTraceRepository.saveAndFlush(systemTrace);

        int databaseSizeBeforeUpdate = systemTraceRepository.findAll().size();

        // Update the systemTrace
        SystemTrace updatedSystemTrace = systemTraceRepository.findById(systemTrace.getId()).get();
        // Disconnect from session so that the updates on updatedSystemTrace are not directly saved in db
        em.detach(updatedSystemTrace);
        updatedSystemTrace
            .traceContenu(UPDATED_TRACE_CONTENU)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        SystemTraceDTO systemTraceDTO = systemTraceMapper.toDto(updatedSystemTrace);

        restSystemTraceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, systemTraceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(systemTraceDTO))
            )
            .andExpect(status().isOk());

        // Validate the SystemTrace in the database
        List<SystemTrace> systemTraceList = systemTraceRepository.findAll();
        assertThat(systemTraceList).hasSize(databaseSizeBeforeUpdate);
        SystemTrace testSystemTrace = systemTraceList.get(systemTraceList.size() - 1);
        assertThat(testSystemTrace.getTraceContenu()).isEqualTo(UPDATED_TRACE_CONTENU);
        assertThat(testSystemTrace.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSystemTrace.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSystemTrace.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSystemTrace.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSystemTrace() throws Exception {
        int databaseSizeBeforeUpdate = systemTraceRepository.findAll().size();
        systemTrace.setId(count.incrementAndGet());

        // Create the SystemTrace
        SystemTraceDTO systemTraceDTO = systemTraceMapper.toDto(systemTrace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemTraceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, systemTraceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(systemTraceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SystemTrace in the database
        List<SystemTrace> systemTraceList = systemTraceRepository.findAll();
        assertThat(systemTraceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSystemTrace() throws Exception {
        int databaseSizeBeforeUpdate = systemTraceRepository.findAll().size();
        systemTrace.setId(count.incrementAndGet());

        // Create the SystemTrace
        SystemTraceDTO systemTraceDTO = systemTraceMapper.toDto(systemTrace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemTraceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(systemTraceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SystemTrace in the database
        List<SystemTrace> systemTraceList = systemTraceRepository.findAll();
        assertThat(systemTraceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSystemTrace() throws Exception {
        int databaseSizeBeforeUpdate = systemTraceRepository.findAll().size();
        systemTrace.setId(count.incrementAndGet());

        // Create the SystemTrace
        SystemTraceDTO systemTraceDTO = systemTraceMapper.toDto(systemTrace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemTraceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(systemTraceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SystemTrace in the database
        List<SystemTrace> systemTraceList = systemTraceRepository.findAll();
        assertThat(systemTraceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSystemTraceWithPatch() throws Exception {
        // Initialize the database
        systemTraceRepository.saveAndFlush(systemTrace);

        int databaseSizeBeforeUpdate = systemTraceRepository.findAll().size();

        // Update the systemTrace using partial update
        SystemTrace partialUpdatedSystemTrace = new SystemTrace();
        partialUpdatedSystemTrace.setId(systemTrace.getId());

        partialUpdatedSystemTrace
            .traceContenu(UPDATED_TRACE_CONTENU)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restSystemTraceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSystemTrace.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSystemTrace))
            )
            .andExpect(status().isOk());

        // Validate the SystemTrace in the database
        List<SystemTrace> systemTraceList = systemTraceRepository.findAll();
        assertThat(systemTraceList).hasSize(databaseSizeBeforeUpdate);
        SystemTrace testSystemTrace = systemTraceList.get(systemTraceList.size() - 1);
        assertThat(testSystemTrace.getTraceContenu()).isEqualTo(UPDATED_TRACE_CONTENU);
        assertThat(testSystemTrace.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSystemTrace.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSystemTrace.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSystemTrace.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSystemTraceWithPatch() throws Exception {
        // Initialize the database
        systemTraceRepository.saveAndFlush(systemTrace);

        int databaseSizeBeforeUpdate = systemTraceRepository.findAll().size();

        // Update the systemTrace using partial update
        SystemTrace partialUpdatedSystemTrace = new SystemTrace();
        partialUpdatedSystemTrace.setId(systemTrace.getId());

        partialUpdatedSystemTrace
            .traceContenu(UPDATED_TRACE_CONTENU)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restSystemTraceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSystemTrace.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSystemTrace))
            )
            .andExpect(status().isOk());

        // Validate the SystemTrace in the database
        List<SystemTrace> systemTraceList = systemTraceRepository.findAll();
        assertThat(systemTraceList).hasSize(databaseSizeBeforeUpdate);
        SystemTrace testSystemTrace = systemTraceList.get(systemTraceList.size() - 1);
        assertThat(testSystemTrace.getTraceContenu()).isEqualTo(UPDATED_TRACE_CONTENU);
        assertThat(testSystemTrace.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSystemTrace.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSystemTrace.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSystemTrace.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSystemTrace() throws Exception {
        int databaseSizeBeforeUpdate = systemTraceRepository.findAll().size();
        systemTrace.setId(count.incrementAndGet());

        // Create the SystemTrace
        SystemTraceDTO systemTraceDTO = systemTraceMapper.toDto(systemTrace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemTraceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, systemTraceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(systemTraceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SystemTrace in the database
        List<SystemTrace> systemTraceList = systemTraceRepository.findAll();
        assertThat(systemTraceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSystemTrace() throws Exception {
        int databaseSizeBeforeUpdate = systemTraceRepository.findAll().size();
        systemTrace.setId(count.incrementAndGet());

        // Create the SystemTrace
        SystemTraceDTO systemTraceDTO = systemTraceMapper.toDto(systemTrace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemTraceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(systemTraceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SystemTrace in the database
        List<SystemTrace> systemTraceList = systemTraceRepository.findAll();
        assertThat(systemTraceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSystemTrace() throws Exception {
        int databaseSizeBeforeUpdate = systemTraceRepository.findAll().size();
        systemTrace.setId(count.incrementAndGet());

        // Create the SystemTrace
        SystemTraceDTO systemTraceDTO = systemTraceMapper.toDto(systemTrace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemTraceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(systemTraceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SystemTrace in the database
        List<SystemTrace> systemTraceList = systemTraceRepository.findAll();
        assertThat(systemTraceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSystemTrace() throws Exception {
        // Initialize the database
        systemTraceRepository.saveAndFlush(systemTrace);

        int databaseSizeBeforeDelete = systemTraceRepository.findAll().size();

        // Delete the systemTrace
        restSystemTraceMockMvc
            .perform(delete(ENTITY_API_URL_ID, systemTrace.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SystemTrace> systemTraceList = systemTraceRepository.findAll();
        assertThat(systemTraceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
