package com.carrepairshop.api.application.uc.ticket.get


import com.carrepairshop.api.application.port.out.FindTicketByIdPort
import com.carrepairshop.api.application.port.out.FindTicketsAllPort
import com.carrepairshop.api.application.port.out.FindTicketsByCustomerIdPort
import com.carrepairshop.api.application.port.out.FindUserByEmailPort
import org.springframework.data.domain.Pageable
import org.springframework.security.access.AccessDeniedException
import spock.lang.Specification
import spock.lang.Unroll

import javax.persistence.EntityNotFoundException

import static com.carrepairshop.api.application.domain.DomainUtils.stubCustomer
import static com.carrepairshop.api.application.domain.DomainUtils.stubTicket
import static com.carrepairshop.api.application.domain.DomainUtils.stubTicketWithCustomer
import static com.carrepairshop.api.application.domain.DomainUtils.stubTicketsPage
import static com.carrepairshop.api.application.domain.DomainUtils.stubUserPrinciple
import static com.carrepairshop.api.application.domain.User.Role.CUSTOMER
import static com.carrepairshop.api.application.domain.User.Role.EMPLOYEE
import static com.carrepairshop.api.application.domain.User.Role.HEAD

class GetTicketUCSpec extends Specification {

    private FindTicketByIdPort findTicketByIdPort
    private FindUserByEmailPort findUserByEmailPort
    private FindTicketsByCustomerIdPort findTicketsByCustomerIdPort
    private FindTicketsAllPort findTicketsAllPort
    private GetTicketUC uc

    def setup() {
        findTicketByIdPort = Mock(FindTicketByIdPort)
        findUserByEmailPort = Mock(FindUserByEmailPort)
        findTicketsByCustomerIdPort = Mock(FindTicketsByCustomerIdPort)
        findTicketsAllPort = Mock(FindTicketsAllPort)
        uc = new GetTicketService(findTicketByIdPort, findUserByEmailPort, findTicketsByCustomerIdPort, findTicketsAllPort)
    }

    @Unroll
    def "should get all tickets page when invoker role is '#INVOKER_ROLE'"() {
        given:
            def userPrinciple = stubUserPrinciple(INVOKER_ROLE)
            def expectedTicketsPage = stubTicketsPage()

        and:
            1 * findTicketsAllPort.findTicketsAll(!null) >> expectedTicketsPage
            0 * _

        when:
            def actualTicketsPage = uc.getTickets(userPrinciple, Pageable.unpaged())

        then:
            actualTicketsPage == expectedTicketsPage

        where:
            INVOKER_ROLE << [EMPLOYEE, HEAD]
    }

    @Unroll
    def "should get only customer tickets page when invoker role is '#INVOKER_ROLE'"() {
        given:
            def userPrinciple = stubUserPrinciple(INVOKER_ROLE)
            def customer = stubCustomer()
            def expectedTicketsPage = stubTicketsPage()

        and:
            1 * findUserByEmailPort.findUserByEmail(userPrinciple.username) >> Optional.of(customer)
            1 * findTicketsByCustomerIdPort.findTicketsByCustomerId(customer.uuid, !null) >> expectedTicketsPage
            0 * _

        when:
            def actualTicketsPage = uc.getTickets(userPrinciple, Pageable.unpaged())

        then:
            actualTicketsPage == expectedTicketsPage

        where:
            INVOKER_ROLE << [CUSTOMER]
    }

    @Unroll
    def "should not get only customer tickets page when invoker role is '#INVOKER_ROLE' and email not exists in db"() {
        given:
            def userPrinciple = stubUserPrinciple(INVOKER_ROLE)

        and:
            1 * findUserByEmailPort.findUserByEmail(userPrinciple.username) >> Optional.empty()
            0 * _

        when:
            uc.getTickets(userPrinciple, Pageable.unpaged())

        then:
            def exception = thrown(EntityNotFoundException)
            exception.getMessage() == "email '" + userPrinciple.username + "' not exists."

        where:
            INVOKER_ROLE << [CUSTOMER]
    }

    @Unroll
    def "should get ticket when invoker role is '#INVOKER_ROLE'"() {
        given:
            def userPrinciple = stubUserPrinciple(INVOKER_ROLE)
            def expectedTicket = stubTicket()

        and:
            1 * findTicketByIdPort.findTicketById(!null) >> Optional.of(expectedTicket)
            0 * _

        when:
            def actualTicket = uc.getTicket(UUID.randomUUID(), userPrinciple)

        then:
            actualTicket == expectedTicket

        where:
            INVOKER_ROLE << [EMPLOYEE, HEAD]
    }

    @Unroll
    def "should get ticket belongs to `#TICKET_EMAIL` email when invoker role is '#INVOKER_ROLE' with `#INVOKER_EMAIL` email"() {
        given:
            def userPrinciple = stubUserPrinciple(INVOKER_ROLE, INVOKER_EMAIL)
            def expectedTicket = stubTicketWithCustomer(stubCustomer(TICKET_EMAIL))

        and:
            1 * findTicketByIdPort.findTicketById(!null) >> Optional.of(expectedTicket)
            0 * _

        when:
            def actualTicket = uc.getTicket(UUID.randomUUID(), userPrinciple)

        then:
            actualTicket == expectedTicket

        where:
            TICKET_EMAIL  | INVOKER_ROLE | INVOKER_EMAIL
            "abc@abc.com" | CUSTOMER     | "abc@abc.com"
    }

    @Unroll
    def "should not get ticket when not exists."() {
        given:
            def userPrinciple = stubUserPrinciple()
            def ticketId = UUID.randomUUID()

        and:
            1 * findTicketByIdPort.findTicketById(!null) >> Optional.empty()
            0 * _

        when:
            uc.getTicket(ticketId, userPrinciple)

        then:
            def exception = thrown(EntityNotFoundException)
            exception.getMessage() == "ticket '" + ticketId + "' not exists."
    }

    @Unroll
    def "should not get ticket belongs to `#TICKET_EMAIL` email when invoker role is '#INVOKER_ROLE' with `#INVOKER_EMAIL` email"() {
        given:
            def userPrinciple = stubUserPrinciple(INVOKER_ROLE, INVOKER_EMAIL)
            def expectedTicket = stubTicketWithCustomer(stubCustomer(TICKET_EMAIL))

        and:
            1 * findTicketByIdPort.findTicketById(!null) >> Optional.of(expectedTicket)
            0 * _

        when:
            uc.getTicket(UUID.randomUUID(), userPrinciple)

        then:
            def exception = thrown(AccessDeniedException)
            exception.getMessage() == "Action forbidden, check your permissions."

        where:
            TICKET_EMAIL   | INVOKER_ROLE | INVOKER_EMAIL
            "abcd@abc.com" | CUSTOMER     | "abc@abc.com"
    }
}