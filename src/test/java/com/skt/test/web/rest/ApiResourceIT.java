package com.skt.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.skt.test.IntegrationTest;
import com.skt.test.domain.Api;
import com.skt.test.repository.ApiRepository;
import com.skt.test.service.dto.ApiDTO;
import com.skt.test.service.mapper.ApiMapper;
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
 * Integration tests for the {@link ApiResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApiResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long UPDATED_PROJECT_ID = 2L;

    private static final Long DEFAULT_MANAGER_ID = 1L;
    private static final Long UPDATED_MANAGER_ID = 2L;

    private static final String DEFAULT_API_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_API_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_HOST = "AAAAAAAAAA";
    private static final String UPDATED_HOST = "BBBBBBBBBB";

    private static final Integer DEFAULT_PORT = 1;
    private static final Integer UPDATED_PORT = 2;

    private static final String DEFAULT_URI = "AAAAAAAAAA";
    private static final String UPDATED_URI = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_MODIFIED_BY = 1L;
    private static final Long UPDATED_MODIFIED_BY = 2L;

    private static final LocalDate DEFAULT_MODIFIED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/apis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApiMockMvc;

    private Api api;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Api createEntity(EntityManager em) {
        Api api = new Api()
            .name(DEFAULT_NAME)
            .projectId(DEFAULT_PROJECT_ID)
            .managerId(DEFAULT_MANAGER_ID)
            .apiType(DEFAULT_API_TYPE)
            .host(DEFAULT_HOST)
            .port(DEFAULT_PORT)
            .uri(DEFAULT_URI)
            .version(DEFAULT_VERSION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedAt(DEFAULT_MODIFIED_AT);
        return api;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Api createUpdatedEntity(EntityManager em) {
        Api api = new Api()
            .name(UPDATED_NAME)
            .projectId(UPDATED_PROJECT_ID)
            .managerId(UPDATED_MANAGER_ID)
            .apiType(UPDATED_API_TYPE)
            .host(UPDATED_HOST)
            .port(UPDATED_PORT)
            .uri(UPDATED_URI)
            .version(UPDATED_VERSION)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);
        return api;
    }

    @BeforeEach
    public void initTest() {
        api = createEntity(em);
    }

    @Test
    @Transactional
    void createApi() throws Exception {
        int databaseSizeBeforeCreate = apiRepository.findAll().size();
        // Create the Api
        ApiDTO apiDTO = apiMapper.toDto(api);
        restApiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiDTO)))
            .andExpect(status().isCreated());

        // Validate the Api in the database
        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeCreate + 1);
        Api testApi = apiList.get(apiList.size() - 1);
        assertThat(testApi.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApi.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testApi.getManagerId()).isEqualTo(DEFAULT_MANAGER_ID);
        assertThat(testApi.getApiType()).isEqualTo(DEFAULT_API_TYPE);
        assertThat(testApi.getHost()).isEqualTo(DEFAULT_HOST);
        assertThat(testApi.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testApi.getUri()).isEqualTo(DEFAULT_URI);
        assertThat(testApi.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testApi.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApi.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testApi.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testApi.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
    }

    @Test
    @Transactional
    void createApiWithExistingId() throws Exception {
        // Create the Api with an existing ID
        api.setId(1L);
        ApiDTO apiDTO = apiMapper.toDto(api);

        int databaseSizeBeforeCreate = apiRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Api in the database
        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiRepository.findAll().size();
        // set the field null
        api.setName(null);

        // Create the Api, which fails.
        ApiDTO apiDTO = apiMapper.toDto(api);

        restApiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiDTO)))
            .andExpect(status().isBadRequest());

        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProjectIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiRepository.findAll().size();
        // set the field null
        api.setProjectId(null);

        // Create the Api, which fails.
        ApiDTO apiDTO = apiMapper.toDto(api);

        restApiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiDTO)))
            .andExpect(status().isBadRequest());

        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkManagerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiRepository.findAll().size();
        // set the field null
        api.setManagerId(null);

        // Create the Api, which fails.
        ApiDTO apiDTO = apiMapper.toDto(api);

        restApiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiDTO)))
            .andExpect(status().isBadRequest());

        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApiTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiRepository.findAll().size();
        // set the field null
        api.setApiType(null);

        // Create the Api, which fails.
        ApiDTO apiDTO = apiMapper.toDto(api);

        restApiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiDTO)))
            .andExpect(status().isBadRequest());

        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiRepository.findAll().size();
        // set the field null
        api.setCreatedBy(null);

        // Create the Api, which fails.
        ApiDTO apiDTO = apiMapper.toDto(api);

        restApiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiDTO)))
            .andExpect(status().isBadRequest());

        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiRepository.findAll().size();
        // set the field null
        api.setCreatedAt(null);

        // Create the Api, which fails.
        ApiDTO apiDTO = apiMapper.toDto(api);

        restApiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiDTO)))
            .andExpect(status().isBadRequest());

        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApis() throws Exception {
        // Initialize the database
        apiRepository.saveAndFlush(api);

        // Get all the apiList
        restApiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(api.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].managerId").value(hasItem(DEFAULT_MANAGER_ID.intValue())))
            .andExpect(jsonPath("$.[*].apiType").value(hasItem(DEFAULT_API_TYPE)))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST)))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
            .andExpect(jsonPath("$.[*].uri").value(hasItem(DEFAULT_URI)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedAt").value(hasItem(DEFAULT_MODIFIED_AT.toString())));
    }

    @Test
    @Transactional
    void getApi() throws Exception {
        // Initialize the database
        apiRepository.saveAndFlush(api);

        // Get the api
        restApiMockMvc
            .perform(get(ENTITY_API_URL_ID, api.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(api.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.intValue()))
            .andExpect(jsonPath("$.managerId").value(DEFAULT_MANAGER_ID.intValue()))
            .andExpect(jsonPath("$.apiType").value(DEFAULT_API_TYPE))
            .andExpect(jsonPath("$.host").value(DEFAULT_HOST))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT))
            .andExpect(jsonPath("$.uri").value(DEFAULT_URI))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.intValue()))
            .andExpect(jsonPath("$.modifiedAt").value(DEFAULT_MODIFIED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingApi() throws Exception {
        // Get the api
        restApiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApi() throws Exception {
        // Initialize the database
        apiRepository.saveAndFlush(api);

        int databaseSizeBeforeUpdate = apiRepository.findAll().size();

        // Update the api
        Api updatedApi = apiRepository.findById(api.getId()).get();
        // Disconnect from session so that the updates on updatedApi are not directly saved in db
        em.detach(updatedApi);
        updatedApi
            .name(UPDATED_NAME)
            .projectId(UPDATED_PROJECT_ID)
            .managerId(UPDATED_MANAGER_ID)
            .apiType(UPDATED_API_TYPE)
            .host(UPDATED_HOST)
            .port(UPDATED_PORT)
            .uri(UPDATED_URI)
            .version(UPDATED_VERSION)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);
        ApiDTO apiDTO = apiMapper.toDto(updatedApi);

        restApiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apiDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apiDTO))
            )
            .andExpect(status().isOk());

        // Validate the Api in the database
        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeUpdate);
        Api testApi = apiList.get(apiList.size() - 1);
        assertThat(testApi.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApi.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testApi.getManagerId()).isEqualTo(UPDATED_MANAGER_ID);
        assertThat(testApi.getApiType()).isEqualTo(UPDATED_API_TYPE);
        assertThat(testApi.getHost()).isEqualTo(UPDATED_HOST);
        assertThat(testApi.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testApi.getUri()).isEqualTo(UPDATED_URI);
        assertThat(testApi.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testApi.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApi.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApi.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testApi.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void putNonExistingApi() throws Exception {
        int databaseSizeBeforeUpdate = apiRepository.findAll().size();
        api.setId(count.incrementAndGet());

        // Create the Api
        ApiDTO apiDTO = apiMapper.toDto(api);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apiDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Api in the database
        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApi() throws Exception {
        int databaseSizeBeforeUpdate = apiRepository.findAll().size();
        api.setId(count.incrementAndGet());

        // Create the Api
        ApiDTO apiDTO = apiMapper.toDto(api);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Api in the database
        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApi() throws Exception {
        int databaseSizeBeforeUpdate = apiRepository.findAll().size();
        api.setId(count.incrementAndGet());

        // Create the Api
        ApiDTO apiDTO = apiMapper.toDto(api);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Api in the database
        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApiWithPatch() throws Exception {
        // Initialize the database
        apiRepository.saveAndFlush(api);

        int databaseSizeBeforeUpdate = apiRepository.findAll().size();

        // Update the api using partial update
        Api partialUpdatedApi = new Api();
        partialUpdatedApi.setId(api.getId());

        partialUpdatedApi
            .name(UPDATED_NAME)
            .projectId(UPDATED_PROJECT_ID)
            .managerId(UPDATED_MANAGER_ID)
            .apiType(UPDATED_API_TYPE)
            .host(UPDATED_HOST)
            .port(UPDATED_PORT)
            .uri(UPDATED_URI)
            .version(UPDATED_VERSION)
            .createdBy(UPDATED_CREATED_BY);

        restApiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApi))
            )
            .andExpect(status().isOk());

        // Validate the Api in the database
        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeUpdate);
        Api testApi = apiList.get(apiList.size() - 1);
        assertThat(testApi.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApi.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testApi.getManagerId()).isEqualTo(UPDATED_MANAGER_ID);
        assertThat(testApi.getApiType()).isEqualTo(UPDATED_API_TYPE);
        assertThat(testApi.getHost()).isEqualTo(UPDATED_HOST);
        assertThat(testApi.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testApi.getUri()).isEqualTo(UPDATED_URI);
        assertThat(testApi.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testApi.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApi.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testApi.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testApi.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
    }

    @Test
    @Transactional
    void fullUpdateApiWithPatch() throws Exception {
        // Initialize the database
        apiRepository.saveAndFlush(api);

        int databaseSizeBeforeUpdate = apiRepository.findAll().size();

        // Update the api using partial update
        Api partialUpdatedApi = new Api();
        partialUpdatedApi.setId(api.getId());

        partialUpdatedApi
            .name(UPDATED_NAME)
            .projectId(UPDATED_PROJECT_ID)
            .managerId(UPDATED_MANAGER_ID)
            .apiType(UPDATED_API_TYPE)
            .host(UPDATED_HOST)
            .port(UPDATED_PORT)
            .uri(UPDATED_URI)
            .version(UPDATED_VERSION)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);

        restApiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApi))
            )
            .andExpect(status().isOk());

        // Validate the Api in the database
        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeUpdate);
        Api testApi = apiList.get(apiList.size() - 1);
        assertThat(testApi.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApi.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testApi.getManagerId()).isEqualTo(UPDATED_MANAGER_ID);
        assertThat(testApi.getApiType()).isEqualTo(UPDATED_API_TYPE);
        assertThat(testApi.getHost()).isEqualTo(UPDATED_HOST);
        assertThat(testApi.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testApi.getUri()).isEqualTo(UPDATED_URI);
        assertThat(testApi.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testApi.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApi.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApi.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testApi.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingApi() throws Exception {
        int databaseSizeBeforeUpdate = apiRepository.findAll().size();
        api.setId(count.incrementAndGet());

        // Create the Api
        ApiDTO apiDTO = apiMapper.toDto(api);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, apiDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Api in the database
        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApi() throws Exception {
        int databaseSizeBeforeUpdate = apiRepository.findAll().size();
        api.setId(count.incrementAndGet());

        // Create the Api
        ApiDTO apiDTO = apiMapper.toDto(api);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Api in the database
        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApi() throws Exception {
        int databaseSizeBeforeUpdate = apiRepository.findAll().size();
        api.setId(count.incrementAndGet());

        // Create the Api
        ApiDTO apiDTO = apiMapper.toDto(api);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(apiDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Api in the database
        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApi() throws Exception {
        // Initialize the database
        apiRepository.saveAndFlush(api);

        int databaseSizeBeforeDelete = apiRepository.findAll().size();

        // Delete the api
        restApiMockMvc.perform(delete(ENTITY_API_URL_ID, api.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
