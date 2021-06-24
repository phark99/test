package com.skt.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.skt.test.IntegrationTest;
import com.skt.test.domain.ApiRequest;
import com.skt.test.repository.ApiRequestRepository;
import com.skt.test.service.ApiRequestService;
import com.skt.test.service.dto.ApiRequestDTO;
import com.skt.test.service.mapper.ApiRequestMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ApiRequestResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ApiRequestResourceIT {

    private static final Long DEFAULT_API_ID = 1L;
    private static final Long UPDATED_API_ID = 2L;

    private static final String DEFAULT_CLIENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long UPDATED_PROJECT_ID = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_MODIFIED_BY = 1L;
    private static final Long UPDATED_MODIFIED_BY = 2L;

    private static final LocalDate DEFAULT_MODIFIED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/api-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApiRequestRepository apiRequestRepository;

    @Mock
    private ApiRequestRepository apiRequestRepositoryMock;

    @Autowired
    private ApiRequestMapper apiRequestMapper;

    @Mock
    private ApiRequestService apiRequestServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApiRequestMockMvc;

    private ApiRequest apiRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiRequest createEntity(EntityManager em) {
        ApiRequest apiRequest = new ApiRequest()
            .apiId(DEFAULT_API_ID)
            .clientType(DEFAULT_CLIENT_TYPE)
            .projectId(DEFAULT_PROJECT_ID)
            .description(DEFAULT_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedAt(DEFAULT_MODIFIED_AT);
        return apiRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiRequest createUpdatedEntity(EntityManager em) {
        ApiRequest apiRequest = new ApiRequest()
            .apiId(UPDATED_API_ID)
            .clientType(UPDATED_CLIENT_TYPE)
            .projectId(UPDATED_PROJECT_ID)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);
        return apiRequest;
    }

    @BeforeEach
    public void initTest() {
        apiRequest = createEntity(em);
    }

    @Test
    @Transactional
    void createApiRequest() throws Exception {
        int databaseSizeBeforeCreate = apiRequestRepository.findAll().size();
        // Create the ApiRequest
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);
        restApiRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiRequestDTO)))
            .andExpect(status().isCreated());

        // Validate the ApiRequest in the database
        List<ApiRequest> apiRequestList = apiRequestRepository.findAll();
        assertThat(apiRequestList).hasSize(databaseSizeBeforeCreate + 1);
        ApiRequest testApiRequest = apiRequestList.get(apiRequestList.size() - 1);
        assertThat(testApiRequest.getApiId()).isEqualTo(DEFAULT_API_ID);
        assertThat(testApiRequest.getClientType()).isEqualTo(DEFAULT_CLIENT_TYPE);
        assertThat(testApiRequest.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testApiRequest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApiRequest.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApiRequest.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testApiRequest.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testApiRequest.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
    }

    @Test
    @Transactional
    void createApiRequestWithExistingId() throws Exception {
        // Create the ApiRequest with an existing ID
        apiRequest.setId(1L);
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        int databaseSizeBeforeCreate = apiRequestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiRequest in the database
        List<ApiRequest> apiRequestList = apiRequestRepository.findAll();
        assertThat(apiRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkApiIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiRequestRepository.findAll().size();
        // set the field null
        apiRequest.setApiId(null);

        // Create the ApiRequest, which fails.
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        restApiRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiRequestDTO)))
            .andExpect(status().isBadRequest());

        List<ApiRequest> apiRequestList = apiRequestRepository.findAll();
        assertThat(apiRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkClientTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiRequestRepository.findAll().size();
        // set the field null
        apiRequest.setClientType(null);

        // Create the ApiRequest, which fails.
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        restApiRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiRequestDTO)))
            .andExpect(status().isBadRequest());

        List<ApiRequest> apiRequestList = apiRequestRepository.findAll();
        assertThat(apiRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProjectIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiRequestRepository.findAll().size();
        // set the field null
        apiRequest.setProjectId(null);

        // Create the ApiRequest, which fails.
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        restApiRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiRequestDTO)))
            .andExpect(status().isBadRequest());

        List<ApiRequest> apiRequestList = apiRequestRepository.findAll();
        assertThat(apiRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiRequestRepository.findAll().size();
        // set the field null
        apiRequest.setCreatedBy(null);

        // Create the ApiRequest, which fails.
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        restApiRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiRequestDTO)))
            .andExpect(status().isBadRequest());

        List<ApiRequest> apiRequestList = apiRequestRepository.findAll();
        assertThat(apiRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiRequestRepository.findAll().size();
        // set the field null
        apiRequest.setCreatedAt(null);

        // Create the ApiRequest, which fails.
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        restApiRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiRequestDTO)))
            .andExpect(status().isBadRequest());

        List<ApiRequest> apiRequestList = apiRequestRepository.findAll();
        assertThat(apiRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApiRequests() throws Exception {
        // Initialize the database
        apiRequestRepository.saveAndFlush(apiRequest);

        // Get all the apiRequestList
        restApiRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].apiId").value(hasItem(DEFAULT_API_ID.intValue())))
            .andExpect(jsonPath("$.[*].clientType").value(hasItem(DEFAULT_CLIENT_TYPE)))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedAt").value(hasItem(DEFAULT_MODIFIED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllApiRequestsWithEagerRelationshipsIsEnabled() throws Exception {
        when(apiRequestServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restApiRequestMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(apiRequestServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllApiRequestsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(apiRequestServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restApiRequestMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(apiRequestServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getApiRequest() throws Exception {
        // Initialize the database
        apiRequestRepository.saveAndFlush(apiRequest);

        // Get the apiRequest
        restApiRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, apiRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apiRequest.getId().intValue()))
            .andExpect(jsonPath("$.apiId").value(DEFAULT_API_ID.intValue()))
            .andExpect(jsonPath("$.clientType").value(DEFAULT_CLIENT_TYPE))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.intValue()))
            .andExpect(jsonPath("$.modifiedAt").value(DEFAULT_MODIFIED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingApiRequest() throws Exception {
        // Get the apiRequest
        restApiRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApiRequest() throws Exception {
        // Initialize the database
        apiRequestRepository.saveAndFlush(apiRequest);

        int databaseSizeBeforeUpdate = apiRequestRepository.findAll().size();

        // Update the apiRequest
        ApiRequest updatedApiRequest = apiRequestRepository.findById(apiRequest.getId()).get();
        // Disconnect from session so that the updates on updatedApiRequest are not directly saved in db
        em.detach(updatedApiRequest);
        updatedApiRequest
            .apiId(UPDATED_API_ID)
            .clientType(UPDATED_CLIENT_TYPE)
            .projectId(UPDATED_PROJECT_ID)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(updatedApiRequest);

        restApiRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apiRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apiRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the ApiRequest in the database
        List<ApiRequest> apiRequestList = apiRequestRepository.findAll();
        assertThat(apiRequestList).hasSize(databaseSizeBeforeUpdate);
        ApiRequest testApiRequest = apiRequestList.get(apiRequestList.size() - 1);
        assertThat(testApiRequest.getApiId()).isEqualTo(UPDATED_API_ID);
        assertThat(testApiRequest.getClientType()).isEqualTo(UPDATED_CLIENT_TYPE);
        assertThat(testApiRequest.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testApiRequest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApiRequest.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApiRequest.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApiRequest.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testApiRequest.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void putNonExistingApiRequest() throws Exception {
        int databaseSizeBeforeUpdate = apiRequestRepository.findAll().size();
        apiRequest.setId(count.incrementAndGet());

        // Create the ApiRequest
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apiRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apiRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiRequest in the database
        List<ApiRequest> apiRequestList = apiRequestRepository.findAll();
        assertThat(apiRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApiRequest() throws Exception {
        int databaseSizeBeforeUpdate = apiRequestRepository.findAll().size();
        apiRequest.setId(count.incrementAndGet());

        // Create the ApiRequest
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apiRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiRequest in the database
        List<ApiRequest> apiRequestList = apiRequestRepository.findAll();
        assertThat(apiRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApiRequest() throws Exception {
        int databaseSizeBeforeUpdate = apiRequestRepository.findAll().size();
        apiRequest.setId(count.incrementAndGet());

        // Create the ApiRequest
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiRequestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiRequestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApiRequest in the database
        List<ApiRequest> apiRequestList = apiRequestRepository.findAll();
        assertThat(apiRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApiRequestWithPatch() throws Exception {
        // Initialize the database
        apiRequestRepository.saveAndFlush(apiRequest);

        int databaseSizeBeforeUpdate = apiRequestRepository.findAll().size();

        // Update the apiRequest using partial update
        ApiRequest partialUpdatedApiRequest = new ApiRequest();
        partialUpdatedApiRequest.setId(apiRequest.getId());

        partialUpdatedApiRequest.description(UPDATED_DESCRIPTION);

        restApiRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApiRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApiRequest))
            )
            .andExpect(status().isOk());

        // Validate the ApiRequest in the database
        List<ApiRequest> apiRequestList = apiRequestRepository.findAll();
        assertThat(apiRequestList).hasSize(databaseSizeBeforeUpdate);
        ApiRequest testApiRequest = apiRequestList.get(apiRequestList.size() - 1);
        assertThat(testApiRequest.getApiId()).isEqualTo(DEFAULT_API_ID);
        assertThat(testApiRequest.getClientType()).isEqualTo(DEFAULT_CLIENT_TYPE);
        assertThat(testApiRequest.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testApiRequest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApiRequest.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApiRequest.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testApiRequest.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testApiRequest.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
    }

    @Test
    @Transactional
    void fullUpdateApiRequestWithPatch() throws Exception {
        // Initialize the database
        apiRequestRepository.saveAndFlush(apiRequest);

        int databaseSizeBeforeUpdate = apiRequestRepository.findAll().size();

        // Update the apiRequest using partial update
        ApiRequest partialUpdatedApiRequest = new ApiRequest();
        partialUpdatedApiRequest.setId(apiRequest.getId());

        partialUpdatedApiRequest
            .apiId(UPDATED_API_ID)
            .clientType(UPDATED_CLIENT_TYPE)
            .projectId(UPDATED_PROJECT_ID)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);

        restApiRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApiRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApiRequest))
            )
            .andExpect(status().isOk());

        // Validate the ApiRequest in the database
        List<ApiRequest> apiRequestList = apiRequestRepository.findAll();
        assertThat(apiRequestList).hasSize(databaseSizeBeforeUpdate);
        ApiRequest testApiRequest = apiRequestList.get(apiRequestList.size() - 1);
        assertThat(testApiRequest.getApiId()).isEqualTo(UPDATED_API_ID);
        assertThat(testApiRequest.getClientType()).isEqualTo(UPDATED_CLIENT_TYPE);
        assertThat(testApiRequest.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testApiRequest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApiRequest.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApiRequest.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApiRequest.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testApiRequest.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingApiRequest() throws Exception {
        int databaseSizeBeforeUpdate = apiRequestRepository.findAll().size();
        apiRequest.setId(count.incrementAndGet());

        // Create the ApiRequest
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, apiRequestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apiRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiRequest in the database
        List<ApiRequest> apiRequestList = apiRequestRepository.findAll();
        assertThat(apiRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApiRequest() throws Exception {
        int databaseSizeBeforeUpdate = apiRequestRepository.findAll().size();
        apiRequest.setId(count.incrementAndGet());

        // Create the ApiRequest
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apiRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiRequest in the database
        List<ApiRequest> apiRequestList = apiRequestRepository.findAll();
        assertThat(apiRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApiRequest() throws Exception {
        int databaseSizeBeforeUpdate = apiRequestRepository.findAll().size();
        apiRequest.setId(count.incrementAndGet());

        // Create the ApiRequest
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiRequestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(apiRequestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApiRequest in the database
        List<ApiRequest> apiRequestList = apiRequestRepository.findAll();
        assertThat(apiRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApiRequest() throws Exception {
        // Initialize the database
        apiRequestRepository.saveAndFlush(apiRequest);

        int databaseSizeBeforeDelete = apiRequestRepository.findAll().size();

        // Delete the apiRequest
        restApiRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, apiRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiRequest> apiRequestList = apiRequestRepository.findAll();
        assertThat(apiRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
