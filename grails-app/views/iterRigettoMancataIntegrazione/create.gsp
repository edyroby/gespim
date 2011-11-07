

<%@ page import="it.solvingteam.gespim.workflow.IterRigettoMancataIntegrazione" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'iterRigettoMancataIntegrazione.label', default: 'IterRigettoMancataIntegrazione')}" />
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
            <g:hasErrors bean="${iterRigettoMancataIntegrazioneInstance}">
            <div class="errors">
                <g:renderErrors bean="${iterRigettoMancataIntegrazioneInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="pratica"><g:message code="iterRigettoMancataIntegrazione.pratica.label" default="Pratica" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: iterRigettoMancataIntegrazioneInstance, field: 'pratica', 'errors')}">
                                    <g:select name="pratica.id" from="${it.solvingteam.gespim.pratica.Pratica.list()}" optionKey="id" value="${iterRigettoMancataIntegrazioneInstance?.pratica?.id}" noSelection="['null': '']" />
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
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="username"><g:message code="iterRigettoMancataIntegrazione.username.label" default="Username" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: iterRigettoMancataIntegrazioneInstance, field: 'username', 'errors')}">
                                    <g:textField name="username" value="${iterRigettoMancataIntegrazioneInstance?.username}" />
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
