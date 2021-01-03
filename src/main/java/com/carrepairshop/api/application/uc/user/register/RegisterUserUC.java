package com.carrepairshop.api.application.uc.user.register;

import com.carrepairshop.api.application.domain.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

public interface RegisterUserUC {

    User registerUser(final RegisterUserCommand command);

    @Value
    @Builder
    @With
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode(callSuper = false)
    class RegisterUserCommand {

        @NotBlank
        String firstName;

        @NotBlank
        String lastName;

        @Email
        String email;

        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Minimum eight characters, at least one letter and one number")
        String password;

        @Pattern(regexp = "(^$|\\d{10})")
        String mobilePhone;
    }
}
