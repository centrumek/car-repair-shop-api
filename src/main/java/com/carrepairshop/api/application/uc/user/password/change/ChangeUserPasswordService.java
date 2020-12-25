package com.carrepairshop.api.application.uc.user.password.change;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.carrepairshop.api.application.domain.user.UserPrincipal;
import com.carrepairshop.api.application.port.user.in.UpdateUserPasswordPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ChangeUserPasswordService implements ChangeUserPasswordUC {

    private final UpdateUserPasswordPort updateUserPasswordPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void changeUserPassword(final ChangeUserPasswordCommand command,
                                   final UserPrincipal userPrincipal) {
        final var email = userPrincipal.getUsername();
        final var password = encode(command.getPassword());
        updateUserPasswordPort.updateUserPasswordByEmail(password, email);
    }

    private String encode(final String string) {
        return passwordEncoder.encode(string);
    }
}
