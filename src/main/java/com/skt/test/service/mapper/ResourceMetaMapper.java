package com.skt.test.service.mapper;

import com.skt.test.domain.*;
import com.skt.test.service.dto.ResourceMetaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResourceMeta} and its DTO {@link ResourceMetaDTO}.
 */
@Mapper(componentModel = "spring", uses = { ResourceMapper.class })
public interface ResourceMetaMapper extends EntityMapper<ResourceMetaDTO, ResourceMeta> {
    @Mapping(target = "resource", source = "resource", qualifiedByName = "id")
    ResourceMetaDTO toDto(ResourceMeta s);
}
