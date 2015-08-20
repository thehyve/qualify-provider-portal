<h1>Statistics</h1>

<g:set var="granularity" value="day" />

<div>
  <form action="index" class="form-inline">
    <input class="form-control" type="date" name="since" value="${since}" />
    <select class="form-control selectpicker" name="period" value="${period}">
      <option>day</option>
      <option>week</option>
      <option>month</option>
      <option>year</option>
    </select>
    <input type="submit" class="btn btn-primary" value="Update" />
  </form>
  <asset:script type="text/javascript">
    $('select[name=period]').val('${period}');
  </asset:script>
</div>

<g:if test="${webservices}">
  <ul>
    <g:each in="${webservices}" var="webservice">
      <li>
        <h2>${webservice.name}</h2>

        <div id="statistics-${webservice.id}" class="statistics"></div>
        <asset:script type="text/javascript">
          $.plot( '#statistics-${webservice.id}', ${flotAllData(webservice.getAllStats(since, period, granularity))}, { xaxis: { mode: 'time', timeformat: '%Y/%m' } } );
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
