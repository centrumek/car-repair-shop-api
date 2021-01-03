package com.carrepairshop.api.adapter.persistance;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import com.carrepairshop.api.application.domain.Ticket;
import com.carrepairshop.api.application.port.in.InsertTicketPort;
import com.carrepairshop.api.application.port.in.UpdateTicketPort;
import com.carrepairshop.api.application.port.out.FindTicketsAllPort;
import com.carrepairshop.api.application.port.out.FindTicketByIdPort;
import com.carrepairshop.api.application.port.out.FindTicketsByCustomerIdPort;
import com.carrepairshop.api.application.uc.ticket.create.CreateTicketUC.CreateTicketCommand;
import com.carrepairshop.api.application.uc.ticket.edit.EditTicketUC.EditTicketCommand;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import static com.carrepairshop.api.adapter.persistance.TicketEntityMapper.TICKET_ENTITY_MAPPER;

@RequiredArgsConstructor
class TicketPersistenceAdapter implements InsertTicketPort, UpdateTicketPort, FindTicketByIdPort, FindTicketsAllPort,
    FindTicketsByCustomerIdPort {

    private final TicketRepository ticketRepository;

    @Transactional
    @Override
    public Ticket insertTicket(CreateTicketCommand command) {
        var entity = TICKET_ENTITY_MAPPER.mapToEntity(command);
        var persistedEntity = ticketRepository.apply(entity);
        return TICKET_ENTITY_MAPPER.mapToSlo(persistedEntity);
    }

    @Transactional
    @Override
    public Ticket updateTicket(EditTicketCommand command) {
        var entity = TICKET_ENTITY_MAPPER.mapToEntity(command);
        var mergedEntity = ticketRepository.apply(entity);
        return TICKET_ENTITY_MAPPER.mapToSlo(mergedEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Ticket> findTicketById(UUID uuid) {
        return ticketRepository.findById(uuid)
                               .map(TICKET_ENTITY_MAPPER::mapToSlo);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Ticket> findTicketsAll(final Pageable pageable) {
        return ticketRepository.findAll(pageable)
                               .map(TICKET_ENTITY_MAPPER::mapToSlo);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Ticket> findTicketsByCustomerId(final UUID uuid, final Pageable pageable) {
        return ticketRepository.findAllByCustomerUuid(uuid, pageable)
                               .map(TICKET_ENTITY_MAPPER::mapToSlo);
    }

}
