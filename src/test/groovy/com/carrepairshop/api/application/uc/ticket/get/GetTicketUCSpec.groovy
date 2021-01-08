package com.carrepairshop.api.application.uc.ticket.get

import com.carrepairshop.api.application.port.out.FindTicketByIdPort
import com.carrepairshop.api.application.port.out.FindTicketsAllPort
import com.carrepairshop.api.application.port.out.FindTicketsByCustomerIdPort
import com.carrepairshop.api.application.port.out.FindUserByEmailPort
import spock.lang.Specification

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


}