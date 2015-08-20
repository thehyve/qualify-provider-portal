				<ul class="nav navbar-nav">
				  <li><g:link controller="home">Home</g:link></li>
				  <li><g:link controller="stats">Statistics</g:link></li>
				  <li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">Admin<span class="caret"></span></a>
					<ul class="dropdown-menu" role="menu">
					  <li><g:link controller="webservice">Webservice management</g:link></li>
					  <li><g:link controller="webservice" action="create">Add webservice</g:link></li>
					</ul>
				  </li>
				  <li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">Security<span class="caret"></span></a>
					<ul class="dropdown-menu" role="menu">
					  <li><g:link controller="user" action="search">User management</g:link></li>
					  <li><g:link controller="user" action="create">Create user</g:link></li>
					  <li class="divider"></li>
					  <li><g:link controller="role" action="search">Role management</g:link></li>
					  <li><g:link controller="role" action="create">Create role</g:link></li>
					  <li class="divider"></li>
					  <li><g:link controller="registrationCode" action='search'>Registration codes</g:link></li>
					</ul>
				  </li>
				</ul>


				<g:form controller="logout" action="index">
					<ul class="nav navbar-nav navbar-right">
						<sec:ifLoggedIn>
						  <li><p class="navbar-text"><sec:loggedInUserInfo field="username"/></p></li>
						  <li>
							<a href="#" onClick="$(this).parents('form').submit();">Logout</a>
						  </li>
						</sec:ifLoggedIn>
						<sec:ifNotLoggedIn>
						  <li><g:link controller="login">Login</g:link></li>
						</sec:ifNotLoggedIn>
					</ul>
				</g:form>
