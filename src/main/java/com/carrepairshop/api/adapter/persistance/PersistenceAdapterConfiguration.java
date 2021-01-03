package com.carrepairshop.api.adapter.persistance;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.carrepairshop.api.configuration.jpa.CustomJpaRepositoryImpl;

@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
@Configuration
class PersistenceAdapterConfiguration {

    @Bean
    UserPersistenceAdapter userPersistenceAdapter(final UserRepository userRepository) {
        return new UserPersistenceAdapter(userRepository);
    }

    @Bean
    TicketPersistenceAdapter ticketPersistenceAdapter(final TicketRepository ticketRepository) {
        return new TicketPersistenceAdapter(ticketRepository);
    }
}
