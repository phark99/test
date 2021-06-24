package com.skt.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.skt.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResourceMetaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResourceMeta.class);
        ResourceMeta resourceMeta1 = new ResourceMeta();
        resourceMeta1.setId(1L);
        ResourceMeta resourceMeta2 = new ResourceMeta();
        resourceMeta2.setId(resourceMeta1.getId());
        assertThat(resourceMeta1).isEqualTo(resourceMeta2);
        resourceMeta2.setId(2L);
        assertThat(resourceMeta1).isNotEqualTo(resourceMeta2);
        resourceMeta1.setId(null);
        assertThat(resourceMeta1).isNotEqualTo(resourceMeta2);
    }
}
