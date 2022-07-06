package com.seventh.lotobig.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TirageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TirageDTO.class);
        TirageDTO tirageDTO1 = new TirageDTO();
        tirageDTO1.setId(1L);
        TirageDTO tirageDTO2 = new TirageDTO();
        assertThat(tirageDTO1).isNotEqualTo(tirageDTO2);
        tirageDTO2.setId(tirageDTO1.getId());
        assertThat(tirageDTO1).isEqualTo(tirageDTO2);
        tirageDTO2.setId(2L);
        assertThat(tirageDTO1).isNotEqualTo(tirageDTO2);
        tirageDTO1.setId(null);
        assertThat(tirageDTO1).isNotEqualTo(tirageDTO2);
    }
}
