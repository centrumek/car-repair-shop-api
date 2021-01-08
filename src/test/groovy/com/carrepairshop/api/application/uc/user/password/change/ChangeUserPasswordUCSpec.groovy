package com.carrepairshop.api.application.uc.user.password.change

import com.carrepairshop.api.application.port.in.UpdateUserPasswordPort
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import static com.carrepairshop.api.application.domain.DomainUtils.stubUserPrinciple
import static com.carrepairshop.api.application.uc.user.password.change.ChangeUserPasswordUC.ChangeUserPasswordCommand

class ChangeUserPasswordUCSpec extends Specification {

    private UpdateUserPasswordPort updateUserPasswordPort
    private PasswordEncoder passwordEncoder
    private ChangeUserPasswordUC uc

    def setup() {
        updateUserPasswordPort = Mock(UpdateUserPasswordPort)
        passwordEncoder = Mock(PasswordEncoder)
        uc = new ChangeUserPasswordService(updateUserPasswordPort, passwordEncoder)
    }

    def "should change user password"() {
        given:
            def userPrincipal = stubUserPrinciple()
            def command = ChangeUserPasswordCommand.builder()
                    .password(CMD_PASSWORD)
                    .build()
        and:
            1 * passwordEncoder.encode(command.getPassword()) >> ENCODED_PASSWORD
            1 * updateUserPasswordPort.updateUserPasswordByEmail(ENCODED_PASSWORD, userPrincipal.username)
            0 * _

        expect:
            uc.changeUserPassword(command, userPrincipal)

        where:
            CMD_PASSWORD | ENCODED_PASSWORD
            "abcd123"    | "%^H&*HJO"
    }

}