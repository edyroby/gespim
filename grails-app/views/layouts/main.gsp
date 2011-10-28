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
    	  <g:if test="${current == 'cerca'}">
    	    <li><a title="Ricerca Pratiche" class="current"  href="${createLink(uri: '/')}"><g:message code="default.menu.ricercaPratiche.label"/></a></li>
    	  </g:if>
    	   <g:elseif test="${current != 'cerca'}">
     			<li><a title="Ricerca Pratiche" href="${createLink(uri: '/')}"><g:message code="default.menu.ricercaPratiche.label"/></a></li>
			</g:elseif>
		

				<li><a href="#" target="_self" title="Protocollo">Protocollo</a></li>

				<li><a href="${createLink(controller:'task',action:'unassignedTaskList')}" target="_self" title="Assegna">Assegnazioni</a></li>

				
				<li><a href="${createLink(controller:'appuntamenti')}" target="_self" title="Stampa">Convocazioni</a></li>

            <g:if test="${current == 'stampa'}">
            <li><a class="current" href="${createLink(controller:'pratica',action:'searchStampa')}" target="_self" title="Stampa">Stampa</a></li>
            </g:if>
            <g:elseif test="${current != 'stampa'}">
     			<li><a href="${createLink(controller:'pratica',action:'searchStampa')}" target="_self" title="Stampa">Stampa</a></li>
			</g:elseif>
				<li><g:link class="show" controller="logout" action="index" >Logout</g:link></li>
			</ul>
        <g:layoutBody />
        
    </body>
</html>