package com.carrepairshop.api.application.uc.ticket.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.carrepairshop.api.application.domain.Ticket;
import com.carrepairshop.api.application.port.out.FindTicketsByFullTextPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SearchTicketService implements SearchTicketUC {

    private final FindTicketsByFullTextPort findTicketsByFullTextPort;

    @Override
    public Page<Ticket> searchTickets(final String text, final Pageable pageable) {
        return findTicketsByFullTextPort.findTicketsByFullText(text, pageable);
    }
}
