package com.carrepairshop.api.application.port.out;

import java.util.Optional;
import com.carrepairshop.api.application.domain.User;
import com.carrepairshop.api.application.domain.UserPrincipal;

public interface FindUserByEmailPort {
    Optional<UserPrincipal> findUserPrincipalByEmail(String email);
    Optional<User> findUserByEmail(String email);
}
