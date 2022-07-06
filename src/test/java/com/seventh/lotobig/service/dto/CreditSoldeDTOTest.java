package com.seventh.lotobig.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreditSoldeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditSoldeDTO.class);
        CreditSoldeDTO creditSoldeDTO1 = new CreditSoldeDTO();
        creditSoldeDTO1.setId(1L);
        CreditSoldeDTO creditSoldeDTO2 = new CreditSoldeDTO();
        assertThat(creditSoldeDTO1).isNotEqualTo(creditSoldeDTO2);
        creditSoldeDTO2.setId(creditSoldeDTO1.getId());
        assertThat(creditSoldeDTO1).isEqualTo(creditSoldeDTO2);
        creditSoldeDTO2.setId(2L);
        assertThat(creditSoldeDTO1).isNotEqualTo(creditSoldeDTO2);
        creditSoldeDTO1.setId(null);
        assertThat(creditSoldeDTO1).isNotEqualTo(creditSoldeDTO2);
    }
}
