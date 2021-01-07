package com.carrepairshop.api.application.uc.ticket.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.carrepairshop.api.application.domain.Ticket;

public interface SearchTicketUC {

    Page<Ticket> searchTickets(final String text, final Pageable pageable);
}
