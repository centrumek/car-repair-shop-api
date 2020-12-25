package com.carrepairshop.api.application.uc.user.password.reset;

import com.carrepairshop.api.application.domain.user.UserPrincipal;
import javax.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

public interface ResetUserPasswordUC {

    void resetUserPassword(final ResetUserPasswordCommand command,
                           final UserPrincipal userPrincipal);

    @Value
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode(callSuper = false)
    class ResetUserPasswordCommand {

        @Email
        String email;
    }
}
