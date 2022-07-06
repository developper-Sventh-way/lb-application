package com.seventh.lotobig.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreditSoldeMapperTest {

    private CreditSoldeMapper creditSoldeMapper;

    @BeforeEach
    public void setUp() {
        creditSoldeMapper = new CreditSoldeMapperImpl();
    }
}
