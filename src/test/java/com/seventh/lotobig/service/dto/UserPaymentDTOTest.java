package com.seventh.lotobig.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserPaymentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPaymentDTO.class);
        UserPaymentDTO userPaymentDTO1 = new UserPaymentDTO();
        userPaymentDTO1.setId(1L);
        UserPaymentDTO userPaymentDTO2 = new UserPaymentDTO();
        assertThat(userPaymentDTO1).isNotEqualTo(userPaymentDTO2);
        userPaymentDTO2.setId(userPaymentDTO1.getId());
        assertThat(userPaymentDTO1).isEqualTo(userPaymentDTO2);
        userPaymentDTO2.setId(2L);
        assertThat(userPaymentDTO1).isNotEqualTo(userPaymentDTO2);
        userPaymentDTO1.setId(null);
        assertThat(userPaymentDTO1).isNotEqualTo(userPaymentDTO2);
    }
}
