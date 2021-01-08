package com.carrepairshop.api

import com.carrepairshop.api.application.uc.ticket.create.CreateTicketUC
import com.carrepairshop.api.application.uc.ticket.edit.EditTicketUC
import com.carrepairshop.api.application.uc.ticket.get.GetTicketUC
import com.carrepairshop.api.application.uc.ticket.search.SearchTicketUC
import com.carrepairshop.api.application.uc.user.create.CreateUserUC
import com.carrepairshop.api.application.uc.user.get.GetUserUC
import com.carrepairshop.api.application.uc.user.login.LoginUserUC
import com.carrepairshop.api.application.uc.user.password.change.ChangeUserPasswordUC
import com.carrepairshop.api.application.uc.user.password.reset.ResetUserPasswordUC
import com.carrepairshop.api.application.uc.user.register.RegisterUserUC
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest
class CarRepairShopApiSpec extends Specification {

    @Autowired
    private ApplicationContext applicationContext

    @Unroll
    def "should register '#BEAN_NAME' spring bean when application context is loaded"() {
        expect:
            applicationContext.getBean(BEAN_NAME, UC_NAME) != null

        where:
            BEAN_NAME                   | UC_NAME
            "getUserService"            | GetUserUC
            "createUserService"         | CreateUserUC
            "registerUserService"       | RegisterUserUC
            "loginUserService"          | LoginUserUC
            "changeUserPasswordService" | ChangeUserPasswordUC
            "resetUserPasswordService"  | ResetUserPasswordUC
            "createTicketService"       | CreateTicketUC
            "getTicketService"          | GetTicketUC
            "searchTicketService"       | SearchTicketUC
            "editTicketService"         | EditTicketUC
    }
}