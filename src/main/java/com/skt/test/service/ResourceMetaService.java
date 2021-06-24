package com.skt.test.service;

import com.skt.test.service.dto.ResourceMetaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skt.test.domain.ResourceMeta}.
 */
public interface ResourceMetaService {
    /**
     * Save a resourceMeta.
     *
     * @param resourceMetaDTO the entity to save.
     * @return the persisted entity.
     */
    ResourceMetaDTO save(ResourceMetaDTO resourceMetaDTO);

    /**
     * Partially updates a resourceMeta.
     *
     * @param resourceMetaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ResourceMetaDTO> partialUpdate(ResourceMetaDTO resourceMetaDTO);

    /**
     * Get all the resourceMetas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResourceMetaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" resourceMeta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResourceMetaDTO> findOne(Long id);

    /**
     * Delete the "id" resourceMeta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
