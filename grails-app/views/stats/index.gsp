<asset:javascript src="statistics.js"/>
<h1>Statistics</h1>

<div>
  <form action="index" class="form-inline" id="stats-form">
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
  <ul class="stats">
    <g:each in="${webservices}" var="webservice">
      <li>
        <h2>${webservice.name}</h2>

        <div id="statistics-${webservice.id}" class="statistics" data-webservice-id="${webservice.id}" data-url="${g.createLink(controller: 'stats', action: 'loadStats', params: [ service_id: webservice.threescale_id ] )}"></div>
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
