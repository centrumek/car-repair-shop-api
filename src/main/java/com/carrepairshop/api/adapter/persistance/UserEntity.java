package com.carrepairshop.api.adapter.persistance;

import com.carrepairshop.api.application.domain.User.Role;
import com.carrepairshop.api.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.search.annotations.Field;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "user")
@Data
@Builder
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
class UserEntity extends BaseEntity {

    @Field
    @Column(name = "first_name")
    String firstName;

    @Field
    @Column(name = "last_name")
    String lastName;

    @Field
    @Column(name = "email")
    String email;

    @Enumerated(value = STRING)
    @Column(name = "role")
    Role role;

    @Column(name = "password")
    String password;

    @Field
    @Column(name = "mobile_phone")
    String mobilePhone;
}
