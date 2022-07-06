package com.seventh.lotobig.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PointOfSaleConfTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointOfSaleConf.class);
        PointOfSaleConf pointOfSaleConf1 = new PointOfSaleConf();
        pointOfSaleConf1.setId(1L);
        PointOfSaleConf pointOfSaleConf2 = new PointOfSaleConf();
        pointOfSaleConf2.setId(pointOfSaleConf1.getId());
        assertThat(pointOfSaleConf1).isEqualTo(pointOfSaleConf2);
        pointOfSaleConf2.setId(2L);
        assertThat(pointOfSaleConf1).isNotEqualTo(pointOfSaleConf2);
        pointOfSaleConf1.setId(null);
        assertThat(pointOfSaleConf1).isNotEqualTo(pointOfSaleConf2);
    }
}
