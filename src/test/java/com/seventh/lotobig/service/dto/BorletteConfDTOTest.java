package com.seventh.lotobig.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BorletteConfDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BorletteConfDTO.class);
        BorletteConfDTO borletteConfDTO1 = new BorletteConfDTO();
        borletteConfDTO1.setId(1L);
        BorletteConfDTO borletteConfDTO2 = new BorletteConfDTO();
        assertThat(borletteConfDTO1).isNotEqualTo(borletteConfDTO2);
        borletteConfDTO2.setId(borletteConfDTO1.getId());
        assertThat(borletteConfDTO1).isEqualTo(borletteConfDTO2);
        borletteConfDTO2.setId(2L);
        assertThat(borletteConfDTO1).isNotEqualTo(borletteConfDTO2);
        borletteConfDTO1.setId(null);
        assertThat(borletteConfDTO1).isNotEqualTo(borletteConfDTO2);
    }
}
