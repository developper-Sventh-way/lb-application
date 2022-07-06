package com.seventh.lotobig.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LimitConfPointMapperTest {

    private LimitConfPointMapper limitConfPointMapper;

    @BeforeEach
    public void setUp() {
        limitConfPointMapper = new LimitConfPointMapperImpl();
    }
}
