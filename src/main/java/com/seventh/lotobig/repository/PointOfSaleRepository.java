package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.PointOfSale;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PointOfSale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointOfSaleRepository extends JpaRepository<PointOfSale, Long> {}
