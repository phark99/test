package com.skt.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.skt.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserTokenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserToken.class);
        UserToken userToken1 = new UserToken();
        userToken1.setId(1L);
        UserToken userToken2 = new UserToken();
        userToken2.setId(userToken1.getId());
        assertThat(userToken1).isEqualTo(userToken2);
        userToken2.setId(2L);
        assertThat(userToken1).isNotEqualTo(userToken2);
        userToken1.setId(null);
        assertThat(userToken1).isNotEqualTo(userToken2);
    }
}
