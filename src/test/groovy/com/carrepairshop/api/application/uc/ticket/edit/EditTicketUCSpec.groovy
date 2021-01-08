package com.carrepairshop.api.application.uc.ticket.edit


import com.carrepairshop.api.application.port.in.UpdateTicketPort
import com.carrepairshop.api.application.port.out.FindTicketByIdPort
import com.carrepairshop.api.application.port.out.FindUserByEmailPort
import org.apache.commons.lang3.RandomStringUtils
import spock.lang.Specification
import spock.lang.Unroll

import javax.persistence.EntityNotFoundException
import java.time.Instant

import static com.carrepairshop.api.application.domain.DomainUtils.stubEmployee
import static com.carrepairshop.api.application.domain.DomainUtils.stubTicket
import static com.carrepairshop.api.application.domain.DomainUtils.stubUserPrinciple
import static com.carrepairshop.api.application.domain.Ticket.Status.BLOCKED
import static com.carrepairshop.api.application.domain.Ticket.Status.IN_PROGRESS
import static com.carrepairshop.api.application.domain.Ticket.Status.READY_TO_PICK_UP
import static com.carrepairshop.api.application.domain.Ticket.Status.RELEASED
import static com.carrepairshop.api.application.uc.ticket.edit.EditTicketUC.EditTicketCommand

class EditTicketUCSpec extends Specification {

    private FindTicketByIdPort findTicketByIdPort
    private FindUserByEmailPort findUserByEmailPort
    private UpdateTicketPort updateTicketPort
    private EditTicketUC uc

    def setup() {
        findTicketByIdPort = Mock(FindTicketByIdPort)
        findUserByEmailPort = Mock(FindUserByEmailPort)
        updateTicketPort = Mock(UpdateTicketPort)
        uc = new EditTicketService(findTicketByIdPort, findUserByEmailPort, updateTicketPort)
    }

    @Unroll
    def "should edit ticket without releasedBy when status is '#STATUS'"() {
        given:
            def userPrincipal = stubUserPrinciple()
            def ticketId = UUID.randomUUID()
            def ticket = stubTicket()
            def employee = stubEmployee()
            def command = EditTicketCommand.builder()
                    .uuid(ticketId)
                    .title(RandomStringUtils.randomAscii(10))
                    .brand(RandomStringUtils.randomAscii(10))
                    .model(RandomStringUtils.randomAscii(10))
                    .description(RandomStringUtils.randomAscii(10))
                    .attachedItems(RandomStringUtils.randomAscii(10))
                    .status(STATUS.name())
                    .finalPrice(BigDecimal.ONE)
                    .calculationNote(RandomStringUtils.randomAscii(10))
                    .estimatedPrice(ticket.getEstimatedPrice())
                    .estimatedRelease(ticket.getEstimatedRelease())
                    .customerId(ticket.getCustomer().uuid)
                    .createdBy(ticket.getCreatedBy().uuid)
                    .build()
            def expectedTicket = stubTicket()

        and:
            1 * findTicketByIdPort.findTicketById(ticketId) >> Optional.of(ticket)
            1 * findUserByEmailPort.findUserByEmail(userPrincipal.username) >> Optional.of(employee)
            1 * updateTicketPort.updateTicket(command) >> expectedTicket
            0 * _

        when:
            def actualTicket = uc.editTicket(ticketId, command, userPrincipal)

        then:
            actualTicket == expectedTicket

        where:
            STATUS << [BLOCKED, READY_TO_PICK_UP, IN_PROGRESS]
    }

    @Unroll
    def "should edit ticket with releasedBy when status is '#STATUS'"() {
        given:
            def userPrincipal = stubUserPrinciple()
            def ticketId = UUID.randomUUID()
            def ticket = stubTicket()
            def employee = stubEmployee()
            def command = EditTicketCommand.builder()
                    .uuid(ticketId)
                    .title(RandomStringUtils.randomAscii(10))
                    .brand(RandomStringUtils.randomAscii(10))
                    .model(RandomStringUtils.randomAscii(10))
                    .description(RandomStringUtils.randomAscii(10))
                    .attachedItems(RandomStringUtils.randomAscii(10))
                    .status(STATUS.name())
                    .finalPrice(BigDecimal.ONE)
                    .calculationNote(RandomStringUtils.randomAscii(10))
                    .estimatedPrice(ticket.getEstimatedPrice())
                    .estimatedRelease(ticket.getEstimatedRelease())
                    .customerId(ticket.getCustomer().uuid)
                    .createdBy(ticket.getCreatedBy().uuid)
                    .releasedAt(Instant.now())
                    .releasedBy(employee.uuid)
                    .build()
            def expectedTicket = stubTicket()

        and:
            1 * findTicketByIdPort.findTicketById(ticketId) >> Optional.of(ticket)
            1 * findUserByEmailPort.findUserByEmail(userPrincipal.username) >> Optional.of(employee)
            1 * updateTicketPort.updateTicket(!null) >> expectedTicket
            0 * _

        when:
            def actualTicket = uc.editTicket(ticketId, command, userPrincipal)

        then:
            actualTicket == expectedTicket

        where:
            STATUS << [RELEASED]
    }

    def "should not edit ticket when ticket id not found in db"() {
        given:
            def ticketId = UUID.randomUUID()
            def userPrincipal = stubUserPrinciple()
            def command = EditTicketCommand.builder()
                    .build()

        and:
            1 * findTicketByIdPort.findTicketById(ticketId) >> Optional.empty()
            0 * _

        when:
            uc.editTicket(ticketId, command, userPrincipal)

        then:
            def exception = thrown(EntityNotFoundException)
            exception.getMessage() == "ticket '" + ticketId + "' not exists."
    }

    def "should not edit ticket when email not found in db"() {
        given:
            def userPrincipal = stubUserPrinciple()
            def ticketId = UUID.randomUUID()
            def ticket = stubTicket()
            def command = EditTicketCommand.builder()
                    .build()

        and:
            1 * findTicketByIdPort.findTicketById(ticketId) >> Optional.of(ticket)
            1 * findUserByEmailPort.findUserByEmail(userPrincipal.username) >> Optional.empty()
            0 * _

        when:
            uc.editTicket(ticketId, command, userPrincipal)

        then:
            def exception = thrown(EntityNotFoundException)
            exception.getMessage() == "email '" + userPrincipal.username + "' not exists."
    }
}