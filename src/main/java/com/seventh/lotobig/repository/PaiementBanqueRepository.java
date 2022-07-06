package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.PaiementBanque;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PaiementBanque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaiementBanqueRepository extends JpaRepository<PaiementBanque, Long> {}
