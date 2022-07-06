package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.CouponGratuitConf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CouponGratuitConf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CouponGratuitConfRepository extends JpaRepository<CouponGratuitConf, Long> {}
