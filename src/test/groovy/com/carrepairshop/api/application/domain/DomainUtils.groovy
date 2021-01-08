package com.carrepairshop.api.application.domain

import org.springframework.data.domain.PageImpl

import java.security.SecureRandom
import java.time.Instant

import static org.apache.commons.lang3.RandomStringUtils.random
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric

class DomainUtils {

    private static final SecureRandom RANDOM = new SecureRandom()
    private static final int DEFAULT_RANDOM_LENGTH = 10

    static def stubTicketsPage() {
        def users = [stubTicket(), stubTicket(), stubTicket(), stubTicket()]
        return new PageImpl<>(users)
    }

    static def stubTicket() {
        return Ticket.builder()
        .uuid(UUID.randomUUID())
        .title(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
        .brand(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
        .model(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
        .description(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
        .attachedItems(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
        .status(Ticket.Status.values()[RANDOM.nextInt(Ticket.Status.values().size())])
        .calculationNote(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
        .estimatedPrice(new BigDecimal(randomNumeric(DEFAULT_RANDOM_LENGTH)))
        .finalPrice(new BigDecimal(randomNumeric(DEFAULT_RANDOM_LENGTH)))
        .customer(stubUser())
        .createdBy(stubUser())
        .releasedBy(stubUser())
        .createdAt(Instant.now())
        .estimatedRelease(Instant.now())
        .releasedAt(Instant.now())
        .build()
    }

    static def stubTicketWithCustomer(User customer) {
        return Ticket.builder()
                .uuid(UUID.randomUUID())
                .title(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
                .brand(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
                .model(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
                .description(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
                .attachedItems(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
                .status(Ticket.Status.values()[RANDOM.nextInt(Ticket.Status.values().size())])
                .calculationNote(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
                .estimatedPrice(new BigDecimal(randomNumeric(DEFAULT_RANDOM_LENGTH)))
                .finalPrice(new BigDecimal(randomNumeric(DEFAULT_RANDOM_LENGTH)))
                .customer(customer)
                .createdBy(stubUser())
                .releasedBy(stubUser())
                .createdAt(Instant.now())
                .estimatedRelease(Instant.now())
                .releasedAt(Instant.now())
                .build()
    }

    static def stubUsersPage() {
        def users = [stubUser(), stubUser(), stubUser(), stubUser()]
        return new PageImpl<>(users)
    }

    static def stubEmployee() {
        return User.builder()
                .uuid(UUID.randomUUID())
                .email(random(DEFAULT_RANDOM_LENGTH))
                .firstName(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
                .lastName(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
                .mobilePhone(randomNumeric(DEFAULT_RANDOM_LENGTH))
                .role(User.Role.EMPLOYEE)
                .build()
    }

    static def stubCustomer() {
        return User.builder()
                .uuid(UUID.randomUUID())
                .email(random(DEFAULT_RANDOM_LENGTH))
                .firstName(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
                .lastName(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
                .mobilePhone(randomNumeric(DEFAULT_RANDOM_LENGTH))
                .role(User.Role.CUSTOMER)
                .build()
    }

    static def stubCustomer(String email) {
        return User.builder()
                .uuid(UUID.randomUUID())
                .email(email)
                .firstName(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
                .lastName(randomAlphabetic(DEFAULT_RANDOM_LENGTH))
                .mobilePhone(randomNumeric(DEFAULT_RANDOM_LENGTH))
                .role(User.Role.CUSTOMER)
                .build()
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

    static def stubUserPrinciple(User.Role role, String email) {
        return UserPrincipal.builder()
                .username(email)
                .password(random(DEFAULT_RANDOM_LENGTH))
                .role(role)
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
