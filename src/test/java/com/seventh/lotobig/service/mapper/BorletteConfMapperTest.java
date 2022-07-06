package com.seventh.lotobig.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BorletteConfMapperTest {

    private BorletteConfMapper borletteConfMapper;

    @BeforeEach
    public void setUp() {
        borletteConfMapper = new BorletteConfMapperImpl();
    }
}
