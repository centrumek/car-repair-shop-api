package com.carrepairshop.api.application.uc.user.login;

import com.carrepairshop.api.application.domain.User;
import com.carrepairshop.api.application.domain.UserPrincipal;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

public interface LoginUserUC {

    User loginUser(final UserPrincipal userPrincipal);
}
