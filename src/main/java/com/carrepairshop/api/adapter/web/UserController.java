package com.carrepairshop.api.adapter.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.carrepairshop.api.application.domain.User;
import com.carrepairshop.api.application.domain.UserPrincipal;
import com.carrepairshop.api.application.uc.user.password.change.ChangeUserPasswordUC;
import com.carrepairshop.api.application.uc.user.password.change.ChangeUserPasswordUC.ChangeUserPasswordCommand;
import com.carrepairshop.api.application.uc.user.create.CreateUserUC;
import com.carrepairshop.api.application.uc.user.create.CreateUserUC.CreateUserCommand;
import com.carrepairshop.api.application.uc.user.register.RegisterUserUC;
import com.carrepairshop.api.application.uc.user.register.RegisterUserUC.RegisterUserCommand;
import com.carrepairshop.api.application.uc.user.password.reset.ResetUserPasswordUC;
import com.carrepairshop.api.application.uc.user.password.reset.ResetUserPasswordUC.ResetUserPasswordCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UserController {

    private final RegisterUserUC registerUserUC;
    private final CreateUserUC createUserUC;
    private final ResetUserPasswordUC resetUserPasswordUC;
    private final ChangeUserPasswordUC changeUserPasswordUC;

    @PostMapping(value = "/register")
    User registerUser(@Valid @RequestBody final RegisterUserCommand command) {
        return registerUserUC.registerUser(command);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'HEAD')")
    @PostMapping
    User createUser(@Valid @RequestBody final CreateUserCommand command,
                    @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return createUserUC.createUser(command, userPrincipal);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'HEAD')")
    @PatchMapping(value = "/resetPassword")
    void resetUserPassword(@Valid @RequestBody final ResetUserPasswordCommand command,
                           @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        resetUserPasswordUC.resetUserPassword(command, userPrincipal);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'HEAD')")
    @PatchMapping(value = "/changePassword")
    void changeUserPassword(@Valid @RequestBody final ChangeUserPasswordCommand command,
                            @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        changeUserPasswordUC.changeUserPassword(command, userPrincipal);
    }
}
