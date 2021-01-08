package com.carrepairshop.api.application.uc.ticket.get;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import com.carrepairshop.api.application.domain.Ticket;
import com.carrepairshop.api.application.domain.User;
import com.carrepairshop.api.application.domain.UserPrincipal;
import com.carrepairshop.api.application.port.out.FindTicketsAllPort;
import com.carrepairshop.api.application.port.out.FindTicketByIdPort;
import com.carrepairshop.api.application.port.out.FindTicketsByCustomerIdPort;
import com.carrepairshop.api.application.port.out.FindUserByEmailPort;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import static com.carrepairshop.api.application.domain.User.Role.CUSTOMER;

@RequiredArgsConstructor
class GetTicketService implements GetTicketUC {

    private final FindTicketByIdPort findTicketByIdPort;
    private final FindUserByEmailPort findUserByEmailPort;
    private final FindTicketsByCustomerIdPort findTicketsByCustomerIdPort;
    private final FindTicketsAllPort findTicketsAllPort;

    @Override
    public Page<Ticket> getTickets(final UserPrincipal userPrincipal,
                                   final Pageable pageable) {
        if(userPrincipal.getRole() == CUSTOMER) {
            final var customer = getUserByEmail(userPrincipal.getUsername());
            return findTicketsByCustomerIdPort.findTicketsByCustomerId(customer.getUuid(), pageable);
        } else {
            return findTicketsAllPort.findTicketsAll(pageable);
        }
    }

    @Override
    public Ticket getTicket(final UUID uuid,
                            final UserPrincipal userPrincipal) {
        final var ticket = getTicketById(uuid);
        validateGetTicketPermission(ticket.getCustomer().getEmail(), userPrincipal);
        return ticket;
    }

    private User getUserByEmail(final String email) {
        return findUserByEmailPort.findUserByEmail(email)
                                  .orElseThrow(() -> new EntityNotFoundException("email '" + email + "' not exists."));
    }

    private Ticket getTicketById(final UUID uuid) {
        return findTicketByIdPort.findTicketById(uuid)
                                 .orElseThrow(() -> new EntityNotFoundException("ticket '" + uuid + "' not exists."));
    }

    private void validateGetTicketPermission(final String ticketEmail,
                                             final UserPrincipal userPrincipal) {
        var customerCondition = userPrincipal.getRole() == CUSTOMER && !ticketEmail.equals(userPrincipal.getUsername());
        if (customerCondition) {
            throw new AccessDeniedException("Action forbidden, check your permissions.");
        }
    }
}
