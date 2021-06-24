package com.skt.test.service.impl;

import com.skt.test.domain.LoginHistory;
import com.skt.test.repository.LoginHistoryRepository;
import com.skt.test.service.LoginHistoryService;
import com.skt.test.service.dto.LoginHistoryDTO;
import com.skt.test.service.mapper.LoginHistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LoginHistory}.
 */
@Service
@Transactional
public class LoginHistoryServiceImpl implements LoginHistoryService {

    private final Logger log = LoggerFactory.getLogger(LoginHistoryServiceImpl.class);

    private final LoginHistoryRepository loginHistoryRepository;

    private final LoginHistoryMapper loginHistoryMapper;

    public LoginHistoryServiceImpl(LoginHistoryRepository loginHistoryRepository, LoginHistoryMapper loginHistoryMapper) {
        this.loginHistoryRepository = loginHistoryRepository;
        this.loginHistoryMapper = loginHistoryMapper;
    }

    @Override
    public LoginHistoryDTO save(LoginHistoryDTO loginHistoryDTO) {
        log.debug("Request to save LoginHistory : {}", loginHistoryDTO);
        LoginHistory loginHistory = loginHistoryMapper.toEntity(loginHistoryDTO);
        loginHistory = loginHistoryRepository.save(loginHistory);
        return loginHistoryMapper.toDto(loginHistory);
    }

    @Override
    public Optional<LoginHistoryDTO> partialUpdate(LoginHistoryDTO loginHistoryDTO) {
        log.debug("Request to partially update LoginHistory : {}", loginHistoryDTO);

        return loginHistoryRepository
            .findById(loginHistoryDTO.getId())
            .map(
                existingLoginHistory -> {
                    loginHistoryMapper.partialUpdate(existingLoginHistory, loginHistoryDTO);
                    return existingLoginHistory;
                }
            )
            .map(loginHistoryRepository::save)
            .map(loginHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LoginHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LoginHistories");
        return loginHistoryRepository.findAll(pageable).map(loginHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LoginHistoryDTO> findOne(Long id) {
        log.debug("Request to get LoginHistory : {}", id);
        return loginHistoryRepository.findById(id).map(loginHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LoginHistory : {}", id);
        loginHistoryRepository.deleteById(id);
    }
}
