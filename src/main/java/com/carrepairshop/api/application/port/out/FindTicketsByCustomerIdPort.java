package com.carrepairshop.api.application.port.out;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.carrepairshop.api.application.domain.Ticket;

public interface FindTicketsByCustomerIdPort {
    Page<Ticket> findTicketsByCustomerId(final UUID uuid,
                                         final Pageable pageable);
}
