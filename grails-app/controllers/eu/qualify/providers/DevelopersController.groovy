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

    /**
     * Loads data for all applications for a given webservice
     * @return
     */
    def loadApplications() {
        // Check if the user is allowed to access this webservice data
        def webservice = checkWebservice(params.service_id)
        if(!webservice)
            return

        // Fetch list of applications
        def applications = threescaleService.getApplications(webservice)

        // Add detailUrl to every application
        applications.eachWithIndex { el, idx  ->
            applications[idx].details_url = createLink(action: "applicationInfo", params: [ account_id: el.user_account_id, application_id: el.id ])
        }

        render applications as JSON
    }

    /**
     * Loads more information (account name, application plan etc.) for the given application
     */
    def applicationInfo() {
        def accountId = params.long('account_id')
        def applicationId = params.long('application_id')

        // We still have to check whether the given application_id belongs
        // to the specified webservice. If not, raise an error as well
        def applicationInfo = threescaleService.getApplicationInfo(accountId, applicationId)

        if( !applicationInfo ) {
            render status: 404, text: "Application not found"
            return
        }

        // Check if the user is allowed to access this webservice data
        if(!checkWebservice(applicationInfo.service_id))
            return

        // Also fetch account information for this account
        applicationInfo.account = threescaleService.getAccountInfo(accountId)

        // Determine the service plan for the current account
        applicationInfo.service_plan = applicationInfo.account.plans.find { it.service_id == applicationInfo.service_id && it.type == "service_plan" }

        render applicationInfo as JSON

    }

    protected def checkWebservice(serviceId) {
        def webservice = Webservice.findByThreescale_id(serviceId.toString())

        if( !webservice ) {
            render status: 404, text: "Webservice not found"
            return null
        }

        if( !webservice.canAccess(springSecurityService.currentUser) ) {
            render status: 403, text: "Forbidden"
            return null
        }

        return webservice
    }

}
