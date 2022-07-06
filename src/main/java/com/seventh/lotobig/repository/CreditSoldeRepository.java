package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.CreditSolde;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CreditSolde entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditSoldeRepository extends JpaRepository<CreditSolde, Long> {}
