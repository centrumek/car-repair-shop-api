package com.carrepairshop.api.application.uc.user.register;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.carrepairshop.api.application.domain.user.User;
import com.carrepairshop.api.application.domain.user.User.Role;
import com.carrepairshop.api.application.port.user.in.InsertUserPort;
import com.carrepairshop.api.application.port.user.out.FindUserByEmailPort;
import javax.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class RegisterUserService implements RegisterUserUC {

    private final InsertUserPort insertUserPort;
    private final FindUserByEmailPort findUserByEmailPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(final RegisterUserCommand command) {
        validateExistsByEmail(command.getEmail());
        return insertUserPort.insertUser(encodePassword(command), Role.CUSTOMER);
    }

    private RegisterUserCommand encodePassword(final RegisterUserCommand command) {
        return command.withPassword(passwordEncoder.encode(command.getPassword()));
    }

    private void validateExistsByEmail(final String email) {
        if (findUserByEmailPort.findUserByEmail(email).isPresent()) {
            throw new EntityExistsException("email '" + email + "' already exists.");
        }
    }
}
