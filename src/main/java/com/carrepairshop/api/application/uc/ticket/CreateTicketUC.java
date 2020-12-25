package com.carrepairshop.api.application.uc.ticket;

import com.carrepairshop.api.application.domain.ticket.Ticket;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

public interface CreateTicketUC {

    Ticket createTicket(final CreateTicketCommand command);

    @Value
    @Builder
    @With
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode(callSuper = false)
    class CreateTicketCommand {

        @NotBlank
        String title;

        @NotBlank
        String description;

        @Email
        String email;

        @NotBlank
        String password;

        @Pattern(regexp = "(^$|\\d{10})")
        String mobilePhone;

        String role;
    }
}
