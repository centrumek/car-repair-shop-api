package com.carrepairshop.api.application.uc.ticket.create;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import com.carrepairshop.api.application.domain.Ticket;
import com.carrepairshop.api.application.domain.UserPrincipal;
import io.swagger.v3.oas.annotations.Hidden;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

public interface CreateTicketUC {

    Ticket createTicket(final CreateTicketCommand command,
                        final UserPrincipal userPrincipal);

    @Value
    @Builder
    @With
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode(callSuper = false)
    class CreateTicketCommand {

        @Email
        String email;

        @NotBlank
        String title;

        @NotBlank
        String brand;

        @NotBlank
        String model;

        @NotBlank
        String description;

        String attachedItems;

        @NotNull
        @DecimalMin(value = "0")
        BigDecimal estimatedPrice;

        @NotNull
        @Future
        Instant estimatedRelease;

        @Hidden
        UUID customerId;

        @Hidden
        UUID createdBy;
    }
}
