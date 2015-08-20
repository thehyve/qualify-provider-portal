package eu.qualify.providers


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class WebserviceController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Webservice.list(params), model: [webserviceInstanceCount: Webservice.count()]
    }

    def show(Webservice webserviceInstance) {
        respond webserviceInstance
    }

    def create() {
        def webserviceInstance = new Webservice(params)
        respond webserviceInstance, model: [webserviceInstance: webserviceInstance, users: User.list()]
    }

    @Transactional
    def save(Webservice webserviceInstance) {
        if (webserviceInstance == null) {
            notFound()
            return
        }

        if (webserviceInstance.hasErrors()) {
            respond webserviceInstance.errors, [view: 'create', model: [webserviceInstance: webserviceInstance, users: User.list()]]
            return
        }

        // Store the newly selected associatedUsers as well
        params.associatedUsers.each {userId ->
            webserviceInstance.addToUsers(User.get(userId.toLong()))
        }

        webserviceInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'webservice.label', default: 'Webservice'), webserviceInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' {respond webserviceInstance, [status: CREATED]}
        }
    }

    def edit(Webservice webserviceInstance) {
        respond webserviceInstance, model: [webserviceInstance: webserviceInstance, users: User.list()]
    }

    @Transactional
    def update(Webservice webserviceInstance) {
        if (webserviceInstance == null) {
            notFound()
            return
        }

        if (webserviceInstance.hasErrors()) {
            respond webserviceInstance.errors, [view: 'edit', model: [webserviceInstance: webserviceInstance, users: User.list()]]
            return
        }

        // Store the newly selected associatedUsers as well
        ([] + webserviceInstance.users).each {user ->
            webserviceInstance.removeFromUsers(user);
        }

        params.associatedUsers.each {userId ->
            webserviceInstance.addToUsers(User.get(userId.toLong()))
        }

        webserviceInstance.save flush: true


        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Webservice.label', default: 'Webservice'), webserviceInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' {respond webserviceInstance, [status: OK]}
        }
    }

    @Transactional
    def delete(Webservice webserviceInstance) {

        if (webserviceInstance == null) {
            notFound()
            return
        }

        webserviceInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Webservice.label', default: 'Webservice'), webserviceInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' {render status: NO_CONTENT}
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'webservice.label', default: 'Webservice'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' {render status: NOT_FOUND}
        }
    }
}
