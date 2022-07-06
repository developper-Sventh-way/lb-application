package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.UserPaymentConf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserPaymentConf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPaymentConfRepository extends JpaRepository<UserPaymentConf, Long> {}
