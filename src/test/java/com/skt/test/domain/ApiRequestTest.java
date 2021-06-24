package com.skt.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.skt.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApiRequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiRequest.class);
        ApiRequest apiRequest1 = new ApiRequest();
        apiRequest1.setId(1L);
        ApiRequest apiRequest2 = new ApiRequest();
        apiRequest2.setId(apiRequest1.getId());
        assertThat(apiRequest1).isEqualTo(apiRequest2);
        apiRequest2.setId(2L);
        assertThat(apiRequest1).isNotEqualTo(apiRequest2);
        apiRequest1.setId(null);
        assertThat(apiRequest1).isNotEqualTo(apiRequest2);
    }
}
