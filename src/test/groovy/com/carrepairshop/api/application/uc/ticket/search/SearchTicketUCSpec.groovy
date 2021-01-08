package com.carrepairshop.api.application.uc.ticket.search


import com.carrepairshop.api.application.port.out.FindTicketsByFullTextPort
import spock.lang.Specification

class SearchTicketUCSpec extends Specification {

    private FindTicketsByFullTextPort findTicketsByFullTextPort
    private SearchTicketUC uc

    def setup() {
        findTicketsByFullTextPort = Mock(FindTicketsByFullTextPort)
        uc = new SearchTicketService(findTicketsByFullTextPort)
    }


}