package com.seventh.lotobig.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class POSConfigurationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(POSConfiguration.class);
        POSConfiguration pOSConfiguration1 = new POSConfiguration();
        pOSConfiguration1.setId(1L);
        POSConfiguration pOSConfiguration2 = new POSConfiguration();
        pOSConfiguration2.setId(pOSConfiguration1.getId());
        assertThat(pOSConfiguration1).isEqualTo(pOSConfiguration2);
        pOSConfiguration2.setId(2L);
        assertThat(pOSConfiguration1).isNotEqualTo(pOSConfiguration2);
        pOSConfiguration1.setId(null);
        assertThat(pOSConfiguration1).isNotEqualTo(pOSConfiguration2);
    }
}
