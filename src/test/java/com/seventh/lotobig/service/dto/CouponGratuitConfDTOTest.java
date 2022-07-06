package com.seventh.lotobig.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CouponGratuitConfDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CouponGratuitConfDTO.class);
        CouponGratuitConfDTO couponGratuitConfDTO1 = new CouponGratuitConfDTO();
        couponGratuitConfDTO1.setId(1L);
        CouponGratuitConfDTO couponGratuitConfDTO2 = new CouponGratuitConfDTO();
        assertThat(couponGratuitConfDTO1).isNotEqualTo(couponGratuitConfDTO2);
        couponGratuitConfDTO2.setId(couponGratuitConfDTO1.getId());
        assertThat(couponGratuitConfDTO1).isEqualTo(couponGratuitConfDTO2);
        couponGratuitConfDTO2.setId(2L);
        assertThat(couponGratuitConfDTO1).isNotEqualTo(couponGratuitConfDTO2);
        couponGratuitConfDTO1.setId(null);
        assertThat(couponGratuitConfDTO1).isNotEqualTo(couponGratuitConfDTO2);
    }
}
