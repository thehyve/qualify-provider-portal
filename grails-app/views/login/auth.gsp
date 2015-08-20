<html>

<head>
  <title><g:message code='spring.security.ui.login.title'/></title>
  <meta name='layout' content='login'/>
</head>

<body>

<form action='${postUrl}' method='POST' class="form-signin" role="form">
  <h2 class="form-signin-heading">Please sign in</h2>
  <input name="j_username" type="text" class="form-control"
         placeholder="Username" required autofocus>
  <input name="j_password" type="password" class="form-control"
         placeholder="Password" required>

  <div class="checkbox">
    <label>
      <input name="${rememberMeParameter}" type="checkbox"> Remember me
    </label>
  </div>
  <span class="forgot-link">
    <g:link controller='register' action='forgotPassword'><g:message
        code='spring.security.ui.login.forgotPassword'/></g:link>
  </span>
  <button class="btn btn-lg btn-primary btn-block"
          type="submit">Sign in</button>
</form>

</body>
</html>
