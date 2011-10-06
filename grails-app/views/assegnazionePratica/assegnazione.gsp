

<%@page import="it.solvingteam.gespim.assegnazione.AreaCompetenza"%>
<%@ page import="it.solvingteam.gespim.pratica.Pratica" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'pratica.label', default: 'Pratica')}" />
         <title>SANA - Sportello Unico Immigrazione - Roma (Pratica)</title>
    </head>
    <body>
       
        <div class="body">
            
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
            <br />
             <div id="assegnazione_pratica">
             <h3>Assegnazione Pratica</h3>
             
            <g:form method="post" >
                <g:hiddenField name="id" value="${praticaInstance?.id}" />
                <g:hiddenField name="version" value="${praticaInstance?.version}" />
                <div class="dialog">
                    <table class="table_assegnazione_pratica">
                        <tbody>
		                    
		                    <tr>
		                        <td valign="top">Numero Pratica</td>
		                        
		                        <td valign="top">${fieldValue(bean: praticaInstance, field: "numeroPratica")}</td>
		                        
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
		                    <tr>
                                <td valign="top">
                                  <label for="areaCompetenzaId">Area Competenza</label>
                                </td>
                                <td valign="top" class="allineo_sx value ${hasErrors(bean: praticaInstance, field: 'tipologiaLegale', 'errors')}">
                                    <g:each var="areaCompInstance" in="${areeMap}">
										<g:checkBox name="areaId" value="${areaCompInstance.key.id }" checked="${areaCompInstance.value.checked}" disabled="${areaCompInstance.value.presaInCarico }"/>
										<span class="allineo_sx_txt">${areaCompInstance.key?.encodeAsHTML()}<br/></span>
									</g:each>
                                </td>
                            </tr>
                        	
		                    
		                    
                        </tbody>
                    </table>
                </div>
                <div class="buttons_multipli">
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
        </div>
    </body>
</html>
