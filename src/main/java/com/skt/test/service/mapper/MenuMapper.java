package com.skt.test.service.mapper;

import com.skt.test.domain.*;
import com.skt.test.service.dto.MenuDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Menu} and its DTO {@link MenuDTO}.
 */
@Mapper(componentModel = "spring", uses = { RoleMapper.class })
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {
    @Mapping(target = "roles", source = "roles", qualifiedByName = "idSet")
    MenuDTO toDto(Menu s);

    @Mapping(target = "removeRole", ignore = true)
    Menu toEntity(MenuDTO menuDTO);
}
