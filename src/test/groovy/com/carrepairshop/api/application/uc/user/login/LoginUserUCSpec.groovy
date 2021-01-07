package com.carrepairshop.api.application.uc.user.login

import com.carrepairshop.api.application.port.out.FindUserByEmailPort
import spock.lang.Specification
import spock.lang.Unroll

import javax.persistence.EntityNotFoundException

import static com.carrepairshop.api.application.domain.DomainUtils.stubUser
import static com.carrepairshop.api.application.domain.DomainUtils.stubUserPrinciple

class LoginUserUCSpec extends Specification {

    private FindUserByEmailPort findUserByEmailPort
    private LoginUserUC uc

    def setup() {
        findUserByEmailPort = Mock(FindUserByEmailPort)
        uc = new LoginUserService(findUserByEmailPort)
    }

    @Unroll
    def "should log in user when email '#EMAIL' found in database."() {
        given:
            def userPrincipal = stubUserPrinciple(EMAIL)
            def expectedUser = stubUser()

        and:
            1 * findUserByEmailPort.findUserByEmail(!null) >> Optional.of(expectedUser)

        when:
            def actualUser = uc.loginUser(userPrincipal)

        then:
            actualUser == expectedUser

        where:
            EMAIL = 'abc@abc.com'
    }

    @Unroll
    def "should not log in user when email '#EMAIL' not found in database."() {
        given:
            def userPrincipal = stubUserPrinciple(EMAIL)

        and:
            1 * findUserByEmailPort.findUserByEmail(!null) >> Optional.empty()

        when:
            uc.loginUser(userPrincipal)

        then:
            def exception = thrown(EntityNotFoundException)
            exception.getMessage() == "email '" + EMAIL + "' not exists."

        where:
            EMAIL = 'abc@abc.com'
    }
}