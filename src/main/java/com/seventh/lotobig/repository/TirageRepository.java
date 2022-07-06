package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.Tirage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Tirage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TirageRepository extends JpaRepository<Tirage, Long> {}
