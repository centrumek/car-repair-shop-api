package com.carrepairshop.api.application.uc.user.authorize;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.carrepairshop.api.application.port.user.out.FindUserByEmailPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AuthorizeUserService implements AuthorizeUserUC {

    private final FindUserByEmailPort findUserByEmailPort;

    @Override
    public UserDetails loadUserByUsername(final String email) {
        return findUserByEmailPort.findUserPrincipalByEmail(email)
                                  .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
