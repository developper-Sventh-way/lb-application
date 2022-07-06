package com.seventh.lotobig.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserPaymentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPayment.class);
        UserPayment userPayment1 = new UserPayment();
        userPayment1.setId(1L);
        UserPayment userPayment2 = new UserPayment();
        userPayment2.setId(userPayment1.getId());
        assertThat(userPayment1).isEqualTo(userPayment2);
        userPayment2.setId(2L);
        assertThat(userPayment1).isNotEqualTo(userPayment2);
        userPayment1.setId(null);
        assertThat(userPayment1).isNotEqualTo(userPayment2);
    }
}
