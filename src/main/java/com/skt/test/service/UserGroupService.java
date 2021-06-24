package com.skt.test.service;

import com.skt.test.service.dto.UserGroupDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skt.test.domain.UserGroup}.
 */
public interface UserGroupService {
    /**
     * Save a userGroup.
     *
     * @param userGroupDTO the entity to save.
     * @return the persisted entity.
     */
    UserGroupDTO save(UserGroupDTO userGroupDTO);

    /**
     * Partially updates a userGroup.
     *
     * @param userGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserGroupDTO> partialUpdate(UserGroupDTO userGroupDTO);

    /**
     * Get all the userGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserGroupDTO> findAll(Pageable pageable);

    /**
     * Get all the userGroups with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserGroupDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" userGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserGroupDTO> findOne(Long id);

    /**
     * Delete the "id" userGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
