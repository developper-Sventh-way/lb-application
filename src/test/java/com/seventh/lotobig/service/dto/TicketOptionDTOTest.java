package com.seventh.lotobig.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TicketOptionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketOptionDTO.class);
        TicketOptionDTO ticketOptionDTO1 = new TicketOptionDTO();
        ticketOptionDTO1.setId(1L);
        TicketOptionDTO ticketOptionDTO2 = new TicketOptionDTO();
        assertThat(ticketOptionDTO1).isNotEqualTo(ticketOptionDTO2);
        ticketOptionDTO2.setId(ticketOptionDTO1.getId());
        assertThat(ticketOptionDTO1).isEqualTo(ticketOptionDTO2);
        ticketOptionDTO2.setId(2L);
        assertThat(ticketOptionDTO1).isNotEqualTo(ticketOptionDTO2);
        ticketOptionDTO1.setId(null);
        assertThat(ticketOptionDTO1).isNotEqualTo(ticketOptionDTO2);
    }
}
