package com.skt.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.skt.test.IntegrationTest;
import com.skt.test.domain.Role;
import com.skt.test.repository.RoleRepository;
import com.skt.test.service.RoleService;
import com.skt.test.service.dto.RoleDTO;
import com.skt.test.service.mapper.RoleMapper;
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
 * Integration tests for the {@link RoleResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RoleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_APPLY_TARGET = "AAAAAAAAAA";
    private static final String UPDATED_APPLY_TARGET = "BBBBBBBBBB";

    private static final String DEFAULT_PERMISSION = "AAAAAAAAAA";
    private static final String UPDATED_PERMISSION = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_MODIFIED_BY = 1L;
    private static final Long UPDATED_MODIFIED_BY = 2L;

    private static final LocalDate DEFAULT_MODIFIED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/roles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoleRepository roleRepository;

    @Mock
    private RoleRepository roleRepositoryMock;

    @Autowired
    private RoleMapper roleMapper;

    @Mock
    private RoleService roleServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoleMockMvc;

    private Role role;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Role createEntity(EntityManager em) {
        Role role = new Role()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .applyTarget(DEFAULT_APPLY_TARGET)
            .permission(DEFAULT_PERMISSION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedAt(DEFAULT_MODIFIED_AT);
        return role;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Role createUpdatedEntity(EntityManager em) {
        Role role = new Role()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .applyTarget(UPDATED_APPLY_TARGET)
            .permission(UPDATED_PERMISSION)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);
        return role;
    }

    @BeforeEach
    public void initTest() {
        role = createEntity(em);
    }

    @Test
    @Transactional
    void createRole() throws Exception {
        int databaseSizeBeforeCreate = roleRepository.findAll().size();
        // Create the Role
        RoleDTO roleDTO = roleMapper.toDto(role);
        restRoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isCreated());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeCreate + 1);
        Role testRole = roleList.get(roleList.size() - 1);
        assertThat(testRole.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRole.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRole.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRole.getApplyTarget()).isEqualTo(DEFAULT_APPLY_TARGET);
        assertThat(testRole.getPermission()).isEqualTo(DEFAULT_PERMISSION);
        assertThat(testRole.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRole.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testRole.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testRole.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
    }

    @Test
    @Transactional
    void createRoleWithExistingId() throws Exception {
        // Create the Role with an existing ID
        role.setId(1L);
        RoleDTO roleDTO = roleMapper.toDto(role);

        int databaseSizeBeforeCreate = roleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setCreatedBy(null);

        // Create the Role, which fails.
        RoleDTO roleDTO = roleMapper.toDto(role);

        restRoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setCreatedAt(null);

        // Create the Role, which fails.
        RoleDTO roleDTO = roleMapper.toDto(role);

        restRoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRoles() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList
        restRoleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(role.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].applyTarget").value(hasItem(DEFAULT_APPLY_TARGET)))
            .andExpect(jsonPath("$.[*].permission").value(hasItem(DEFAULT_PERMISSION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedAt").value(hasItem(DEFAULT_MODIFIED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRolesWithEagerRelationshipsIsEnabled() throws Exception {
        when(roleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRoleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(roleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRolesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(roleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRoleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(roleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get the role
        restRoleMockMvc
            .perform(get(ENTITY_API_URL_ID, role.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(role.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.applyTarget").value(DEFAULT_APPLY_TARGET))
            .andExpect(jsonPath("$.permission").value(DEFAULT_PERMISSION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.intValue()))
            .andExpect(jsonPath("$.modifiedAt").value(DEFAULT_MODIFIED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRole() throws Exception {
        // Get the role
        restRoleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        int databaseSizeBeforeUpdate = roleRepository.findAll().size();

        // Update the role
        Role updatedRole = roleRepository.findById(role.getId()).get();
        // Disconnect from session so that the updates on updatedRole are not directly saved in db
        em.detach(updatedRole);
        updatedRole
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .applyTarget(UPDATED_APPLY_TARGET)
            .permission(UPDATED_PERMISSION)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);
        RoleDTO roleDTO = roleMapper.toDto(updatedRole);

        restRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleDTO))
            )
            .andExpect(status().isOk());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeUpdate);
        Role testRole = roleList.get(roleList.size() - 1);
        assertThat(testRole.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRole.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRole.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRole.getApplyTarget()).isEqualTo(UPDATED_APPLY_TARGET);
        assertThat(testRole.getPermission()).isEqualTo(UPDATED_PERMISSION);
        assertThat(testRole.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRole.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRole.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testRole.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void putNonExistingRole() throws Exception {
        int databaseSizeBeforeUpdate = roleRepository.findAll().size();
        role.setId(count.incrementAndGet());

        // Create the Role
        RoleDTO roleDTO = roleMapper.toDto(role);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRole() throws Exception {
        int databaseSizeBeforeUpdate = roleRepository.findAll().size();
        role.setId(count.incrementAndGet());

        // Create the Role
        RoleDTO roleDTO = roleMapper.toDto(role);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRole() throws Exception {
        int databaseSizeBeforeUpdate = roleRepository.findAll().size();
        role.setId(count.incrementAndGet());

        // Create the Role
        RoleDTO roleDTO = roleMapper.toDto(role);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoleWithPatch() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        int databaseSizeBeforeUpdate = roleRepository.findAll().size();

        // Update the role using partial update
        Role partialUpdatedRole = new Role();
        partialUpdatedRole.setId(role.getId());

        partialUpdatedRole
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .applyTarget(UPDATED_APPLY_TARGET)
            .permission(UPDATED_PERMISSION)
            .createdAt(UPDATED_CREATED_AT);

        restRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRole))
            )
            .andExpect(status().isOk());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeUpdate);
        Role testRole = roleList.get(roleList.size() - 1);
        assertThat(testRole.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRole.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRole.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRole.getApplyTarget()).isEqualTo(UPDATED_APPLY_TARGET);
        assertThat(testRole.getPermission()).isEqualTo(UPDATED_PERMISSION);
        assertThat(testRole.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRole.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRole.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testRole.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
    }

    @Test
    @Transactional
    void fullUpdateRoleWithPatch() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        int databaseSizeBeforeUpdate = roleRepository.findAll().size();

        // Update the role using partial update
        Role partialUpdatedRole = new Role();
        partialUpdatedRole.setId(role.getId());

        partialUpdatedRole
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .applyTarget(UPDATED_APPLY_TARGET)
            .permission(UPDATED_PERMISSION)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);

        restRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRole))
            )
            .andExpect(status().isOk());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeUpdate);
        Role testRole = roleList.get(roleList.size() - 1);
        assertThat(testRole.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRole.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRole.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRole.getApplyTarget()).isEqualTo(UPDATED_APPLY_TARGET);
        assertThat(testRole.getPermission()).isEqualTo(UPDATED_PERMISSION);
        assertThat(testRole.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRole.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRole.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testRole.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingRole() throws Exception {
        int databaseSizeBeforeUpdate = roleRepository.findAll().size();
        role.setId(count.incrementAndGet());

        // Create the Role
        RoleDTO roleDTO = roleMapper.toDto(role);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRole() throws Exception {
        int databaseSizeBeforeUpdate = roleRepository.findAll().size();
        role.setId(count.incrementAndGet());

        // Create the Role
        RoleDTO roleDTO = roleMapper.toDto(role);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRole() throws Exception {
        int databaseSizeBeforeUpdate = roleRepository.findAll().size();
        role.setId(count.incrementAndGet());

        // Create the Role
        RoleDTO roleDTO = roleMapper.toDto(role);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        int databaseSizeBeforeDelete = roleRepository.findAll().size();

        // Delete the role
        restRoleMockMvc
            .perform(delete(ENTITY_API_URL_ID, role.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
