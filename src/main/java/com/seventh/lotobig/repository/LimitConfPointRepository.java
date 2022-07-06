package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.LimitConfPoint;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LimitConfPoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LimitConfPointRepository extends JpaRepository<LimitConfPoint, Long> {}
