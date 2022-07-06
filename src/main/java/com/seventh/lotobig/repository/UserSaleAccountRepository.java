package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.UserSaleAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserSaleAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserSaleAccountRepository extends JpaRepository<UserSaleAccount, Long> {}
