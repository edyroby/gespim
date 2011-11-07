
<%@ page import="it.solvingteam.gespim.workflow.IterRigettoMancataIntegrazione" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'iterRigettoMancataIntegrazione.label', default: 'IterRigettoMancataIntegrazione')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
	    <span class="menuButton"><g:link class="list" controller="task" action="myTaskList"><g:message code="myTasks.label" default="My Tasks ({0})" args="[myTasksCount]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="start"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'iterRigettoMancataIntegrazione.id.label', default: 'Id')}" />
                        
                            <th><g:message code="iterRigettoMancataIntegrazione.pratica.label" default="Pratica" /></th>
                        
                            <g:sortableColumn property="note" title="${message(code: 'iterRigettoMancataIntegrazione.note.label', default: 'Note')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'iterRigettoMancataIntegrazione.dateCreated.label', default: 'Date Created')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'iterRigettoMancataIntegrazione.lastUpdated.label', default: 'Last Updated')}" />
                        
                            <g:sortableColumn property="username" title="${message(code: 'iterRigettoMancataIntegrazione.username.label', default: 'Username')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${iterRigettoMancataIntegrazioneInstanceList}" status="i" var="iterRigettoMancataIntegrazioneInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${iterRigettoMancataIntegrazioneInstance.id}">${fieldValue(bean: iterRigettoMancataIntegrazioneInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: iterRigettoMancataIntegrazioneInstance, field: "pratica")}</td>
                        
                            <td>${fieldValue(bean: iterRigettoMancataIntegrazioneInstance, field: "note")}</td>
                        
                            <td><g:formatDate date="${iterRigettoMancataIntegrazioneInstance.dateCreated}" /></td>
                        
                            <td><g:formatDate date="${iterRigettoMancataIntegrazioneInstance.lastUpdated}" /></td>
                        
                            <td>${fieldValue(bean: iterRigettoMancataIntegrazioneInstance, field: "username")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${iterRigettoMancataIntegrazioneInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
