package com.seventh.lotobig.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LimitConfBorletteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LimitConfBorlette.class);
        LimitConfBorlette limitConfBorlette1 = new LimitConfBorlette();
        limitConfBorlette1.setId(1L);
        LimitConfBorlette limitConfBorlette2 = new LimitConfBorlette();
        limitConfBorlette2.setId(limitConfBorlette1.getId());
        assertThat(limitConfBorlette1).isEqualTo(limitConfBorlette2);
        limitConfBorlette2.setId(2L);
        assertThat(limitConfBorlette1).isNotEqualTo(limitConfBorlette2);
        limitConfBorlette1.setId(null);
        assertThat(limitConfBorlette1).isNotEqualTo(limitConfBorlette2);
    }
}
