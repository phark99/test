package com.skt.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.skt.test.IntegrationTest;
import com.skt.test.domain.ApiMeta;
import com.skt.test.repository.ApiMetaRepository;
import com.skt.test.service.dto.ApiMetaDTO;
import com.skt.test.service.mapper.ApiMetaMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ApiMetaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApiMetaResourceIT {

    private static final Long DEFAULT_APP_ID = 1L;
    private static final Long UPDATED_APP_ID = 2L;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_MODIFIED_BY = 1L;
    private static final Long UPDATED_MODIFIED_BY = 2L;

    private static final LocalDate DEFAULT_MODIFIED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/api-metas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApiMetaRepository apiMetaRepository;

    @Autowired
    private ApiMetaMapper apiMetaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApiMetaMockMvc;

    private ApiMeta apiMeta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiMeta createEntity(EntityManager em) {
        ApiMeta apiMeta = new ApiMeta()
            .appId(DEFAULT_APP_ID)
            .type(DEFAULT_TYPE)
            .value(DEFAULT_VALUE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedAt(DEFAULT_MODIFIED_AT);
        return apiMeta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiMeta createUpdatedEntity(EntityManager em) {
        ApiMeta apiMeta = new ApiMeta()
            .appId(UPDATED_APP_ID)
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);
        return apiMeta;
    }

    @BeforeEach
    public void initTest() {
        apiMeta = createEntity(em);
    }

    @Test
    @Transactional
    void createApiMeta() throws Exception {
        int databaseSizeBeforeCreate = apiMetaRepository.findAll().size();
        // Create the ApiMeta
        ApiMetaDTO apiMetaDTO = apiMetaMapper.toDto(apiMeta);
        restApiMetaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiMetaDTO)))
            .andExpect(status().isCreated());

        // Validate the ApiMeta in the database
        List<ApiMeta> apiMetaList = apiMetaRepository.findAll();
        assertThat(apiMetaList).hasSize(databaseSizeBeforeCreate + 1);
        ApiMeta testApiMeta = apiMetaList.get(apiMetaList.size() - 1);
        assertThat(testApiMeta.getAppId()).isEqualTo(DEFAULT_APP_ID);
        assertThat(testApiMeta.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testApiMeta.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testApiMeta.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApiMeta.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testApiMeta.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testApiMeta.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
    }

    @Test
    @Transactional
    void createApiMetaWithExistingId() throws Exception {
        // Create the ApiMeta with an existing ID
        apiMeta.setId(1L);
        ApiMetaDTO apiMetaDTO = apiMetaMapper.toDto(apiMeta);

        int databaseSizeBeforeCreate = apiMetaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiMetaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiMetaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiMeta in the database
        List<ApiMeta> apiMetaList = apiMetaRepository.findAll();
        assertThat(apiMetaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAppIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiMetaRepository.findAll().size();
        // set the field null
        apiMeta.setAppId(null);

        // Create the ApiMeta, which fails.
        ApiMetaDTO apiMetaDTO = apiMetaMapper.toDto(apiMeta);

        restApiMetaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiMetaDTO)))
            .andExpect(status().isBadRequest());

        List<ApiMeta> apiMetaList = apiMetaRepository.findAll();
        assertThat(apiMetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiMetaRepository.findAll().size();
        // set the field null
        apiMeta.setType(null);

        // Create the ApiMeta, which fails.
        ApiMetaDTO apiMetaDTO = apiMetaMapper.toDto(apiMeta);

        restApiMetaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiMetaDTO)))
            .andExpect(status().isBadRequest());

        List<ApiMeta> apiMetaList = apiMetaRepository.findAll();
        assertThat(apiMetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiMetaRepository.findAll().size();
        // set the field null
        apiMeta.setCreatedBy(null);

        // Create the ApiMeta, which fails.
        ApiMetaDTO apiMetaDTO = apiMetaMapper.toDto(apiMeta);

        restApiMetaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiMetaDTO)))
            .andExpect(status().isBadRequest());

        List<ApiMeta> apiMetaList = apiMetaRepository.findAll();
        assertThat(apiMetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiMetaRepository.findAll().size();
        // set the field null
        apiMeta.setCreatedAt(null);

        // Create the ApiMeta, which fails.
        ApiMetaDTO apiMetaDTO = apiMetaMapper.toDto(apiMeta);

        restApiMetaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiMetaDTO)))
            .andExpect(status().isBadRequest());

        List<ApiMeta> apiMetaList = apiMetaRepository.findAll();
        assertThat(apiMetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApiMetas() throws Exception {
        // Initialize the database
        apiMetaRepository.saveAndFlush(apiMeta);

        // Get all the apiMetaList
        restApiMetaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiMeta.getId().intValue())))
            .andExpect(jsonPath("$.[*].appId").value(hasItem(DEFAULT_APP_ID.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedAt").value(hasItem(DEFAULT_MODIFIED_AT.toString())));
    }

    @Test
    @Transactional
    void getApiMeta() throws Exception {
        // Initialize the database
        apiMetaRepository.saveAndFlush(apiMeta);

        // Get the apiMeta
        restApiMetaMockMvc
            .perform(get(ENTITY_API_URL_ID, apiMeta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apiMeta.getId().intValue()))
            .andExpect(jsonPath("$.appId").value(DEFAULT_APP_ID.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.intValue()))
            .andExpect(jsonPath("$.modifiedAt").value(DEFAULT_MODIFIED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingApiMeta() throws Exception {
        // Get the apiMeta
        restApiMetaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApiMeta() throws Exception {
        // Initialize the database
        apiMetaRepository.saveAndFlush(apiMeta);

        int databaseSizeBeforeUpdate = apiMetaRepository.findAll().size();

        // Update the apiMeta
        ApiMeta updatedApiMeta = apiMetaRepository.findById(apiMeta.getId()).get();
        // Disconnect from session so that the updates on updatedApiMeta are not directly saved in db
        em.detach(updatedApiMeta);
        updatedApiMeta
            .appId(UPDATED_APP_ID)
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);
        ApiMetaDTO apiMetaDTO = apiMetaMapper.toDto(updatedApiMeta);

        restApiMetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apiMetaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apiMetaDTO))
            )
            .andExpect(status().isOk());

        // Validate the ApiMeta in the database
        List<ApiMeta> apiMetaList = apiMetaRepository.findAll();
        assertThat(apiMetaList).hasSize(databaseSizeBeforeUpdate);
        ApiMeta testApiMeta = apiMetaList.get(apiMetaList.size() - 1);
        assertThat(testApiMeta.getAppId()).isEqualTo(UPDATED_APP_ID);
        assertThat(testApiMeta.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testApiMeta.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testApiMeta.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApiMeta.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApiMeta.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testApiMeta.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void putNonExistingApiMeta() throws Exception {
        int databaseSizeBeforeUpdate = apiMetaRepository.findAll().size();
        apiMeta.setId(count.incrementAndGet());

        // Create the ApiMeta
        ApiMetaDTO apiMetaDTO = apiMetaMapper.toDto(apiMeta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiMetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apiMetaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apiMetaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiMeta in the database
        List<ApiMeta> apiMetaList = apiMetaRepository.findAll();
        assertThat(apiMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApiMeta() throws Exception {
        int databaseSizeBeforeUpdate = apiMetaRepository.findAll().size();
        apiMeta.setId(count.incrementAndGet());

        // Create the ApiMeta
        ApiMetaDTO apiMetaDTO = apiMetaMapper.toDto(apiMeta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiMetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apiMetaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiMeta in the database
        List<ApiMeta> apiMetaList = apiMetaRepository.findAll();
        assertThat(apiMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApiMeta() throws Exception {
        int databaseSizeBeforeUpdate = apiMetaRepository.findAll().size();
        apiMeta.setId(count.incrementAndGet());

        // Create the ApiMeta
        ApiMetaDTO apiMetaDTO = apiMetaMapper.toDto(apiMeta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiMetaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiMetaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApiMeta in the database
        List<ApiMeta> apiMetaList = apiMetaRepository.findAll();
        assertThat(apiMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApiMetaWithPatch() throws Exception {
        // Initialize the database
        apiMetaRepository.saveAndFlush(apiMeta);

        int databaseSizeBeforeUpdate = apiMetaRepository.findAll().size();

        // Update the apiMeta using partial update
        ApiMeta partialUpdatedApiMeta = new ApiMeta();
        partialUpdatedApiMeta.setId(apiMeta.getId());

        partialUpdatedApiMeta.type(UPDATED_TYPE).value(UPDATED_VALUE).modifiedAt(UPDATED_MODIFIED_AT);

        restApiMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApiMeta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApiMeta))
            )
            .andExpect(status().isOk());

        // Validate the ApiMeta in the database
        List<ApiMeta> apiMetaList = apiMetaRepository.findAll();
        assertThat(apiMetaList).hasSize(databaseSizeBeforeUpdate);
        ApiMeta testApiMeta = apiMetaList.get(apiMetaList.size() - 1);
        assertThat(testApiMeta.getAppId()).isEqualTo(DEFAULT_APP_ID);
        assertThat(testApiMeta.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testApiMeta.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testApiMeta.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApiMeta.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testApiMeta.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testApiMeta.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void fullUpdateApiMetaWithPatch() throws Exception {
        // Initialize the database
        apiMetaRepository.saveAndFlush(apiMeta);

        int databaseSizeBeforeUpdate = apiMetaRepository.findAll().size();

        // Update the apiMeta using partial update
        ApiMeta partialUpdatedApiMeta = new ApiMeta();
        partialUpdatedApiMeta.setId(apiMeta.getId());

        partialUpdatedApiMeta
            .appId(UPDATED_APP_ID)
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);

        restApiMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApiMeta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApiMeta))
            )
            .andExpect(status().isOk());

        // Validate the ApiMeta in the database
        List<ApiMeta> apiMetaList = apiMetaRepository.findAll();
        assertThat(apiMetaList).hasSize(databaseSizeBeforeUpdate);
        ApiMeta testApiMeta = apiMetaList.get(apiMetaList.size() - 1);
        assertThat(testApiMeta.getAppId()).isEqualTo(UPDATED_APP_ID);
        assertThat(testApiMeta.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testApiMeta.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testApiMeta.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApiMeta.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApiMeta.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testApiMeta.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingApiMeta() throws Exception {
        int databaseSizeBeforeUpdate = apiMetaRepository.findAll().size();
        apiMeta.setId(count.incrementAndGet());

        // Create the ApiMeta
        ApiMetaDTO apiMetaDTO = apiMetaMapper.toDto(apiMeta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, apiMetaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apiMetaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiMeta in the database
        List<ApiMeta> apiMetaList = apiMetaRepository.findAll();
        assertThat(apiMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApiMeta() throws Exception {
        int databaseSizeBeforeUpdate = apiMetaRepository.findAll().size();
        apiMeta.setId(count.incrementAndGet());

        // Create the ApiMeta
        ApiMetaDTO apiMetaDTO = apiMetaMapper.toDto(apiMeta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apiMetaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiMeta in the database
        List<ApiMeta> apiMetaList = apiMetaRepository.findAll();
        assertThat(apiMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApiMeta() throws Exception {
        int databaseSizeBeforeUpdate = apiMetaRepository.findAll().size();
        apiMeta.setId(count.incrementAndGet());

        // Create the ApiMeta
        ApiMetaDTO apiMetaDTO = apiMetaMapper.toDto(apiMeta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiMetaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(apiMetaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApiMeta in the database
        List<ApiMeta> apiMetaList = apiMetaRepository.findAll();
        assertThat(apiMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApiMeta() throws Exception {
        // Initialize the database
        apiMetaRepository.saveAndFlush(apiMeta);

        int databaseSizeBeforeDelete = apiMetaRepository.findAll().size();

        // Delete the apiMeta
        restApiMetaMockMvc
            .perform(delete(ENTITY_API_URL_ID, apiMeta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiMeta> apiMetaList = apiMetaRepository.findAll();
        assertThat(apiMetaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
