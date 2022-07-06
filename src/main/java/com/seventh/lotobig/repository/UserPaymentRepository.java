package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.UserPayment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {}
