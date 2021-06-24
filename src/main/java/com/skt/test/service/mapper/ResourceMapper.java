package com.skt.test.service.mapper;

import com.skt.test.domain.*;
import com.skt.test.service.dto.ResourceDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Resource} and its DTO {@link ResourceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ResourceMapper extends EntityMapper<ResourceDTO, Resource> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResourceDTO toDtoId(Resource resource);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<ResourceDTO> toDtoIdSet(Set<Resource> resource);
}
