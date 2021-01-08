package com.carrepairshop.api.application.uc.user.create;

import com.carrepairshop.api.application.domain.User;
import com.carrepairshop.api.application.domain.UserPrincipal;
import io.swagger.v3.oas.annotations.Hidden;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

public interface CreateUserUC {

    User createUser(final CreateUserCommand command,
                    final UserPrincipal userPrincipal);

    @Value
    @Builder
    @With
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode(callSuper = false)
    class CreateUserCommand {

        @NotBlank
        String firstName;

        @NotBlank
        String lastName;

        @Email
        @NotNull
        String email;

        @Pattern(regexp = "(^$|\\d{10})")
        @NotNull
        String mobilePhone;

        @Pattern(regexp = "CUSTOMER|EMPLOYEE")
        @NotNull
        String role;

        @Hidden
        String password;
    }
}
