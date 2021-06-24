package com.skt.test.service.mapper;

import com.skt.test.domain.*;
import com.skt.test.service.dto.UserTokenDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserToken} and its DTO {@link UserTokenDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface UserTokenMapper extends EntityMapper<UserTokenDTO, UserToken> {
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    UserTokenDTO toDto(UserToken s);
}
