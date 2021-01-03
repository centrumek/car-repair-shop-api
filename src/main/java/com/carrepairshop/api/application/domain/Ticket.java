package com.carrepairshop.api.application.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Ticket {

    UUID uuid;
    User customer;
    String title;
    String brand;
    String model;
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
