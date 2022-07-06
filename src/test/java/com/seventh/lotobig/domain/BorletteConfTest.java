package com.seventh.lotobig.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BorletteConfTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BorletteConf.class);
        BorletteConf borletteConf1 = new BorletteConf();
        borletteConf1.setId(1L);
        BorletteConf borletteConf2 = new BorletteConf();
        borletteConf2.setId(borletteConf1.getId());
        assertThat(borletteConf1).isEqualTo(borletteConf2);
        borletteConf2.setId(2L);
        assertThat(borletteConf1).isNotEqualTo(borletteConf2);
        borletteConf1.setId(null);
        assertThat(borletteConf1).isNotEqualTo(borletteConf2);
    }
}
