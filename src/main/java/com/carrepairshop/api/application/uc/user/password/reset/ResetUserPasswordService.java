package com.carrepairshop.api.application.uc.user.password.reset;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.carrepairshop.api.application.domain.User;
import com.carrepairshop.api.application.domain.User.Role;
import com.carrepairshop.api.application.domain.UserPrincipal;
import com.carrepairshop.api.application.port.in.UpdateUserPasswordPort;
import com.carrepairshop.api.application.port.out.FindUserByEmailPort;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import static com.carrepairshop.api.application.domain.User.Role.CUSTOMER;
import static com.carrepairshop.api.application.domain.User.Role.EMPLOYEE;
import static com.carrepairshop.api.application.domain.User.Role.HEAD;

@RequiredArgsConstructor
class ResetUserPasswordService implements ResetUserPasswordUC {

    private final FindUserByEmailPort findUserByEmailPort;
    private final UpdateUserPasswordPort updateUserPasswordPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void resetUserPassword(final ResetUserPasswordCommand command,
                                  final UserPrincipal userPrincipal) {
        validateResetPasswordPermission(userPrincipal.getRole(), getRoleByEmail(command.getEmail()));
        updateUserPasswordPort.updateUserPasswordByEmail(encode(command.getEmail()), command.getEmail());
    }

    private String encode(final String string) {
        return passwordEncoder.encode(string);
    }

    private Role getRoleByEmail(String email) {
        return findUserByEmailPort.findUserByEmail(email)
                                  .map(User::getRole)
                                  .orElseThrow(() -> new EntityNotFoundException("email '" + email + "' not exists."));
    }

    private void validateResetPasswordPermission(final Role invokerRole,
                                                 final Role emailRole) {
        var customerCondition = (emailRole == CUSTOMER && (invokerRole == EMPLOYEE || invokerRole == HEAD));
        var employeeCondition = (emailRole == EMPLOYEE && invokerRole == HEAD);
        if (!customerCondition && !employeeCondition) {
            throw new AccessDeniedException("Action forbidden, check your permissions.");
        }
    }
}
