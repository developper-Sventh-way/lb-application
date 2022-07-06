package com.seventh.lotobig.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CouponGratuitConfMapperTest {

    private CouponGratuitConfMapper couponGratuitConfMapper;

    @BeforeEach
    public void setUp() {
        couponGratuitConfMapper = new CouponGratuitConfMapperImpl();
    }
}
