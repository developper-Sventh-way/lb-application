package com.seventh.lotobig.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seventh.lotobig.IntegrationTest;
import com.seventh.lotobig.domain.MembershipConf;
import com.seventh.lotobig.repository.MembershipConfRepository;
import com.seventh.lotobig.service.dto.MembershipConfDTO;
import com.seventh.lotobig.service.mapper.MembershipConfMapper;
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
 * Integration tests for the {@link MembershipConfResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MembershipConfResourceIT {

    private static final String DEFAULT_NOM_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_SLOGAN = "AAAAAAAAAA";
    private static final String UPDATED_SLOGAN = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONES = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONES = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_INFOS = "AAAAAAAAAA";
    private static final String UPDATED_INFOS = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/membership-confs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MembershipConfRepository membershipConfRepository;

    @Autowired
    private MembershipConfMapper membershipConfMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMembershipConfMockMvc;

    private MembershipConf membershipConf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MembershipConf createEntity(EntityManager em) {
        MembershipConf membershipConf = new MembershipConf()
            .nomClient(DEFAULT_NOM_CLIENT)
            .slogan(DEFAULT_SLOGAN)
            .telephones(DEFAULT_TELEPHONES)
            .adresse(DEFAULT_ADRESSE)
            .infos(DEFAULT_INFOS)
            .logoLink(DEFAULT_LOGO_LINK)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return membershipConf;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MembershipConf createUpdatedEntity(EntityManager em) {
        MembershipConf membershipConf = new MembershipConf()
            .nomClient(UPDATED_NOM_CLIENT)
            .slogan(UPDATED_SLOGAN)
            .telephones(UPDATED_TELEPHONES)
            .adresse(UPDATED_ADRESSE)
            .infos(UPDATED_INFOS)
            .logoLink(UPDATED_LOGO_LINK)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return membershipConf;
    }

    @BeforeEach
    public void initTest() {
        membershipConf = createEntity(em);
    }

    @Test
    @Transactional
    void createMembershipConf() throws Exception {
        int databaseSizeBeforeCreate = membershipConfRepository.findAll().size();
        // Create the MembershipConf
        MembershipConfDTO membershipConfDTO = membershipConfMapper.toDto(membershipConf);
        restMembershipConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(membershipConfDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MembershipConf in the database
        List<MembershipConf> membershipConfList = membershipConfRepository.findAll();
        assertThat(membershipConfList).hasSize(databaseSizeBeforeCreate + 1);
        MembershipConf testMembershipConf = membershipConfList.get(membershipConfList.size() - 1);
        assertThat(testMembershipConf.getNomClient()).isEqualTo(DEFAULT_NOM_CLIENT);
        assertThat(testMembershipConf.getSlogan()).isEqualTo(DEFAULT_SLOGAN);
        assertThat(testMembershipConf.getTelephones()).isEqualTo(DEFAULT_TELEPHONES);
        assertThat(testMembershipConf.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testMembershipConf.getInfos()).isEqualTo(DEFAULT_INFOS);
        assertThat(testMembershipConf.getLogoLink()).isEqualTo(DEFAULT_LOGO_LINK);
        assertThat(testMembershipConf.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMembershipConf.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMembershipConf.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMembershipConf.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createMembershipConfWithExistingId() throws Exception {
        // Create the MembershipConf with an existing ID
        membershipConf.setId(1L);
        MembershipConfDTO membershipConfDTO = membershipConfMapper.toDto(membershipConf);

        int databaseSizeBeforeCreate = membershipConfRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembershipConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(membershipConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MembershipConf in the database
        List<MembershipConf> membershipConfList = membershipConfRepository.findAll();
        assertThat(membershipConfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = membershipConfRepository.findAll().size();
        // set the field null
        membershipConf.setCreatedBy(null);

        // Create the MembershipConf, which fails.
        MembershipConfDTO membershipConfDTO = membershipConfMapper.toDto(membershipConf);

        restMembershipConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(membershipConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<MembershipConf> membershipConfList = membershipConfRepository.findAll();
        assertThat(membershipConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = membershipConfRepository.findAll().size();
        // set the field null
        membershipConf.setCreatedDate(null);

        // Create the MembershipConf, which fails.
        MembershipConfDTO membershipConfDTO = membershipConfMapper.toDto(membershipConf);

        restMembershipConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(membershipConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<MembershipConf> membershipConfList = membershipConfRepository.findAll();
        assertThat(membershipConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMembershipConfs() throws Exception {
        // Initialize the database
        membershipConfRepository.saveAndFlush(membershipConf);

        // Get all the membershipConfList
        restMembershipConfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membershipConf.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomClient").value(hasItem(DEFAULT_NOM_CLIENT)))
            .andExpect(jsonPath("$.[*].slogan").value(hasItem(DEFAULT_SLOGAN)))
            .andExpect(jsonPath("$.[*].telephones").value(hasItem(DEFAULT_TELEPHONES)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].infos").value(hasItem(DEFAULT_INFOS)))
            .andExpect(jsonPath("$.[*].logoLink").value(hasItem(DEFAULT_LOGO_LINK)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getMembershipConf() throws Exception {
        // Initialize the database
        membershipConfRepository.saveAndFlush(membershipConf);

        // Get the membershipConf
        restMembershipConfMockMvc
            .perform(get(ENTITY_API_URL_ID, membershipConf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(membershipConf.getId().intValue()))
            .andExpect(jsonPath("$.nomClient").value(DEFAULT_NOM_CLIENT))
            .andExpect(jsonPath("$.slogan").value(DEFAULT_SLOGAN))
            .andExpect(jsonPath("$.telephones").value(DEFAULT_TELEPHONES))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.infos").value(DEFAULT_INFOS))
            .andExpect(jsonPath("$.logoLink").value(DEFAULT_LOGO_LINK))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMembershipConf() throws Exception {
        // Get the membershipConf
        restMembershipConfMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMembershipConf() throws Exception {
        // Initialize the database
        membershipConfRepository.saveAndFlush(membershipConf);

        int databaseSizeBeforeUpdate = membershipConfRepository.findAll().size();

        // Update the membershipConf
        MembershipConf updatedMembershipConf = membershipConfRepository.findById(membershipConf.getId()).get();
        // Disconnect from session so that the updates on updatedMembershipConf are not directly saved in db
        em.detach(updatedMembershipConf);
        updatedMembershipConf
            .nomClient(UPDATED_NOM_CLIENT)
            .slogan(UPDATED_SLOGAN)
            .telephones(UPDATED_TELEPHONES)
            .adresse(UPDATED_ADRESSE)
            .infos(UPDATED_INFOS)
            .logoLink(UPDATED_LOGO_LINK)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        MembershipConfDTO membershipConfDTO = membershipConfMapper.toDto(updatedMembershipConf);

        restMembershipConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, membershipConfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(membershipConfDTO))
            )
            .andExpect(status().isOk());

        // Validate the MembershipConf in the database
        List<MembershipConf> membershipConfList = membershipConfRepository.findAll();
        assertThat(membershipConfList).hasSize(databaseSizeBeforeUpdate);
        MembershipConf testMembershipConf = membershipConfList.get(membershipConfList.size() - 1);
        assertThat(testMembershipConf.getNomClient()).isEqualTo(UPDATED_NOM_CLIENT);
        assertThat(testMembershipConf.getSlogan()).isEqualTo(UPDATED_SLOGAN);
        assertThat(testMembershipConf.getTelephones()).isEqualTo(UPDATED_TELEPHONES);
        assertThat(testMembershipConf.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testMembershipConf.getInfos()).isEqualTo(UPDATED_INFOS);
        assertThat(testMembershipConf.getLogoLink()).isEqualTo(UPDATED_LOGO_LINK);
        assertThat(testMembershipConf.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMembershipConf.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMembershipConf.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMembershipConf.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingMembershipConf() throws Exception {
        int databaseSizeBeforeUpdate = membershipConfRepository.findAll().size();
        membershipConf.setId(count.incrementAndGet());

        // Create the MembershipConf
        MembershipConfDTO membershipConfDTO = membershipConfMapper.toDto(membershipConf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembershipConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, membershipConfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(membershipConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MembershipConf in the database
        List<MembershipConf> membershipConfList = membershipConfRepository.findAll();
        assertThat(membershipConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMembershipConf() throws Exception {
        int databaseSizeBeforeUpdate = membershipConfRepository.findAll().size();
        membershipConf.setId(count.incrementAndGet());

        // Create the MembershipConf
        MembershipConfDTO membershipConfDTO = membershipConfMapper.toDto(membershipConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembershipConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(membershipConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MembershipConf in the database
        List<MembershipConf> membershipConfList = membershipConfRepository.findAll();
        assertThat(membershipConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMembershipConf() throws Exception {
        int databaseSizeBeforeUpdate = membershipConfRepository.findAll().size();
        membershipConf.setId(count.incrementAndGet());

        // Create the MembershipConf
        MembershipConfDTO membershipConfDTO = membershipConfMapper.toDto(membershipConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembershipConfMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(membershipConfDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MembershipConf in the database
        List<MembershipConf> membershipConfList = membershipConfRepository.findAll();
        assertThat(membershipConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMembershipConfWithPatch() throws Exception {
        // Initialize the database
        membershipConfRepository.saveAndFlush(membershipConf);

        int databaseSizeBeforeUpdate = membershipConfRepository.findAll().size();

        // Update the membershipConf using partial update
        MembershipConf partialUpdatedMembershipConf = new MembershipConf();
        partialUpdatedMembershipConf.setId(membershipConf.getId());

        partialUpdatedMembershipConf
            .infos(UPDATED_INFOS)
            .logoLink(UPDATED_LOGO_LINK)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restMembershipConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMembershipConf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMembershipConf))
            )
            .andExpect(status().isOk());

        // Validate the MembershipConf in the database
        List<MembershipConf> membershipConfList = membershipConfRepository.findAll();
        assertThat(membershipConfList).hasSize(databaseSizeBeforeUpdate);
        MembershipConf testMembershipConf = membershipConfList.get(membershipConfList.size() - 1);
        assertThat(testMembershipConf.getNomClient()).isEqualTo(DEFAULT_NOM_CLIENT);
        assertThat(testMembershipConf.getSlogan()).isEqualTo(DEFAULT_SLOGAN);
        assertThat(testMembershipConf.getTelephones()).isEqualTo(DEFAULT_TELEPHONES);
        assertThat(testMembershipConf.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testMembershipConf.getInfos()).isEqualTo(UPDATED_INFOS);
        assertThat(testMembershipConf.getLogoLink()).isEqualTo(UPDATED_LOGO_LINK);
        assertThat(testMembershipConf.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMembershipConf.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMembershipConf.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMembershipConf.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateMembershipConfWithPatch() throws Exception {
        // Initialize the database
        membershipConfRepository.saveAndFlush(membershipConf);

        int databaseSizeBeforeUpdate = membershipConfRepository.findAll().size();

        // Update the membershipConf using partial update
        MembershipConf partialUpdatedMembershipConf = new MembershipConf();
        partialUpdatedMembershipConf.setId(membershipConf.getId());

        partialUpdatedMembershipConf
            .nomClient(UPDATED_NOM_CLIENT)
            .slogan(UPDATED_SLOGAN)
            .telephones(UPDATED_TELEPHONES)
            .adresse(UPDATED_ADRESSE)
            .infos(UPDATED_INFOS)
            .logoLink(UPDATED_LOGO_LINK)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restMembershipConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMembershipConf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMembershipConf))
            )
            .andExpect(status().isOk());

        // Validate the MembershipConf in the database
        List<MembershipConf> membershipConfList = membershipConfRepository.findAll();
        assertThat(membershipConfList).hasSize(databaseSizeBeforeUpdate);
        MembershipConf testMembershipConf = membershipConfList.get(membershipConfList.size() - 1);
        assertThat(testMembershipConf.getNomClient()).isEqualTo(UPDATED_NOM_CLIENT);
        assertThat(testMembershipConf.getSlogan()).isEqualTo(UPDATED_SLOGAN);
        assertThat(testMembershipConf.getTelephones()).isEqualTo(UPDATED_TELEPHONES);
        assertThat(testMembershipConf.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testMembershipConf.getInfos()).isEqualTo(UPDATED_INFOS);
        assertThat(testMembershipConf.getLogoLink()).isEqualTo(UPDATED_LOGO_LINK);
        assertThat(testMembershipConf.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMembershipConf.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMembershipConf.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMembershipConf.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingMembershipConf() throws Exception {
        int databaseSizeBeforeUpdate = membershipConfRepository.findAll().size();
        membershipConf.setId(count.incrementAndGet());

        // Create the MembershipConf
        MembershipConfDTO membershipConfDTO = membershipConfMapper.toDto(membershipConf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembershipConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, membershipConfDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(membershipConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MembershipConf in the database
        List<MembershipConf> membershipConfList = membershipConfRepository.findAll();
        assertThat(membershipConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMembershipConf() throws Exception {
        int databaseSizeBeforeUpdate = membershipConfRepository.findAll().size();
        membershipConf.setId(count.incrementAndGet());

        // Create the MembershipConf
        MembershipConfDTO membershipConfDTO = membershipConfMapper.toDto(membershipConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembershipConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(membershipConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MembershipConf in the database
        List<MembershipConf> membershipConfList = membershipConfRepository.findAll();
        assertThat(membershipConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMembershipConf() throws Exception {
        int databaseSizeBeforeUpdate = membershipConfRepository.findAll().size();
        membershipConf.setId(count.incrementAndGet());

        // Create the MembershipConf
        MembershipConfDTO membershipConfDTO = membershipConfMapper.toDto(membershipConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembershipConfMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(membershipConfDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MembershipConf in the database
        List<MembershipConf> membershipConfList = membershipConfRepository.findAll();
        assertThat(membershipConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMembershipConf() throws Exception {
        // Initialize the database
        membershipConfRepository.saveAndFlush(membershipConf);

        int databaseSizeBeforeDelete = membershipConfRepository.findAll().size();

        // Delete the membershipConf
        restMembershipConfMockMvc
            .perform(delete(ENTITY_API_URL_ID, membershipConf.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MembershipConf> membershipConfList = membershipConfRepository.findAll();
        assertThat(membershipConfList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
