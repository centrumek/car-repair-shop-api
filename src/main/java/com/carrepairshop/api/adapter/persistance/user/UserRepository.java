package com.carrepairshop.api.adapter.persistance.user;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.carrepairshop.api.configuration.jpa.CustomJpaRepository;

@Repository
interface UserRepository extends CustomJpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(final String email);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE UserEntity SET password = :password, updatedAt = CURRENT_TIMESTAMP WHERE email = :email")
    void updatePassword(@Param("password") final String password,
                        @Param("email") final String email);
}
