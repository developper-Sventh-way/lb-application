package com.seventh.lotobig.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserPaymentConfMapperTest {

    private UserPaymentConfMapper userPaymentConfMapper;

    @BeforeEach
    public void setUp() {
        userPaymentConfMapper = new UserPaymentConfMapperImpl();
    }
}
