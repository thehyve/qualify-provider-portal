<html>

<head>
  <title><g:message code='spring.security.ui.resetPassword.title'/></title>
  <meta name='layout' content='login'/>
</head>

<body>

<g:form action='resetPassword' name='resetPasswordForm' autocomplete='off'
        class="form-signin" role="form">
  <g:hiddenField name='t' value='${token}'/>

  <g:if test='${emailSent}'>
    <g:message code='spring.security.ui.forgotPassword.sent'/>
  </g:if>
  <g:else>
    <h2 class="form-signin-heading"><g:message
        code="spring.security.ui.resetPassword.header"/></h2>

    <p><g:message code='spring.security.ui.resetPassword.description'/></p>
    <g:passwordField name="password" class="form-control" placeholder="Password"
                     value="${command?.password}" required="required"
                     autofocus="autofocus"/>
    <g:passwordField name="password2" class="form-control"
                     placeholder="Password (again)"
                     value="${command?.password2}" required="required"/>
    <button class="btn btn-lg btn-primary btn-block" type="submit"><g:message
        code="spring.security.ui.resetPassword.submit"/></button>
  </g:else>
</g:form>
</body>
</html>
