package com.carrepairshop.api.application.uc.user.password.reset

import com.carrepairshop.api.application.port.in.UpdateUserPasswordPort
import com.carrepairshop.api.application.port.out.FindUserByEmailPort
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import spock.lang.Unroll

import javax.persistence.EntityNotFoundException

import static com.carrepairshop.api.application.domain.DomainUtils.stubUser
import static com.carrepairshop.api.application.domain.DomainUtils.stubUserPrinciple
import static com.carrepairshop.api.application.domain.User.Role.CUSTOMER
import static com.carrepairshop.api.application.domain.User.Role.EMPLOYEE
import static com.carrepairshop.api.application.domain.User.Role.HEAD
import static com.carrepairshop.api.application.uc.user.password.reset.ResetUserPasswordUC.ResetUserPasswordCommand
import static org.apache.commons.lang3.RandomStringUtils.random

class ResetUserPasswordUCSpec extends Specification {

    private FindUserByEmailPort findUserByEmailPort
    private UpdateUserPasswordPort updateUserPasswordPort
    private PasswordEncoder passwordEncoder
    private ResetUserPasswordUC uc

    def setup() {
        findUserByEmailPort = Mock(FindUserByEmailPort)
        updateUserPasswordPort = Mock(UpdateUserPasswordPort)
        passwordEncoder = Mock(PasswordEncoder)
        uc = new ResetUserPasswordService(findUserByEmailPort, updateUserPasswordPort, passwordEncoder)
    }

    @Unroll
    def "should reset password for '#CMD_EMAIL' email belongs to '#EMAIL_ROLE' role when invoker role is '#INVOKER_ROLE'"() {
        given:
            def command = ResetUserPasswordCommand.builder()
                    .email(CMD_EMAIL)
                    .build()
            def userPrincipal = stubUserPrinciple(INVOKER_ROLE)
            def expectedUser = stubUser(EMAIL_ROLE)

        and:
            1 * findUserByEmailPort.findUserByEmail(!null) >> Optional.of(expectedUser)
            1 * passwordEncoder.encode(command.email) >> CMD_EMAIL
            1 * updateUserPasswordPort.updateUserPasswordByEmail(CMD_EMAIL, CMD_EMAIL)
            0 * _

        expect:
            uc.resetUserPassword(command, userPrincipal)

        where:
            CMD_EMAIL        | EMAIL_ROLE | INVOKER_ROLE
            "vc@example.com" | CUSTOMER   | EMPLOYEE
            "ab@example.com" | CUSTOMER   | HEAD
            "ac@example.com" | EMPLOYEE   | HEAD
    }

    @Unroll
    def "should not reset password when email '#CMD_EMAIL' not exists"() {
        given:
            def command = ResetUserPasswordCommand.builder()
                    .email(CMD_EMAIL)
                    .build()
            def userPrincipal = stubUserPrinciple()

        and:
            1 * findUserByEmailPort.findUserByEmail(!null) >> Optional.empty()
            0 * _

        when:
            uc.resetUserPassword(command, userPrincipal)

        then:
            def exception = thrown(EntityNotFoundException)
            exception.getMessage() == "email '" + CMD_EMAIL + "' not exists."

        where:
            CMD_EMAIL = "abc@example.com"
    }

    @Unroll
    def "should not reset password when email belongs to '#EMAIL_ROLE' role and invoker role is '#INVOKER_ROLE'"() {
        given:
            def command = ResetUserPasswordCommand.builder()
                    .email(random(10))
                    .build()
            def userPrincipal = stubUserPrinciple(INVOKER_ROLE)
            def expectedUser = stubUser(EMAIL_ROLE)

        and:
            1 * findUserByEmailPort.findUserByEmail(!null) >> Optional.of(expectedUser)
            0 * _

        when:
            uc.resetUserPassword(command, userPrincipal)

        then:
            def exception = thrown(AccessDeniedException)
            exception.getMessage() == "Action forbidden, check your permissions."

        where:
            EMAIL_ROLE | INVOKER_ROLE
            CUSTOMER   | CUSTOMER
            EMPLOYEE   | CUSTOMER
            HEAD       | CUSTOMER
            EMPLOYEE   | EMPLOYEE
            HEAD       | EMPLOYEE
    }

}