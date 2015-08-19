<%@ page import="eu.qualify.providers.Webservice" %>

  <div class="form-group">
    <label for="name" class="col-sm-2 control-label required">
    	<g:message code="webservice.name.label" default="Name" />
    </label>
    <div class="col-sm-10">
    	<g:textField name="name" class="form-control" required="" value="${webserviceInstance?.name}"/>
    </div>
  </div>
  <div class="form-group">
    <label for="threescale_id" class="col-sm-2 control-label required">
    	<g:message code="webservice.threescale_id.label" default="Threescaleid" />
    </label>
    <div class="col-sm-10">
    	<g:textField name="threescale_id" class="form-control" required="" value="${webserviceInstance?.threescale_id}"/>
    </div>
  </div>
  <div class="form-group">
    <label for="users" class="col-sm-2 control-label"><g:message code="webservice.users.label" default="Users with access" /></label>
    <div class="col-sm-10">
    	<g:select name="associatedUsers" multiple="true" from="${users}" value="${webserviceInstance?.users*.id}" optionValue="username" class="form-control" optionKey="id" />
    </div>
  </div>
