package com.seventh.lotobig.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TirageMapperTest {

    private TirageMapper tirageMapper;

    @BeforeEach
    public void setUp() {
        tirageMapper = new TirageMapperImpl();
    }
}
