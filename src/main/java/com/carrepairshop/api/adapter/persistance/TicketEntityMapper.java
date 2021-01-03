package com.carrepairshop.api.adapter.persistance;

import com.carrepairshop.api.application.domain.Ticket;
import com.carrepairshop.api.application.uc.ticket.create.CreateTicketUC.CreateTicketCommand;
import com.carrepairshop.api.application.uc.ticket.edit.EditTicketUC.EditTicketCommand;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = TicketEntityMapperHelper.class)
abstract class TicketEntityMapper {

    static final TicketEntityMapper TICKET_ENTITY_MAPPER = getMapper(TicketEntityMapper.class);

    @BeanMapping(builder = @Builder(disableBuilder = true))
    @Mapping(target = "status", constant = "CREATED")
    @Mapping(target = "customer.uuid", source = "customerId")
    @Mapping(target = "createdBy.uuid", source = "createdBy")
    abstract TicketEntity mapToEntity(final CreateTicketCommand command);

    @BeanMapping(builder = @Builder(disableBuilder = true))
    @Mapping(target = "customer.uuid", source = "customerId")
    @Mapping(target = "createdBy.uuid", source = "createdBy")
    @Mapping(target = "releasedBy", source = "releasedBy", qualifiedByName = "mapReleasedBy")
    abstract TicketEntity mapToEntity(final EditTicketCommand command);

    abstract Ticket mapToSlo(final TicketEntity ticketEntity);
}
