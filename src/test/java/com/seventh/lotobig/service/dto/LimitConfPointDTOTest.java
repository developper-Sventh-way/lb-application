package com.seventh.lotobig.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LimitConfPointDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LimitConfPointDTO.class);
        LimitConfPointDTO limitConfPointDTO1 = new LimitConfPointDTO();
        limitConfPointDTO1.setId(1L);
        LimitConfPointDTO limitConfPointDTO2 = new LimitConfPointDTO();
        assertThat(limitConfPointDTO1).isNotEqualTo(limitConfPointDTO2);
        limitConfPointDTO2.setId(limitConfPointDTO1.getId());
        assertThat(limitConfPointDTO1).isEqualTo(limitConfPointDTO2);
        limitConfPointDTO2.setId(2L);
        assertThat(limitConfPointDTO1).isNotEqualTo(limitConfPointDTO2);
        limitConfPointDTO1.setId(null);
        assertThat(limitConfPointDTO1).isNotEqualTo(limitConfPointDTO2);
    }
}
