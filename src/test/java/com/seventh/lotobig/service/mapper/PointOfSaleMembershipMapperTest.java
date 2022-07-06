package com.seventh.lotobig.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PointOfSaleMembershipMapperTest {

    private PointOfSaleMembershipMapper pointOfSaleMembershipMapper;

    @BeforeEach
    public void setUp() {
        pointOfSaleMembershipMapper = new PointOfSaleMembershipMapperImpl();
    }
}
