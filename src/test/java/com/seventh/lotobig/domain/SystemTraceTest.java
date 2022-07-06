package com.seventh.lotobig.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SystemTraceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemTrace.class);
        SystemTrace systemTrace1 = new SystemTrace();
        systemTrace1.setId(1L);
        SystemTrace systemTrace2 = new SystemTrace();
        systemTrace2.setId(systemTrace1.getId());
        assertThat(systemTrace1).isEqualTo(systemTrace2);
        systemTrace2.setId(2L);
        assertThat(systemTrace1).isNotEqualTo(systemTrace2);
        systemTrace1.setId(null);
        assertThat(systemTrace1).isNotEqualTo(systemTrace2);
    }
}
