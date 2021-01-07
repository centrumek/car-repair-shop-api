package com.carrepairshop.api.adapter.persistance;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import com.carrepairshop.api.application.domain.User;
import com.carrepairshop.api.application.domain.User.Role;
import com.carrepairshop.api.application.domain.UserPrincipal;
import com.carrepairshop.api.application.port.in.UpdateUserPasswordPort;
import com.carrepairshop.api.application.port.in.InsertUserPort;
import com.carrepairshop.api.application.port.out.FindUserByEmailPort;
import com.carrepairshop.api.application.port.out.FindUsersAllPort;
import com.carrepairshop.api.application.uc.user.create.CreateUserUC.CreateUserCommand;
import com.carrepairshop.api.application.uc.user.register.RegisterUserUC.RegisterUserCommand;
import lombok.RequiredArgsConstructor;

import static com.carrepairshop.api.adapter.persistance.UserEntityMapper.USER_ENTITY_MAPPER;

@RequiredArgsConstructor
class UserPersistenceAdapter implements InsertUserPort, FindUserByEmailPort, UpdateUserPasswordPort, FindUsersAllPort {

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

    @Override
    public Page<User> findUsersAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                             .map(USER_ENTITY_MAPPER::mapToSlo);
    }
}
