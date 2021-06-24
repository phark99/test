package com.skt.test.service.mapper;

import com.skt.test.domain.*;
import com.skt.test.service.dto.ApiMetaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApiMeta} and its DTO {@link ApiMetaDTO}.
 */
@Mapper(componentModel = "spring", uses = { ApiMapper.class })
public interface ApiMetaMapper extends EntityMapper<ApiMetaDTO, ApiMeta> {
    @Mapping(target = "api", source = "api", qualifiedByName = "id")
    ApiMetaDTO toDto(ApiMeta s);
}
