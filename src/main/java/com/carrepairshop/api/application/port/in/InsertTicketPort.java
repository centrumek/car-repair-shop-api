package com.carrepairshop.api.application.port.in;

import com.carrepairshop.api.application.domain.Ticket;
import com.carrepairshop.api.application.uc.ticket.create.CreateTicketUC.CreateTicketCommand;

public interface InsertTicketPort {

    Ticket insertTicket(final CreateTicketCommand command);
}
