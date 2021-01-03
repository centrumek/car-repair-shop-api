package com.carrepairshop.api.application.uc.user.authorize;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.carrepairshop.api.application.port.out.FindUserByEmailPort;

@Configuration
class AuthorizeUserConfiguration {

    @Bean
    AuthorizeUserService authorizeUserService(final FindUserByEmailPort findUserByEmailPort) {
        return new AuthorizeUserService(findUserByEmailPort);
    }
}
