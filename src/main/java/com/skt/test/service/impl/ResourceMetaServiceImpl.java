package com.skt.test.service.impl;

import com.skt.test.domain.ResourceMeta;
import com.skt.test.repository.ResourceMetaRepository;
import com.skt.test.service.ResourceMetaService;
import com.skt.test.service.dto.ResourceMetaDTO;
import com.skt.test.service.mapper.ResourceMetaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ResourceMeta}.
 */
@Service
@Transactional
public class ResourceMetaServiceImpl implements ResourceMetaService {

    private final Logger log = LoggerFactory.getLogger(ResourceMetaServiceImpl.class);

    private final ResourceMetaRepository resourceMetaRepository;

    private final ResourceMetaMapper resourceMetaMapper;

    public ResourceMetaServiceImpl(ResourceMetaRepository resourceMetaRepository, ResourceMetaMapper resourceMetaMapper) {
        this.resourceMetaRepository = resourceMetaRepository;
        this.resourceMetaMapper = resourceMetaMapper;
    }

    @Override
    public ResourceMetaDTO save(ResourceMetaDTO resourceMetaDTO) {
        log.debug("Request to save ResourceMeta : {}", resourceMetaDTO);
        ResourceMeta resourceMeta = resourceMetaMapper.toEntity(resourceMetaDTO);
        resourceMeta = resourceMetaRepository.save(resourceMeta);
        return resourceMetaMapper.toDto(resourceMeta);
    }

    @Override
    public Optional<ResourceMetaDTO> partialUpdate(ResourceMetaDTO resourceMetaDTO) {
        log.debug("Request to partially update ResourceMeta : {}", resourceMetaDTO);

        return resourceMetaRepository
            .findById(resourceMetaDTO.getId())
            .map(
                existingResourceMeta -> {
                    resourceMetaMapper.partialUpdate(existingResourceMeta, resourceMetaDTO);
                    return existingResourceMeta;
                }
            )
            .map(resourceMetaRepository::save)
            .map(resourceMetaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResourceMetaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResourceMetas");
        return resourceMetaRepository.findAll(pageable).map(resourceMetaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResourceMetaDTO> findOne(Long id) {
        log.debug("Request to get ResourceMeta : {}", id);
        return resourceMetaRepository.findById(id).map(resourceMetaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResourceMeta : {}", id);
        resourceMetaRepository.deleteById(id);
    }
}
