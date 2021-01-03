package com.carrepairshop.api.application.port.in;

public interface UpdateUserPasswordPort {
    void updateUserPasswordByEmail(final String password, final String email);
}
