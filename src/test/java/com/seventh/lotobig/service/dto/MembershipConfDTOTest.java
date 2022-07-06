package com.seventh.lotobig.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MembershipConfDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MembershipConfDTO.class);
        MembershipConfDTO membershipConfDTO1 = new MembershipConfDTO();
        membershipConfDTO1.setId(1L);
        MembershipConfDTO membershipConfDTO2 = new MembershipConfDTO();
        assertThat(membershipConfDTO1).isNotEqualTo(membershipConfDTO2);
        membershipConfDTO2.setId(membershipConfDTO1.getId());
        assertThat(membershipConfDTO1).isEqualTo(membershipConfDTO2);
        membershipConfDTO2.setId(2L);
        assertThat(membershipConfDTO1).isNotEqualTo(membershipConfDTO2);
        membershipConfDTO1.setId(null);
        assertThat(membershipConfDTO1).isNotEqualTo(membershipConfDTO2);
    }
}
