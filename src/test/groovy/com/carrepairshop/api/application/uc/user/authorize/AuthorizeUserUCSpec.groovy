package com.carrepairshop.api.application.uc.user.authorize

import com.carrepairshop.api.application.domain.DomainUtils
import com.carrepairshop.api.application.port.out.FindUserByEmailPort
import org.springframework.security.core.userdetails.UsernameNotFoundException
import spock.lang.Specification
import spock.lang.Unroll

class AuthorizeUserUCSpec extends Specification {

    private FindUserByEmailPort findUserByEmailPort
    private AuthorizeUserUC uc

    def setup() {
        findUserByEmailPort = Mock(FindUserByEmailPort)
        uc = new AuthorizeUserService(findUserByEmailPort)
    }

    @Unroll
    "should authorize user when email '#EMAIL' exists in db"() {
        given:
            def expectedUserPrincipal = DomainUtils.stubUserPrinciple(EMAIL)

        and:
            1 * findUserByEmailPort.findUserPrincipalByEmail(EMAIL) >> Optional.of(expectedUserPrincipal)

        when:
            def actualUserPrincipal = uc.loadUserByUsername(EMAIL)

        then:
            actualUserPrincipal == expectedUserPrincipal

        where:
            EMAIL << ["abc@example.com"]
    }

    @Unroll
    "should not authorize user when email '#EMAIL' not exists in db"() {
        given:
            1 * findUserByEmailPort.findUserPrincipalByEmail(EMAIL) >> Optional.empty()

        when:
            uc.loadUserByUsername(EMAIL)

        then:
            def exception = thrown(UsernameNotFoundException)
            exception.getMessage() == EMAIL

        where:
            EMAIL << ["cba@example.com"]
    }
}