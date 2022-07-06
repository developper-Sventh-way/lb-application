package com.seventh.lotobig.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LimitConfPointTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LimitConfPoint.class);
        LimitConfPoint limitConfPoint1 = new LimitConfPoint();
        limitConfPoint1.setId(1L);
        LimitConfPoint limitConfPoint2 = new LimitConfPoint();
        limitConfPoint2.setId(limitConfPoint1.getId());
        assertThat(limitConfPoint1).isEqualTo(limitConfPoint2);
        limitConfPoint2.setId(2L);
        assertThat(limitConfPoint1).isNotEqualTo(limitConfPoint2);
        limitConfPoint1.setId(null);
        assertThat(limitConfPoint1).isNotEqualTo(limitConfPoint2);
    }
}
