package com.skt.test.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.skt.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResourceMetaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResourceMetaDTO.class);
        ResourceMetaDTO resourceMetaDTO1 = new ResourceMetaDTO();
        resourceMetaDTO1.setId(1L);
        ResourceMetaDTO resourceMetaDTO2 = new ResourceMetaDTO();
        assertThat(resourceMetaDTO1).isNotEqualTo(resourceMetaDTO2);
        resourceMetaDTO2.setId(resourceMetaDTO1.getId());
        assertThat(resourceMetaDTO1).isEqualTo(resourceMetaDTO2);
        resourceMetaDTO2.setId(2L);
        assertThat(resourceMetaDTO1).isNotEqualTo(resourceMetaDTO2);
        resourceMetaDTO1.setId(null);
        assertThat(resourceMetaDTO1).isNotEqualTo(resourceMetaDTO2);
    }
}
