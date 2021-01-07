package com.carrepairshop.api.application.uc.user.register


import com.carrepairshop.api.application.port.in.InsertUserPort
import com.carrepairshop.api.application.port.out.FindUserByEmailPort
import com.carrepairshop.api.application.uc.user.register.RegisterUserUC.RegisterUserCommand
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import spock.lang.Unroll

import javax.persistence.EntityExistsException

import static com.carrepairshop.api.application.domain.DomainUtils.stubUser
import static com.carrepairshop.api.application.domain.User.Role.CUSTOMER

class RegisterUserUCSpec extends Specification {

    private InsertUserPort insertUserPort
    private FindUserByEmailPort findUserByEmailPort
    private PasswordEncoder passwordEncoder
    private RegisterUserUC uc

    def setup() {
        insertUserPort = Mock(InsertUserPort)
        findUserByEmailPort = Mock(FindUserByEmailPort)
        passwordEncoder = Mock(PasswordEncoder)
        uc = new RegisterUserService(insertUserPort, findUserByEmailPort, passwordEncoder)
    }

    def "should register 'CUSTOMER' user"() {
        given:
            def command = RegisterUserCommand.builder()
                    .email(CMD_EMAIL)
                    .password(CMD_PASSWORD)
                    .build()

            def expectedUser = stubUser(CUSTOMER)

        and:
            1 * findUserByEmailPort.findUserByEmail(!null) >> Optional.empty()
            1 * passwordEncoder.encode(command.password) >> CMD_PASSWORD
            1 * insertUserPort.insertUser(command, CUSTOMER) >> expectedUser
            0 * _

        when:
            def actualUser = uc.registerUser(command)

        then:
            actualUser == expectedUser

        where:
            CMD_EMAIL = "abc@example.com"
            CMD_PASSWORD = "abcd123456"
    }

    @Unroll
    def "should not register 'CUSTOMER' user when email '#CMD_EMAIL' already exists"() {
        given:
            def command = RegisterUserCommand.builder()
                    .email(CMD_EMAIL)
                    .build()

        and:
            1 * findUserByEmailPort.findUserByEmail(!null) >> Optional.of(stubUser())
            0 * _

        when:
            uc.registerUser(command)

        then:
            def exception = thrown(EntityExistsException)
            exception.getMessage() == "email '" + CMD_EMAIL + "' already exists."

        where:
            CMD_EMAIL = "abc@example.com"
    }
}