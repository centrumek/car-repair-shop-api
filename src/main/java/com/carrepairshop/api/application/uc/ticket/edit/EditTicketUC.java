package com.carrepairshop.api.application.uc.ticket.edit;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import com.carrepairshop.api.application.domain.Ticket;
import com.carrepairshop.api.application.domain.UserPrincipal;
import io.swagger.v3.oas.annotations.Hidden;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

public interface EditTicketUC {

    Ticket editTicket(final UUID uuid,
                      final EditTicketCommand command,
                      final UserPrincipal userPrincipal);

    @Value
    @Builder
    @With
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode(callSuper = false)
    class EditTicketCommand {

        @NotBlank
        String title;

        @NotBlank
        String brand;

        @NotBlank
        String model;

        @NotBlank
        String description;

        String attachedItems;

        @Pattern(regexp = "CREATED|BLOCKED|IN_PROGRESS|READY_TO_PICK_UP|RELEASED|CANCELLED")
        String status;

        @DecimalMin(value = "0")
        BigDecimal finalPrice;

        String calculationNote;

        @Hidden
        UUID uuid;

        @Hidden
        BigDecimal estimatedPrice;

        @Hidden
        Instant estimatedRelease;

        @Hidden
        UUID customerId;

        @Hidden
        UUID createdBy;

        @Hidden
        UUID releasedBy;

        @Hidden
        Instant releasedAt;
    }
}
