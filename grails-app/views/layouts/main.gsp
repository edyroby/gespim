<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'style.css')}" />
         <link rel="stylesheet" href="${resource(dir:'css',file:'menu_style.css')}" />
        <!--  <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />-->
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
    <body>
        
        <div id="header"><img src="${resource(dir:'images',file:'emblema_header.png')}" alt="logo Sana"/></div>
    	 <ul id="menu">
		<li><a href="#" target="_self" title="Ricerca Pratiche" class="current">Ricerca Pratiche</a></li>

				<li><a href="#" target="_self" title="Protocollo">Protocollo</a></li>

				<li><a href="#" target="_self" title="Assegna">Assegnazioni</a></li>

				<li><a href="${createLink(controller:'pratica',action:'searchStampa')}" target="_self" title="Stampa">Stampa</a></li>
				 <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>

				<li><g:link class="show" controller="logout" action="index" >Logout</g:link></li>
			</ul>
        <g:layoutBody />
        
    </body>
</html>