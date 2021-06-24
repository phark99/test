package com.skt.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.skt.test.IntegrationTest;
import com.skt.test.domain.UserGroup;
import com.skt.test.repository.UserGroupRepository;
import com.skt.test.service.UserGroupService;
import com.skt.test.service.dto.UserGroupDTO;
import com.skt.test.service.mapper.UserGroupMapper;
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
 * Integration tests for the {@link UserGroupResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UserGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_MODIFIED_BY = 1L;
    private static final Long UPDATED_MODIFIED_BY = 2L;

    private static final LocalDate DEFAULT_MODIFIED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/user-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Mock
    private UserGroupRepository userGroupRepositoryMock;

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Mock
    private UserGroupService userGroupServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserGroupMockMvc;

    private UserGroup userGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGroup createEntity(EntityManager em) {
        UserGroup userGroup = new UserGroup()
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedAt(DEFAULT_MODIFIED_AT);
        return userGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGroup createUpdatedEntity(EntityManager em) {
        UserGroup userGroup = new UserGroup()
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);
        return userGroup;
    }

    @BeforeEach
    public void initTest() {
        userGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createUserGroup() throws Exception {
        int databaseSizeBeforeCreate = userGroupRepository.findAll().size();
        // Create the UserGroup
        UserGroupDTO userGroupDTO = userGroupMapper.toDto(userGroup);
        restUserGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeCreate + 1);
        UserGroup testUserGroup = userGroupList.get(userGroupList.size() - 1);
        assertThat(testUserGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserGroup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserGroup.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testUserGroup.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserGroup.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testUserGroup.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
    }

    @Test
    @Transactional
    void createUserGroupWithExistingId() throws Exception {
        // Create the UserGroup with an existing ID
        userGroup.setId(1L);
        UserGroupDTO userGroupDTO = userGroupMapper.toDto(userGroup);

        int databaseSizeBeforeCreate = userGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = userGroupRepository.findAll().size();
        // set the field null
        userGroup.setCreatedBy(null);

        // Create the UserGroup, which fails.
        UserGroupDTO userGroupDTO = userGroupMapper.toDto(userGroup);

        restUserGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userGroupDTO)))
            .andExpect(status().isBadRequest());

        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userGroupRepository.findAll().size();
        // set the field null
        userGroup.setCreatedAt(null);

        // Create the UserGroup, which fails.
        UserGroupDTO userGroupDTO = userGroupMapper.toDto(userGroup);

        restUserGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userGroupDTO)))
            .andExpect(status().isBadRequest());

        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserGroups() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList
        restUserGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedAt").value(hasItem(DEFAULT_MODIFIED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserGroupsWithEagerRelationshipsIsEnabled() throws Exception {
        when(userGroupServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserGroupMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(userGroupServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserGroupsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(userGroupServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserGroupMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(userGroupServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getUserGroup() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get the userGroup
        restUserGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, userGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.intValue()))
            .andExpect(jsonPath("$.modifiedAt").value(DEFAULT_MODIFIED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserGroup() throws Exception {
        // Get the userGroup
        restUserGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserGroup() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        int databaseSizeBeforeUpdate = userGroupRepository.findAll().size();

        // Update the userGroup
        UserGroup updatedUserGroup = userGroupRepository.findById(userGroup.getId()).get();
        // Disconnect from session so that the updates on updatedUserGroup are not directly saved in db
        em.detach(updatedUserGroup);
        updatedUserGroup
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);
        UserGroupDTO userGroupDTO = userGroupMapper.toDto(updatedUserGroup);

        restUserGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeUpdate);
        UserGroup testUserGroup = userGroupList.get(userGroupList.size() - 1);
        assertThat(testUserGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserGroup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserGroup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserGroup.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserGroup.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testUserGroup.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void putNonExistingUserGroup() throws Exception {
        int databaseSizeBeforeUpdate = userGroupRepository.findAll().size();
        userGroup.setId(count.incrementAndGet());

        // Create the UserGroup
        UserGroupDTO userGroupDTO = userGroupMapper.toDto(userGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserGroup() throws Exception {
        int databaseSizeBeforeUpdate = userGroupRepository.findAll().size();
        userGroup.setId(count.incrementAndGet());

        // Create the UserGroup
        UserGroupDTO userGroupDTO = userGroupMapper.toDto(userGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserGroup() throws Exception {
        int databaseSizeBeforeUpdate = userGroupRepository.findAll().size();
        userGroup.setId(count.incrementAndGet());

        // Create the UserGroup
        UserGroupDTO userGroupDTO = userGroupMapper.toDto(userGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userGroupDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserGroupWithPatch() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        int databaseSizeBeforeUpdate = userGroupRepository.findAll().size();

        // Update the userGroup using partial update
        UserGroup partialUpdatedUserGroup = new UserGroup();
        partialUpdatedUserGroup.setId(userGroup.getId());

        partialUpdatedUserGroup.createdBy(UPDATED_CREATED_BY).createdAt(UPDATED_CREATED_AT).modifiedBy(UPDATED_MODIFIED_BY);

        restUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserGroup))
            )
            .andExpect(status().isOk());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeUpdate);
        UserGroup testUserGroup = userGroupList.get(userGroupList.size() - 1);
        assertThat(testUserGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserGroup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserGroup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserGroup.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserGroup.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testUserGroup.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
    }

    @Test
    @Transactional
    void fullUpdateUserGroupWithPatch() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        int databaseSizeBeforeUpdate = userGroupRepository.findAll().size();

        // Update the userGroup using partial update
        UserGroup partialUpdatedUserGroup = new UserGroup();
        partialUpdatedUserGroup.setId(userGroup.getId());

        partialUpdatedUserGroup
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);

        restUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserGroup))
            )
            .andExpect(status().isOk());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeUpdate);
        UserGroup testUserGroup = userGroupList.get(userGroupList.size() - 1);
        assertThat(testUserGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserGroup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserGroup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserGroup.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserGroup.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testUserGroup.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingUserGroup() throws Exception {
        int databaseSizeBeforeUpdate = userGroupRepository.findAll().size();
        userGroup.setId(count.incrementAndGet());

        // Create the UserGroup
        UserGroupDTO userGroupDTO = userGroupMapper.toDto(userGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userGroupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserGroup() throws Exception {
        int databaseSizeBeforeUpdate = userGroupRepository.findAll().size();
        userGroup.setId(count.incrementAndGet());

        // Create the UserGroup
        UserGroupDTO userGroupDTO = userGroupMapper.toDto(userGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserGroup() throws Exception {
        int databaseSizeBeforeUpdate = userGroupRepository.findAll().size();
        userGroup.setId(count.incrementAndGet());

        // Create the UserGroup
        UserGroupDTO userGroupDTO = userGroupMapper.toDto(userGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserGroup() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        int databaseSizeBeforeDelete = userGroupRepository.findAll().size();

        // Delete the userGroup
        restUserGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, userGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
