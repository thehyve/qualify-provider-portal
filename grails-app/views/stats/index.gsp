<h1>Statistics</h1>

<g:if test="${webservices}">
  <ul>
    <g:each in="${webservices}" var="webservice">
      <li>
        <h2>${webservice.name}</h2>

        <div id="statistics-${webservice.id}" class="statistics"></div>
        <asset:script type="text/javascript">
          $.plot( '#statistics-${webservice.id}', ${flotAllData(webservice.allStats)}, { xaxis: { mode: 'time', timeformat: '%Y/%m' } } );
        </asset:script>
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
