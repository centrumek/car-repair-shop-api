package com.carrepairshop.api.adapter.persistance.user;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import com.carrepairshop.api.application.domain.user.User;
import com.carrepairshop.api.application.domain.user.User.Role;
import com.carrepairshop.api.application.domain.user.UserPrincipal;
import com.carrepairshop.api.application.port.user.in.UpdateUserPasswordPort;
import com.carrepairshop.api.application.port.user.in.InsertUserPort;
import com.carrepairshop.api.application.port.user.out.FindUserByEmailPort;
import com.carrepairshop.api.application.uc.user.create.CreateUserUC.CreateUserCommand;
import com.carrepairshop.api.application.uc.user.register.RegisterUserUC.RegisterUserCommand;
import lombok.RequiredArgsConstructor;

import static com.carrepairshop.api.adapter.persistance.user.UserEntityMapper.USER_ENTITY_MAPPER;

@RequiredArgsConstructor
class UserPersistenceAdapter implements InsertUserPort, FindUserByEmailPort, UpdateUserPasswordPort {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public User insertUser(RegisterUserCommand command,
                           Role role) {
        final var entity = USER_ENTITY_MAPPER.mapToEntity(command, role);
        final var persistedEntity = userRepository.apply(entity);
        return USER_ENTITY_MAPPER.mapToSlo(persistedEntity);
    }

    @Transactional
    @Override
    public User insertUser(final CreateUserCommand command) {
        final var entity = USER_ENTITY_MAPPER.mapToEntity(command);
        final var persistedEntity = userRepository.apply(entity);
        return USER_ENTITY_MAPPER.mapToSlo(persistedEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserPrincipal> findUserPrincipalByEmail(String email) {
        return userRepository.findByEmail(email)
                             .map(USER_ENTITY_MAPPER::mapToPrincipal);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                             .map(USER_ENTITY_MAPPER::mapToSlo);
    }

    @Transactional
    @Override
    public void updateUserPasswordByEmail(String password, String email) {
        userRepository.updatePassword(password, email);
    }
}
