package com.seventh.lotobig.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PointOfSaleMembershipDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointOfSaleMembershipDTO.class);
        PointOfSaleMembershipDTO pointOfSaleMembershipDTO1 = new PointOfSaleMembershipDTO();
        pointOfSaleMembershipDTO1.setId(1L);
        PointOfSaleMembershipDTO pointOfSaleMembershipDTO2 = new PointOfSaleMembershipDTO();
        assertThat(pointOfSaleMembershipDTO1).isNotEqualTo(pointOfSaleMembershipDTO2);
        pointOfSaleMembershipDTO2.setId(pointOfSaleMembershipDTO1.getId());
        assertThat(pointOfSaleMembershipDTO1).isEqualTo(pointOfSaleMembershipDTO2);
        pointOfSaleMembershipDTO2.setId(2L);
        assertThat(pointOfSaleMembershipDTO1).isNotEqualTo(pointOfSaleMembershipDTO2);
        pointOfSaleMembershipDTO1.setId(null);
        assertThat(pointOfSaleMembershipDTO1).isNotEqualTo(pointOfSaleMembershipDTO2);
    }
}
