package eu.qualify.providers

import groovy.json.JsonBuilder
import grails.converters.JSON

class StatsController {
    def springSecurityService
    def webserviceService

    private static final timeFormatForPeriod = [
            year: "%b %Y",
            month: "%b %d",
            week: "%b %d",
            day: "%b %d %H:%M"
    ]

    private static final granularityForPeriod = [
            year: "month",
            month: "day",
            week: "day",
            day: "hour"
    ]

    // Shows statistics about the webservices the current user has access to
    def index() {
        def webservices = webserviceService.getWebservicesFor(springSecurityService.currentUser)

        def since = params.since ?: "2014-12-01"
        def period = params.period ?: "month"
        [   webservices: webservices,
            since: since,
            period: period,
        ]
    }

    def loadStats() {
        // Check if the user is allowed to access this webservice data
        def webservice = checkWebservice(params.service_id)
        if(!webservice)
            return

        // Fetch statistics based on the url parameters
        def since = params.since ?: "2014-12-01"
        def period = params.period ?: "month"
        def granularity = granularityForPeriod[period]
        def timeFormat = timeFormatForPeriod[period]

        def stats = webservice.getAllStats(since, period, granularity)

        // Render output
        def output = [ statistics: createFlotAllData(stats), timeFormat: timeFormat ]
        render output as JSON
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

        flotData
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

}
