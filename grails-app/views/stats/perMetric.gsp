<h1>Statistics</h1>

<g:if test="${webservices}">
  <ul>
    <g:each in="${webservices}" var="webservice">
      <li>
        <h2>${webservice.name}</h2>
        <g:each in="${webservice.allStats}" var="stat">
          <div id="statistics-${stat.metric.id}" class="statistics"></div>
          <asset:script type="text/javascript">
            $.plot( '#statistics-${stat.metric.id}', [ ${flotData(stat)} ], { xaxis: { mode: 'time', timeformat: '%Y/%m' } } );
          </asset:script>
        </g:each>
      </li>
    </g:each>
  </ul>
</g:if>
<g:else>
  <p>
    You don't have access to the statistics for any of the webservices. Please
    contact a system administrator.
  </p>
</g:else>
