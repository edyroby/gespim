
<%@ page import="it.solvingteam.gespim.pratica.Pratica" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
         <title>SANA - Sportello Unico Immigrazione - Roma</title>
        <g:set var="entityName" value="${message(code: 'pratica.label', default: 'Pratica')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <br />
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <br />
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pratica.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: praticaInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pratica.numeroPratica.label" default="Numero Pratica" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: praticaInstance, field: "numeroPratica")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pratica.codiceIstanza.label" default="Codice Istanza" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: praticaInstance, field: "codiceIstanza")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pratica.codiceQuestura.label" default="Codice Questura" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: praticaInstance, field: "codiceQuestura")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pratica.statoPratica.label" default="Stato Pratica" /></td>
                            
                            <td valign="top" class="value"><g:link controller="statoPratica" action="show" id="${praticaInstance?.statoPratica?.id}">${praticaInstance?.statoPratica?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pratica.tipologiaLegale.label" default="Tipologia Legale" /></td>
                            
                            <td valign="top" class="value"><g:link controller="tipologiaLegale" action="show" id="${praticaInstance?.tipologiaLegale?.id}">${praticaInstance?.tipologiaLegale?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pratica.richiedente.label" default="Richiedente" /></td>
                            
                            <td valign="top" class="value"><g:link controller="richiedente" action="show" id="${praticaInstance?.richiedente?.id}">${praticaInstance?.richiedente?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pratica.beneficiario.label" default="Beneficiario" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${praticaInstance.beneficiario}" var="b">
                                    <li><g:link controller="beneficiario" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pratica.tipoPratica.label" default="Tipo Pratica" /></td>
                            
                            <td valign="top" class="value"><g:link controller="tipoPratica" action="show" id="${praticaInstance?.tipoPratica?.id}">${praticaInstance?.tipoPratica?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${praticaInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
