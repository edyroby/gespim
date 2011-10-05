

<%@page import="it.solvingteam.gespim.assegnazione.AreaCompetenza"%>
<%@ page import="it.solvingteam.gespim.pratica.Pratica" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'pratica.label', default: 'Pratica')}" />
        <title>Assegnazione Pratica</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
        </div>
        <div class="body">
            <h1>Assegnazione Pratica</h1>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${flash.error}">
            	<div class="errors">${flash.error}</div>
            </g:if>
            <g:hasErrors bean="${praticaInstance}">
            <div class="errors">
                <g:renderErrors bean="${praticaInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${praticaInstance?.id}" />
                <g:hiddenField name="version" value="${praticaInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
		                    <tr class="prop">
	                        	<td>&nbsp</td>
			                </tr>
		                    <tr class="prop">
		                        <td valign="top" class="name">Numero Pratica</td>
		                        
		                        <td valign="top" class="value">${fieldValue(bean: praticaInstance, field: "numeroPratica")}</td>
		                        
		                    </tr>
		                    <tr class="prop">
		                        <td>&nbsp</td>
		                    </tr>
                        <%-- 
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="areaCompetenzaId">Area Competenza</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaInstance, field: 'tipologiaLegale', 'errors')}">
                                    <g:select name="areaCompetenzaId" from="${it.solvingteam.gespim.assegnazione.AreaCompetenza.list()}" optionKey="id"  noSelection="['null': '']" value="${assegnazionePraticaInstance?.areaCompetenza?.id }"/>
                                </td>
                            </tr>
                        	<tr class="prop">
		                        <td>&nbsp</td>
		                    </tr>
		                    --%>
		                    <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="areaCompetenzaId">Area Competenza</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaInstance, field: 'tipologiaLegale', 'errors')}">
                                    <g:each var="areaCompInstance" in="${areeMap}">
										<g:checkBox name="areaId" value="${areaCompInstance.key.id }" checked="${areaCompInstance.value.checked}" disabled="${areaCompInstance.value.presaInCarico }"/>
										${areaCompInstance.key?.encodeAsHTML()}<br/>
									</g:each>
                                </td>
                            </tr>
                        	<tr class="prop">
		                        <td>&nbsp</td>
		                    </tr>
		                    
		                    
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                	<g:actionSubmit id="mysubmit"  action="confermaAssegnazione" value="Conferma" />
                	<g:actionSubmit id="mysubmit"  action="backToDettaglioPratica" value="Indietro" />
                    <%-- 
                    <span class="button">
                    	<g:actionSubmit class="save" action="update" value="Modifica" />
                    </span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    --%>
                </div>
            </g:form>
        </div>
    </body>
</html>
