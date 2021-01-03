package com.carrepairshop.api.application.domain;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    UUID uuid;
    Role role;
    String email;
    String firstName;
    String lastName;
    String mobilePhone;

    public enum Role {
        CUSTOMER, EMPLOYEE, HEAD
    }
}
