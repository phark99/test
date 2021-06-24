package com.skt.test.service.impl;

import com.skt.test.domain.UserToken;
import com.skt.test.repository.UserTokenRepository;
import com.skt.test.service.UserTokenService;
import com.skt.test.service.dto.UserTokenDTO;
import com.skt.test.service.mapper.UserTokenMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserToken}.
 */
@Service
@Transactional
public class UserTokenServiceImpl implements UserTokenService {

    private final Logger log = LoggerFactory.getLogger(UserTokenServiceImpl.class);

    private final UserTokenRepository userTokenRepository;

    private final UserTokenMapper userTokenMapper;

    public UserTokenServiceImpl(UserTokenRepository userTokenRepository, UserTokenMapper userTokenMapper) {
        this.userTokenRepository = userTokenRepository;
        this.userTokenMapper = userTokenMapper;
    }

    @Override
    public UserTokenDTO save(UserTokenDTO userTokenDTO) {
        log.debug("Request to save UserToken : {}", userTokenDTO);
        UserToken userToken = userTokenMapper.toEntity(userTokenDTO);
        userToken = userTokenRepository.save(userToken);
        return userTokenMapper.toDto(userToken);
    }

    @Override
    public Optional<UserTokenDTO> partialUpdate(UserTokenDTO userTokenDTO) {
        log.debug("Request to partially update UserToken : {}", userTokenDTO);

        return userTokenRepository
            .findById(userTokenDTO.getId())
            .map(
                existingUserToken -> {
                    userTokenMapper.partialUpdate(existingUserToken, userTokenDTO);
                    return existingUserToken;
                }
            )
            .map(userTokenRepository::save)
            .map(userTokenMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserTokenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserTokens");
        return userTokenRepository.findAll(pageable).map(userTokenMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserTokenDTO> findOne(Long id) {
        log.debug("Request to get UserToken : {}", id);
        return userTokenRepository.findById(id).map(userTokenMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserToken : {}", id);
        userTokenRepository.deleteById(id);
    }
}
