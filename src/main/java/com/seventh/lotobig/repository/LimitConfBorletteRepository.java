package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.LimitConfBorlette;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LimitConfBorlette entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LimitConfBorletteRepository extends JpaRepository<LimitConfBorlette, Long> {}
