package com.carrepairshop.api.application.domain

import org.springframework.data.domain.PageImpl

import java.security.SecureRandom

import static org.apache.commons.lang3.RandomStringUtils.random
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric

class DomainUtils {

    private static final SecureRandom RANDOM = new SecureRandom()
    private static final int DEFAULT_RANDOM_LENGTH = 10

    static def stubUsersPage() {
        def users = [stubUser(), stubUser(), stubUser(), stubUser()]
        return new PageImpl<>(users)
    }

    static def stubUser(User.Role role) {
        return User.builder()
                .uuid(UUID.randomUUID())
                .email(random(DEFAULT_RANDOM_LENGTH))
                .firstName(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
                .lastName(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
                .mobilePhone(randomNumeric(DEFAULT_RANDOM_LENGTH))
                .role(role)
                .build()
    }

    static def stubUser() {
        return User.builder()
                .uuid(UUID.randomUUID())
                .email(random(DEFAULT_RANDOM_LENGTH))
                .firstName(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
                .lastName(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
                .mobilePhone(randomNumeric(DEFAULT_RANDOM_LENGTH))
                .role(User.Role.values()[RANDOM.nextInt(User.Role.values().size())])
                .build()
    }

    static def stubUserPrinciple(User.Role role) {
        return UserPrincipal.builder()
                .username(random(DEFAULT_RANDOM_LENGTH))
                .password(random(DEFAULT_RANDOM_LENGTH))
                .role(role)
                .build()
    }

    static def stubUserPrinciple(String email) {
        return UserPrincipal.builder()
                .username(email)
                .password(random(DEFAULT_RANDOM_LENGTH))
                .role(User.Role.values()[RANDOM.nextInt(User.Role.values().size())])
                .build()
    }

    static def stubUserPrinciple() {
        return UserPrincipal.builder()
                .username(random(DEFAULT_RANDOM_LENGTH))
                .password(random(DEFAULT_RANDOM_LENGTH))
                .role(User.Role.values()[RANDOM.nextInt(User.Role.values().size())])
                .build()
    }
}
