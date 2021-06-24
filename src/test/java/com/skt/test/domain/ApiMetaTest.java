package com.skt.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.skt.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApiMetaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiMeta.class);
        ApiMeta apiMeta1 = new ApiMeta();
        apiMeta1.setId(1L);
        ApiMeta apiMeta2 = new ApiMeta();
        apiMeta2.setId(apiMeta1.getId());
        assertThat(apiMeta1).isEqualTo(apiMeta2);
        apiMeta2.setId(2L);
        assertThat(apiMeta1).isNotEqualTo(apiMeta2);
        apiMeta1.setId(null);
        assertThat(apiMeta1).isNotEqualTo(apiMeta2);
    }
}
