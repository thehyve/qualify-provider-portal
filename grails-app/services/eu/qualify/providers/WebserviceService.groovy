package eu.qualify.providers

import grails.transaction.Transactional

@Transactional
class WebserviceService {
    @Lazy
    def adminRole = {
        Role.findByAuthority("ROLE_ADMIN")
    }()

    /**
     * Retrieves webservices the current user has access to
     * Administrators have access to all webservices
     */
    def getWebservicesFor(User user) {
        if (adminRole.id in user.authorities*.id) {
            Webservice.list()
        } else {
            user.webservices
        }
    }
}
