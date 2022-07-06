package com.seventh.lotobig.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class POSConfigurationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(POSConfigurationDTO.class);
        POSConfigurationDTO pOSConfigurationDTO1 = new POSConfigurationDTO();
        pOSConfigurationDTO1.setId(1L);
        POSConfigurationDTO pOSConfigurationDTO2 = new POSConfigurationDTO();
        assertThat(pOSConfigurationDTO1).isNotEqualTo(pOSConfigurationDTO2);
        pOSConfigurationDTO2.setId(pOSConfigurationDTO1.getId());
        assertThat(pOSConfigurationDTO1).isEqualTo(pOSConfigurationDTO2);
        pOSConfigurationDTO2.setId(2L);
        assertThat(pOSConfigurationDTO1).isNotEqualTo(pOSConfigurationDTO2);
        pOSConfigurationDTO1.setId(null);
        assertThat(pOSConfigurationDTO1).isNotEqualTo(pOSConfigurationDTO2);
    }
}
