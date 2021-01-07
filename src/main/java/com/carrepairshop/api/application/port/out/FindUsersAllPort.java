package com.carrepairshop.api.application.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.carrepairshop.api.application.domain.User;

public interface FindUsersAllPort {

    Page<User> findUsersAll(final Pageable pageable);
}
