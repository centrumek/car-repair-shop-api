package com.carrepairshop.api.application.uc.ticket.edit

import com.carrepairshop.api.application.port.in.UpdateTicketPort
import com.carrepairshop.api.application.port.out.FindTicketByIdPort
import com.carrepairshop.api.application.port.out.FindUserByEmailPort
import spock.lang.Specification

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

}