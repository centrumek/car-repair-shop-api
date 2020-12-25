package com.carrepairshop.api.application.uc.user.password.change;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.carrepairshop.api.application.port.user.in.UpdateUserPasswordPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
class ChangeUserPasswordConfiguration {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ChangeUserPasswordService changeUserPasswordService(final UpdateUserPasswordPort updateUserPasswordPort) {
        return new ChangeUserPasswordService(updateUserPasswordPort, passwordEncoder);
    }
}
