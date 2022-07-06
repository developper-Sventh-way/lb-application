package com.seventh.lotobig.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserPaymentConfTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPaymentConf.class);
        UserPaymentConf userPaymentConf1 = new UserPaymentConf();
        userPaymentConf1.setId(1L);
        UserPaymentConf userPaymentConf2 = new UserPaymentConf();
        userPaymentConf2.setId(userPaymentConf1.getId());
        assertThat(userPaymentConf1).isEqualTo(userPaymentConf2);
        userPaymentConf2.setId(2L);
        assertThat(userPaymentConf1).isNotEqualTo(userPaymentConf2);
        userPaymentConf1.setId(null);
        assertThat(userPaymentConf1).isNotEqualTo(userPaymentConf2);
    }
}
