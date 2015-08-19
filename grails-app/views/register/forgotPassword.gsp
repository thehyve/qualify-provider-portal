<html>

<head>
	<title><g:message code='spring.security.ui.forgotPassword.title'/></title>
	<meta name='layout' content='login'/>
</head>

<body>

    <g:form action='forgotPassword'  class="form-signin" role="form">
		<g:if test='${emailSent}'>
			<g:message code='spring.security.ui.forgotPassword.sent'/>
		</g:if>
		<g:else>
			<h2 class="form-signin-heading">Forgot password</h2>
			<p><g:message code='spring.security.ui.forgotPassword.description'/></p>
			<g:textField name="username" class="form-control" placeholder="Username" required="required" autofocus="autofocus" />
			<button class="btn btn-lg btn-primary btn-block" type="submit"><g:message code="spring.security.ui.forgotPassword.submit" /></button>
		</g:else>
	  </g:form>
</body>
</html>
