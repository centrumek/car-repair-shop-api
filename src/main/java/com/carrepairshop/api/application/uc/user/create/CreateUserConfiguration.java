package com.carrepairshop.api.application.uc.user.create;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.carrepairshop.api.application.port.user.in.InsertUserPort;
import com.carrepairshop.api.application.port.user.out.FindUserByEmailPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
class CreateUserConfiguration {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CreateUserService createUserService(final InsertUserPort insertUserPort,
                                        final FindUserByEmailPort findUserByEmailPort) {
        return new CreateUserService(insertUserPort, findUserByEmailPort, passwordEncoder);
    }
}
