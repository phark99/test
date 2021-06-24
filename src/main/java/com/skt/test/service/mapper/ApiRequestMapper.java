package com.skt.test.service.mapper;

import com.skt.test.domain.*;
import com.skt.test.service.dto.ApiRequestDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApiRequest} and its DTO {@link ApiRequestDTO}.
 */
@Mapper(componentModel = "spring", uses = { ApiMapper.class })
public interface ApiRequestMapper extends EntityMapper<ApiRequestDTO, ApiRequest> {
    @Mapping(target = "apis", source = "apis", qualifiedByName = "idSet")
    ApiRequestDTO toDto(ApiRequest s);

    @Mapping(target = "removeApi", ignore = true)
    ApiRequest toEntity(ApiRequestDTO apiRequestDTO);
}
