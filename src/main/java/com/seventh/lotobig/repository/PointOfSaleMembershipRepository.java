package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.PointOfSaleMembership;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PointOfSaleMembership entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointOfSaleMembershipRepository extends JpaRepository<PointOfSaleMembership, Long> {}
