package com.seventh.lotobig.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LimitConfManagerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LimitConfManager.class);
        LimitConfManager limitConfManager1 = new LimitConfManager();
        limitConfManager1.setId(1L);
        LimitConfManager limitConfManager2 = new LimitConfManager();
        limitConfManager2.setId(limitConfManager1.getId());
        assertThat(limitConfManager1).isEqualTo(limitConfManager2);
        limitConfManager2.setId(2L);
        assertThat(limitConfManager1).isNotEqualTo(limitConfManager2);
        limitConfManager1.setId(null);
        assertThat(limitConfManager1).isNotEqualTo(limitConfManager2);
    }
}
