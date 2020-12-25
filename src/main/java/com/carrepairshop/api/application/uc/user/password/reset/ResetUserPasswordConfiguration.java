package com.carrepairshop.api.application.uc.user.password.reset;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.carrepairshop.api.application.port.user.in.UpdateUserPasswordPort;
import com.carrepairshop.api.application.port.user.out.FindUserByEmailPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
class ResetUserPasswordConfiguration {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ResetUserPasswordService resetUserPasswordService(final FindUserByEmailPort findUserByEmailPort,
                                                      final UpdateUserPasswordPort updateUserPasswordPort) {
        return new ResetUserPasswordService(findUserByEmailPort, updateUserPasswordPort, passwordEncoder);
    }

}
