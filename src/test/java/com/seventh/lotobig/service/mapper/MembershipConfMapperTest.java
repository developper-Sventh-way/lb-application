package com.seventh.lotobig.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MembershipConfMapperTest {

    private MembershipConfMapper membershipConfMapper;

    @BeforeEach
    public void setUp() {
        membershipConfMapper = new MembershipConfMapperImpl();
    }
}
