package com.carrepairshop.api.application.uc.ticket.get;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.carrepairshop.api.application.port.out.FindTicketsAllPort;
import com.carrepairshop.api.application.port.out.FindTicketByIdPort;
import com.carrepairshop.api.application.port.out.FindTicketsByCustomerIdPort;
import com.carrepairshop.api.application.port.out.FindUserByEmailPort;

@Configuration
class GetTicketConfiguration {

    @Bean
    GetTicketService getTicketService(final FindTicketByIdPort findTicketByIdPort,
                                      final FindUserByEmailPort findUserByEmailPort,
                                      final FindTicketsByCustomerIdPort findTicketsByCustomerIdPort,
                                      final FindTicketsAllPort findTicketsAllPort) {
        return new GetTicketService(findTicketByIdPort,
            findUserByEmailPort,
            findTicketsByCustomerIdPort,
            findTicketsAllPort);
    }
}
