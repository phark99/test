package com.skt.test.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.skt.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApiMetaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiMetaDTO.class);
        ApiMetaDTO apiMetaDTO1 = new ApiMetaDTO();
        apiMetaDTO1.setId(1L);
        ApiMetaDTO apiMetaDTO2 = new ApiMetaDTO();
        assertThat(apiMetaDTO1).isNotEqualTo(apiMetaDTO2);
        apiMetaDTO2.setId(apiMetaDTO1.getId());
        assertThat(apiMetaDTO1).isEqualTo(apiMetaDTO2);
        apiMetaDTO2.setId(2L);
        assertThat(apiMetaDTO1).isNotEqualTo(apiMetaDTO2);
        apiMetaDTO1.setId(null);
        assertThat(apiMetaDTO1).isNotEqualTo(apiMetaDTO2);
    }
}
