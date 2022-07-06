package com.seventh.lotobig.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PointOfSaleMapperTest {

    private PointOfSaleMapper pointOfSaleMapper;

    @BeforeEach
    public void setUp() {
        pointOfSaleMapper = new PointOfSaleMapperImpl();
    }
}
