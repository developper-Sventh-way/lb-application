package com.seventh.lotobig.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserPaymentMapperTest {

    private UserPaymentMapper userPaymentMapper;

    @BeforeEach
    public void setUp() {
        userPaymentMapper = new UserPaymentMapperImpl();
    }
}
