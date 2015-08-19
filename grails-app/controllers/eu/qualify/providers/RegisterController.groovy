package eu.qualify.providers

class RegisterController extends grails.plugin.springsecurity.ui.RegisterController {
    def index() {
        redirect( controller: "home" )
        return
    }
}
