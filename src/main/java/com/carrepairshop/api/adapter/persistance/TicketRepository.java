package com.carrepairshop.api.adapter.persistance;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.carrepairshop.api.common.jpa.CustomJpaRepository;

@Repository
interface TicketRepository extends CustomJpaRepository<TicketEntity, UUID> {
    Page<TicketEntity> findAllByCustomerUuid(final UUID uuid, final Pageable pageable);
}
