package com.skt.test.service.mapper;

import com.skt.test.domain.*;
import com.skt.test.service.dto.LoginHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LoginHistory} and its DTO {@link LoginHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface LoginHistoryMapper extends EntityMapper<LoginHistoryDTO, LoginHistory> {
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    LoginHistoryDTO toDto(LoginHistory s);
}
