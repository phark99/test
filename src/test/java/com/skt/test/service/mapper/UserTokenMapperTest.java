package com.skt.test.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTokenMapperTest {

    private UserTokenMapper userTokenMapper;

    @BeforeEach
    public void setUp() {
        userTokenMapper = new UserTokenMapperImpl();
    }
}
