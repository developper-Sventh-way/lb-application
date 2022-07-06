package com.seventh.lotobig.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreditSoldeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditSolde.class);
        CreditSolde creditSolde1 = new CreditSolde();
        creditSolde1.setId(1L);
        CreditSolde creditSolde2 = new CreditSolde();
        creditSolde2.setId(creditSolde1.getId());
        assertThat(creditSolde1).isEqualTo(creditSolde2);
        creditSolde2.setId(2L);
        assertThat(creditSolde1).isNotEqualTo(creditSolde2);
        creditSolde1.setId(null);
        assertThat(creditSolde1).isNotEqualTo(creditSolde2);
    }
}
