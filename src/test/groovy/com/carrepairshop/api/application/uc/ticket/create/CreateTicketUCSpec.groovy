package com.carrepairshop.api.application.uc.ticket.create

import com.carrepairshop.api.application.port.in.InsertTicketPort
import com.carrepairshop.api.application.port.out.FindUserByEmailPort
import org.springframework.data.domain.Pageable
import spock.lang.Specification
import spock.lang.Unroll

import javax.persistence.EntityNotFoundException

import static com.carrepairshop.api.application.domain.DomainUtils.stubCustomer
import static com.carrepairshop.api.application.domain.DomainUtils.stubEmployee
import static com.carrepairshop.api.application.domain.DomainUtils.stubTicket
import static com.carrepairshop.api.application.domain.DomainUtils.stubUserPrinciple
import static com.carrepairshop.api.application.uc.ticket.create.CreateTicketUC.*

class CreateTicketUCSpec extends Specification {

    private FindUserByEmailPort findUserByEmailPort
    private InsertTicketPort insertTicketPort
    private CreateTicketUC uc

    def setup() {
        findUserByEmailPort = Mock(FindUserByEmailPort)
        insertTicketPort = Mock(InsertTicketPort)
        uc = new CreateTicketService(findUserByEmailPort, insertTicketPort)
    }

    @Unroll
    def "should create ticket with customer '#CMD_EMAIL' email created by '#INVOKER_EMAIL' invoker"() {
        given:
            def userPrinciple = stubUserPrinciple(INVOKER_EMAIL)
            def customer = stubCustomer()
            def employee = stubEmployee()
            def command = CreateTicketCommand.builder()
                    .email(CMD_EMAIL)
                    .createdBy(employee.uuid)
                    .customerId(customer.uuid)
                    .build()
            def expectedTicket = stubTicket()

        and:
            1 * findUserByEmailPort.findUserByEmail(CMD_EMAIL) >> Optional.of(customer)
            1 * findUserByEmailPort.findUserByEmail(INVOKER_EMAIL) >> Optional.of(employee)
            1 * insertTicketPort.insertTicket(command) >> expectedTicket
            0 * _

        when:
            def actualTicket = uc.createTicket(command, userPrinciple)

        then:
            actualTicket == expectedTicket

        where:
            CMD_EMAIL       | INVOKER_EMAIL
            "cust1@abc.com" | "emp2@abc.com"
    }

    @Unroll
    def "should not create ticket when `#CMD_EMAIL` email not found in db"() {
        given:
            def userPrinciple = stubUserPrinciple()
            def command = CreateTicketCommand.builder()
                    .email(CMD_EMAIL)
                    .build()

        and:
            1 * findUserByEmailPort.findUserByEmail(CMD_EMAIL) >> Optional.empty()
            0 * _

        when:
            uc.createTicket(command, userPrinciple)

        then:
            def exception = thrown(EntityNotFoundException)
            exception.getMessage() == "email '" + CMD_EMAIL + "' not exists."

        where:
            CMD_EMAIL << ["cust1@abc.com"]
    }

}