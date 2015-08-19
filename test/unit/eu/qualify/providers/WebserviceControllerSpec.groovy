package eu.qualify.providers



import grails.test.mixin.*
import spock.lang.*

@TestFor(WebserviceController)
@Mock(Webservice)
class WebserviceControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.webserviceInstanceList
            model.webserviceInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.webserviceInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            def webservice = new Webservice()
            webservice.validate()
            controller.save(webservice)

        then:"The create view is rendered again with the correct model"
            model.webserviceInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            webservice = new Webservice(params)

            controller.save(webservice)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/webservice/show/1'
            controller.flash.message != null
            Webservice.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def webservice = new Webservice(params)
            controller.show(webservice)

        then:"A model is populated containing the domain instance"
            model.webserviceInstance == webservice
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def webservice = new Webservice(params)
            controller.edit(webservice)

        then:"A model is populated containing the domain instance"
            model.webserviceInstance == webservice
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/webservice/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def webservice = new Webservice()
            webservice.validate()
            controller.update(webservice)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.webserviceInstance == webservice

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            webservice = new Webservice(params).save(flush: true)
            controller.update(webservice)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/webservice/show/$webservice.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/webservice/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def webservice = new Webservice(params).save(flush: true)

        then:"It exists"
            Webservice.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(webservice)

        then:"The instance is deleted"
            Webservice.count() == 0
            response.redirectedUrl == '/webservice/index'
            flash.message != null
    }
}
