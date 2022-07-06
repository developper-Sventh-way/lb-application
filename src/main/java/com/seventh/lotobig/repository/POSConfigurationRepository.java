package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.POSConfiguration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the POSConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface POSConfigurationRepository extends JpaRepository<POSConfiguration, Long> {}
