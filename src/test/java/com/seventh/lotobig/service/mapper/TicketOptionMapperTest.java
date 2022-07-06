package com.seventh.lotobig.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicketOptionMapperTest {

    private TicketOptionMapper ticketOptionMapper;

    @BeforeEach
    public void setUp() {
        ticketOptionMapper = new TicketOptionMapperImpl();
    }
}
