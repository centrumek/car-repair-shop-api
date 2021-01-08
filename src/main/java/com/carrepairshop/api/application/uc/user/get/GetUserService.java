package com.carrepairshop.api.application.uc.user.get;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.carrepairshop.api.application.domain.User;
import com.carrepairshop.api.application.port.out.FindUsersAllPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserService implements GetUserUC {

    private final FindUsersAllPort findUsersAllPort;

    @Override
    public Page<User> getUsers(final Pageable pageable) {
        return findUsersAllPort.findUsersAll(pageable);
    }
}
