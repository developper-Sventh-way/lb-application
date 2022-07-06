package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.LimitConfManager;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LimitConfManager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LimitConfManagerRepository extends JpaRepository<LimitConfManager, Long> {}
