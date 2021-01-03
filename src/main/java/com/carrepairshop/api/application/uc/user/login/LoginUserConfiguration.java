package com.carrepairshop.api.application.uc.user.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.carrepairshop.api.application.port.in.InsertUserPort;
import com.carrepairshop.api.application.port.out.FindUserByEmailPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
class LoginUserConfiguration {

    @Bean
    LoginUserService loginUserService(final FindUserByEmailPort findUserByEmailPort) {
        return new LoginUserService(findUserByEmailPort);
    }
}
