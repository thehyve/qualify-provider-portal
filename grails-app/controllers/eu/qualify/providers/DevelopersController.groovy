package eu.qualify.providers

import groovy.json.JsonBuilder
import grails.converters.JSON

class DevelopersController {
    def springSecurityService
    def webserviceService
    def threescaleService

    static allowedMethods = [updateApplication: "PUT"]

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
            applications[idx].urls = [
                detailsLink: createLink(action: "applicationInfo", params: [ account_id: el.user_account_id, application_id: el.id ]),

                acceptLink: createLink(action: "acceptApplication", params: [ account_id: el.user_account_id, application_id: el.id ]),
                suspendLink: createLink(action: "suspendApplication", params: [ account_id: el.user_account_id, application_id: el.id ]),
                resumeLink: createLink(action: "resumeApplication", params: [ account_id: el.user_account_id, application_id: el.id ]),
            ]
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
        def applicationInfo = checkApplication(accountId, applicationId)
        if(!applicationInfo)
            return

        // Also fetch account information for this account
        applicationInfo.account = threescaleService.getAccountInfo(accountId)

        // Determine the service plan for the current account
        applicationInfo.service_plan = applicationInfo.account.plans.find { it.service_id == applicationInfo.service_id && it.type == "service_plan" }

        render applicationInfo as JSON
    }

    def acceptApplication() {
        def accountId = params.long('account_id')
        def applicationId = params.long('application_id')

        if( !checkApplication(accountId, applicationId) )
            return

        if( !threescaleService.acceptApplication(accountId, applicationId) )
            render status: 500, text: "Could not succesfully accept application"
        else
            render status: 200, text: "Application accepted"
    }

    def suspendApplication() {
        def accountId = params.long('account_id')
        def applicationId = params.long('application_id')

        if( !checkApplication(accountId, applicationId) )
            return

        if( !threescaleService.suspendApplication(accountId, applicationId) )
            render status: 500, text: "Could not succesfully suspend application"
        else
            render status: 200, text: "Application accepted"
    }

    def resumeApplication() {
        def accountId = params.long('account_id')
        def applicationId = params.long('application_id')

        if( !checkApplication(accountId, applicationId) )
            return

        if( !threescaleService.resumeApplication(accountId, applicationId) )
            render status: 500, text: "Could not succesfully resume application"
        else
            render status: 200, text: "Application resumed"
    }

    /**
     * Checks whether the user is allowed to access the given webservice
     * @param serviceId
     * @return Webservice object or null if the user is not allowed to access the webservice
     */
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

    /**
     * Checks whether the user is allowed to access the given application
     * @param accountId
     * @param applicationId
     * @return  Map with application info or null if the user is not allowed to access this application
     */
    protected def checkApplication(accountId, applicationId) {
        // Make sure the user logged in is authorized to access this application
        def applicationInfo = threescaleService.getApplicationInfo(accountId, applicationId)

        if( !applicationInfo ) {
            render status: 404, text: "Application not found"
            return null
        }

        // Check if the user is allowed to access this webservice data
        if(!checkWebservice(applicationInfo.service_id))
            return null

        return applicationInfo
    }


}
