package com.skt.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.skt.test.IntegrationTest;
import com.skt.test.domain.UserToken;
import com.skt.test.repository.UserTokenRepository;
import com.skt.test.service.dto.UserTokenDTO;
import com.skt.test.service.mapper.UserTokenMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link UserTokenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserTokenResourceIT {

    private static final String DEFAULT_ACC_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_ACC_TOKEN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ACC_EXP_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACC_EXP_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REF_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_REF_TOKEN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REF_EXP_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REF_EXP_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/user-tokens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private UserTokenMapper userTokenMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserTokenMockMvc;

    private UserToken userToken;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserToken createEntity(EntityManager em) {
        UserToken userToken = new UserToken()
            .accToken(DEFAULT_ACC_TOKEN)
            .accExpTime(DEFAULT_ACC_EXP_TIME)
            .refToken(DEFAULT_REF_TOKEN)
            .refExpTime(DEFAULT_REF_EXP_TIME)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT);
        return userToken;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserToken createUpdatedEntity(EntityManager em) {
        UserToken userToken = new UserToken()
            .accToken(UPDATED_ACC_TOKEN)
            .accExpTime(UPDATED_ACC_EXP_TIME)
            .refToken(UPDATED_REF_TOKEN)
            .refExpTime(UPDATED_REF_EXP_TIME)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT);
        return userToken;
    }

    @BeforeEach
    public void initTest() {
        userToken = createEntity(em);
    }

    @Test
    @Transactional
    void createUserToken() throws Exception {
        int databaseSizeBeforeCreate = userTokenRepository.findAll().size();
        // Create the UserToken
        UserTokenDTO userTokenDTO = userTokenMapper.toDto(userToken);
        restUserTokenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTokenDTO)))
            .andExpect(status().isCreated());

        // Validate the UserToken in the database
        List<UserToken> userTokenList = userTokenRepository.findAll();
        assertThat(userTokenList).hasSize(databaseSizeBeforeCreate + 1);
        UserToken testUserToken = userTokenList.get(userTokenList.size() - 1);
        assertThat(testUserToken.getAccToken()).isEqualTo(DEFAULT_ACC_TOKEN);
        assertThat(testUserToken.getAccExpTime()).isEqualTo(DEFAULT_ACC_EXP_TIME);
        assertThat(testUserToken.getRefToken()).isEqualTo(DEFAULT_REF_TOKEN);
        assertThat(testUserToken.getRefExpTime()).isEqualTo(DEFAULT_REF_EXP_TIME);
        assertThat(testUserToken.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testUserToken.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createUserTokenWithExistingId() throws Exception {
        // Create the UserToken with an existing ID
        userToken.setId(1L);
        UserTokenDTO userTokenDTO = userTokenMapper.toDto(userToken);

        int databaseSizeBeforeCreate = userTokenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserTokenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTokenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserToken in the database
        List<UserToken> userTokenList = userTokenRepository.findAll();
        assertThat(userTokenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = userTokenRepository.findAll().size();
        // set the field null
        userToken.setCreatedBy(null);

        // Create the UserToken, which fails.
        UserTokenDTO userTokenDTO = userTokenMapper.toDto(userToken);

        restUserTokenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTokenDTO)))
            .andExpect(status().isBadRequest());

        List<UserToken> userTokenList = userTokenRepository.findAll();
        assertThat(userTokenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userTokenRepository.findAll().size();
        // set the field null
        userToken.setCreatedAt(null);

        // Create the UserToken, which fails.
        UserTokenDTO userTokenDTO = userTokenMapper.toDto(userToken);

        restUserTokenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTokenDTO)))
            .andExpect(status().isBadRequest());

        List<UserToken> userTokenList = userTokenRepository.findAll();
        assertThat(userTokenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserTokens() throws Exception {
        // Initialize the database
        userTokenRepository.saveAndFlush(userToken);

        // Get all the userTokenList
        restUserTokenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userToken.getId().intValue())))
            .andExpect(jsonPath("$.[*].accToken").value(hasItem(DEFAULT_ACC_TOKEN)))
            .andExpect(jsonPath("$.[*].accExpTime").value(hasItem(DEFAULT_ACC_EXP_TIME.toString())))
            .andExpect(jsonPath("$.[*].refToken").value(hasItem(DEFAULT_REF_TOKEN)))
            .andExpect(jsonPath("$.[*].refExpTime").value(hasItem(DEFAULT_REF_EXP_TIME.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getUserToken() throws Exception {
        // Initialize the database
        userTokenRepository.saveAndFlush(userToken);

        // Get the userToken
        restUserTokenMockMvc
            .perform(get(ENTITY_API_URL_ID, userToken.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userToken.getId().intValue()))
            .andExpect(jsonPath("$.accToken").value(DEFAULT_ACC_TOKEN))
            .andExpect(jsonPath("$.accExpTime").value(DEFAULT_ACC_EXP_TIME.toString()))
            .andExpect(jsonPath("$.refToken").value(DEFAULT_REF_TOKEN))
            .andExpect(jsonPath("$.refExpTime").value(DEFAULT_REF_EXP_TIME.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserToken() throws Exception {
        // Get the userToken
        restUserTokenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserToken() throws Exception {
        // Initialize the database
        userTokenRepository.saveAndFlush(userToken);

        int databaseSizeBeforeUpdate = userTokenRepository.findAll().size();

        // Update the userToken
        UserToken updatedUserToken = userTokenRepository.findById(userToken.getId()).get();
        // Disconnect from session so that the updates on updatedUserToken are not directly saved in db
        em.detach(updatedUserToken);
        updatedUserToken
            .accToken(UPDATED_ACC_TOKEN)
            .accExpTime(UPDATED_ACC_EXP_TIME)
            .refToken(UPDATED_REF_TOKEN)
            .refExpTime(UPDATED_REF_EXP_TIME)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT);
        UserTokenDTO userTokenDTO = userTokenMapper.toDto(updatedUserToken);

        restUserTokenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userTokenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userTokenDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserToken in the database
        List<UserToken> userTokenList = userTokenRepository.findAll();
        assertThat(userTokenList).hasSize(databaseSizeBeforeUpdate);
        UserToken testUserToken = userTokenList.get(userTokenList.size() - 1);
        assertThat(testUserToken.getAccToken()).isEqualTo(UPDATED_ACC_TOKEN);
        assertThat(testUserToken.getAccExpTime()).isEqualTo(UPDATED_ACC_EXP_TIME);
        assertThat(testUserToken.getRefToken()).isEqualTo(UPDATED_REF_TOKEN);
        assertThat(testUserToken.getRefExpTime()).isEqualTo(UPDATED_REF_EXP_TIME);
        assertThat(testUserToken.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserToken.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingUserToken() throws Exception {
        int databaseSizeBeforeUpdate = userTokenRepository.findAll().size();
        userToken.setId(count.incrementAndGet());

        // Create the UserToken
        UserTokenDTO userTokenDTO = userTokenMapper.toDto(userToken);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserTokenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userTokenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userTokenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserToken in the database
        List<UserToken> userTokenList = userTokenRepository.findAll();
        assertThat(userTokenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserToken() throws Exception {
        int databaseSizeBeforeUpdate = userTokenRepository.findAll().size();
        userToken.setId(count.incrementAndGet());

        // Create the UserToken
        UserTokenDTO userTokenDTO = userTokenMapper.toDto(userToken);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTokenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userTokenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserToken in the database
        List<UserToken> userTokenList = userTokenRepository.findAll();
        assertThat(userTokenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserToken() throws Exception {
        int databaseSizeBeforeUpdate = userTokenRepository.findAll().size();
        userToken.setId(count.incrementAndGet());

        // Create the UserToken
        UserTokenDTO userTokenDTO = userTokenMapper.toDto(userToken);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTokenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTokenDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserToken in the database
        List<UserToken> userTokenList = userTokenRepository.findAll();
        assertThat(userTokenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserTokenWithPatch() throws Exception {
        // Initialize the database
        userTokenRepository.saveAndFlush(userToken);

        int databaseSizeBeforeUpdate = userTokenRepository.findAll().size();

        // Update the userToken using partial update
        UserToken partialUpdatedUserToken = new UserToken();
        partialUpdatedUserToken.setId(userToken.getId());

        partialUpdatedUserToken
            .refToken(UPDATED_REF_TOKEN)
            .refExpTime(UPDATED_REF_EXP_TIME)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT);

        restUserTokenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserToken.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserToken))
            )
            .andExpect(status().isOk());

        // Validate the UserToken in the database
        List<UserToken> userTokenList = userTokenRepository.findAll();
        assertThat(userTokenList).hasSize(databaseSizeBeforeUpdate);
        UserToken testUserToken = userTokenList.get(userTokenList.size() - 1);
        assertThat(testUserToken.getAccToken()).isEqualTo(DEFAULT_ACC_TOKEN);
        assertThat(testUserToken.getAccExpTime()).isEqualTo(DEFAULT_ACC_EXP_TIME);
        assertThat(testUserToken.getRefToken()).isEqualTo(UPDATED_REF_TOKEN);
        assertThat(testUserToken.getRefExpTime()).isEqualTo(UPDATED_REF_EXP_TIME);
        assertThat(testUserToken.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserToken.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateUserTokenWithPatch() throws Exception {
        // Initialize the database
        userTokenRepository.saveAndFlush(userToken);

        int databaseSizeBeforeUpdate = userTokenRepository.findAll().size();

        // Update the userToken using partial update
        UserToken partialUpdatedUserToken = new UserToken();
        partialUpdatedUserToken.setId(userToken.getId());

        partialUpdatedUserToken
            .accToken(UPDATED_ACC_TOKEN)
            .accExpTime(UPDATED_ACC_EXP_TIME)
            .refToken(UPDATED_REF_TOKEN)
            .refExpTime(UPDATED_REF_EXP_TIME)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT);

        restUserTokenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserToken.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserToken))
            )
            .andExpect(status().isOk());

        // Validate the UserToken in the database
        List<UserToken> userTokenList = userTokenRepository.findAll();
        assertThat(userTokenList).hasSize(databaseSizeBeforeUpdate);
        UserToken testUserToken = userTokenList.get(userTokenList.size() - 1);
        assertThat(testUserToken.getAccToken()).isEqualTo(UPDATED_ACC_TOKEN);
        assertThat(testUserToken.getAccExpTime()).isEqualTo(UPDATED_ACC_EXP_TIME);
        assertThat(testUserToken.getRefToken()).isEqualTo(UPDATED_REF_TOKEN);
        assertThat(testUserToken.getRefExpTime()).isEqualTo(UPDATED_REF_EXP_TIME);
        assertThat(testUserToken.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserToken.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingUserToken() throws Exception {
        int databaseSizeBeforeUpdate = userTokenRepository.findAll().size();
        userToken.setId(count.incrementAndGet());

        // Create the UserToken
        UserTokenDTO userTokenDTO = userTokenMapper.toDto(userToken);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserTokenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userTokenDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userTokenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserToken in the database
        List<UserToken> userTokenList = userTokenRepository.findAll();
        assertThat(userTokenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserToken() throws Exception {
        int databaseSizeBeforeUpdate = userTokenRepository.findAll().size();
        userToken.setId(count.incrementAndGet());

        // Create the UserToken
        UserTokenDTO userTokenDTO = userTokenMapper.toDto(userToken);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTokenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userTokenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserToken in the database
        List<UserToken> userTokenList = userTokenRepository.findAll();
        assertThat(userTokenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserToken() throws Exception {
        int databaseSizeBeforeUpdate = userTokenRepository.findAll().size();
        userToken.setId(count.incrementAndGet());

        // Create the UserToken
        UserTokenDTO userTokenDTO = userTokenMapper.toDto(userToken);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTokenMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userTokenDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserToken in the database
        List<UserToken> userTokenList = userTokenRepository.findAll();
        assertThat(userTokenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserToken() throws Exception {
        // Initialize the database
        userTokenRepository.saveAndFlush(userToken);

        int databaseSizeBeforeDelete = userTokenRepository.findAll().size();

        // Delete the userToken
        restUserTokenMockMvc
            .perform(delete(ENTITY_API_URL_ID, userToken.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserToken> userTokenList = userTokenRepository.findAll();
        assertThat(userTokenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
