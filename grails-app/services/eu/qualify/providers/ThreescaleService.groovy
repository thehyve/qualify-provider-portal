package eu.qualify.providers

import grails.transaction.Transactional
import grails.plugins.rest.client.RestBuilder
import org.springframework.http.HttpStatus
import org.apache.http.client.utils.URIBuilder

@Transactional
class ThreescaleService {
    def grailsApplication

    /**
     * Contains a list of metrics available for this webservice
     * Each metrics properties is defined by the 3scale API. It contains
     * a.o. 'id', 'name' and 'unit'
     * @see https://support.3scale.net/reference/active-docs
     */
    def getMetrics(Webservice webservice) {
        def data = call(
            builder().setPath( "/admin/api/services/" + webservice.threescale_id + "/metrics.json" )
        )

        if( data ) {
            data.json.metrics*.metric
        } else {
            []
        }
    }

    /**
     * Returns statistics for the given webservice metric in the given period and granularity
     * @param webservice
     * @param metricSystemName
     * @param since             Startdate in YYYY-mm-dd format
     * @param period            Period description. Can be day, week, month, year
     * @param granularity       Granularity of the results
     * @see https://support.3scale.net/reference/active-docs
     */
    def getStats(Webservice webservice, String metricSystemName, String since, String period, String granularity) {
        def data = call( builder()
            .setPath( "/stats/services/" + webservice.threescale_id + "/usage.json" )
            .addParameter("metric_name", metricSystemName)
            .addParameter("since", since)
            .addParameter("period", period)
            .addParameter("granularity", granularity)
            .addParameter("skip_change", "true")
        )

        data ? data.json : [:]
    }

    /**
     * Returns a list of applications for the given webservice
     * @param webservice
     * @param per_page      Number of applications per page. Defaults to 100
     * @param page          Page number to return. Defaults to 1
     * @return
     */
    def getApplications(Webservice webservice, int per_page = 100, int page = 1) {
        // Using XML retrieval here as it returns more information per application
        def data = call( builder()
                .setPath( "/admin/api/applications.xml" )
                .addParameter("page", page.toString())
                .addParameter("per_page", per_page.toString())
                .addParameter("service_id", webservice.threescale_id)
        )

        data ? data.xml.children().collect { convertXmlToMap(it) } : []
    }

    /**
     * Returns information about the given application
     * @param applicationId
     */
    def getApplicationInfo(Long accountId, Long applicationId) {
        // Using XML retrieval here as it returns more information per application
        def data = call( builder()
                .setPath( "/admin/api/accounts/" + accountId + "/applications/" + applicationId + ".xml" )
        )

        data ? convertXmlToMap(data.xml) : [:]
    }

    /**
     * Returns information about the given account
     * @param accountId
     * @return
     */
    def getAccountInfo(Long accountId) {
        // Using XML retrieval here as it returns more information for an account
        def data = call( builder()
                .setPath( "/admin/api/accounts/" + accountId + ".xml" )
        )

        data ? convertXmlToMap(data.xml, [ "plans", "users" ]) : [:]
    }

    /**
     * Generic method to make calls to the 3scale API
     */
    def call(String uri) {
        log.debug( "Making request to " + uri )
        def rest = new RestBuilder()
        def resp = rest.get(uri)

        if (resp.statusCode != HttpStatus.OK) {
            log.warn("No data could be retrieved for call " + uri + " (code " + resp.statusCode + "): " + resp.json)
            return null
        }

        return resp
    }

    /**
     * Utility method to convert an XML element into a map
     * @param nodes
     * @return
     */
    protected def convertXmlToMap(nodes, convertToList = []) {
        nodes.children().collectEntries {
            if( it.childNodes() ) {
                // Convert some of the tag names to a list
                if( it.name() in convertToList ) {
                    [ it.name(), it.children().collect { convertXmlToMap( it, convertToList ) } ]
                } else {
                    [ it.name(), convertXmlToMap(it, convertToList) ]
                }
            } else {
                [ it.name(), it.text() ]
            }
        }
    }

    /**
     * Generic method to make calls to the 3scale API
     */
    def call(URIBuilder builder) { call(builder.toString()) }

    /**
     * Generic method to make calls to the 3scale API
     */
    protected URIBuilder builder() {
        def builder = new URIBuilder("https://" + grailsApplication.config.threescale.admin_domain)
        builder.addParameter( "provider_key", grailsApplication.config.threescale.provider_id)
    }


}
