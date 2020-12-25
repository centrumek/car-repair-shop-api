package com.carrepairshop.api.application.uc.user.password.change;

import com.carrepairshop.api.application.domain.user.UserPrincipal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

public interface ChangeUserPasswordUC {

    void changeUserPassword(final ChangeUserPasswordCommand command,
                            final UserPrincipal userPrincipal);

    @Value
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode(callSuper = false)
    class ChangeUserPasswordCommand {

        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Minimum eight characters, at least one letter and one number")
        String password;
    }
}
