package com.skt.test.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApiRequestMapperTest {

    private ApiRequestMapper apiRequestMapper;

    @BeforeEach
    public void setUp() {
        apiRequestMapper = new ApiRequestMapperImpl();
    }
}
