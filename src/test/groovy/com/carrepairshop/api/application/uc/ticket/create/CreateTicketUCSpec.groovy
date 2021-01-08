package com.carrepairshop.api.application.uc.ticket.create

import com.carrepairshop.api.application.port.in.InsertTicketPort
import com.carrepairshop.api.application.port.out.FindUserByEmailPort
import spock.lang.Specification

class CreateTicketUCSpec extends Specification {

    private FindUserByEmailPort findUserByEmailPort
    private InsertTicketPort insertTicketPort
    private CreateTicketUC uc

    def setup() {
        findUserByEmailPort = Mock(FindUserByEmailPort)
        insertTicketPort = Mock(InsertTicketPort)
        uc = new CreateTicketService(findUserByEmailPort, insertTicketPort)
    }

}