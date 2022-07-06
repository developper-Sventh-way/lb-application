package com.seventh.lotobig.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CouponGratuitConfTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CouponGratuitConf.class);
        CouponGratuitConf couponGratuitConf1 = new CouponGratuitConf();
        couponGratuitConf1.setId(1L);
        CouponGratuitConf couponGratuitConf2 = new CouponGratuitConf();
        couponGratuitConf2.setId(couponGratuitConf1.getId());
        assertThat(couponGratuitConf1).isEqualTo(couponGratuitConf2);
        couponGratuitConf2.setId(2L);
        assertThat(couponGratuitConf1).isNotEqualTo(couponGratuitConf2);
        couponGratuitConf1.setId(null);
        assertThat(couponGratuitConf1).isNotEqualTo(couponGratuitConf2);
    }
}
