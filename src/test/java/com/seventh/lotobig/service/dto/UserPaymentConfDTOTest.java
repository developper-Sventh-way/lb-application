package com.seventh.lotobig.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserPaymentConfDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPaymentConfDTO.class);
        UserPaymentConfDTO userPaymentConfDTO1 = new UserPaymentConfDTO();
        userPaymentConfDTO1.setId(1L);
        UserPaymentConfDTO userPaymentConfDTO2 = new UserPaymentConfDTO();
        assertThat(userPaymentConfDTO1).isNotEqualTo(userPaymentConfDTO2);
        userPaymentConfDTO2.setId(userPaymentConfDTO1.getId());
        assertThat(userPaymentConfDTO1).isEqualTo(userPaymentConfDTO2);
        userPaymentConfDTO2.setId(2L);
        assertThat(userPaymentConfDTO1).isNotEqualTo(userPaymentConfDTO2);
        userPaymentConfDTO1.setId(null);
        assertThat(userPaymentConfDTO1).isNotEqualTo(userPaymentConfDTO2);
    }
}
