<%@ page import="eu.qualify.providers.Webservice" %>
<!DOCTYPE html>
<html>
<head>
  <g:set var="entityName"
         value="${message(code: 'webservice.label', default: 'Webservice')}"/>
  <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div id="list-webservice" class="content scaffold-list" role="main">
  <h2><g:message code="default.list.label" args="[entityName]"/></h2>
  <g:if test="${flash.message}">
    <p class="message bg-info" role="status">${flash.message}</p>
  </g:if>
  <table class="table table-striped">
    <thead>
    <tr>
      <g:sortableColumn property="name"
                        title="${message(code: 'webservice.name.label', default: 'Name')}"/>

      <g:sortableColumn property="threescale_id"
                        title="${message(code: 'webservice.threescale_id.label', default: 'Threescaleid')}"/>
      <th>Users with access</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${webserviceInstanceList}" status="i" var="webserviceInstance">
      <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

        <td><g:link action="edit"
                    id="${webserviceInstance.id}">${fieldValue(bean: webserviceInstance, field: "name")}</g:link></td>

        <td>${fieldValue(bean: webserviceInstance, field: "threescale_id")}</td>

        <td>${webserviceInstance.users*.username.join(", ")}</td>

      </tr>
    </g:each>
    </tbody>
  </table>
  <nav>
    <g:paginate class="pagination" total="${webserviceInstanceCount ?: 0}"/>
  </nav>

  <g:link action="create" class="btn btn-default">Add webservice</g:link>
</div>
</body>
</html>
