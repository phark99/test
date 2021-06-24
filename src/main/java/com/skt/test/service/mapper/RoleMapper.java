package com.skt.test.service.mapper;

import com.skt.test.domain.*;
import com.skt.test.service.dto.RoleDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Role} and its DTO {@link RoleDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, ResourceMapper.class })
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {
    @Mapping(target = "users", source = "users", qualifiedByName = "idSet")
    @Mapping(target = "resources", source = "resources", qualifiedByName = "idSet")
    RoleDTO toDto(Role s);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<RoleDTO> toDtoIdSet(Set<Role> role);

    @Mapping(target = "removeUser", ignore = true)
    @Mapping(target = "removeResource", ignore = true)
    Role toEntity(RoleDTO roleDTO);
}
