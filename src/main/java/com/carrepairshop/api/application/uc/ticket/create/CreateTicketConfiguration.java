package com.carrepairshop.api.application.uc.ticket.create;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.carrepairshop.api.application.port.in.InsertTicketPort;
import com.carrepairshop.api.application.port.out.FindUserByEmailPort;

@Configuration
class CreateTicketConfiguration {

    @Bean
    CreateTicketService createTicketService(final FindUserByEmailPort findUserByEmailPort,
                                            final InsertTicketPort insertTicketPort) {
        return new CreateTicketService(findUserByEmailPort, insertTicketPort);
    }
}
