package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.PointOfSaleConf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PointOfSaleConf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointOfSaleConfRepository extends JpaRepository<PointOfSaleConf, Long> {}
