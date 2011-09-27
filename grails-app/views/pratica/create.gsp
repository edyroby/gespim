

<%@ page import="it.solvingteam.gespim.pratica.Pratica" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'pratica.label', default: 'Pratica')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${praticaInstance}">
            <div class="errors">
                <g:renderErrors bean="${praticaInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="numeroPratica"><g:message code="pratica.numeroPratica.label" default="Numero Pratica" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaInstance, field: 'numeroPratica', 'errors')}">
                                    <g:textField name="numeroPratica" value="${praticaInstance?.numeroPratica}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="codiceIstanza"><g:message code="pratica.codiceIstanza.label" default="Codice Istanza" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaInstance, field: 'codiceIstanza', 'errors')}">
                                    <g:textField name="codiceIstanza" value="${praticaInstance?.codiceIstanza}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="codiceQuestura"><g:message code="pratica.codiceQuestura.label" default="Codice Questura" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaInstance, field: 'codiceQuestura', 'errors')}">
                                    <g:textField name="codiceQuestura" maxlength="8" value="${praticaInstance?.codiceQuestura}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="statoPratica"><g:message code="pratica.statoPratica.label" default="Stato Pratica" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaInstance, field: 'statoPratica', 'errors')}">
                                    <g:select name="statoPratica.id" from="${it.solvingteam.gespim.tipologiche.StatoPratica.list()}" optionKey="id" value="${praticaInstance?.statoPratica?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tipologiaLegale"><g:message code="pratica.tipologiaLegale.label" default="Tipologia Legale" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaInstance, field: 'tipologiaLegale', 'errors')}">
                                    <g:select name="tipologiaLegale.id" from="${it.solvingteam.gespim.tipologiche.TipologiaLegale.list()}" optionKey="id" value="${praticaInstance?.tipologiaLegale?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="richiedente"><g:message code="pratica.richiedente.label" default="Richiedente" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaInstance, field: 'richiedente', 'errors')}">
                                    <g:select name="richiedente.id" from="${it.solvingteam.gespim.pratica.Richiedente.list()}" optionKey="id" value="${praticaInstance?.richiedente?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tipoPratica"><g:message code="pratica.tipoPratica.label" default="Tipo Pratica" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaInstance, field: 'tipoPratica', 'errors')}">
                                    <g:select name="tipoPratica.id" from="${it.solvingteam.gespim.tipologiche.TipoPratica.list()}" optionKey="id" value="${praticaInstance?.tipoPratica?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
