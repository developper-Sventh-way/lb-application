package com.seventh.lotobig.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LimitConfManagerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LimitConfManagerDTO.class);
        LimitConfManagerDTO limitConfManagerDTO1 = new LimitConfManagerDTO();
        limitConfManagerDTO1.setId(1L);
        LimitConfManagerDTO limitConfManagerDTO2 = new LimitConfManagerDTO();
        assertThat(limitConfManagerDTO1).isNotEqualTo(limitConfManagerDTO2);
        limitConfManagerDTO2.setId(limitConfManagerDTO1.getId());
        assertThat(limitConfManagerDTO1).isEqualTo(limitConfManagerDTO2);
        limitConfManagerDTO2.setId(2L);
        assertThat(limitConfManagerDTO1).isNotEqualTo(limitConfManagerDTO2);
        limitConfManagerDTO1.setId(null);
        assertThat(limitConfManagerDTO1).isNotEqualTo(limitConfManagerDTO2);
    }
}
