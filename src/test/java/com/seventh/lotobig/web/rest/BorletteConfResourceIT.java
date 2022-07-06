package com.seventh.lotobig.web.rest;

import static com.seventh.lotobig.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.BorletteConf;
import com.seventh.lotobig.domain.enumeration.TirageName;
import com.seventh.lotobig.repository.BorletteConfRepository;
import com.seventh.lotobig.service.dto.BorletteConfDTO;
import com.seventh.lotobig.service.mapper.BorletteConfMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link BorletteConfResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BorletteConfResourceIT {

    private static final TirageName DEFAULT_NAME = TirageName.NEWYORK;
    private static final TirageName UPDATED_NAME = TirageName.FLORIDA;

    private static final Integer DEFAULT_PREMIER_LOT = 1;
    private static final Integer UPDATED_PREMIER_LOT = 2;

    private static final Integer DEFAULT_DEUXIEME_LOT = 1;
    private static final Integer UPDATED_DEUXIEME_LOT = 2;

    private static final Integer DEFAULT_TROISIEME_LOT = 1;
    private static final Integer UPDATED_TROISIEME_LOT = 2;

    private static final BigDecimal DEFAULT_MARIAGE_GRATIS_PRIX = new BigDecimal(1);
    private static final BigDecimal UPDATED_MARIAGE_GRATIS_PRIX = new BigDecimal(2);

    private static final Long DEFAULT_MONTANT_MINIMUM = 1L;
    private static final Long UPDATED_MONTANT_MINIMUM = 2L;

    private static final Long DEFAULT_MONTANT_MAXIMUM = 1L;
    private static final Long UPDATED_MONTANT_MAXIMUM = 2L;

    private static final String DEFAULT_CLOSE_TIME_MIDI = "AAAAA";
    private static final String UPDATED_CLOSE_TIME_MIDI = "BBBBB";

    private static final String DEFAULT_CLOSE_TIME_SOIR = "AAAAA";
    private static final String UPDATED_CLOSE_TIME_SOIR = "BBBBB";

    private static final String ENTITY_API_URL = "/api/borlette-confs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BorletteConfRepository borletteConfRepository;

    @Autowired
    private BorletteConfMapper borletteConfMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBorletteConfMockMvc;

    private BorletteConf borletteConf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BorletteConf createEntity(EntityManager em) {
        BorletteConf borletteConf = new BorletteConf()
            .name(DEFAULT_NAME)
            .premierLot(DEFAULT_PREMIER_LOT)
            .deuxiemeLot(DEFAULT_DEUXIEME_LOT)
            .troisiemeLot(DEFAULT_TROISIEME_LOT)
            .mariageGratisPrix(DEFAULT_MARIAGE_GRATIS_PRIX)
            .montantMinimum(DEFAULT_MONTANT_MINIMUM)
            .montantMaximum(DEFAULT_MONTANT_MAXIMUM)
            .closeTimeMidi(DEFAULT_CLOSE_TIME_MIDI)
            .closeTimeSoir(DEFAULT_CLOSE_TIME_SOIR);
        return borletteConf;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BorletteConf createUpdatedEntity(EntityManager em) {
        BorletteConf borletteConf = new BorletteConf()
            .name(UPDATED_NAME)
            .premierLot(UPDATED_PREMIER_LOT)
            .deuxiemeLot(UPDATED_DEUXIEME_LOT)
            .troisiemeLot(UPDATED_TROISIEME_LOT)
            .mariageGratisPrix(UPDATED_MARIAGE_GRATIS_PRIX)
            .montantMinimum(UPDATED_MONTANT_MINIMUM)
            .montantMaximum(UPDATED_MONTANT_MAXIMUM)
            .closeTimeMidi(UPDATED_CLOSE_TIME_MIDI)
            .closeTimeSoir(UPDATED_CLOSE_TIME_SOIR);
        return borletteConf;
    }

    @BeforeEach
    public void initTest() {
        borletteConf = createEntity(em);
    }

    @Test
    @Transactional
    void createBorletteConf() throws Exception {
        int databaseSizeBeforeCreate = borletteConfRepository.findAll().size();
        // Create the BorletteConf
        BorletteConfDTO borletteConfDTO = borletteConfMapper.toDto(borletteConf);
        restBorletteConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borletteConfDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BorletteConf in the database
        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeCreate + 1);
        BorletteConf testBorletteConf = borletteConfList.get(borletteConfList.size() - 1);
        assertThat(testBorletteConf.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBorletteConf.getPremierLot()).isEqualTo(DEFAULT_PREMIER_LOT);
        assertThat(testBorletteConf.getDeuxiemeLot()).isEqualTo(DEFAULT_DEUXIEME_LOT);
        assertThat(testBorletteConf.getTroisiemeLot()).isEqualTo(DEFAULT_TROISIEME_LOT);
        assertThat(testBorletteConf.getMariageGratisPrix()).isEqualByComparingTo(DEFAULT_MARIAGE_GRATIS_PRIX);
        assertThat(testBorletteConf.getMontantMinimum()).isEqualTo(DEFAULT_MONTANT_MINIMUM);
        assertThat(testBorletteConf.getMontantMaximum()).isEqualTo(DEFAULT_MONTANT_MAXIMUM);
        assertThat(testBorletteConf.getCloseTimeMidi()).isEqualTo(DEFAULT_CLOSE_TIME_MIDI);
        assertThat(testBorletteConf.getCloseTimeSoir()).isEqualTo(DEFAULT_CLOSE_TIME_SOIR);
    }

    @Test
    @Transactional
    void createBorletteConfWithExistingId() throws Exception {
        // Create the BorletteConf with an existing ID
        borletteConf.setId(1L);
        BorletteConfDTO borletteConfDTO = borletteConfMapper.toDto(borletteConf);

        int databaseSizeBeforeCreate = borletteConfRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBorletteConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borletteConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorletteConf in the database
        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = borletteConfRepository.findAll().size();
        // set the field null
        borletteConf.setName(null);

        // Create the BorletteConf, which fails.
        BorletteConfDTO borletteConfDTO = borletteConfMapper.toDto(borletteConf);

        restBorletteConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borletteConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPremierLotIsRequired() throws Exception {
        int databaseSizeBeforeTest = borletteConfRepository.findAll().size();
        // set the field null
        borletteConf.setPremierLot(null);

        // Create the BorletteConf, which fails.
        BorletteConfDTO borletteConfDTO = borletteConfMapper.toDto(borletteConf);

        restBorletteConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borletteConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeuxiemeLotIsRequired() throws Exception {
        int databaseSizeBeforeTest = borletteConfRepository.findAll().size();
        // set the field null
        borletteConf.setDeuxiemeLot(null);

        // Create the BorletteConf, which fails.
        BorletteConfDTO borletteConfDTO = borletteConfMapper.toDto(borletteConf);

        restBorletteConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borletteConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTroisiemeLotIsRequired() throws Exception {
        int databaseSizeBeforeTest = borletteConfRepository.findAll().size();
        // set the field null
        borletteConf.setTroisiemeLot(null);

        // Create the BorletteConf, which fails.
        BorletteConfDTO borletteConfDTO = borletteConfMapper.toDto(borletteConf);

        restBorletteConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borletteConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantMinimumIsRequired() throws Exception {
        int databaseSizeBeforeTest = borletteConfRepository.findAll().size();
        // set the field null
        borletteConf.setMontantMinimum(null);

        // Create the BorletteConf, which fails.
        BorletteConfDTO borletteConfDTO = borletteConfMapper.toDto(borletteConf);

        restBorletteConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borletteConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantMaximumIsRequired() throws Exception {
        int databaseSizeBeforeTest = borletteConfRepository.findAll().size();
        // set the field null
        borletteConf.setMontantMaximum(null);

        // Create the BorletteConf, which fails.
        BorletteConfDTO borletteConfDTO = borletteConfMapper.toDto(borletteConf);

        restBorletteConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borletteConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCloseTimeMidiIsRequired() throws Exception {
        int databaseSizeBeforeTest = borletteConfRepository.findAll().size();
        // set the field null
        borletteConf.setCloseTimeMidi(null);

        // Create the BorletteConf, which fails.
        BorletteConfDTO borletteConfDTO = borletteConfMapper.toDto(borletteConf);

        restBorletteConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borletteConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCloseTimeSoirIsRequired() throws Exception {
        int databaseSizeBeforeTest = borletteConfRepository.findAll().size();
        // set the field null
        borletteConf.setCloseTimeSoir(null);

        // Create the BorletteConf, which fails.
        BorletteConfDTO borletteConfDTO = borletteConfMapper.toDto(borletteConf);

        restBorletteConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borletteConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBorletteConfs() throws Exception {
        // Initialize the database
        borletteConfRepository.saveAndFlush(borletteConf);

        // Get all the borletteConfList
        restBorletteConfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(borletteConf.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].premierLot").value(hasItem(DEFAULT_PREMIER_LOT)))
            .andExpect(jsonPath("$.[*].deuxiemeLot").value(hasItem(DEFAULT_DEUXIEME_LOT)))
            .andExpect(jsonPath("$.[*].troisiemeLot").value(hasItem(DEFAULT_TROISIEME_LOT)))
            .andExpect(jsonPath("$.[*].mariageGratisPrix").value(hasItem(sameNumber(DEFAULT_MARIAGE_GRATIS_PRIX))))
            .andExpect(jsonPath("$.[*].montantMinimum").value(hasItem(DEFAULT_MONTANT_MINIMUM.intValue())))
            .andExpect(jsonPath("$.[*].montantMaximum").value(hasItem(DEFAULT_MONTANT_MAXIMUM.intValue())))
            .andExpect(jsonPath("$.[*].closeTimeMidi").value(hasItem(DEFAULT_CLOSE_TIME_MIDI)))
            .andExpect(jsonPath("$.[*].closeTimeSoir").value(hasItem(DEFAULT_CLOSE_TIME_SOIR)));
    }

    @Test
    @Transactional
    void getBorletteConf() throws Exception {
        // Initialize the database
        borletteConfRepository.saveAndFlush(borletteConf);

        // Get the borletteConf
        restBorletteConfMockMvc
            .perform(get(ENTITY_API_URL_ID, borletteConf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(borletteConf.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.premierLot").value(DEFAULT_PREMIER_LOT))
            .andExpect(jsonPath("$.deuxiemeLot").value(DEFAULT_DEUXIEME_LOT))
            .andExpect(jsonPath("$.troisiemeLot").value(DEFAULT_TROISIEME_LOT))
            .andExpect(jsonPath("$.mariageGratisPrix").value(sameNumber(DEFAULT_MARIAGE_GRATIS_PRIX)))
            .andExpect(jsonPath("$.montantMinimum").value(DEFAULT_MONTANT_MINIMUM.intValue()))
            .andExpect(jsonPath("$.montantMaximum").value(DEFAULT_MONTANT_MAXIMUM.intValue()))
            .andExpect(jsonPath("$.closeTimeMidi").value(DEFAULT_CLOSE_TIME_MIDI))
            .andExpect(jsonPath("$.closeTimeSoir").value(DEFAULT_CLOSE_TIME_SOIR));
    }

    @Test
    @Transactional
    void getNonExistingBorletteConf() throws Exception {
        // Get the borletteConf
        restBorletteConfMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBorletteConf() throws Exception {
        // Initialize the database
        borletteConfRepository.saveAndFlush(borletteConf);

        int databaseSizeBeforeUpdate = borletteConfRepository.findAll().size();

        // Update the borletteConf
        BorletteConf updatedBorletteConf = borletteConfRepository.findById(borletteConf.getId()).get();
        // Disconnect from session so that the updates on updatedBorletteConf are not directly saved in db
        em.detach(updatedBorletteConf);
        updatedBorletteConf
            .name(UPDATED_NAME)
            .premierLot(UPDATED_PREMIER_LOT)
            .deuxiemeLot(UPDATED_DEUXIEME_LOT)
            .troisiemeLot(UPDATED_TROISIEME_LOT)
            .mariageGratisPrix(UPDATED_MARIAGE_GRATIS_PRIX)
            .montantMinimum(UPDATED_MONTANT_MINIMUM)
            .montantMaximum(UPDATED_MONTANT_MAXIMUM)
            .closeTimeMidi(UPDATED_CLOSE_TIME_MIDI)
            .closeTimeSoir(UPDATED_CLOSE_TIME_SOIR);
        BorletteConfDTO borletteConfDTO = borletteConfMapper.toDto(updatedBorletteConf);

        restBorletteConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, borletteConfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(borletteConfDTO))
            )
            .andExpect(status().isOk());

        // Validate the BorletteConf in the database
        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeUpdate);
        BorletteConf testBorletteConf = borletteConfList.get(borletteConfList.size() - 1);
        assertThat(testBorletteConf.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBorletteConf.getPremierLot()).isEqualTo(UPDATED_PREMIER_LOT);
        assertThat(testBorletteConf.getDeuxiemeLot()).isEqualTo(UPDATED_DEUXIEME_LOT);
        assertThat(testBorletteConf.getTroisiemeLot()).isEqualTo(UPDATED_TROISIEME_LOT);
        assertThat(testBorletteConf.getMariageGratisPrix()).isEqualByComparingTo(UPDATED_MARIAGE_GRATIS_PRIX);
        assertThat(testBorletteConf.getMontantMinimum()).isEqualTo(UPDATED_MONTANT_MINIMUM);
        assertThat(testBorletteConf.getMontantMaximum()).isEqualTo(UPDATED_MONTANT_MAXIMUM);
        assertThat(testBorletteConf.getCloseTimeMidi()).isEqualTo(UPDATED_CLOSE_TIME_MIDI);
        assertThat(testBorletteConf.getCloseTimeSoir()).isEqualTo(UPDATED_CLOSE_TIME_SOIR);
    }

    @Test
    @Transactional
    void putNonExistingBorletteConf() throws Exception {
        int databaseSizeBeforeUpdate = borletteConfRepository.findAll().size();
        borletteConf.setId(count.incrementAndGet());

        // Create the BorletteConf
        BorletteConfDTO borletteConfDTO = borletteConfMapper.toDto(borletteConf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBorletteConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, borletteConfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(borletteConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorletteConf in the database
        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBorletteConf() throws Exception {
        int databaseSizeBeforeUpdate = borletteConfRepository.findAll().size();
        borletteConf.setId(count.incrementAndGet());

        // Create the BorletteConf
        BorletteConfDTO borletteConfDTO = borletteConfMapper.toDto(borletteConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBorletteConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(borletteConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorletteConf in the database
        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBorletteConf() throws Exception {
        int databaseSizeBeforeUpdate = borletteConfRepository.findAll().size();
        borletteConf.setId(count.incrementAndGet());

        // Create the BorletteConf
        BorletteConfDTO borletteConfDTO = borletteConfMapper.toDto(borletteConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBorletteConfMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borletteConfDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BorletteConf in the database
        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBorletteConfWithPatch() throws Exception {
        // Initialize the database
        borletteConfRepository.saveAndFlush(borletteConf);

        int databaseSizeBeforeUpdate = borletteConfRepository.findAll().size();

        // Update the borletteConf using partial update
        BorletteConf partialUpdatedBorletteConf = new BorletteConf();
        partialUpdatedBorletteConf.setId(borletteConf.getId());

        partialUpdatedBorletteConf
            .premierLot(UPDATED_PREMIER_LOT)
            .deuxiemeLot(UPDATED_DEUXIEME_LOT)
            .troisiemeLot(UPDATED_TROISIEME_LOT)
            .mariageGratisPrix(UPDATED_MARIAGE_GRATIS_PRIX)
            .closeTimeMidi(UPDATED_CLOSE_TIME_MIDI);

        restBorletteConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBorletteConf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBorletteConf))
            )
            .andExpect(status().isOk());

        // Validate the BorletteConf in the database
        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeUpdate);
        BorletteConf testBorletteConf = borletteConfList.get(borletteConfList.size() - 1);
        assertThat(testBorletteConf.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBorletteConf.getPremierLot()).isEqualTo(UPDATED_PREMIER_LOT);
        assertThat(testBorletteConf.getDeuxiemeLot()).isEqualTo(UPDATED_DEUXIEME_LOT);
        assertThat(testBorletteConf.getTroisiemeLot()).isEqualTo(UPDATED_TROISIEME_LOT);
        assertThat(testBorletteConf.getMariageGratisPrix()).isEqualByComparingTo(UPDATED_MARIAGE_GRATIS_PRIX);
        assertThat(testBorletteConf.getMontantMinimum()).isEqualTo(DEFAULT_MONTANT_MINIMUM);
        assertThat(testBorletteConf.getMontantMaximum()).isEqualTo(DEFAULT_MONTANT_MAXIMUM);
        assertThat(testBorletteConf.getCloseTimeMidi()).isEqualTo(UPDATED_CLOSE_TIME_MIDI);
        assertThat(testBorletteConf.getCloseTimeSoir()).isEqualTo(DEFAULT_CLOSE_TIME_SOIR);
    }

    @Test
    @Transactional
    void fullUpdateBorletteConfWithPatch() throws Exception {
        // Initialize the database
        borletteConfRepository.saveAndFlush(borletteConf);

        int databaseSizeBeforeUpdate = borletteConfRepository.findAll().size();

        // Update the borletteConf using partial update
        BorletteConf partialUpdatedBorletteConf = new BorletteConf();
        partialUpdatedBorletteConf.setId(borletteConf.getId());

        partialUpdatedBorletteConf
            .name(UPDATED_NAME)
            .premierLot(UPDATED_PREMIER_LOT)
            .deuxiemeLot(UPDATED_DEUXIEME_LOT)
            .troisiemeLot(UPDATED_TROISIEME_LOT)
            .mariageGratisPrix(UPDATED_MARIAGE_GRATIS_PRIX)
            .montantMinimum(UPDATED_MONTANT_MINIMUM)
            .montantMaximum(UPDATED_MONTANT_MAXIMUM)
            .closeTimeMidi(UPDATED_CLOSE_TIME_MIDI)
            .closeTimeSoir(UPDATED_CLOSE_TIME_SOIR);

        restBorletteConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBorletteConf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBorletteConf))
            )
            .andExpect(status().isOk());

        // Validate the BorletteConf in the database
        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeUpdate);
        BorletteConf testBorletteConf = borletteConfList.get(borletteConfList.size() - 1);
        assertThat(testBorletteConf.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBorletteConf.getPremierLot()).isEqualTo(UPDATED_PREMIER_LOT);
        assertThat(testBorletteConf.getDeuxiemeLot()).isEqualTo(UPDATED_DEUXIEME_LOT);
        assertThat(testBorletteConf.getTroisiemeLot()).isEqualTo(UPDATED_TROISIEME_LOT);
        assertThat(testBorletteConf.getMariageGratisPrix()).isEqualByComparingTo(UPDATED_MARIAGE_GRATIS_PRIX);
        assertThat(testBorletteConf.getMontantMinimum()).isEqualTo(UPDATED_MONTANT_MINIMUM);
        assertThat(testBorletteConf.getMontantMaximum()).isEqualTo(UPDATED_MONTANT_MAXIMUM);
        assertThat(testBorletteConf.getCloseTimeMidi()).isEqualTo(UPDATED_CLOSE_TIME_MIDI);
        assertThat(testBorletteConf.getCloseTimeSoir()).isEqualTo(UPDATED_CLOSE_TIME_SOIR);
    }

    @Test
    @Transactional
    void patchNonExistingBorletteConf() throws Exception {
        int databaseSizeBeforeUpdate = borletteConfRepository.findAll().size();
        borletteConf.setId(count.incrementAndGet());

        // Create the BorletteConf
        BorletteConfDTO borletteConfDTO = borletteConfMapper.toDto(borletteConf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBorletteConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, borletteConfDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(borletteConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorletteConf in the database
        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBorletteConf() throws Exception {
        int databaseSizeBeforeUpdate = borletteConfRepository.findAll().size();
        borletteConf.setId(count.incrementAndGet());

        // Create the BorletteConf
        BorletteConfDTO borletteConfDTO = borletteConfMapper.toDto(borletteConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBorletteConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(borletteConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorletteConf in the database
        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBorletteConf() throws Exception {
        int databaseSizeBeforeUpdate = borletteConfRepository.findAll().size();
        borletteConf.setId(count.incrementAndGet());

        // Create the BorletteConf
        BorletteConfDTO borletteConfDTO = borletteConfMapper.toDto(borletteConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBorletteConfMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(borletteConfDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BorletteConf in the database
        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBorletteConf() throws Exception {
        // Initialize the database
        borletteConfRepository.saveAndFlush(borletteConf);

        int databaseSizeBeforeDelete = borletteConfRepository.findAll().size();

        // Delete the borletteConf
        restBorletteConfMockMvc
            .perform(delete(ENTITY_API_URL_ID, borletteConf.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BorletteConf> borletteConfList = borletteConfRepository.findAll();
        assertThat(borletteConfList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
