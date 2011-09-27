
<%@ page import="it.solvingteam.gespim.pratica.Pratica" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'pratica.label', default: 'Pratica')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'pratica.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="numeroPratica" title="${message(code: 'pratica.numeroPratica.label', default: 'Numero Pratica')}" />
                        
                            <g:sortableColumn property="codiceIstanza" title="${message(code: 'pratica.codiceIstanza.label', default: 'Codice Istanza')}" />
                        
                            <g:sortableColumn property="codiceQuestura" title="${message(code: 'pratica.codiceQuestura.label', default: 'Codice Questura')}" />
                        
                            <th><g:message code="pratica.statoPratica.label" default="Stato Pratica" /></th>
                        
                            <th><g:message code="pratica.tipologiaLegale.label" default="Tipologia Legale" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${praticaInstanceList}" status="i" var="praticaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${praticaInstance.id}">${fieldValue(bean: praticaInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: praticaInstance, field: "numeroPratica")}</td>
                        
                            <td>${fieldValue(bean: praticaInstance, field: "codiceIstanza")}</td>
                        
                            <td>${fieldValue(bean: praticaInstance, field: "codiceQuestura")}</td>
                        
                            <td>${fieldValue(bean: praticaInstance, field: "statoPratica")}</td>
                        
                            <td>${fieldValue(bean: praticaInstance, field: "tipologiaLegale")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${praticaInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
