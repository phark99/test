package com.skt.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.skt.test.IntegrationTest;
import com.skt.test.domain.ProjectHistory;
import com.skt.test.repository.ProjectHistoryRepository;
import com.skt.test.service.dto.ProjectHistoryDTO;
import com.skt.test.service.mapper.ProjectHistoryMapper;
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
 * Integration tests for the {@link ProjectHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectHistoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DEPT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DEPT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_MAIN_ADMIN_ID = 1L;
    private static final Long UPDATED_MAIN_ADMIN_ID = 2L;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/project-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectHistoryRepository projectHistoryRepository;

    @Autowired
    private ProjectHistoryMapper projectHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectHistoryMockMvc;

    private ProjectHistory projectHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectHistory createEntity(EntityManager em) {
        ProjectHistory projectHistory = new ProjectHistory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .deptCode(DEFAULT_DEPT_CODE)
            .tags(DEFAULT_TAGS)
            .status(DEFAULT_STATUS)
            .mainAdminId(DEFAULT_MAIN_ADMIN_ID)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT);
        return projectHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectHistory createUpdatedEntity(EntityManager em) {
        ProjectHistory projectHistory = new ProjectHistory()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .deptCode(UPDATED_DEPT_CODE)
            .tags(UPDATED_TAGS)
            .status(UPDATED_STATUS)
            .mainAdminId(UPDATED_MAIN_ADMIN_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT);
        return projectHistory;
    }

    @BeforeEach
    public void initTest() {
        projectHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectHistory() throws Exception {
        int databaseSizeBeforeCreate = projectHistoryRepository.findAll().size();
        // Create the ProjectHistory
        ProjectHistoryDTO projectHistoryDTO = projectHistoryMapper.toDto(projectHistory);
        restProjectHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectHistory in the database
        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAll();
        assertThat(projectHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectHistory testProjectHistory = projectHistoryList.get(projectHistoryList.size() - 1);
        assertThat(testProjectHistory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectHistory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjectHistory.getDeptCode()).isEqualTo(DEFAULT_DEPT_CODE);
        assertThat(testProjectHistory.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testProjectHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProjectHistory.getMainAdminId()).isEqualTo(DEFAULT_MAIN_ADMIN_ID);
        assertThat(testProjectHistory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProjectHistory.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createProjectHistoryWithExistingId() throws Exception {
        // Create the ProjectHistory with an existing ID
        projectHistory.setId(1L);
        ProjectHistoryDTO projectHistoryDTO = projectHistoryMapper.toDto(projectHistory);

        int databaseSizeBeforeCreate = projectHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectHistory in the database
        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAll();
        assertThat(projectHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectHistoryRepository.findAll().size();
        // set the field null
        projectHistory.setCreatedBy(null);

        // Create the ProjectHistory, which fails.
        ProjectHistoryDTO projectHistoryDTO = projectHistoryMapper.toDto(projectHistory);

        restProjectHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAll();
        assertThat(projectHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectHistoryRepository.findAll().size();
        // set the field null
        projectHistory.setCreatedAt(null);

        // Create the ProjectHistory, which fails.
        ProjectHistoryDTO projectHistoryDTO = projectHistoryMapper.toDto(projectHistory);

        restProjectHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAll();
        assertThat(projectHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProjectHistories() throws Exception {
        // Initialize the database
        projectHistoryRepository.saveAndFlush(projectHistory);

        // Get all the projectHistoryList
        restProjectHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].deptCode").value(hasItem(DEFAULT_DEPT_CODE)))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].mainAdminId").value(hasItem(DEFAULT_MAIN_ADMIN_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getProjectHistory() throws Exception {
        // Initialize the database
        projectHistoryRepository.saveAndFlush(projectHistory);

        // Get the projectHistory
        restProjectHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, projectHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectHistory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.deptCode").value(DEFAULT_DEPT_CODE))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.mainAdminId").value(DEFAULT_MAIN_ADMIN_ID.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProjectHistory() throws Exception {
        // Get the projectHistory
        restProjectHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProjectHistory() throws Exception {
        // Initialize the database
        projectHistoryRepository.saveAndFlush(projectHistory);

        int databaseSizeBeforeUpdate = projectHistoryRepository.findAll().size();

        // Update the projectHistory
        ProjectHistory updatedProjectHistory = projectHistoryRepository.findById(projectHistory.getId()).get();
        // Disconnect from session so that the updates on updatedProjectHistory are not directly saved in db
        em.detach(updatedProjectHistory);
        updatedProjectHistory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .deptCode(UPDATED_DEPT_CODE)
            .tags(UPDATED_TAGS)
            .status(UPDATED_STATUS)
            .mainAdminId(UPDATED_MAIN_ADMIN_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT);
        ProjectHistoryDTO projectHistoryDTO = projectHistoryMapper.toDto(updatedProjectHistory);

        restProjectHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProjectHistory in the database
        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAll();
        assertThat(projectHistoryList).hasSize(databaseSizeBeforeUpdate);
        ProjectHistory testProjectHistory = projectHistoryList.get(projectHistoryList.size() - 1);
        assertThat(testProjectHistory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectHistory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectHistory.getDeptCode()).isEqualTo(UPDATED_DEPT_CODE);
        assertThat(testProjectHistory.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testProjectHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProjectHistory.getMainAdminId()).isEqualTo(UPDATED_MAIN_ADMIN_ID);
        assertThat(testProjectHistory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProjectHistory.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingProjectHistory() throws Exception {
        int databaseSizeBeforeUpdate = projectHistoryRepository.findAll().size();
        projectHistory.setId(count.incrementAndGet());

        // Create the ProjectHistory
        ProjectHistoryDTO projectHistoryDTO = projectHistoryMapper.toDto(projectHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectHistory in the database
        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAll();
        assertThat(projectHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectHistory() throws Exception {
        int databaseSizeBeforeUpdate = projectHistoryRepository.findAll().size();
        projectHistory.setId(count.incrementAndGet());

        // Create the ProjectHistory
        ProjectHistoryDTO projectHistoryDTO = projectHistoryMapper.toDto(projectHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectHistory in the database
        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAll();
        assertThat(projectHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectHistory() throws Exception {
        int databaseSizeBeforeUpdate = projectHistoryRepository.findAll().size();
        projectHistory.setId(count.incrementAndGet());

        // Create the ProjectHistory
        ProjectHistoryDTO projectHistoryDTO = projectHistoryMapper.toDto(projectHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectHistoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectHistory in the database
        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAll();
        assertThat(projectHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectHistoryWithPatch() throws Exception {
        // Initialize the database
        projectHistoryRepository.saveAndFlush(projectHistory);

        int databaseSizeBeforeUpdate = projectHistoryRepository.findAll().size();

        // Update the projectHistory using partial update
        ProjectHistory partialUpdatedProjectHistory = new ProjectHistory();
        partialUpdatedProjectHistory.setId(projectHistory.getId());

        partialUpdatedProjectHistory
            .description(UPDATED_DESCRIPTION)
            .tags(UPDATED_TAGS)
            .mainAdminId(UPDATED_MAIN_ADMIN_ID)
            .createdAt(UPDATED_CREATED_AT);

        restProjectHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectHistory))
            )
            .andExpect(status().isOk());

        // Validate the ProjectHistory in the database
        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAll();
        assertThat(projectHistoryList).hasSize(databaseSizeBeforeUpdate);
        ProjectHistory testProjectHistory = projectHistoryList.get(projectHistoryList.size() - 1);
        assertThat(testProjectHistory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectHistory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectHistory.getDeptCode()).isEqualTo(DEFAULT_DEPT_CODE);
        assertThat(testProjectHistory.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testProjectHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProjectHistory.getMainAdminId()).isEqualTo(UPDATED_MAIN_ADMIN_ID);
        assertThat(testProjectHistory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProjectHistory.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateProjectHistoryWithPatch() throws Exception {
        // Initialize the database
        projectHistoryRepository.saveAndFlush(projectHistory);

        int databaseSizeBeforeUpdate = projectHistoryRepository.findAll().size();

        // Update the projectHistory using partial update
        ProjectHistory partialUpdatedProjectHistory = new ProjectHistory();
        partialUpdatedProjectHistory.setId(projectHistory.getId());

        partialUpdatedProjectHistory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .deptCode(UPDATED_DEPT_CODE)
            .tags(UPDATED_TAGS)
            .status(UPDATED_STATUS)
            .mainAdminId(UPDATED_MAIN_ADMIN_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT);

        restProjectHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectHistory))
            )
            .andExpect(status().isOk());

        // Validate the ProjectHistory in the database
        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAll();
        assertThat(projectHistoryList).hasSize(databaseSizeBeforeUpdate);
        ProjectHistory testProjectHistory = projectHistoryList.get(projectHistoryList.size() - 1);
        assertThat(testProjectHistory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectHistory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectHistory.getDeptCode()).isEqualTo(UPDATED_DEPT_CODE);
        assertThat(testProjectHistory.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testProjectHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProjectHistory.getMainAdminId()).isEqualTo(UPDATED_MAIN_ADMIN_ID);
        assertThat(testProjectHistory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProjectHistory.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingProjectHistory() throws Exception {
        int databaseSizeBeforeUpdate = projectHistoryRepository.findAll().size();
        projectHistory.setId(count.incrementAndGet());

        // Create the ProjectHistory
        ProjectHistoryDTO projectHistoryDTO = projectHistoryMapper.toDto(projectHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectHistory in the database
        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAll();
        assertThat(projectHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectHistory() throws Exception {
        int databaseSizeBeforeUpdate = projectHistoryRepository.findAll().size();
        projectHistory.setId(count.incrementAndGet());

        // Create the ProjectHistory
        ProjectHistoryDTO projectHistoryDTO = projectHistoryMapper.toDto(projectHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectHistory in the database
        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAll();
        assertThat(projectHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectHistory() throws Exception {
        int databaseSizeBeforeUpdate = projectHistoryRepository.findAll().size();
        projectHistory.setId(count.incrementAndGet());

        // Create the ProjectHistory
        ProjectHistoryDTO projectHistoryDTO = projectHistoryMapper.toDto(projectHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectHistory in the database
        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAll();
        assertThat(projectHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectHistory() throws Exception {
        // Initialize the database
        projectHistoryRepository.saveAndFlush(projectHistory);

        int databaseSizeBeforeDelete = projectHistoryRepository.findAll().size();

        // Delete the projectHistory
        restProjectHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAll();
        assertThat(projectHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
