
<%@ page import="it.solvingteam.gespim.workflow.IterRigettoMancataIntegrazione" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'iterRigettoMancataIntegrazione.label', default: 'IterRigettoMancataIntegrazione')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
	    <span class="menuButton"><g:link class="list" controller="task" action="myTaskList"><g:message code="myTasks.label" default="My Tasks ({0})" args="[myTasksCount]" /></g:link></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="start"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="iterRigettoMancataIntegrazione.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: iterRigettoMancataIntegrazioneInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="iterRigettoMancataIntegrazione.pratica.label" default="Pratica" /></td>
                            
                            <td valign="top" class="value"><g:link controller="pratica" action="show" id="${iterRigettoMancataIntegrazioneInstance?.pratica?.id}">${iterRigettoMancataIntegrazioneInstance?.pratica?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="iterRigettoMancataIntegrazione.note.label" default="Note" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: iterRigettoMancataIntegrazioneInstance, field: "note")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="iterRigettoMancataIntegrazione.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${iterRigettoMancataIntegrazioneInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="iterRigettoMancataIntegrazione.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${iterRigettoMancataIntegrazioneInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="iterRigettoMancataIntegrazione.username.label" default="Username" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: iterRigettoMancataIntegrazioneInstance, field: "username")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <g:if test="${!params.complete && params.taskId}">
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${iterRigettoMancataIntegrazioneInstance?.id}" />
                    <g:hiddenField name="taskId" value="${params.taskId}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
            </g:if>
        </div>
    </body>
</html>