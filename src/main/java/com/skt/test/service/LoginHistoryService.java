package com.skt.test.service;

import com.skt.test.service.dto.LoginHistoryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skt.test.domain.LoginHistory}.
 */
public interface LoginHistoryService {
    /**
     * Save a loginHistory.
     *
     * @param loginHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    LoginHistoryDTO save(LoginHistoryDTO loginHistoryDTO);

    /**
     * Partially updates a loginHistory.
     *
     * @param loginHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LoginHistoryDTO> partialUpdate(LoginHistoryDTO loginHistoryDTO);

    /**
     * Get all the loginHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LoginHistoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" loginHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LoginHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" loginHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
