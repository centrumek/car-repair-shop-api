package com.carrepairshop.api.application.uc.ticket.get;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.carrepairshop.api.application.domain.Ticket;
import com.carrepairshop.api.application.domain.UserPrincipal;

public interface GetTicketUC {

    Page<Ticket> getTickets(final UserPrincipal userPrincipal,
                            final Pageable pageable);
    Ticket getTicket(final UUID uuid,
                     final UserPrincipal userPrincipal);
}
