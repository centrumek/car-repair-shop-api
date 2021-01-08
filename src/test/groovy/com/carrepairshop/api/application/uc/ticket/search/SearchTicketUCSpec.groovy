package com.carrepairshop.api.application.uc.ticket.search

import com.carrepairshop.api.application.port.out.FindTicketsByFullTextPort
import org.springframework.data.domain.PageRequest
import spock.lang.Specification
import spock.lang.Unroll

import static com.carrepairshop.api.application.domain.DomainUtils.stubTicketsPage

class SearchTicketUCSpec extends Specification {

    private FindTicketsByFullTextPort findTicketsByFullTextPort
    private SearchTicketUC uc

    def setup() {
        findTicketsByFullTextPort = Mock(FindTicketsByFullTextPort)
        uc = new SearchTicketService(findTicketsByFullTextPort)
    }

    @Unroll
    def "should get tickets page contains '#TEXT' keyword"() {
        given:
            def expectedPage = stubTicketsPage()

        and:
            1 * findTicketsByFullTextPort.findTicketsByFullText(!null, !null) >> expectedPage
            0 * _

        expect:
            uc.searchTickets("someText", PageRequest.unpaged())

        where:
            TEXT << ["someText"]
    }
}