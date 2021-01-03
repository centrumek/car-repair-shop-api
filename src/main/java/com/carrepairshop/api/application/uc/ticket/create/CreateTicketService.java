package com.carrepairshop.api.application.uc.ticket.create;

import com.carrepairshop.api.application.domain.Ticket;
import com.carrepairshop.api.application.domain.User;
import com.carrepairshop.api.application.domain.UserPrincipal;
import com.carrepairshop.api.application.port.in.InsertTicketPort;
import com.carrepairshop.api.application.port.out.FindUserByEmailPort;
import javax.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CreateTicketService implements CreateTicketUC {

    private final FindUserByEmailPort findUserByEmailPort;
    private final InsertTicketPort insertTicketPort;

    @Override
    public Ticket createTicket(final CreateTicketCommand command,
                               final UserPrincipal userPrincipal) {
        final var customer = getUserByEmail(command.getEmail());
        final var createdBy = getUserByEmail(userPrincipal.getUsername());
        return insertTicketPort.insertTicket(command
            .withCustomerId(customer.getUuid())
            .withCreatedBy(createdBy.getUuid()));
    }

    private User getUserByEmail(final String email) {
        return findUserByEmailPort.findUserByEmail(email)
                                  .orElseThrow(() -> new EntityExistsException("email '" + email + "' not exists."));
    }
}
