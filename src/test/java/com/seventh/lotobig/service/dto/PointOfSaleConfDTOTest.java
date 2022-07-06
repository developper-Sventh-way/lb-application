package com.seventh.lotobig.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PointOfSaleConfDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointOfSaleConfDTO.class);
        PointOfSaleConfDTO pointOfSaleConfDTO1 = new PointOfSaleConfDTO();
        pointOfSaleConfDTO1.setId(1L);
        PointOfSaleConfDTO pointOfSaleConfDTO2 = new PointOfSaleConfDTO();
        assertThat(pointOfSaleConfDTO1).isNotEqualTo(pointOfSaleConfDTO2);
        pointOfSaleConfDTO2.setId(pointOfSaleConfDTO1.getId());
        assertThat(pointOfSaleConfDTO1).isEqualTo(pointOfSaleConfDTO2);
        pointOfSaleConfDTO2.setId(2L);
        assertThat(pointOfSaleConfDTO1).isNotEqualTo(pointOfSaleConfDTO2);
        pointOfSaleConfDTO1.setId(null);
        assertThat(pointOfSaleConfDTO1).isNotEqualTo(pointOfSaleConfDTO2);
    }
}
