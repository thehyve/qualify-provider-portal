package eu.qualify.providers


class Webservice {
    transient def threescaleService
    transient def webserviceService

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
        threescaleService.getMetrics(this)
    }()

    /**
     * Returns statistics for all known metrics
     * @param since
     * @param period
     * @param granularity
     * @return
     */
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
        threescaleService.getStats(this, metricSystemName, since, period, granularity)
    }

    /**
     * Checks whether the given user is allowed to access this webservice
     * @param user
     * @return
     */
    def canAccess(User user) {
        webserviceService.canAccess(this, user)
    }
}
