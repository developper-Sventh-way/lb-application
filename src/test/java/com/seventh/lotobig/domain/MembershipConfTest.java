package com.seventh.lotobig.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MembershipConfTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MembershipConf.class);
        MembershipConf membershipConf1 = new MembershipConf();
        membershipConf1.setId(1L);
        MembershipConf membershipConf2 = new MembershipConf();
        membershipConf2.setId(membershipConf1.getId());
        assertThat(membershipConf1).isEqualTo(membershipConf2);
        membershipConf2.setId(2L);
        assertThat(membershipConf1).isNotEqualTo(membershipConf2);
        membershipConf1.setId(null);
        assertThat(membershipConf1).isNotEqualTo(membershipConf2);
    }
}
