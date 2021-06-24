package com.skt.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.skt.test.IntegrationTest;
import com.skt.test.domain.ResourceMeta;
import com.skt.test.repository.ResourceMetaRepository;
import com.skt.test.service.dto.ResourceMetaDTO;
import com.skt.test.service.mapper.ResourceMetaMapper;
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
 * Integration tests for the {@link ResourceMetaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResourceMetaResourceIT {

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

    private static final String ENTITY_API_URL = "/api/resource-metas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResourceMetaRepository resourceMetaRepository;

    @Autowired
    private ResourceMetaMapper resourceMetaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResourceMetaMockMvc;

    private ResourceMeta resourceMeta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResourceMeta createEntity(EntityManager em) {
        ResourceMeta resourceMeta = new ResourceMeta()
            .type(DEFAULT_TYPE)
            .value(DEFAULT_VALUE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedAt(DEFAULT_MODIFIED_AT);
        return resourceMeta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResourceMeta createUpdatedEntity(EntityManager em) {
        ResourceMeta resourceMeta = new ResourceMeta()
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);
        return resourceMeta;
    }

    @BeforeEach
    public void initTest() {
        resourceMeta = createEntity(em);
    }

    @Test
    @Transactional
    void createResourceMeta() throws Exception {
        int databaseSizeBeforeCreate = resourceMetaRepository.findAll().size();
        // Create the ResourceMeta
        ResourceMetaDTO resourceMetaDTO = resourceMetaMapper.toDto(resourceMeta);
        restResourceMetaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resourceMetaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ResourceMeta in the database
        List<ResourceMeta> resourceMetaList = resourceMetaRepository.findAll();
        assertThat(resourceMetaList).hasSize(databaseSizeBeforeCreate + 1);
        ResourceMeta testResourceMeta = resourceMetaList.get(resourceMetaList.size() - 1);
        assertThat(testResourceMeta.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testResourceMeta.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testResourceMeta.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testResourceMeta.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testResourceMeta.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testResourceMeta.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
    }

    @Test
    @Transactional
    void createResourceMetaWithExistingId() throws Exception {
        // Create the ResourceMeta with an existing ID
        resourceMeta.setId(1L);
        ResourceMetaDTO resourceMetaDTO = resourceMetaMapper.toDto(resourceMeta);

        int databaseSizeBeforeCreate = resourceMetaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourceMetaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resourceMetaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceMeta in the database
        List<ResourceMeta> resourceMetaList = resourceMetaRepository.findAll();
        assertThat(resourceMetaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceMetaRepository.findAll().size();
        // set the field null
        resourceMeta.setType(null);

        // Create the ResourceMeta, which fails.
        ResourceMetaDTO resourceMetaDTO = resourceMetaMapper.toDto(resourceMeta);

        restResourceMetaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resourceMetaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResourceMeta> resourceMetaList = resourceMetaRepository.findAll();
        assertThat(resourceMetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceMetaRepository.findAll().size();
        // set the field null
        resourceMeta.setValue(null);

        // Create the ResourceMeta, which fails.
        ResourceMetaDTO resourceMetaDTO = resourceMetaMapper.toDto(resourceMeta);

        restResourceMetaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resourceMetaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResourceMeta> resourceMetaList = resourceMetaRepository.findAll();
        assertThat(resourceMetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceMetaRepository.findAll().size();
        // set the field null
        resourceMeta.setCreatedBy(null);

        // Create the ResourceMeta, which fails.
        ResourceMetaDTO resourceMetaDTO = resourceMetaMapper.toDto(resourceMeta);

        restResourceMetaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resourceMetaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResourceMeta> resourceMetaList = resourceMetaRepository.findAll();
        assertThat(resourceMetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceMetaRepository.findAll().size();
        // set the field null
        resourceMeta.setCreatedAt(null);

        // Create the ResourceMeta, which fails.
        ResourceMetaDTO resourceMetaDTO = resourceMetaMapper.toDto(resourceMeta);

        restResourceMetaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resourceMetaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResourceMeta> resourceMetaList = resourceMetaRepository.findAll();
        assertThat(resourceMetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllResourceMetas() throws Exception {
        // Initialize the database
        resourceMetaRepository.saveAndFlush(resourceMeta);

        // Get all the resourceMetaList
        restResourceMetaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resourceMeta.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedAt").value(hasItem(DEFAULT_MODIFIED_AT.toString())));
    }

    @Test
    @Transactional
    void getResourceMeta() throws Exception {
        // Initialize the database
        resourceMetaRepository.saveAndFlush(resourceMeta);

        // Get the resourceMeta
        restResourceMetaMockMvc
            .perform(get(ENTITY_API_URL_ID, resourceMeta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resourceMeta.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.intValue()))
            .andExpect(jsonPath("$.modifiedAt").value(DEFAULT_MODIFIED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingResourceMeta() throws Exception {
        // Get the resourceMeta
        restResourceMetaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewResourceMeta() throws Exception {
        // Initialize the database
        resourceMetaRepository.saveAndFlush(resourceMeta);

        int databaseSizeBeforeUpdate = resourceMetaRepository.findAll().size();

        // Update the resourceMeta
        ResourceMeta updatedResourceMeta = resourceMetaRepository.findById(resourceMeta.getId()).get();
        // Disconnect from session so that the updates on updatedResourceMeta are not directly saved in db
        em.detach(updatedResourceMeta);
        updatedResourceMeta
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);
        ResourceMetaDTO resourceMetaDTO = resourceMetaMapper.toDto(updatedResourceMeta);

        restResourceMetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resourceMetaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceMetaDTO))
            )
            .andExpect(status().isOk());

        // Validate the ResourceMeta in the database
        List<ResourceMeta> resourceMetaList = resourceMetaRepository.findAll();
        assertThat(resourceMetaList).hasSize(databaseSizeBeforeUpdate);
        ResourceMeta testResourceMeta = resourceMetaList.get(resourceMetaList.size() - 1);
        assertThat(testResourceMeta.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testResourceMeta.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testResourceMeta.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testResourceMeta.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testResourceMeta.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testResourceMeta.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void putNonExistingResourceMeta() throws Exception {
        int databaseSizeBeforeUpdate = resourceMetaRepository.findAll().size();
        resourceMeta.setId(count.incrementAndGet());

        // Create the ResourceMeta
        ResourceMetaDTO resourceMetaDTO = resourceMetaMapper.toDto(resourceMeta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceMetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resourceMetaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceMetaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceMeta in the database
        List<ResourceMeta> resourceMetaList = resourceMetaRepository.findAll();
        assertThat(resourceMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResourceMeta() throws Exception {
        int databaseSizeBeforeUpdate = resourceMetaRepository.findAll().size();
        resourceMeta.setId(count.incrementAndGet());

        // Create the ResourceMeta
        ResourceMetaDTO resourceMetaDTO = resourceMetaMapper.toDto(resourceMeta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceMetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceMetaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceMeta in the database
        List<ResourceMeta> resourceMetaList = resourceMetaRepository.findAll();
        assertThat(resourceMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResourceMeta() throws Exception {
        int databaseSizeBeforeUpdate = resourceMetaRepository.findAll().size();
        resourceMeta.setId(count.incrementAndGet());

        // Create the ResourceMeta
        ResourceMetaDTO resourceMetaDTO = resourceMetaMapper.toDto(resourceMeta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceMetaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resourceMetaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResourceMeta in the database
        List<ResourceMeta> resourceMetaList = resourceMetaRepository.findAll();
        assertThat(resourceMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResourceMetaWithPatch() throws Exception {
        // Initialize the database
        resourceMetaRepository.saveAndFlush(resourceMeta);

        int databaseSizeBeforeUpdate = resourceMetaRepository.findAll().size();

        // Update the resourceMeta using partial update
        ResourceMeta partialUpdatedResourceMeta = new ResourceMeta();
        partialUpdatedResourceMeta.setId(resourceMeta.getId());

        partialUpdatedResourceMeta.type(UPDATED_TYPE).value(UPDATED_VALUE).createdAt(UPDATED_CREATED_AT).modifiedBy(UPDATED_MODIFIED_BY);

        restResourceMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResourceMeta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResourceMeta))
            )
            .andExpect(status().isOk());

        // Validate the ResourceMeta in the database
        List<ResourceMeta> resourceMetaList = resourceMetaRepository.findAll();
        assertThat(resourceMetaList).hasSize(databaseSizeBeforeUpdate);
        ResourceMeta testResourceMeta = resourceMetaList.get(resourceMetaList.size() - 1);
        assertThat(testResourceMeta.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testResourceMeta.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testResourceMeta.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testResourceMeta.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testResourceMeta.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testResourceMeta.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
    }

    @Test
    @Transactional
    void fullUpdateResourceMetaWithPatch() throws Exception {
        // Initialize the database
        resourceMetaRepository.saveAndFlush(resourceMeta);

        int databaseSizeBeforeUpdate = resourceMetaRepository.findAll().size();

        // Update the resourceMeta using partial update
        ResourceMeta partialUpdatedResourceMeta = new ResourceMeta();
        partialUpdatedResourceMeta.setId(resourceMeta.getId());

        partialUpdatedResourceMeta
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);

        restResourceMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResourceMeta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResourceMeta))
            )
            .andExpect(status().isOk());

        // Validate the ResourceMeta in the database
        List<ResourceMeta> resourceMetaList = resourceMetaRepository.findAll();
        assertThat(resourceMetaList).hasSize(databaseSizeBeforeUpdate);
        ResourceMeta testResourceMeta = resourceMetaList.get(resourceMetaList.size() - 1);
        assertThat(testResourceMeta.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testResourceMeta.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testResourceMeta.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testResourceMeta.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testResourceMeta.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testResourceMeta.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingResourceMeta() throws Exception {
        int databaseSizeBeforeUpdate = resourceMetaRepository.findAll().size();
        resourceMeta.setId(count.incrementAndGet());

        // Create the ResourceMeta
        ResourceMetaDTO resourceMetaDTO = resourceMetaMapper.toDto(resourceMeta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resourceMetaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resourceMetaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceMeta in the database
        List<ResourceMeta> resourceMetaList = resourceMetaRepository.findAll();
        assertThat(resourceMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResourceMeta() throws Exception {
        int databaseSizeBeforeUpdate = resourceMetaRepository.findAll().size();
        resourceMeta.setId(count.incrementAndGet());

        // Create the ResourceMeta
        ResourceMetaDTO resourceMetaDTO = resourceMetaMapper.toDto(resourceMeta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resourceMetaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceMeta in the database
        List<ResourceMeta> resourceMetaList = resourceMetaRepository.findAll();
        assertThat(resourceMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResourceMeta() throws Exception {
        int databaseSizeBeforeUpdate = resourceMetaRepository.findAll().size();
        resourceMeta.setId(count.incrementAndGet());

        // Create the ResourceMeta
        ResourceMetaDTO resourceMetaDTO = resourceMetaMapper.toDto(resourceMeta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceMetaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resourceMetaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResourceMeta in the database
        List<ResourceMeta> resourceMetaList = resourceMetaRepository.findAll();
        assertThat(resourceMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResourceMeta() throws Exception {
        // Initialize the database
        resourceMetaRepository.saveAndFlush(resourceMeta);

        int databaseSizeBeforeDelete = resourceMetaRepository.findAll().size();

        // Delete the resourceMeta
        restResourceMetaMockMvc
            .perform(delete(ENTITY_API_URL_ID, resourceMeta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResourceMeta> resourceMetaList = resourceMetaRepository.findAll();
        assertThat(resourceMetaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
