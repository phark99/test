package com.skt.test.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResourceMetaMapperTest {

    private ResourceMetaMapper resourceMetaMapper;

    @BeforeEach
    public void setUp() {
        resourceMetaMapper = new ResourceMetaMapperImpl();
    }
}
