package com.skt.test.service;

import com.skt.test.service.dto.ApiMetaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skt.test.domain.ApiMeta}.
 */
public interface ApiMetaService {
    /**
     * Save a apiMeta.
     *
     * @param apiMetaDTO the entity to save.
     * @return the persisted entity.
     */
    ApiMetaDTO save(ApiMetaDTO apiMetaDTO);

    /**
     * Partially updates a apiMeta.
     *
     * @param apiMetaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApiMetaDTO> partialUpdate(ApiMetaDTO apiMetaDTO);

    /**
     * Get all the apiMetas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApiMetaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" apiMeta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiMetaDTO> findOne(Long id);

    /**
     * Delete the "id" apiMeta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
