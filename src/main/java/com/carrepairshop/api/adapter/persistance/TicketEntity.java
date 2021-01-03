package com.carrepairshop.api.adapter.persistance;

import java.math.BigDecimal;
import java.time.Instant;
import com.carrepairshop.api.application.domain.Ticket.Status;
import com.carrepairshop.api.common.BaseEntity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "ticket")
@Builder
@FieldDefaults(level = PRIVATE)
@Data
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
class TicketEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id")
    UserEntity customer;

    @Column(name = "title")
    String title;

    @Column(name = "brand")
    String brand;

    @Column(name = "model")
    String model;

    @Lob
    @Column(name = "description")
    String description;

    @Lob
    @Column(name = "attached_items")
    String attachedItems;

    @Column(name = "status")
    @Enumerated(value = STRING)
    Status status;

    @Column(name = "estimated_price")
    BigDecimal estimatedPrice;

    @Column(name = "estimated_release")
    Instant estimatedRelease;

    @Column(name = "final_price")
    BigDecimal finalPrice;

    @Lob
    @Column(name = "calculation_note")
    String calculationNote;

    @ManyToOne
    @JoinColumn(name = "created_by")
    UserEntity createdBy;

    @ManyToOne()
    @JoinColumn(name = "released_by")
    UserEntity releasedBy;

    @Column(name = "released_at")
    Instant releasedAt;
}