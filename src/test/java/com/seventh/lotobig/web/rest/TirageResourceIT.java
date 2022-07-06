package com.seventh.lotobig.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.Tirage;
import com.seventh.lotobig.domain.enumeration.TirageName;
import com.seventh.lotobig.domain.enumeration.TirageType;
import com.seventh.lotobig.domain.enumeration.UserStatut;
import com.seventh.lotobig.repository.TirageRepository;
import com.seventh.lotobig.service.dto.TirageDTO;
import com.seventh.lotobig.service.mapper.TirageMapper;
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
 * Integration tests for the {@link TirageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TirageResourceIT {

    private static final TirageName DEFAULT_TIRAGE_NAME = TirageName.NEWYORK;
    private static final TirageName UPDATED_TIRAGE_NAME = TirageName.FLORIDA;

    private static final TirageType DEFAULT_TYPE = TirageType.MIDI;
    private static final TirageType UPDATED_TYPE = TirageType.SOIR;

    private static final String DEFAULT_PREMIER_LOT = "AA";
    private static final String UPDATED_PREMIER_LOT = "BB";

    private static final String DEFAULT_DEUXIEME_LOT = "AA";
    private static final String UPDATED_DEUXIEME_LOT = "BB";

    private static final String DEFAULT_TROISIEME_LOT = "AA";
    private static final String UPDATED_TROISIEME_LOT = "BB";

    private static final String DEFAULT_LOTO_3_CHIF = "AA";
    private static final String UPDATED_LOTO_3_CHIF = "BB";

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

    private static final String ENTITY_API_URL = "/api/tirages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TirageRepository tirageRepository;

    @Autowired
    private TirageMapper tirageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTirageMockMvc;

    private Tirage tirage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tirage createEntity(EntityManager em) {
        Tirage tirage = new Tirage()
            .tirageName(DEFAULT_TIRAGE_NAME)
            .type(DEFAULT_TYPE)
            .premierLot(DEFAULT_PREMIER_LOT)
            .deuxiemeLot(DEFAULT_DEUXIEME_LOT)
            .troisiemeLot(DEFAULT_TROISIEME_LOT)
            .loto3Chif(DEFAULT_LOTO_3_CHIF)
            .statut(DEFAULT_STATUT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return tirage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tirage createUpdatedEntity(EntityManager em) {
        Tirage tirage = new Tirage()
            .tirageName(UPDATED_TIRAGE_NAME)
            .type(UPDATED_TYPE)
            .premierLot(UPDATED_PREMIER_LOT)
            .deuxiemeLot(UPDATED_DEUXIEME_LOT)
            .troisiemeLot(UPDATED_TROISIEME_LOT)
            .loto3Chif(UPDATED_LOTO_3_CHIF)
            .statut(UPDATED_STATUT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return tirage;
    }

    @BeforeEach
    public void initTest() {
        tirage = createEntity(em);
    }

    @Test
    @Transactional
    void createTirage() throws Exception {
        int databaseSizeBeforeCreate = tirageRepository.findAll().size();
        // Create the Tirage
        TirageDTO tirageDTO = tirageMapper.toDto(tirage);
        restTirageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tirageDTO)))
            .andExpect(status().isCreated());

        // Validate the Tirage in the database
        List<Tirage> tirageList = tirageRepository.findAll();
        assertThat(tirageList).hasSize(databaseSizeBeforeCreate + 1);
        Tirage testTirage = tirageList.get(tirageList.size() - 1);
        assertThat(testTirage.getTirageName()).isEqualTo(DEFAULT_TIRAGE_NAME);
        assertThat(testTirage.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTirage.getPremierLot()).isEqualTo(DEFAULT_PREMIER_LOT);
        assertThat(testTirage.getDeuxiemeLot()).isEqualTo(DEFAULT_DEUXIEME_LOT);
        assertThat(testTirage.getTroisiemeLot()).isEqualTo(DEFAULT_TROISIEME_LOT);
        assertThat(testTirage.getLoto3Chif()).isEqualTo(DEFAULT_LOTO_3_CHIF);
        assertThat(testTirage.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testTirage.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTirage.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTirage.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testTirage.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createTirageWithExistingId() throws Exception {
        // Create the Tirage with an existing ID
        tirage.setId(1L);
        TirageDTO tirageDTO = tirageMapper.toDto(tirage);

        int databaseSizeBeforeCreate = tirageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTirageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tirageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tirage in the database
        List<Tirage> tirageList = tirageRepository.findAll();
        assertThat(tirageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTirageNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tirageRepository.findAll().size();
        // set the field null
        tirage.setTirageName(null);

        // Create the Tirage, which fails.
        TirageDTO tirageDTO = tirageMapper.toDto(tirage);

        restTirageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tirageDTO)))
            .andExpect(status().isBadRequest());

        List<Tirage> tirageList = tirageRepository.findAll();
        assertThat(tirageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tirageRepository.findAll().size();
        // set the field null
        tirage.setType(null);

        // Create the Tirage, which fails.
        TirageDTO tirageDTO = tirageMapper.toDto(tirage);

        restTirageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tirageDTO)))
            .andExpect(status().isBadRequest());

        List<Tirage> tirageList = tirageRepository.findAll();
        assertThat(tirageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = tirageRepository.findAll().size();
        // set the field null
        tirage.setStatut(null);

        // Create the Tirage, which fails.
        TirageDTO tirageDTO = tirageMapper.toDto(tirage);

        restTirageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tirageDTO)))
            .andExpect(status().isBadRequest());

        List<Tirage> tirageList = tirageRepository.findAll();
        assertThat(tirageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = tirageRepository.findAll().size();
        // set the field null
        tirage.setCreatedBy(null);

        // Create the Tirage, which fails.
        TirageDTO tirageDTO = tirageMapper.toDto(tirage);

        restTirageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tirageDTO)))
            .andExpect(status().isBadRequest());

        List<Tirage> tirageList = tirageRepository.findAll();
        assertThat(tirageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tirageRepository.findAll().size();
        // set the field null
        tirage.setCreatedDate(null);

        // Create the Tirage, which fails.
        TirageDTO tirageDTO = tirageMapper.toDto(tirage);

        restTirageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tirageDTO)))
            .andExpect(status().isBadRequest());

        List<Tirage> tirageList = tirageRepository.findAll();
        assertThat(tirageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTirages() throws Exception {
        // Initialize the database
        tirageRepository.saveAndFlush(tirage);

        // Get all the tirageList
        restTirageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tirage.getId().intValue())))
            .andExpect(jsonPath("$.[*].tirageName").value(hasItem(DEFAULT_TIRAGE_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].premierLot").value(hasItem(DEFAULT_PREMIER_LOT)))
            .andExpect(jsonPath("$.[*].deuxiemeLot").value(hasItem(DEFAULT_DEUXIEME_LOT)))
            .andExpect(jsonPath("$.[*].troisiemeLot").value(hasItem(DEFAULT_TROISIEME_LOT)))
            .andExpect(jsonPath("$.[*].loto3Chif").value(hasItem(DEFAULT_LOTO_3_CHIF)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getTirage() throws Exception {
        // Initialize the database
        tirageRepository.saveAndFlush(tirage);

        // Get the tirage
        restTirageMockMvc
            .perform(get(ENTITY_API_URL_ID, tirage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tirage.getId().intValue()))
            .andExpect(jsonPath("$.tirageName").value(DEFAULT_TIRAGE_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.premierLot").value(DEFAULT_PREMIER_LOT))
            .andExpect(jsonPath("$.deuxiemeLot").value(DEFAULT_DEUXIEME_LOT))
            .andExpect(jsonPath("$.troisiemeLot").value(DEFAULT_TROISIEME_LOT))
            .andExpect(jsonPath("$.loto3Chif").value(DEFAULT_LOTO_3_CHIF))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTirage() throws Exception {
        // Get the tirage
        restTirageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTirage() throws Exception {
        // Initialize the database
        tirageRepository.saveAndFlush(tirage);

        int databaseSizeBeforeUpdate = tirageRepository.findAll().size();

        // Update the tirage
        Tirage updatedTirage = tirageRepository.findById(tirage.getId()).get();
        // Disconnect from session so that the updates on updatedTirage are not directly saved in db
        em.detach(updatedTirage);
        updatedTirage
            .tirageName(UPDATED_TIRAGE_NAME)
            .type(UPDATED_TYPE)
            .premierLot(UPDATED_PREMIER_LOT)
            .deuxiemeLot(UPDATED_DEUXIEME_LOT)
            .troisiemeLot(UPDATED_TROISIEME_LOT)
            .loto3Chif(UPDATED_LOTO_3_CHIF)
            .statut(UPDATED_STATUT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        TirageDTO tirageDTO = tirageMapper.toDto(updatedTirage);

        restTirageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tirageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tirageDTO))
            )
            .andExpect(status().isOk());

        // Validate the Tirage in the database
        List<Tirage> tirageList = tirageRepository.findAll();
        assertThat(tirageList).hasSize(databaseSizeBeforeUpdate);
        Tirage testTirage = tirageList.get(tirageList.size() - 1);
        assertThat(testTirage.getTirageName()).isEqualTo(UPDATED_TIRAGE_NAME);
        assertThat(testTirage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTirage.getPremierLot()).isEqualTo(UPDATED_PREMIER_LOT);
        assertThat(testTirage.getDeuxiemeLot()).isEqualTo(UPDATED_DEUXIEME_LOT);
        assertThat(testTirage.getTroisiemeLot()).isEqualTo(UPDATED_TROISIEME_LOT);
        assertThat(testTirage.getLoto3Chif()).isEqualTo(UPDATED_LOTO_3_CHIF);
        assertThat(testTirage.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testTirage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTirage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTirage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTirage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTirage() throws Exception {
        int databaseSizeBeforeUpdate = tirageRepository.findAll().size();
        tirage.setId(count.incrementAndGet());

        // Create the Tirage
        TirageDTO tirageDTO = tirageMapper.toDto(tirage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTirageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tirageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tirageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tirage in the database
        List<Tirage> tirageList = tirageRepository.findAll();
        assertThat(tirageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTirage() throws Exception {
        int databaseSizeBeforeUpdate = tirageRepository.findAll().size();
        tirage.setId(count.incrementAndGet());

        // Create the Tirage
        TirageDTO tirageDTO = tirageMapper.toDto(tirage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTirageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tirageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tirage in the database
        List<Tirage> tirageList = tirageRepository.findAll();
        assertThat(tirageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTirage() throws Exception {
        int databaseSizeBeforeUpdate = tirageRepository.findAll().size();
        tirage.setId(count.incrementAndGet());

        // Create the Tirage
        TirageDTO tirageDTO = tirageMapper.toDto(tirage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTirageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tirageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tirage in the database
        List<Tirage> tirageList = tirageRepository.findAll();
        assertThat(tirageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTirageWithPatch() throws Exception {
        // Initialize the database
        tirageRepository.saveAndFlush(tirage);

        int databaseSizeBeforeUpdate = tirageRepository.findAll().size();

        // Update the tirage using partial update
        Tirage partialUpdatedTirage = new Tirage();
        partialUpdatedTirage.setId(tirage.getId());

        partialUpdatedTirage
            .tirageName(UPDATED_TIRAGE_NAME)
            .troisiemeLot(UPDATED_TROISIEME_LOT)
            .loto3Chif(UPDATED_LOTO_3_CHIF)
            .statut(UPDATED_STATUT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restTirageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTirage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTirage))
            )
            .andExpect(status().isOk());

        // Validate the Tirage in the database
        List<Tirage> tirageList = tirageRepository.findAll();
        assertThat(tirageList).hasSize(databaseSizeBeforeUpdate);
        Tirage testTirage = tirageList.get(tirageList.size() - 1);
        assertThat(testTirage.getTirageName()).isEqualTo(UPDATED_TIRAGE_NAME);
        assertThat(testTirage.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTirage.getPremierLot()).isEqualTo(DEFAULT_PREMIER_LOT);
        assertThat(testTirage.getDeuxiemeLot()).isEqualTo(DEFAULT_DEUXIEME_LOT);
        assertThat(testTirage.getTroisiemeLot()).isEqualTo(UPDATED_TROISIEME_LOT);
        assertThat(testTirage.getLoto3Chif()).isEqualTo(UPDATED_LOTO_3_CHIF);
        assertThat(testTirage.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testTirage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTirage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTirage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTirage.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTirageWithPatch() throws Exception {
        // Initialize the database
        tirageRepository.saveAndFlush(tirage);

        int databaseSizeBeforeUpdate = tirageRepository.findAll().size();

        // Update the tirage using partial update
        Tirage partialUpdatedTirage = new Tirage();
        partialUpdatedTirage.setId(tirage.getId());

        partialUpdatedTirage
            .tirageName(UPDATED_TIRAGE_NAME)
            .type(UPDATED_TYPE)
            .premierLot(UPDATED_PREMIER_LOT)
            .deuxiemeLot(UPDATED_DEUXIEME_LOT)
            .troisiemeLot(UPDATED_TROISIEME_LOT)
            .loto3Chif(UPDATED_LOTO_3_CHIF)
            .statut(UPDATED_STATUT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restTirageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTirage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTirage))
            )
            .andExpect(status().isOk());

        // Validate the Tirage in the database
        List<Tirage> tirageList = tirageRepository.findAll();
        assertThat(tirageList).hasSize(databaseSizeBeforeUpdate);
        Tirage testTirage = tirageList.get(tirageList.size() - 1);
        assertThat(testTirage.getTirageName()).isEqualTo(UPDATED_TIRAGE_NAME);
        assertThat(testTirage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTirage.getPremierLot()).isEqualTo(UPDATED_PREMIER_LOT);
        assertThat(testTirage.getDeuxiemeLot()).isEqualTo(UPDATED_DEUXIEME_LOT);
        assertThat(testTirage.getTroisiemeLot()).isEqualTo(UPDATED_TROISIEME_LOT);
        assertThat(testTirage.getLoto3Chif()).isEqualTo(UPDATED_LOTO_3_CHIF);
        assertThat(testTirage.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testTirage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTirage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTirage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTirage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTirage() throws Exception {
        int databaseSizeBeforeUpdate = tirageRepository.findAll().size();
        tirage.setId(count.incrementAndGet());

        // Create the Tirage
        TirageDTO tirageDTO = tirageMapper.toDto(tirage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTirageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tirageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tirageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tirage in the database
        List<Tirage> tirageList = tirageRepository.findAll();
        assertThat(tirageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTirage() throws Exception {
        int databaseSizeBeforeUpdate = tirageRepository.findAll().size();
        tirage.setId(count.incrementAndGet());

        // Create the Tirage
        TirageDTO tirageDTO = tirageMapper.toDto(tirage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTirageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tirageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tirage in the database
        List<Tirage> tirageList = tirageRepository.findAll();
        assertThat(tirageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTirage() throws Exception {
        int databaseSizeBeforeUpdate = tirageRepository.findAll().size();
        tirage.setId(count.incrementAndGet());

        // Create the Tirage
        TirageDTO tirageDTO = tirageMapper.toDto(tirage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTirageMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tirageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tirage in the database
        List<Tirage> tirageList = tirageRepository.findAll();
        assertThat(tirageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTirage() throws Exception {
        // Initialize the database
        tirageRepository.saveAndFlush(tirage);

        int databaseSizeBeforeDelete = tirageRepository.findAll().size();

        // Delete the tirage
        restTirageMockMvc
            .perform(delete(ENTITY_API_URL_ID, tirage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tirage> tirageList = tirageRepository.findAll();
        assertThat(tirageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
