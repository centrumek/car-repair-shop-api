package com.carrepairshop.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
class CarRepairShopApiSpec extends Specification {

    @Autowired
    private ApplicationContext applicationContext

    def "when context is loaded then application context exists"() {
        expect:
            applicationContext != null
    }
}