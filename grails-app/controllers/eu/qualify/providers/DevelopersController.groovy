package eu.qualify.providers

import groovy.json.JsonBuilder
import grails.converters.JSON

class DevelopersController {
    def springSecurityService
    def webserviceService
    def threescaleService

    // Shows a list of developers for each webservice that the current user has access to
    def index() {
        def webservices = webserviceService.getWebservicesFor(springSecurityService.currentUser)

        [webservices: webservices]
    }

    def loadApplications() {
        def serviceId = params.service_id
        def webservice = Webservice.findByThreescale_id(serviceId.toString())

        if( !webservice ) {
            render status: 404, text: "Not found"
            return
        }

        if( !webservice.canAccess(springSecurityService.currentUser) ) {
            render status: 403, text: "Forbidden"
            return
        }

        render threescaleService.getApplications(webservice) as JSON
    }

}
