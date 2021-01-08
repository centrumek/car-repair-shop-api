package com.carrepairshop.api.application.uc.user.create

import com.carrepairshop.api.application.port.in.InsertUserPort
import com.carrepairshop.api.application.port.out.FindUserByEmailPort
import com.carrepairshop.api.application.uc.user.create.CreateUserUC.CreateUserCommand
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import spock.lang.Unroll

import javax.persistence.EntityExistsException

import static com.carrepairshop.api.application.domain.DomainUtils.stubUser
import static com.carrepairshop.api.application.domain.DomainUtils.stubUserPrinciple
import static com.carrepairshop.api.application.domain.User.Role.CUSTOMER
import static com.carrepairshop.api.application.domain.User.Role.EMPLOYEE
import static com.carrepairshop.api.application.domain.User.Role.HEAD
import static org.apache.commons.lang3.RandomStringUtils.random
import static org.apache.commons.lang3.RandomStringUtils.randomAscii
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric

class CreateUserUCSpec extends Specification {

    private InsertUserPort insertUserPort
    private FindUserByEmailPort findUserByEmailPort
    private PasswordEncoder passwordEncoder
    private CreateUserUC uc

    def setup() {
        insertUserPort = Mock(InsertUserPort)
        findUserByEmailPort = Mock(FindUserByEmailPort)
        passwordEncoder = Mock(PasswordEncoder)
        uc = new CreateUserService(insertUserPort, findUserByEmailPort, passwordEncoder)
    }

    @Unroll
    def "should create '#CMD_ROLE' user when invoker role is '#INVOKER_ROLE'"() {
        given:
            def command = CreateUserCommand.builder()
                    .email(CMD_EMAIL)
                    .password(CMD_EMAIL)
                    .role(CMD_ROLE.name())
                    .firstName(randomAscii(10))
                    .lastName(randomAscii(10))
                    .mobilePhone(randomNumeric(10))
                    .build()
            def userPrincipal = stubUserPrinciple(INVOKER_ROLE)
            def expectedUser = stubUser()

        and:
            1 * findUserByEmailPort.findUserByEmail(!null) >> Optional.empty()
            1 * passwordEncoder.encode(command.email) >> CMD_EMAIL
            1 * insertUserPort.insertUser(command) >> expectedUser
            0 * _

        when:
            def actualUser = uc.createUser(command, userPrincipal)

        then:
            actualUser == expectedUser

        where:
            CMD_EMAIL        | CMD_ROLE | INVOKER_ROLE
            "vc@example.com" | CUSTOMER | EMPLOYEE
            "ab@example.com" | CUSTOMER | HEAD
            "ac@example.com" | EMPLOYEE | HEAD
    }

    @Unroll
    def "should not create any user when email '#CMD_EMAIL' already exists"() {
        given:
            def command = CreateUserCommand.builder()
                    .email(CMD_EMAIL)
                    .build()

        and:
            1 * findUserByEmailPort.findUserByEmail(!null) >> Optional.of(stubUser())
            0 * _

        when:
            uc.createUser(command, stubUserPrinciple())

        then:
            def exception = thrown(EntityExistsException)
            exception.getMessage() == "email '" + CMD_EMAIL + "' already exists."

        where:
            CMD_EMAIL = "abc@example.com"
    }

    @Unroll
    def "should not create '#CMD_ROLE' user when invoker role is '#INVOKER_ROLE'"() {
        given:
            def command = CreateUserCommand.builder()
                    .email(random(10))
                    .role(CMD_ROLE.name())
                    .build()

        and:
            1 * findUserByEmailPort.findUserByEmail(!null) >> Optional.empty()
            0 * _

        when:
            uc.createUser(command, stubUserPrinciple(INVOKER_ROLE))

        then:
            def exception = thrown(AccessDeniedException)
            exception.getMessage() == "Action forbidden, check your permissions."

        where:
            CMD_ROLE | INVOKER_ROLE
            CUSTOMER | CUSTOMER
            EMPLOYEE | CUSTOMER
            HEAD     | CUSTOMER
            EMPLOYEE | EMPLOYEE
            HEAD     | EMPLOYEE
            HEAD     | HEAD
    }
}