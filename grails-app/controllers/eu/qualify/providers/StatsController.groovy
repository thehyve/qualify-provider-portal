package eu.qualify.providers

import groovy.json.JsonBuilder

class StatsController {
    def springSecurityService

    // Shows statistics about the webservices the current user has access to
    def index() {
        def user = springSecurityService.currentUser

        // Retrieve webservices the current user has access to
        // Administrators have access to all webservices
        def adminRole = Role.findByAuthority("ROLE_ADMIN")
        def webservices
        if (adminRole in user.authorities) {
            webservices = Webservice.findAll()
        } else {
            webservices = user.webservices
        }

        [webservices: webservices, flotData: createFlotData, flotAllData: createFlotAllData]
    }

    def createFlotData = {stats ->
        def flotData = []

        // We will have to find a date to associate with each value.
        Date statTime = Date.parse("yyyy-MM-dd'T'HH:mm:ss'Z'", stats.period.since)

        use(groovy.time.TimeCategory) {
            def timeIncrement

            switch (stats.period.granularity) {
            case "hour":
                timeIncrement = 1.hour
                break
            case "day":
                timeIncrement = 1.day
                break
            case "month":
                timeIncrement = 1.month
                break;
            default:
                log.warn("Unsupported granularity in 3scale data: " + stats.period.granularity)
                return ""
            }

            // Loop through the values we got, and assign a date.
            stats.values.each {
                flotData << [statTime.time, it]

                // Increment the timestamp
                statTime += timeIncrement
            }
        }

        return flotData
    }

    def createFlotAllData = {stats ->
        def flotData = []

        stats.each {stat ->
            flotData << [label: stat.metric.name, data: createFlotData(stat)]
        }

        return new JsonBuilder(flotData).toPrettyString()
    }

}
