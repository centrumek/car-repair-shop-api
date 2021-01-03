package com.carrepairshop.api.application.uc.ticket.edit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.carrepairshop.api.application.port.in.UpdateTicketPort;
import com.carrepairshop.api.application.port.out.FindTicketByIdPort;
import com.carrepairshop.api.application.port.out.FindUserByEmailPort;

@Configuration
class EditTicketConfiguration {

    @Bean
    EditTicketService editTicketService(final FindTicketByIdPort findTicketByIdPort,
                                        final FindUserByEmailPort findUserByEmailPort,
                                        final UpdateTicketPort updateTicketPort) {
        return new EditTicketService(findTicketByIdPort, findUserByEmailPort, updateTicketPort);
    }
}
