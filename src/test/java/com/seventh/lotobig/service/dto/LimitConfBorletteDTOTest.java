package com.seventh.lotobig.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LimitConfBorletteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LimitConfBorletteDTO.class);
        LimitConfBorletteDTO limitConfBorletteDTO1 = new LimitConfBorletteDTO();
        limitConfBorletteDTO1.setId(1L);
        LimitConfBorletteDTO limitConfBorletteDTO2 = new LimitConfBorletteDTO();
        assertThat(limitConfBorletteDTO1).isNotEqualTo(limitConfBorletteDTO2);
        limitConfBorletteDTO2.setId(limitConfBorletteDTO1.getId());
        assertThat(limitConfBorletteDTO1).isEqualTo(limitConfBorletteDTO2);
        limitConfBorletteDTO2.setId(2L);
        assertThat(limitConfBorletteDTO1).isNotEqualTo(limitConfBorletteDTO2);
        limitConfBorletteDTO1.setId(null);
        assertThat(limitConfBorletteDTO1).isNotEqualTo(limitConfBorletteDTO2);
    }
}
