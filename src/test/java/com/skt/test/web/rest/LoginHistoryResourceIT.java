package com.skt.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.skt.test.IntegrationTest;
import com.skt.test.domain.LoginHistory;
import com.skt.test.repository.LoginHistoryRepository;
import com.skt.test.service.dto.LoginHistoryDTO;
import com.skt.test.service.mapper.LoginHistoryMapper;
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
 * Integration tests for the {@link LoginHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoginHistoryResourceIT {

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USER_AGENT = "AAAAAAAAAA";
    private static final String UPDATED_USER_AGENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/login-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    @Autowired
    private LoginHistoryMapper loginHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoginHistoryMockMvc;

    private LoginHistory loginHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoginHistory createEntity(EntityManager em) {
        LoginHistory loginHistory = new LoginHistory().createdAt(DEFAULT_CREATED_AT).userAgent(DEFAULT_USER_AGENT);
        return loginHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoginHistory createUpdatedEntity(EntityManager em) {
        LoginHistory loginHistory = new LoginHistory().createdAt(UPDATED_CREATED_AT).userAgent(UPDATED_USER_AGENT);
        return loginHistory;
    }

    @BeforeEach
    public void initTest() {
        loginHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createLoginHistory() throws Exception {
        int databaseSizeBeforeCreate = loginHistoryRepository.findAll().size();
        // Create the LoginHistory
        LoginHistoryDTO loginHistoryDTO = loginHistoryMapper.toDto(loginHistory);
        restLoginHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loginHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        LoginHistory testLoginHistory = loginHistoryList.get(loginHistoryList.size() - 1);
        assertThat(testLoginHistory.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLoginHistory.getUserAgent()).isEqualTo(DEFAULT_USER_AGENT);
    }

    @Test
    @Transactional
    void createLoginHistoryWithExistingId() throws Exception {
        // Create the LoginHistory with an existing ID
        loginHistory.setId(1L);
        LoginHistoryDTO loginHistoryDTO = loginHistoryMapper.toDto(loginHistory);

        int databaseSizeBeforeCreate = loginHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoginHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loginHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = loginHistoryRepository.findAll().size();
        // set the field null
        loginHistory.setCreatedAt(null);

        // Create the LoginHistory, which fails.
        LoginHistoryDTO loginHistoryDTO = loginHistoryMapper.toDto(loginHistory);

        restLoginHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loginHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLoginHistories() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get all the loginHistoryList
        restLoginHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loginHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].userAgent").value(hasItem(DEFAULT_USER_AGENT)));
    }

    @Test
    @Transactional
    void getLoginHistory() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        // Get the loginHistory
        restLoginHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, loginHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loginHistory.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.userAgent").value(DEFAULT_USER_AGENT));
    }

    @Test
    @Transactional
    void getNonExistingLoginHistory() throws Exception {
        // Get the loginHistory
        restLoginHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLoginHistory() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        int databaseSizeBeforeUpdate = loginHistoryRepository.findAll().size();

        // Update the loginHistory
        LoginHistory updatedLoginHistory = loginHistoryRepository.findById(loginHistory.getId()).get();
        // Disconnect from session so that the updates on updatedLoginHistory are not directly saved in db
        em.detach(updatedLoginHistory);
        updatedLoginHistory.createdAt(UPDATED_CREATED_AT).userAgent(UPDATED_USER_AGENT);
        LoginHistoryDTO loginHistoryDTO = loginHistoryMapper.toDto(updatedLoginHistory);

        restLoginHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loginHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loginHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeUpdate);
        LoginHistory testLoginHistory = loginHistoryList.get(loginHistoryList.size() - 1);
        assertThat(testLoginHistory.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLoginHistory.getUserAgent()).isEqualTo(UPDATED_USER_AGENT);
    }

    @Test
    @Transactional
    void putNonExistingLoginHistory() throws Exception {
        int databaseSizeBeforeUpdate = loginHistoryRepository.findAll().size();
        loginHistory.setId(count.incrementAndGet());

        // Create the LoginHistory
        LoginHistoryDTO loginHistoryDTO = loginHistoryMapper.toDto(loginHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoginHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loginHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loginHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoginHistory() throws Exception {
        int databaseSizeBeforeUpdate = loginHistoryRepository.findAll().size();
        loginHistory.setId(count.incrementAndGet());

        // Create the LoginHistory
        LoginHistoryDTO loginHistoryDTO = loginHistoryMapper.toDto(loginHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loginHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoginHistory() throws Exception {
        int databaseSizeBeforeUpdate = loginHistoryRepository.findAll().size();
        loginHistory.setId(count.incrementAndGet());

        // Create the LoginHistory
        LoginHistoryDTO loginHistoryDTO = loginHistoryMapper.toDto(loginHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginHistoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loginHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoginHistoryWithPatch() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        int databaseSizeBeforeUpdate = loginHistoryRepository.findAll().size();

        // Update the loginHistory using partial update
        LoginHistory partialUpdatedLoginHistory = new LoginHistory();
        partialUpdatedLoginHistory.setId(loginHistory.getId());

        partialUpdatedLoginHistory.userAgent(UPDATED_USER_AGENT);

        restLoginHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoginHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoginHistory))
            )
            .andExpect(status().isOk());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeUpdate);
        LoginHistory testLoginHistory = loginHistoryList.get(loginHistoryList.size() - 1);
        assertThat(testLoginHistory.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLoginHistory.getUserAgent()).isEqualTo(UPDATED_USER_AGENT);
    }

    @Test
    @Transactional
    void fullUpdateLoginHistoryWithPatch() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        int databaseSizeBeforeUpdate = loginHistoryRepository.findAll().size();

        // Update the loginHistory using partial update
        LoginHistory partialUpdatedLoginHistory = new LoginHistory();
        partialUpdatedLoginHistory.setId(loginHistory.getId());

        partialUpdatedLoginHistory.createdAt(UPDATED_CREATED_AT).userAgent(UPDATED_USER_AGENT);

        restLoginHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoginHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoginHistory))
            )
            .andExpect(status().isOk());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeUpdate);
        LoginHistory testLoginHistory = loginHistoryList.get(loginHistoryList.size() - 1);
        assertThat(testLoginHistory.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLoginHistory.getUserAgent()).isEqualTo(UPDATED_USER_AGENT);
    }

    @Test
    @Transactional
    void patchNonExistingLoginHistory() throws Exception {
        int databaseSizeBeforeUpdate = loginHistoryRepository.findAll().size();
        loginHistory.setId(count.incrementAndGet());

        // Create the LoginHistory
        LoginHistoryDTO loginHistoryDTO = loginHistoryMapper.toDto(loginHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoginHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loginHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loginHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoginHistory() throws Exception {
        int databaseSizeBeforeUpdate = loginHistoryRepository.findAll().size();
        loginHistory.setId(count.incrementAndGet());

        // Create the LoginHistory
        LoginHistoryDTO loginHistoryDTO = loginHistoryMapper.toDto(loginHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loginHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoginHistory() throws Exception {
        int databaseSizeBeforeUpdate = loginHistoryRepository.findAll().size();
        loginHistory.setId(count.incrementAndGet());

        // Create the LoginHistory
        LoginHistoryDTO loginHistoryDTO = loginHistoryMapper.toDto(loginHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loginHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoginHistory in the database
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoginHistory() throws Exception {
        // Initialize the database
        loginHistoryRepository.saveAndFlush(loginHistory);

        int databaseSizeBeforeDelete = loginHistoryRepository.findAll().size();

        // Delete the loginHistory
        restLoginHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, loginHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        assertThat(loginHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
