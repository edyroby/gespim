

<%@ page import="it.solvingteam.gespim.workflow.IterAssegnaziPresaInCaricoPratica" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
	    <span class="menuButton"><g:link class="list" controller="task" action="myTaskList"><g:message code="myTasks.label" default="My Tasks ({0})" args="[myTasksCount]" /></g:link></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${iterAssegnaziPresaInCaricoPraticaInstance}">
            <div class="errors">
                <g:renderErrors bean="${iterAssegnaziPresaInCaricoPraticaInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="pratica"><g:message code="iterAssegnaziPresaInCaricoPratica.pratica.label" default="Pratica" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: iterAssegnaziPresaInCaricoPraticaInstance, field: 'pratica', 'errors')}">
                                    <g:select name="pratica.id" from="${it.solvingteam.gespim.pratica.Pratica.list()}" optionKey="id" value="${iterAssegnaziPresaInCaricoPraticaInstance?.pratica?.id}" noSelection="['null': '']" />
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
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><g:submitButton name="complete" class="save" value="${message(code: 'default.button.complete.label', default: 'Complete')}" /></span>
                </div>
                <g:hiddenField name="taskId" value="${params.taskId}" />
            </g:form>
        </div>
    </body>
</html>
