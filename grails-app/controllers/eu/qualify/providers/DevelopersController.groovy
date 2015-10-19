package eu.qualify.providers

import groovy.json.JsonBuilder

class DevelopersController {
    def springSecurityService

    // Shows a list of developers for each webservice that the current user has access to
    def index() {
        def webservices = webserviceService.getWebservicesFor(springSecurityService.currentUser)

        [
            webservices: webservices,
        ]
    }

}
