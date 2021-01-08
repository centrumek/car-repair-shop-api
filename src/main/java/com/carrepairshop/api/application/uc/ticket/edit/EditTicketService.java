package com.carrepairshop.api.application.uc.ticket.edit;

import java.time.Instant;
import java.util.UUID;
import com.carrepairshop.api.application.domain.Ticket;
import com.carrepairshop.api.application.domain.Ticket.Status;
import com.carrepairshop.api.application.domain.User;
import com.carrepairshop.api.application.domain.UserPrincipal;
import com.carrepairshop.api.application.port.in.UpdateTicketPort;
import com.carrepairshop.api.application.port.out.FindTicketByIdPort;
import com.carrepairshop.api.application.port.out.FindUserByEmailPort;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import static com.carrepairshop.api.application.domain.Ticket.Status.RELEASED;

@RequiredArgsConstructor
class EditTicketService implements EditTicketUC {

    private final FindTicketByIdPort findTicketByIdPort;
    private final FindUserByEmailPort findUserByEmailPort;
    private final UpdateTicketPort updateTicketPort;

    @Override
    public Ticket editTicket(final UUID uuid,
                             final EditTicketCommand command,
                             final UserPrincipal userPrincipal) {
        final var ticket = getTicketById(uuid);
        final var releasedBy = getUserByEmail(userPrincipal.getUsername());
        final var status = Status.valueOf(command.getStatus());
        final var mergedCommand = mergeTicket(uuid, command, ticket);
        if (status == RELEASED) {
            return updateTicketPort.updateTicket(mergedCommand
                .withReleasedBy(releasedBy.getUuid())
                .withReleasedAt(Instant.now()));
        } else {
            return updateTicketPort.updateTicket(mergedCommand);
        }
    }

    private EditTicketCommand mergeTicket(final UUID uuid,
                                          final EditTicketCommand command,
                                          final Ticket ticket) {
        return EditTicketCommand.builder()
            .uuid(uuid)
            .title(command.getTitle())
            .brand(command.getBrand())
            .model(command.getModel())
            .description(command.getDescription())
            .attachedItems(command.getAttachedItems())
            .status(command.getStatus())
            .finalPrice(command.getFinalPrice())
            .calculationNote(command.getCalculationNote())
            .estimatedPrice(ticket.getEstimatedPrice())
            .estimatedRelease(ticket.getEstimatedRelease())
            .customerId(ticket.getCustomer().getUuid())
            .createdBy(ticket.getCreatedBy().getUuid())
            .build();
    }

    private Ticket getTicketById(final UUID uuid) {
        return findTicketByIdPort.findTicketById(uuid)
                                 .orElseThrow(() -> new EntityNotFoundException("ticket '" + uuid + "' not exists."));
    }

    private User getUserByEmail(final String email) {
        return findUserByEmailPort.findUserByEmail(email)
                                  .orElseThrow(() -> new EntityNotFoundException("email '" + email + "' not exists."));
    }
}
