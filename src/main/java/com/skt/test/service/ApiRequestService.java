package com.skt.test.service;

import com.skt.test.service.dto.ApiRequestDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skt.test.domain.ApiRequest}.
 */
public interface ApiRequestService {
    /**
     * Save a apiRequest.
     *
     * @param apiRequestDTO the entity to save.
     * @return the persisted entity.
     */
    ApiRequestDTO save(ApiRequestDTO apiRequestDTO);

    /**
     * Partially updates a apiRequest.
     *
     * @param apiRequestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApiRequestDTO> partialUpdate(ApiRequestDTO apiRequestDTO);

    /**
     * Get all the apiRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApiRequestDTO> findAll(Pageable pageable);

    /**
     * Get all the apiRequests with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApiRequestDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" apiRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiRequestDTO> findOne(Long id);

    /**
     * Delete the "id" apiRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
