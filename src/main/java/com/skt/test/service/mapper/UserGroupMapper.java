package com.skt.test.service.mapper;

import com.skt.test.domain.*;
import com.skt.test.service.dto.UserGroupDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserGroup} and its DTO {@link UserGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, RoleMapper.class })
public interface UserGroupMapper extends EntityMapper<UserGroupDTO, UserGroup> {
    @Mapping(target = "users", source = "users", qualifiedByName = "idSet")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "idSet")
    UserGroupDTO toDto(UserGroup s);

    @Mapping(target = "removeUser", ignore = true)
    @Mapping(target = "removeRole", ignore = true)
    UserGroup toEntity(UserGroupDTO userGroupDTO);
}
