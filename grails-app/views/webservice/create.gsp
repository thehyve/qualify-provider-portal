<!DOCTYPE html>
<html>
<head>
  <g:set var="entityName"
         value="${message(code: 'webservice.label', default: 'Webservice')}"/>
  <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<div id="create-webservice" class="content scaffold-create" role="main">
  <h2><g:message code="default.create.label" args="[entityName]"/></h2>
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

  <g:form url="[resource: webserviceInstance, action: 'save']"
          class="form-horizontal" role="form">
    <g:render template="form"/>

    <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
        <g:submitButton name="create" class="save" class="btn btn-default"
                        value="${message(code: 'default.button.create.label', default: 'Create')}"/>
      </div>
    </div>
  </g:form>
</div>
</body>
</html>
