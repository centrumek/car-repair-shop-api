package com.carrepairshop.api.application.uc.user.get;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.carrepairshop.api.application.port.out.FindTicketByIdPort;
import com.carrepairshop.api.application.port.out.FindTicketsAllPort;
import com.carrepairshop.api.application.port.out.FindTicketsByCustomerIdPort;
import com.carrepairshop.api.application.port.out.FindUserByEmailPort;
import com.carrepairshop.api.application.port.out.FindUsersAllPort;

@Configuration
class GetUserConfiguration {

    @Bean
    GetUserService getUserService(final FindUsersAllPort findUsersAllPort) {
        return new GetUserService(findUsersAllPort);
    }
}
