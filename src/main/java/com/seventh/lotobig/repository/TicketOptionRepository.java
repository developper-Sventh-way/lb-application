package com.seventh.lotobig.repository;

import com.seventh.lotobig.domain.TicketOption;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TicketOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketOptionRepository extends JpaRepository<TicketOption, Long> {}
