package com.skt.test.service.impl;

import com.skt.test.domain.ApiRequest;
import com.skt.test.repository.ApiRequestRepository;
import com.skt.test.service.ApiRequestService;
import com.skt.test.service.dto.ApiRequestDTO;
import com.skt.test.service.mapper.ApiRequestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApiRequest}.
 */
@Service
@Transactional
public class ApiRequestServiceImpl implements ApiRequestService {

    private final Logger log = LoggerFactory.getLogger(ApiRequestServiceImpl.class);

    private final ApiRequestRepository apiRequestRepository;

    private final ApiRequestMapper apiRequestMapper;

    public ApiRequestServiceImpl(ApiRequestRepository apiRequestRepository, ApiRequestMapper apiRequestMapper) {
        this.apiRequestRepository = apiRequestRepository;
        this.apiRequestMapper = apiRequestMapper;
    }

    @Override
    public ApiRequestDTO save(ApiRequestDTO apiRequestDTO) {
        log.debug("Request to save ApiRequest : {}", apiRequestDTO);
        ApiRequest apiRequest = apiRequestMapper.toEntity(apiRequestDTO);
        apiRequest = apiRequestRepository.save(apiRequest);
        return apiRequestMapper.toDto(apiRequest);
    }

    @Override
    public Optional<ApiRequestDTO> partialUpdate(ApiRequestDTO apiRequestDTO) {
        log.debug("Request to partially update ApiRequest : {}", apiRequestDTO);

        return apiRequestRepository
            .findById(apiRequestDTO.getId())
            .map(
                existingApiRequest -> {
                    apiRequestMapper.partialUpdate(existingApiRequest, apiRequestDTO);
                    return existingApiRequest;
                }
            )
            .map(apiRequestRepository::save)
            .map(apiRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApiRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApiRequests");
        return apiRequestRepository.findAll(pageable).map(apiRequestMapper::toDto);
    }

    public Page<ApiRequestDTO> findAllWithEagerRelationships(Pageable pageable) {
        return apiRequestRepository.findAllWithEagerRelationships(pageable).map(apiRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApiRequestDTO> findOne(Long id) {
        log.debug("Request to get ApiRequest : {}", id);
        return apiRequestRepository.findOneWithEagerRelationships(id).map(apiRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApiRequest : {}", id);
        apiRequestRepository.deleteById(id);
    }
}
