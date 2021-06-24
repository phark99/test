package com.skt.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.skt.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectHistory.class);
        ProjectHistory projectHistory1 = new ProjectHistory();
        projectHistory1.setId(1L);
        ProjectHistory projectHistory2 = new ProjectHistory();
        projectHistory2.setId(projectHistory1.getId());
        assertThat(projectHistory1).isEqualTo(projectHistory2);
        projectHistory2.setId(2L);
        assertThat(projectHistory1).isNotEqualTo(projectHistory2);
        projectHistory1.setId(null);
        assertThat(projectHistory1).isNotEqualTo(projectHistory2);
    }
}
