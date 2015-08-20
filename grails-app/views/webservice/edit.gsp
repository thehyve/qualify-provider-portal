<%@ page import="eu.qualify.providers.Webservice" %>
<!DOCTYPE html>
<html>
<head>
  <g:set var="entityName"
         value="${message(code: 'webservice.label', default: 'Webservice')}"/>
  <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<div id="edit-webservice" class="content scaffold-edit" role="main">
  <h2><g:message code="default.edit.label" args="[entityName]"/></h2>
  <g:if test="${flash.message}">
    <div class="message bg-info" role="status">${flash.message}</div>
  </g:if>
  <g:hasErrors bean="${webserviceInstance}">
    <ul class="errors bg-danger" role="alert">
      <g:eachError bean="${webserviceInstance}" var="error">
        <li<g:if
               test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
            error="${error}"/></li>
      </g:eachError>
    </ul>
  </g:hasErrors>

  <g:form url="[resource: webserviceInstance, action: 'update']"
          class="form-horizontal" role="form" method="PUT">
    <g:hiddenField name="version" value="${webserviceInstance?.version}"/>
    <g:render template="form"/>

    <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
        <g:actionSubmit class="save" action="update" class="btn btn-default"
                        value="${message(code: 'default.button.update.label', default: 'Update')}"/>
      </div>
    </div>
  </g:form>
</div>
</body>
</html>
