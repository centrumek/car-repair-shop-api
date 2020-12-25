package com.carrepairshop.api.application.uc.user.create;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.carrepairshop.api.application.domain.user.User;
import com.carrepairshop.api.application.domain.user.User.Role;
import com.carrepairshop.api.application.domain.user.UserPrincipal;
import com.carrepairshop.api.application.port.user.in.InsertUserPort;
import com.carrepairshop.api.application.port.user.out.FindUserByEmailPort;
import javax.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;

import static com.carrepairshop.api.application.domain.user.User.Role.CUSTOMER;
import static com.carrepairshop.api.application.domain.user.User.Role.EMPLOYEE;
import static com.carrepairshop.api.application.domain.user.User.Role.HEAD;

@RequiredArgsConstructor
class CreateUserService implements CreateUserUC {

    private final InsertUserPort insertUserPort;
    private final FindUserByEmailPort findUserByEmailPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(final CreateUserCommand command,
                           final UserPrincipal userPrincipal) {
        validateExistsByEmail(command.getEmail());
        validateCreateUserPermission(userPrincipal.getRole(), Role.valueOf(command.getRole()));
        return insertUserPort.insertUser(encodePassword(command));
    }

    private void validateExistsByEmail(final String email) {
        if (findUserByEmailPort.findUserByEmail(email).isPresent()) {
            throw new EntityExistsException("email '" + email + "' already exists.");
        }
    }

    private void validateCreateUserPermission(final Role invokerRole,
                                              final Role insertRole) {
        var customerCondition = (insertRole == CUSTOMER && (invokerRole == EMPLOYEE || invokerRole == HEAD));
        var employeeCondition = (insertRole == EMPLOYEE && invokerRole == HEAD);
        if (!customerCondition && !employeeCondition) {
            throw new AccessDeniedException("Action forbidden, check your permissions.");
        }
    }

    private CreateUserCommand encodePassword(final CreateUserCommand command) {
        return command.withPassword(passwordEncoder.encode(command.getEmail()));
    }
}
