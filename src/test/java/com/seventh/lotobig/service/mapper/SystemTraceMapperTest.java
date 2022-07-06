package com.seventh.lotobig.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SystemTraceMapperTest {

    private SystemTraceMapper systemTraceMapper;

    @BeforeEach
    public void setUp() {
        systemTraceMapper = new SystemTraceMapperImpl();
    }
}
