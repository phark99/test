package com.skt.test.service.mapper;

import com.skt.test.domain.*;
import com.skt.test.service.dto.ApiDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Api} and its DTO {@link ApiDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApiMapper extends EntityMapper<ApiDTO, Api> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApiDTO toDtoId(Api api);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<ApiDTO> toDtoIdSet(Set<Api> api);
}
