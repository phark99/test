package com.skt.test.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApiMetaMapperTest {

    private ApiMetaMapper apiMetaMapper;

    @BeforeEach
    public void setUp() {
        apiMetaMapper = new ApiMetaMapperImpl();
    }
}
