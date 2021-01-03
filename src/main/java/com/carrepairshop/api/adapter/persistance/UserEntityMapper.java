package com.carrepairshop.api.adapter.persistance;

import com.carrepairshop.api.application.domain.User;
import com.carrepairshop.api.application.domain.User.Role;
import com.carrepairshop.api.application.domain.UserPrincipal;
import com.carrepairshop.api.application.uc.user.create.CreateUserUC.CreateUserCommand;
import com.carrepairshop.api.application.uc.user.register.RegisterUserUC.RegisterUserCommand;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class UserEntityMapper {

    static final UserEntityMapper USER_ENTITY_MAPPER = getMapper(UserEntityMapper.class);

    @BeanMapping(builder = @Builder(disableBuilder = true))
    abstract UserEntity mapToEntity(final CreateUserCommand command);

    @BeanMapping(builder = @Builder(disableBuilder = true))
    abstract UserEntity mapToEntity(final RegisterUserCommand command,
                                    final Role role);

    abstract User mapToSlo(final UserEntity userEntity);

    @Mapping(target = "username", source = "email")
    abstract UserPrincipal mapToPrincipal(final UserEntity userEntity);
}
