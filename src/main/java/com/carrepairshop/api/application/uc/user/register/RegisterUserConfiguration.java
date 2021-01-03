package com.carrepairshop.api.application.uc.user.register;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.carrepairshop.api.application.port.in.InsertUserPort;
import com.carrepairshop.api.application.port.out.FindUserByEmailPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
class RegisterUserConfiguration {

    private final PasswordEncoder passwordEncoder;

    @Bean
    RegisterUserService registerUserService(final InsertUserPort insertUserPort,
                                            final FindUserByEmailPort findUserByEmailPort) {
        return new RegisterUserService(insertUserPort, findUserByEmailPort, passwordEncoder);
    }
}
