package com.seventh.lotobig.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PointOfSaleConfMapperTest {

    private PointOfSaleConfMapper pointOfSaleConfMapper;

    @BeforeEach
    public void setUp() {
        pointOfSaleConfMapper = new PointOfSaleConfMapperImpl();
    }
}
