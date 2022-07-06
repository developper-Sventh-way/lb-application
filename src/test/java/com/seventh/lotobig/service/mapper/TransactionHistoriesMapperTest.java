package com.seventh.lotobig.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionHistoriesMapperTest {

    private TransactionHistoriesMapper transactionHistoriesMapper;

    @BeforeEach
    public void setUp() {
        transactionHistoriesMapper = new TransactionHistoriesMapperImpl();
    }
}
