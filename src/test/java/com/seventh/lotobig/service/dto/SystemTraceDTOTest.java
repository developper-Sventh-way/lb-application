package com.seventh.lotobig.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SystemTraceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemTraceDTO.class);
        SystemTraceDTO systemTraceDTO1 = new SystemTraceDTO();
        systemTraceDTO1.setId(1L);
        SystemTraceDTO systemTraceDTO2 = new SystemTraceDTO();
        assertThat(systemTraceDTO1).isNotEqualTo(systemTraceDTO2);
        systemTraceDTO2.setId(systemTraceDTO1.getId());
        assertThat(systemTraceDTO1).isEqualTo(systemTraceDTO2);
        systemTraceDTO2.setId(2L);
        assertThat(systemTraceDTO1).isNotEqualTo(systemTraceDTO2);
        systemTraceDTO1.setId(null);
        assertThat(systemTraceDTO1).isNotEqualTo(systemTraceDTO2);
    }
}
