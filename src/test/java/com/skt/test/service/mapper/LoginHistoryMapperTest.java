package com.skt.test.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginHistoryMapperTest {

    private LoginHistoryMapper loginHistoryMapper;

    @BeforeEach
    public void setUp() {
        loginHistoryMapper = new LoginHistoryMapperImpl();
    }
}
