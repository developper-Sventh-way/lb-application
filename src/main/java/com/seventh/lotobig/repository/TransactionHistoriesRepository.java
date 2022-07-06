package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.TransactionHistories;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TransactionHistories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionHistoriesRepository extends JpaRepository<TransactionHistories, Long> {}
