package com.carrepairshop.api.application.domain.ticket;

import java.math.BigDecimal;
import java.time.Instant;
import com.carrepairshop.api.application.domain.user.User;

public class Ticket {

    String id;
    User customer;
    String title;
    String description;
    String attachedItems;
    Status status;
    BigDecimal estimatedPrice;
    Instant estimatedRelease;
    BigDecimal finalPrice;
    String calculationNote;
    Instant createdAt;
    User createdBy;
    Instant releasedAt;
    User releasedBy;

    public enum Status {
        CREATED, BLOCKED, IN_PROGRESS, READY_TO_PICK_UP, RELEASED, CANCELLED
    }
}
