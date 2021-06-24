package com.skt.test.service.impl;

import com.skt.test.domain.Api;
import com.skt.test.repository.ApiRepository;
import com.skt.test.service.ApiService;
import com.skt.test.service.dto.ApiDTO;
import com.skt.test.service.mapper.ApiMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Api}.
 */
@Service
@Transactional
public class ApiServiceImpl implements ApiService {

    private final Logger log = LoggerFactory.getLogger(ApiServiceImpl.class);

    private final ApiRepository apiRepository;

    private final ApiMapper apiMapper;

    public ApiServiceImpl(ApiRepository apiRepository, ApiMapper apiMapper) {
        this.apiRepository = apiRepository;
        this.apiMapper = apiMapper;
    }

    @Override
    public ApiDTO save(ApiDTO apiDTO) {
        log.debug("Request to save Api : {}", apiDTO);
        Api api = apiMapper.toEntity(apiDTO);
        api = apiRepository.save(api);
        return apiMapper.toDto(api);
    }

    @Override
    public Optional<ApiDTO> partialUpdate(ApiDTO apiDTO) {
        log.debug("Request to partially update Api : {}", apiDTO);

        return apiRepository
            .findById(apiDTO.getId())
            .map(
                existingApi -> {
                    apiMapper.partialUpdate(existingApi, apiDTO);
                    return existingApi;
                }
            )
            .map(apiRepository::save)
            .map(apiMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApiDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Apis");
        return apiRepository.findAll(pageable).map(apiMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApiDTO> findOne(Long id) {
        log.debug("Request to get Api : {}", id);
        return apiRepository.findById(id).map(apiMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Api : {}", id);
        apiRepository.deleteById(id);
    }
}
