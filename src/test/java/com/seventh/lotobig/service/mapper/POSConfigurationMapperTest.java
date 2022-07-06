package com.seventh.lotobig.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class POSConfigurationMapperTest {

    private POSConfigurationMapper pOSConfigurationMapper;

    @BeforeEach
    public void setUp() {
        pOSConfigurationMapper = new POSConfigurationMapperImpl();
    }
}
