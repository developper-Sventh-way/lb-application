package com.seventh.lotobig.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TicketOptionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketOption.class);
        TicketOption ticketOption1 = new TicketOption();
        ticketOption1.setId(1L);
        TicketOption ticketOption2 = new TicketOption();
        ticketOption2.setId(ticketOption1.getId());
        assertThat(ticketOption1).isEqualTo(ticketOption2);
        ticketOption2.setId(2L);
        assertThat(ticketOption1).isNotEqualTo(ticketOption2);
        ticketOption1.setId(null);
        assertThat(ticketOption1).isNotEqualTo(ticketOption2);
    }
}
