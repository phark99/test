package com.skt.test.service;

import com.skt.test.service.dto.ProjectHistoryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skt.test.domain.ProjectHistory}.
 */
public interface ProjectHistoryService {
    /**
     * Save a projectHistory.
     *
     * @param projectHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    ProjectHistoryDTO save(ProjectHistoryDTO projectHistoryDTO);

    /**
     * Partially updates a projectHistory.
     *
     * @param projectHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProjectHistoryDTO> partialUpdate(ProjectHistoryDTO projectHistoryDTO);

    /**
     * Get all the projectHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProjectHistoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" projectHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" projectHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
