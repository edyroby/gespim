

<%@ page import="it.solvingteam.gespim.workflow.IterAssegnaziPresaInCaricoPratica" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
        <div id="modifica_pratica">
            <h3>Lavorazione Pratica</h3>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${iterAssegnaziPresaInCaricoPraticaInstance}">
            <div class="errors">
                <g:renderErrors bean="${iterAssegnaziPresaInCaricoPraticaInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
            <g:hiddenField name="id" value="${iterAssegnaziPresaInCaricoPraticaInstance?.id}" />
                <g:hiddenField name="taskId" value="${params.taskId}" />
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
                                <td valign="top">
                                  <label for="tipologiaLegale">Tipologia Legale</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaInstance, field: 'tipologiaLegale', 'errors')}">
                                    <g:select name="tipologiaLegaleId" from="${it.solvingteam.gespim.tipologiche.TipologiaLegale.list()}" optionKey="id"  noSelection="['null': ' -- Nessuna voce selezionata -- ']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="note"><g:message code="iterAssegnaziPresaInCaricoPratica.note.label" default="Note" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: iterAssegnaziPresaInCaricoPraticaInstance, field: 'note', 'errors')}">
                                    <g:textArea name="note" cols="40" rows="5" value="${iterAssegnaziPresaInCaricoPraticaInstance?.note}" />
                                </td>
                            </tr>
                            
                            
                        <%-- 
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="approvalStatus"><g:message code="iterAssegnaziPresaInCaricoPratica.approvalStatus.label" default="Approval Status" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: iterAssegnaziPresaInCaricoPraticaInstance, field: 'approvalStatus', 'errors')}">
                                    <g:select name="approvalStatus" from="${org.grails.activiti.ApprovalStatus?.values()}" value="${iterAssegnaziPresaInCaricoPraticaInstance?.approvalStatus}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="resendRequest"><g:message code="iterAssegnaziPresaInCaricoPratica.resendRequest.label" default="Resend Request" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: iterAssegnaziPresaInCaricoPraticaInstance, field: 'resendRequest', 'errors')}">
                                    <g:checkBox name="resendRequest" value="${iterAssegnaziPresaInCaricoPraticaInstance?.resendRequest}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="area"><g:message code="iterAssegnaziPresaInCaricoPratica.area.label" default="Area" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: iterAssegnaziPresaInCaricoPraticaInstance, field: 'area', 'errors')}">
                                    <g:textField name="area" value="${iterAssegnaziPresaInCaricoPraticaInstance?.area}" />
                                </td>
                            </tr>
                        --%>
                        </tbody>
                    </table>
                <div class="buttons_multipli">
                    <span class="button"><g:actionSubmit class="save" action="sequestro" value="Sequestro" /></span>
                    <span class="button"><g:actionSubmit class="save" action="evidenza" value="Evidenza" /></span>
                    <span class="button"><g:actionSubmit class="save" action="performSceltaTipologia" value="Conferma Scelta Tipologia" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
            </div>
        </div>
    </body>
</html>
