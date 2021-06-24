package com.skt.test.service.impl;

import com.skt.test.domain.UserGroup;
import com.skt.test.repository.UserGroupRepository;
import com.skt.test.service.UserGroupService;
import com.skt.test.service.dto.UserGroupDTO;
import com.skt.test.service.mapper.UserGroupMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserGroup}.
 */
@Service
@Transactional
public class UserGroupServiceImpl implements UserGroupService {

    private final Logger log = LoggerFactory.getLogger(UserGroupServiceImpl.class);

    private final UserGroupRepository userGroupRepository;

    private final UserGroupMapper userGroupMapper;

    public UserGroupServiceImpl(UserGroupRepository userGroupRepository, UserGroupMapper userGroupMapper) {
        this.userGroupRepository = userGroupRepository;
        this.userGroupMapper = userGroupMapper;
    }

    @Override
    public UserGroupDTO save(UserGroupDTO userGroupDTO) {
        log.debug("Request to save UserGroup : {}", userGroupDTO);
        UserGroup userGroup = userGroupMapper.toEntity(userGroupDTO);
        userGroup = userGroupRepository.save(userGroup);
        return userGroupMapper.toDto(userGroup);
    }

    @Override
    public Optional<UserGroupDTO> partialUpdate(UserGroupDTO userGroupDTO) {
        log.debug("Request to partially update UserGroup : {}", userGroupDTO);

        return userGroupRepository
            .findById(userGroupDTO.getId())
            .map(
                existingUserGroup -> {
                    userGroupMapper.partialUpdate(existingUserGroup, userGroupDTO);
                    return existingUserGroup;
                }
            )
            .map(userGroupRepository::save)
            .map(userGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserGroups");
        return userGroupRepository.findAll(pageable).map(userGroupMapper::toDto);
    }

    public Page<UserGroupDTO> findAllWithEagerRelationships(Pageable pageable) {
        return userGroupRepository.findAllWithEagerRelationships(pageable).map(userGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserGroupDTO> findOne(Long id) {
        log.debug("Request to get UserGroup : {}", id);
        return userGroupRepository.findOneWithEagerRelationships(id).map(userGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserGroup : {}", id);
        userGroupRepository.deleteById(id);
    }
}
