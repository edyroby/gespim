
<%@ page import="it.solvingteam.gespim.workflow.IterAssegnaziPresaInCaricoPratica" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
        <div id="modifica_pratica">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
                <table class="table_modifica_pratica">
                    <tbody>
                    
                    
                        <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pratica"><g:message code="iterAssegnaziPresaInCaricoPratica.pratica.label" default="Pratica numero" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: iterAssegnaziPresaInCaricoPraticaInstance, field: 'pratica', 'errors')}">
                                    ${iterAssegnaziPresaInCaricoPraticaInstance?.pratica?.numeroPratica}
                                </td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="iterAssegnaziPresaInCaricoPratica.note.label" default="Note" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: iterAssegnaziPresaInCaricoPraticaInstance, field: "note")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="iterAssegnaziPresaInCaricoPratica.approvalStatus.label" default="Approval Status" /></td>
                            
                            <td valign="top" class="value">${iterAssegnaziPresaInCaricoPraticaInstance?.approvalStatus?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="iterAssegnaziPresaInCaricoPratica.resendRequest.label" default="Resend Request" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${iterAssegnaziPresaInCaricoPraticaInstance?.resendRequest}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="iterAssegnaziPresaInCaricoPratica.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${iterAssegnaziPresaInCaricoPraticaInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="iterAssegnaziPresaInCaricoPratica.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${iterAssegnaziPresaInCaricoPraticaInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr>
		                        <td valign="top">Allegato</td>
		                        
		                        <td valign="top">
		                        	 <g:each in="${iterAssegnaziPresaInCaricoPraticaInstance?.pratica?.documenti}" status="i" var="doc">
										<g:link action="apriAllegato" controller="pratica" params="[idDoc:doc.id,id:iterAssegnaziPresaInCaricoPraticaInstance.pratica.id]">
											<img src="/gespim/images/pdf.png" border="0" alt="Visualizza" > Apri Allegato
										</g:link><br>
									</g:each>
								</td>
		                        
		                    </tr>
                    
                    </tbody>
                </table>
            <g:if test="${!params.complete && params.taskId}">
            <div class="buttons_multipli">
                <g:form>
                    <g:hiddenField name="id" value="${iterAssegnaziPresaInCaricoPraticaInstance?.id}" />
                    <g:hiddenField name="taskId" value="${params.taskId}" />
                    <span class="button"><g:submitButton name="save" class="save" value="${message(code: 'default.button.save.label', default: 'Save')}" /></span>
                    <span class="button"><g:actionSubmit action="performEsameFascicolo" class="save" value="${message(code: 'default.button.complete.label', default: 'Complete')}" /></span>
                    <span class="button"><g:actionSubmit action="riassegna" class="save" value="${message(code: 'default.button.complete.label', default: 'Riassegna')}" /></span>
                </g:form>
            </div>
            </g:if>
        </div>
        </div>
    </body>
</html>
