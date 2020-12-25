package com.carrepairshop.api.application.port.user.in;

import com.carrepairshop.api.application.domain.user.User;
import com.carrepairshop.api.application.domain.user.User.Role;
import com.carrepairshop.api.application.uc.user.create.CreateUserUC.CreateUserCommand;
import com.carrepairshop.api.application.uc.user.register.RegisterUserUC.RegisterUserCommand;

public interface InsertUserPort {

    User insertUser(final RegisterUserCommand command,
                    final Role role);
    User insertUser(final CreateUserCommand command);
}
