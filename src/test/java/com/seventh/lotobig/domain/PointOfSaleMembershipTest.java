package com.seventh.lotobig.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PointOfSaleMembershipTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointOfSaleMembership.class);
        PointOfSaleMembership pointOfSaleMembership1 = new PointOfSaleMembership();
        pointOfSaleMembership1.setId(1L);
        PointOfSaleMembership pointOfSaleMembership2 = new PointOfSaleMembership();
        pointOfSaleMembership2.setId(pointOfSaleMembership1.getId());
        assertThat(pointOfSaleMembership1).isEqualTo(pointOfSaleMembership2);
        pointOfSaleMembership2.setId(2L);
        assertThat(pointOfSaleMembership1).isNotEqualTo(pointOfSaleMembership2);
        pointOfSaleMembership1.setId(null);
        assertThat(pointOfSaleMembership1).isNotEqualTo(pointOfSaleMembership2);
    }
}
