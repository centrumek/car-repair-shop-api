package com.carrepairshop.api.adapter.web;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.carrepairshop.api.application.domain.Ticket;
import com.carrepairshop.api.application.domain.UserPrincipal;
import com.carrepairshop.api.application.uc.ticket.create.CreateTicketUC;
import com.carrepairshop.api.application.uc.ticket.create.CreateTicketUC.CreateTicketCommand;
import com.carrepairshop.api.application.uc.ticket.edit.EditTicketUC;
import com.carrepairshop.api.application.uc.ticket.edit.EditTicketUC.EditTicketCommand;
import com.carrepairshop.api.application.uc.ticket.get.GetTicketUC;
import com.carrepairshop.api.common.ApiPageable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tickets")
class TicketController {

    private final CreateTicketUC createTicketUC;
    private final GetTicketUC getTicketUC;
    private final EditTicketUC editTicketUC;

    @ApiPageable
    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'HEAD')")
    @GetMapping
    Page<Ticket> getTickets(@AuthenticationPrincipal final UserPrincipal userPrincipal,
                            @Parameter(hidden = true) final Pageable pageable) {
        return getTicketUC.getTickets(userPrincipal, pageable);

    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'HEAD')")
    @GetMapping("/{id}")
    Ticket getTicket(@PathVariable final UUID id,
                     @AuthenticationPrincipal final UserPrincipal userPrincipal)  {
        return getTicketUC.getTicket(id, userPrincipal);
    }

    // by email, by status, brand, model, date combine? https://reflectoring.io/spring-data-specifications/
    void searchTickets() {
        throw new UnsupportedOperationException();
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'HEAD')")
    @PostMapping
    Ticket createTicket(@Valid @RequestBody final CreateTicketCommand command,
                        @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return createTicketUC.createTicket(command, userPrincipal);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'HEAD')")
    @PutMapping("/{id}")
    Ticket editTicket(@PathVariable final UUID id,
                      @Valid @RequestBody final EditTicketCommand command,
                      @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return editTicketUC.editTicket(id, command,  userPrincipal);
    }
}
