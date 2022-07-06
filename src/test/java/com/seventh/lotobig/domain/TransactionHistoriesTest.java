package com.seventh.lotobig.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.seventh.lotobig.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransactionHistoriesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionHistories.class);
        TransactionHistories transactionHistories1 = new TransactionHistories();
        transactionHistories1.setId(1L);
        TransactionHistories transactionHistories2 = new TransactionHistories();
        transactionHistories2.setId(transactionHistories1.getId());
        assertThat(transactionHistories1).isEqualTo(transactionHistories2);
        transactionHistories2.setId(2L);
        assertThat(transactionHistories1).isNotEqualTo(transactionHistories2);
        transactionHistories1.setId(null);
        assertThat(transactionHistories1).isNotEqualTo(transactionHistories2);
    }
}
