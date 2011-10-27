
<%@ page import="it.solvingteam.gespim.workflow.IterAssegnaziPresaInCaricoPratica" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
	    <span class="menuButton"><g:link class="list" controller="task" action="myTaskList"><g:message code="myTasks.label" default="My Tasks ({0})" args="[myTasksCount]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="start"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="list" action="unassignedTaskList" controller="task"><g:message code="unassignedTasks.label" default="Unassigned Tasks ({0})" args="[unassignedTasksCount]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'iterAssegnaziPresaInCaricoPratica.id.label', default: 'Id')}" />
                        
                            <th><g:message code="iterAssegnaziPresaInCaricoPratica.pratica.label" default="Pratica" /></th>
                        
                            <g:sortableColumn property="note" title="${message(code: 'iterAssegnaziPresaInCaricoPratica.note.label', default: 'Note')}" />
                        
                            <g:sortableColumn property="approvalStatus" title="${message(code: 'iterAssegnaziPresaInCaricoPratica.approvalStatus.label', default: 'Approval Status')}" />
                        
                            <g:sortableColumn property="resendRequest" title="${message(code: 'iterAssegnaziPresaInCaricoPratica.resendRequest.label', default: 'Resend Request')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'iterAssegnaziPresaInCaricoPratica.dateCreated.label', default: 'Date Created')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${iterAssegnaziPresaInCaricoPraticaInstanceList}" status="i" var="iterAssegnaziPresaInCaricoPraticaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${iterAssegnaziPresaInCaricoPraticaInstance.id}">${fieldValue(bean: iterAssegnaziPresaInCaricoPraticaInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: iterAssegnaziPresaInCaricoPraticaInstance, field: "pratica")}</td>
                        
                            <td>${fieldValue(bean: iterAssegnaziPresaInCaricoPraticaInstance, field: "note")}</td>
                        
                            <td>${fieldValue(bean: iterAssegnaziPresaInCaricoPraticaInstance, field: "approvalStatus")}</td>
                        
                            <td><g:formatBoolean boolean="${iterAssegnaziPresaInCaricoPraticaInstance.resendRequest}" /></td>
                        
                            <td><g:formatDate date="${iterAssegnaziPresaInCaricoPraticaInstance.dateCreated}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${iterAssegnaziPresaInCaricoPraticaInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
