package com.carrepairshop.api.application.port.in;

import com.carrepairshop.api.application.domain.Ticket;
import com.carrepairshop.api.application.uc.ticket.edit.EditTicketUC.EditTicketCommand;

public interface UpdateTicketPort {
    Ticket updateTicket(final EditTicketCommand command);
}
