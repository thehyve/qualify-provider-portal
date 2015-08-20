import eu.qualify.providers.*

class BootStrap {

    def init = {servletContext ->
        println "Initializing administrators (if necessary)..."
        initAdministrators(servletContext)
    }

    def initAdministrators = {
        // set up roles if they do not exist

        // Providers are allowed to view their own statistics
        def providerRole = Role.findByAuthority('ROLE_PROVIDER') ?: new Role(authority: 'ROLE_PROVIDER').save(flush: true, failOnError: true)

        // Administrators are allowed to do anything
        def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(flush: true, failOnError: true)

        def ADMINUSERNAME = 'admin'
        def ADMINPASSWORD = 'qualify-provider-admin'
        def ADMINEMAIL = 'administrator@qualify-fp7.eu'

        if (!User.findByUsername(ADMINUSERNAME)) {
            // create user instance. This is done within a transaction,
            // to prevent a 'Hibernate session not found or not bound to thread' exception.
            // See http://stackoverflow.com/questions/5066880/grails-i-hate-and-simply-cant-unerstand-no-hibernate-session-bound-to-curre
            User.withTransaction {
                def user = new User(
                        username: ADMINUSERNAME,
                        password: ADMINPASSWORD,
                        email: ADMINEMAIL,
                        enabled: true
                ).save(flush: true, failOnError: true)

                // Store roles for this user
                UserRole.create(user, adminRole, true)
            }
        }
    }

    def destroy = {
    }
}
