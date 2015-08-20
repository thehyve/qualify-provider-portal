package eu.qualify.providers

import grails.plugins.rest.client.RestBuilder
import org.springframework.http.HttpStatus

class Webservice {
    transient def grailsApplication

    String name
    String threescale_id

    static constraints = {
    }

    static belongsTo = User
    static hasMany = [users: User]

    /**
     * Contains a list of metrics available for this webservice
     * Each metrics properties is defined by the 3scale API. It contains
     * a.o. 'id', 'name' and 'unit'
     * @see https://support.3scale.net/reference/active-docs
     */
    @Lazy
    def metrics = {
        def uri = "https://" + grailsApplication.config.threescale.admin_domain + "/admin/api/services/" + threescale_id + "/metrics.json"
        uri += "?provider_key=" + grailsApplication.config.threescale.provider_id

        def rest = new RestBuilder()
        def resp = rest.get(uri)

        if (resp.statusCode != HttpStatus.OK) {
            log.warn("No metrics could be retrieved for webservice " + name + " (code " + resp.statusCode + "): " + resp.json)
            return []
        }

        resp.json.metrics*.metric
    }()

    def getAllStats(def since, def period, def granularity) {
        def statistics = []
        metrics.each {
            statistics << getStats(it.system_name, since, period, granularity)
        }
        statistics
    }

    /**
     * Returns the statistics for this webservice
     * @param metric
     */
    def getStats(def metricSystemName, def since, def period, def granularity) {
        def uri = "https://${grailsApplication.config.threescale.admin_domain}/stats/services/${threescale_id}/usage.json" +
                "?provider_key=${grailsApplication.config.threescale.provider_id}" +
                "&metric_name=${metricSystemName}" +
                "&since=${since}" +
                "&period=${period}" +
                "&granularity=${granularity}" +
                "&skip_change=true"

        def rest = new RestBuilder()
        def resp = rest.get(uri)

        if (resp.statusCode != HttpStatus.OK) {
            log.warn("No statistics could be retrieved for webservice " + name + " (code " + resp.statusCode + "): " + resp.json)
            return [:]
        }

        resp.json

    }
}
