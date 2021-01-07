package com.carrepairshop.api.application.uc.ticket.search;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.carrepairshop.api.application.port.out.FindTicketsByFullTextPort;

@Configuration
class SearchTicketConfiguration {

    @Bean
    SearchTicketService searchTicketService(final FindTicketsByFullTextPort findTicketsByFullTextPort) {
        return new SearchTicketService(findTicketsByFullTextPort);
    }
}
