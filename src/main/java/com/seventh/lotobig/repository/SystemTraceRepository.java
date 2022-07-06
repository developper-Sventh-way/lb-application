package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.SystemTrace;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SystemTrace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemTraceRepository extends JpaRepository<SystemTrace, Long> {}
