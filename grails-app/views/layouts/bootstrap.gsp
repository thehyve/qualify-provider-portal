<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title><g:layoutTitle default="Qualify provider portal"/></title>
  <link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}"
        type="image/x-icon">
  <link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
  <link rel="apple-touch-icon" sizes="114x114"
        href="${assetPath(src: 'apple-touch-icon-retina.png')}">

  <asset:stylesheet src="bootstrap.min.css"/>
  <asset:stylesheet src="application.css"/>

  <g:layoutHead/>
</head>

<body>
<div class="container">

  <!-- Static navbar -->
  <div class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed"
                data-toggle="collapse" data-target=".navbar-collapse">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">Qualify Provider Portal</a>
      </div>

      <div class="navbar-collapse collapse">
        <g:render template="/layouts/menu"/>
      </div><!--/.nav-collapse -->
    </div><!--/.container-fluid -->
  </div>

  <!-- Main component for a primary marketing message or call to action -->
  <div class="container">
    <g:layoutBody/>
  </div>

</div> <!-- /container -->

<asset:javascript src="application.js"/>

<asset:deferredScripts/>
</body>
</html>
