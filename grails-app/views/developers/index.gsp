<asset:javascript src="developer-applications.js"/>

<h1>Application list</h1>

<g:if test="${webservices}">
  <ul class="applications">
    <g:each in="${webservices}" var="webservice">
      <li>
        <h2>${webservice.name}</h2>

		<div class="applicationList" data-webservice-id="${webservice.threescale_id}" data-url="${g.createLink(controller: 'developers', action: 'loadApplications', params: [ service_id: webservice.threescale_id ] )}">
		</div>
      </li>
    </g:each>
  </ul>
</g:if>
<g:else>
  <p>
    You don't have access to the list of developers for any of the webservices.
    Please contact a system administrator.
  </p>
</g:else>
