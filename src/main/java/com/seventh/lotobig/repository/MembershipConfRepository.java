package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.MembershipConf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MembershipConf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembershipConfRepository extends JpaRepository<MembershipConf, Long> {}
