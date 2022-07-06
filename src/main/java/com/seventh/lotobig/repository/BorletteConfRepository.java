package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.BorletteConf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BorletteConf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BorletteConfRepository extends JpaRepository<BorletteConf, Long> {}
