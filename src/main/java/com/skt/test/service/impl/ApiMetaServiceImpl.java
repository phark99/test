package com.skt.test.service.impl;

import com.skt.test.domain.ApiMeta;
import com.skt.test.repository.ApiMetaRepository;
import com.skt.test.service.ApiMetaService;
import com.skt.test.service.dto.ApiMetaDTO;
import com.skt.test.service.mapper.ApiMetaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApiMeta}.
 */
@Service
@Transactional
public class ApiMetaServiceImpl implements ApiMetaService {

    private final Logger log = LoggerFactory.getLogger(ApiMetaServiceImpl.class);

    private final ApiMetaRepository apiMetaRepository;

    private final ApiMetaMapper apiMetaMapper;

    public ApiMetaServiceImpl(ApiMetaRepository apiMetaRepository, ApiMetaMapper apiMetaMapper) {
        this.apiMetaRepository = apiMetaRepository;
        this.apiMetaMapper = apiMetaMapper;
    }

    @Override
    public ApiMetaDTO save(ApiMetaDTO apiMetaDTO) {
        log.debug("Request to save ApiMeta : {}", apiMetaDTO);
        ApiMeta apiMeta = apiMetaMapper.toEntity(apiMetaDTO);
        apiMeta = apiMetaRepository.save(apiMeta);
        return apiMetaMapper.toDto(apiMeta);
    }

    @Override
    public Optional<ApiMetaDTO> partialUpdate(ApiMetaDTO apiMetaDTO) {
        log.debug("Request to partially update ApiMeta : {}", apiMetaDTO);

        return apiMetaRepository
            .findById(apiMetaDTO.getId())
            .map(
                existingApiMeta -> {
                    apiMetaMapper.partialUpdate(existingApiMeta, apiMetaDTO);
                    return existingApiMeta;
                }
            )
            .map(apiMetaRepository::save)
            .map(apiMetaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApiMetaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApiMetas");
        return apiMetaRepository.findAll(pageable).map(apiMetaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApiMetaDTO> findOne(Long id) {
        log.debug("Request to get ApiMeta : {}", id);
        return apiMetaRepository.findById(id).map(apiMetaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApiMeta : {}", id);
        apiMetaRepository.deleteById(id);
    }
}
