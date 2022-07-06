package com.seventh.lotobig.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LimitConfBorletteMapperTest {

    private LimitConfBorletteMapper limitConfBorletteMapper;

    @BeforeEach
    public void setUp() {
        limitConfBorletteMapper = new LimitConfBorletteMapperImpl();
    }
}
