package com.seventh.lotobig.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransactionHistoriesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionHistoriesDTO.class);
        TransactionHistoriesDTO transactionHistoriesDTO1 = new TransactionHistoriesDTO();
        transactionHistoriesDTO1.setId(1L);
        TransactionHistoriesDTO transactionHistoriesDTO2 = new TransactionHistoriesDTO();
        assertThat(transactionHistoriesDTO1).isNotEqualTo(transactionHistoriesDTO2);
        transactionHistoriesDTO2.setId(transactionHistoriesDTO1.getId());
        assertThat(transactionHistoriesDTO1).isEqualTo(transactionHistoriesDTO2);
        transactionHistoriesDTO2.setId(2L);
        assertThat(transactionHistoriesDTO1).isNotEqualTo(transactionHistoriesDTO2);
        transactionHistoriesDTO1.setId(null);
        assertThat(transactionHistoriesDTO1).isNotEqualTo(transactionHistoriesDTO2);
    }
}
