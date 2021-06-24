package com.skt.test.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectHistoryMapperTest {

    private ProjectHistoryMapper projectHistoryMapper;

    @BeforeEach
    public void setUp() {
        projectHistoryMapper = new ProjectHistoryMapperImpl();
    }
}
