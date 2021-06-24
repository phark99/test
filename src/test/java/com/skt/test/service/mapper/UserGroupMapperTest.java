package com.skt.test.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserGroupMapperTest {

    private UserGroupMapper userGroupMapper;

    @BeforeEach
    public void setUp() {
        userGroupMapper = new UserGroupMapperImpl();
    }
}
