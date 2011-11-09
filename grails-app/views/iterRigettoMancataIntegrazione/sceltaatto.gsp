

<%@ page import="it.solvingteam.gespim.workflow.IterRigettoMancataIntegrazione" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'iterRigettoMancataIntegrazione.label', default: 'iterRigettoMancataIntegrazione')}" />
        <title>Scelta Atto</title>
    </head>
    <body>
        <div class="body">
        <div id="modifica_pratica">
            <h3>Scelta Atto</h3>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${iterRigettoMancataIntegrazioneInstance}">
            <div class="errors">
                <g:renderErrors bean="${iterRigettoMancataIntegrazioneInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
            <g:hiddenField name="id" value="${iterRigettoMancataIntegrazioneInstance?.id}" />
                <g:hiddenField name="taskId" value="${params.taskId}" />
                    <table class="table_modifica_pratica">
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pratica"><g:message code="iterRigettoMancataIntegrazione.pratica.label" default="Pratica numero" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: iterRigettoMancataIntegrazioneInstance, field: 'pratica', 'errors')}">
                                    ${iterRigettoMancataIntegrazioneInstance?.pratica?.numeroPratica}
                                </td>
                            </tr>
                            
                             <tr class="prop">
                                <td valign="top">
                                  <label for="atto">Scelta Atto</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: iterRigettoMancataIntegrazioneInstance, field: 'atto', 'errors')}">
                                    <g:select name="attoId" from="${it.solvingteam.gespim.documentazione.Atto.findAllByTipologiaLegale(iterRigettoMancataIntegrazioneInstance?.pratica?.tipologiaLegale)}" optionKey="id"  noSelection="['': ' -- Nessuna voce selezionata -- ']" optionValue="descrizione" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="note"><g:message code="iterRigettoMancataIntegrazione.note.label" default="Note" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: iterRigettoMancataIntegrazioneInstance, field: 'note', 'errors')}">
                                    <g:textArea name="note" cols="40" rows="5" value="${iterRigettoMancataIntegrazioneInstance?.note}" />
                                </td>
                            </tr>
                            
                            
                        </tbody>
                    </table>
                <div class="buttons_multipli">
                    <span class="button">
                    	<g:actionSubmit class="save" action="performSceltaAtto" value="Conferma" />
                    </span>
                    <%-- 
                    <span class="button"><g:actionSubmit class="save" action="evidenza" value="Evidenza" /></span>
                    <span class="button"><g:actionSubmit class="save" action="performSceltaTipologia" value="Conferma Scelta Tipologia" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    --%>
                </div>
            </g:form>
            </div>
        </div>
    </body>
</html>
