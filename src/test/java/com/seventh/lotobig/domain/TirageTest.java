package com.seventh.lotobig.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TirageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tirage.class);
        Tirage tirage1 = new Tirage();
        tirage1.setId(1L);
        Tirage tirage2 = new Tirage();
        tirage2.setId(tirage1.getId());
        assertThat(tirage1).isEqualTo(tirage2);
        tirage2.setId(2L);
        assertThat(tirage1).isNotEqualTo(tirage2);
        tirage1.setId(null);
        assertThat(tirage1).isNotEqualTo(tirage2);
    }
}
