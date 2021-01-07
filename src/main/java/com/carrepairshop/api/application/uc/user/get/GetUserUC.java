package com.carrepairshop.api.application.uc.user.get;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.carrepairshop.api.application.domain.User;

public interface GetUserUC {

    Page<User> getUsers(final Pageable pageable);
}
