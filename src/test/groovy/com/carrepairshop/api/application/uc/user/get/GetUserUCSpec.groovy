package com.carrepairshop.api.application.uc.user.get


import com.carrepairshop.api.application.port.out.FindUsersAllPort
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import static com.carrepairshop.api.application.domain.DomainUtils.stubUsersPage

class GetUserUCSpec extends Specification {

    private FindUsersAllPort findUsersAllPort
    private GetUserUC uc

    def setup() {
        findUsersAllPort = Mock(FindUsersAllPort)
        uc = new GetUserService(findUsersAllPort)
    }

    def "should get users page"() {
        given:
            def expectedPage = stubUsersPage()

        and:
            1 * findUsersAllPort.findUsersAll(!null) >> expectedPage

        expect:
            expectedPage == uc.getUsers(PageRequest.unpaged())
    }

}