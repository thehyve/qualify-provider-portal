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


<div class="modal fade" id="modal-application-info">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Modal title</h4>
      </div>
      <div class="modal-body">
        <p>One fine body&hellip;</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->