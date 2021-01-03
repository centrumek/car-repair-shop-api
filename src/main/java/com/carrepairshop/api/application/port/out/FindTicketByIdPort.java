package com.carrepairshop.api.application.port.out;

import java.util.Optional;
import java.util.UUID;
import com.carrepairshop.api.application.domain.Ticket;

public interface FindTicketByIdPort {
    Optional<Ticket> findTicketById(final UUID uuid);
}
