package com.skt.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.skt.test.IntegrationTest;
import com.skt.test.domain.Menu;
import com.skt.test.repository.MenuRepository;
import com.skt.test.service.MenuService;
import com.skt.test.service.dto.MenuDTO;
import com.skt.test.service.mapper.MenuMapper;
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
 * Integration tests for the {@link MenuResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MenuResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DEPTH = 1;
    private static final Integer UPDATED_DEPTH = 2;

    private static final Integer DEFAULT_DISP_ORDER = 1;
    private static final Integer UPDATED_DISP_ORDER = 2;

    private static final String DEFAULT_DISP_YN = "AAAAAAAAAA";
    private static final String UPDATED_DISP_YN = "BBBBBBBBBB";

    private static final String DEFAULT_URI_PATH = "AAAAAAAAAA";
    private static final String UPDATED_URI_PATH = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_MODIFIED_BY = 1L;
    private static final Long UPDATED_MODIFIED_BY = 2L;

    private static final LocalDate DEFAULT_MODIFIED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/menus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MenuRepository menuRepository;

    @Mock
    private MenuRepository menuRepositoryMock;

    @Autowired
    private MenuMapper menuMapper;

    @Mock
    private MenuService menuServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMenuMockMvc;

    private Menu menu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Menu createEntity(EntityManager em) {
        Menu menu = new Menu()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .depth(DEFAULT_DEPTH)
            .dispOrder(DEFAULT_DISP_ORDER)
            .dispYn(DEFAULT_DISP_YN)
            .uriPath(DEFAULT_URI_PATH)
            .parentId(DEFAULT_PARENT_ID)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedAt(DEFAULT_MODIFIED_AT);
        return menu;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Menu createUpdatedEntity(EntityManager em) {
        Menu menu = new Menu()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .depth(UPDATED_DEPTH)
            .dispOrder(UPDATED_DISP_ORDER)
            .dispYn(UPDATED_DISP_YN)
            .uriPath(UPDATED_URI_PATH)
            .parentId(UPDATED_PARENT_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);
        return menu;
    }

    @BeforeEach
    public void initTest() {
        menu = createEntity(em);
    }

    @Test
    @Transactional
    void createMenu() throws Exception {
        int databaseSizeBeforeCreate = menuRepository.findAll().size();
        // Create the Menu
        MenuDTO menuDTO = menuMapper.toDto(menu);
        restMenuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuDTO)))
            .andExpect(status().isCreated());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeCreate + 1);
        Menu testMenu = menuList.get(menuList.size() - 1);
        assertThat(testMenu.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMenu.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMenu.getDepth()).isEqualTo(DEFAULT_DEPTH);
        assertThat(testMenu.getDispOrder()).isEqualTo(DEFAULT_DISP_ORDER);
        assertThat(testMenu.getDispYn()).isEqualTo(DEFAULT_DISP_YN);
        assertThat(testMenu.getUriPath()).isEqualTo(DEFAULT_URI_PATH);
        assertThat(testMenu.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testMenu.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMenu.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testMenu.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testMenu.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
    }

    @Test
    @Transactional
    void createMenuWithExistingId() throws Exception {
        // Create the Menu with an existing ID
        menu.setId(1L);
        MenuDTO menuDTO = menuMapper.toDto(menu);

        int databaseSizeBeforeCreate = menuRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuRepository.findAll().size();
        // set the field null
        menu.setCreatedBy(null);

        // Create the Menu, which fails.
        MenuDTO menuDTO = menuMapper.toDto(menu);

        restMenuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuDTO)))
            .andExpect(status().isBadRequest());

        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuRepository.findAll().size();
        // set the field null
        menu.setCreatedAt(null);

        // Create the Menu, which fails.
        MenuDTO menuDTO = menuMapper.toDto(menu);

        restMenuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuDTO)))
            .andExpect(status().isBadRequest());

        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMenus() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList
        restMenuMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menu.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].depth").value(hasItem(DEFAULT_DEPTH)))
            .andExpect(jsonPath("$.[*].dispOrder").value(hasItem(DEFAULT_DISP_ORDER)))
            .andExpect(jsonPath("$.[*].dispYn").value(hasItem(DEFAULT_DISP_YN)))
            .andExpect(jsonPath("$.[*].uriPath").value(hasItem(DEFAULT_URI_PATH)))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedAt").value(hasItem(DEFAULT_MODIFIED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMenusWithEagerRelationshipsIsEnabled() throws Exception {
        when(menuServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMenuMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(menuServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMenusWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(menuServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMenuMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(menuServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getMenu() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get the menu
        restMenuMockMvc
            .perform(get(ENTITY_API_URL_ID, menu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(menu.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.depth").value(DEFAULT_DEPTH))
            .andExpect(jsonPath("$.dispOrder").value(DEFAULT_DISP_ORDER))
            .andExpect(jsonPath("$.dispYn").value(DEFAULT_DISP_YN))
            .andExpect(jsonPath("$.uriPath").value(DEFAULT_URI_PATH))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.intValue()))
            .andExpect(jsonPath("$.modifiedAt").value(DEFAULT_MODIFIED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMenu() throws Exception {
        // Get the menu
        restMenuMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMenu() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        int databaseSizeBeforeUpdate = menuRepository.findAll().size();

        // Update the menu
        Menu updatedMenu = menuRepository.findById(menu.getId()).get();
        // Disconnect from session so that the updates on updatedMenu are not directly saved in db
        em.detach(updatedMenu);
        updatedMenu
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .depth(UPDATED_DEPTH)
            .dispOrder(UPDATED_DISP_ORDER)
            .dispYn(UPDATED_DISP_YN)
            .uriPath(UPDATED_URI_PATH)
            .parentId(UPDATED_PARENT_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);
        MenuDTO menuDTO = menuMapper.toDto(updatedMenu);

        restMenuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, menuDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(menuDTO))
            )
            .andExpect(status().isOk());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate);
        Menu testMenu = menuList.get(menuList.size() - 1);
        assertThat(testMenu.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMenu.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMenu.getDepth()).isEqualTo(UPDATED_DEPTH);
        assertThat(testMenu.getDispOrder()).isEqualTo(UPDATED_DISP_ORDER);
        assertThat(testMenu.getDispYn()).isEqualTo(UPDATED_DISP_YN);
        assertThat(testMenu.getUriPath()).isEqualTo(UPDATED_URI_PATH);
        assertThat(testMenu.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testMenu.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMenu.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testMenu.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testMenu.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void putNonExistingMenu() throws Exception {
        int databaseSizeBeforeUpdate = menuRepository.findAll().size();
        menu.setId(count.incrementAndGet());

        // Create the Menu
        MenuDTO menuDTO = menuMapper.toDto(menu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, menuDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(menuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMenu() throws Exception {
        int databaseSizeBeforeUpdate = menuRepository.findAll().size();
        menu.setId(count.incrementAndGet());

        // Create the Menu
        MenuDTO menuDTO = menuMapper.toDto(menu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(menuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMenu() throws Exception {
        int databaseSizeBeforeUpdate = menuRepository.findAll().size();
        menu.setId(count.incrementAndGet());

        // Create the Menu
        MenuDTO menuDTO = menuMapper.toDto(menu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMenuWithPatch() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        int databaseSizeBeforeUpdate = menuRepository.findAll().size();

        // Update the menu using partial update
        Menu partialUpdatedMenu = new Menu();
        partialUpdatedMenu.setId(menu.getId());

        partialUpdatedMenu
            .description(UPDATED_DESCRIPTION)
            .dispOrder(UPDATED_DISP_ORDER)
            .dispYn(UPDATED_DISP_YN)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restMenuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMenu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMenu))
            )
            .andExpect(status().isOk());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate);
        Menu testMenu = menuList.get(menuList.size() - 1);
        assertThat(testMenu.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMenu.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMenu.getDepth()).isEqualTo(DEFAULT_DEPTH);
        assertThat(testMenu.getDispOrder()).isEqualTo(UPDATED_DISP_ORDER);
        assertThat(testMenu.getDispYn()).isEqualTo(UPDATED_DISP_YN);
        assertThat(testMenu.getUriPath()).isEqualTo(DEFAULT_URI_PATH);
        assertThat(testMenu.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testMenu.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMenu.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testMenu.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testMenu.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
    }

    @Test
    @Transactional
    void fullUpdateMenuWithPatch() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        int databaseSizeBeforeUpdate = menuRepository.findAll().size();

        // Update the menu using partial update
        Menu partialUpdatedMenu = new Menu();
        partialUpdatedMenu.setId(menu.getId());

        partialUpdatedMenu
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .depth(UPDATED_DEPTH)
            .dispOrder(UPDATED_DISP_ORDER)
            .dispYn(UPDATED_DISP_YN)
            .uriPath(UPDATED_URI_PATH)
            .parentId(UPDATED_PARENT_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT);

        restMenuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMenu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMenu))
            )
            .andExpect(status().isOk());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate);
        Menu testMenu = menuList.get(menuList.size() - 1);
        assertThat(testMenu.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMenu.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMenu.getDepth()).isEqualTo(UPDATED_DEPTH);
        assertThat(testMenu.getDispOrder()).isEqualTo(UPDATED_DISP_ORDER);
        assertThat(testMenu.getDispYn()).isEqualTo(UPDATED_DISP_YN);
        assertThat(testMenu.getUriPath()).isEqualTo(UPDATED_URI_PATH);
        assertThat(testMenu.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testMenu.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMenu.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testMenu.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testMenu.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingMenu() throws Exception {
        int databaseSizeBeforeUpdate = menuRepository.findAll().size();
        menu.setId(count.incrementAndGet());

        // Create the Menu
        MenuDTO menuDTO = menuMapper.toDto(menu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, menuDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(menuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMenu() throws Exception {
        int databaseSizeBeforeUpdate = menuRepository.findAll().size();
        menu.setId(count.incrementAndGet());

        // Create the Menu
        MenuDTO menuDTO = menuMapper.toDto(menu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(menuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMenu() throws Exception {
        int databaseSizeBeforeUpdate = menuRepository.findAll().size();
        menu.setId(count.incrementAndGet());

        // Create the Menu
        MenuDTO menuDTO = menuMapper.toDto(menu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(menuDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMenu() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        int databaseSizeBeforeDelete = menuRepository.findAll().size();

        // Delete the menu
        restMenuMockMvc
            .perform(delete(ENTITY_API_URL_ID, menu.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
