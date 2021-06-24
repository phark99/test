package com.skt.test.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.skt.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectHistoryDTO.class);
        ProjectHistoryDTO projectHistoryDTO1 = new ProjectHistoryDTO();
        projectHistoryDTO1.setId(1L);
        ProjectHistoryDTO projectHistoryDTO2 = new ProjectHistoryDTO();
        assertThat(projectHistoryDTO1).isNotEqualTo(projectHistoryDTO2);
        projectHistoryDTO2.setId(projectHistoryDTO1.getId());
        assertThat(projectHistoryDTO1).isEqualTo(projectHistoryDTO2);
        projectHistoryDTO2.setId(2L);
        assertThat(projectHistoryDTO1).isNotEqualTo(projectHistoryDTO2);
        projectHistoryDTO1.setId(null);
        assertThat(projectHistoryDTO1).isNotEqualTo(projectHistoryDTO2);
    }
}
