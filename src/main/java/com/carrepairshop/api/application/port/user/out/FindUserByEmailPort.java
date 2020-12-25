package com.carrepairshop.api.application.port.user.out;

import java.util.Optional;
import com.carrepairshop.api.application.domain.user.User;
import com.carrepairshop.api.application.domain.user.UserPrincipal;

public interface FindUserByEmailPort {
    Optional<UserPrincipal> findUserPrincipalByEmail(String email);
    Optional<User> findUserByEmail(String email);
}
