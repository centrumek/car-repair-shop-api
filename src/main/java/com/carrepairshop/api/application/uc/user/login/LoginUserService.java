package com.carrepairshop.api.application.uc.user.login;

import com.carrepairshop.api.application.domain.User;
import com.carrepairshop.api.application.domain.UserPrincipal;
import com.carrepairshop.api.application.port.out.FindUserByEmailPort;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class LoginUserService implements LoginUserUC {

    private final FindUserByEmailPort findUserByEmailPort;

    @Override
    public User loginUser(final UserPrincipal userPrincipal) {
        final var email = userPrincipal.getUsername();
        return findUserByEmailPort.findUserByEmail(email)
                                  .orElseThrow(() -> new EntityNotFoundException("email '" + email + "' not exists."));
    }
}
