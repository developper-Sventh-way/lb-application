package com.seventh.lotobig.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LimitConfManagerMapperTest {

    private LimitConfManagerMapper limitConfManagerMapper;

    @BeforeEach
    public void setUp() {
        limitConfManagerMapper = new LimitConfManagerMapperImpl();
    }
}
