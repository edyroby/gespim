
<%@page import="it.solvingteam.gespim.tipologiche.TipoPratica"%>
<%@page import="it.solvingteam.gespim.tipologiche.TipologiaLegale"%>
<%@page import="it.solvingteam.gespim.pratica.Beneficiario"%>
<%@page import="it.solvingteam.gespim.pratica.Richiedente"%>
<%@page import="it.solvingteam.gespim.tipologiche.StatoPratica"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>SANA - Sportello Unico Immigrazione - Roma (Ricerca Pratica)</title>
         <g:javascript src="jQuery/jquery-1.5.1.min.js" />
	    <g:javascript src="jQuery/jquery-ui-1.8.12.custom.min.js" />
        
        <script type="text/javascript" src="${resource(dir:'js/jQuery',file:'jquery.tokeninput.js')}"></script>
        <link rel="stylesheet" href="${resource(dir:'css/token-input',file:'token-input.css')}"></link>
        
	    <link rel="stylesheet" media="all" type="text/css"  href="${resource(dir:'css/redmond', file:'jquery-ui-1.8.13.custom.css')}" />
	    
	     <script type="text/javascript">
		     $(document).ready(function () {
	             $("#beneficiari").tokenInput("${createLink(controller:'pratica', action:'autocompleteResult')}", {
	                 hintText: "Digitare nome e/o cognome del lavoratore/straniero (almeno 3 caratteri)",
	                 noResultsText: "Nessun risultato",
	                 searchingText: "Ricerca...",
	                 minChars: "3",
	                 method: "POST",
	                 preventDuplicates: true
	             });
		     });
	     </script>
		

<g:jqDatepickerLocale lang="it" />
    </head>
    <body>
       <%--  <div class="nav">
       
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            
            <!--  <span class="menuButton"><g:link controller="importFile"  action="insert"><g:message code="default.import.label" args="[entityName]" /></g:link></span> -->
        </div>
        --%>
        <div class="body">
        <br />
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            	<br />
            </g:if>
            <g:if test="${flash.error}">
            	<div class="errors">${flash.error}</div>
            	<br />
            </g:if>
            <g:hasErrors bean="${praticaFlussiStagionaliInstance}">
            <div class="errors">
                <g:renderErrors bean="${praticaFlussiStagionaliInstance}" as="list" />
                <br />
            </div>
            </g:hasErrors>
            <g:form action="results" controller="pratica">
                <div class="content">
                    <table id="table">
                        <tbody>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="assegnateAdUfficio">Solo Area di Appartenenza</label>
                                </td>
                                <td valign="top" class="value">
                                    <g:checkBox name="assegnateAdUfficio"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="numeroPratica"><g:message code="praticaFlussiStagionali.numeroPratica.label" default="Numero Pratica" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaFlussiStagionaliInstance, field: 'numeroPratica', 'errors')}">
                                    <g:textField name="numeroPratica"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="codiceIstanza"><g:message code="praticaFlussiStagionali.codiceIstanza.label" default="Codice Istanza" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaFlussiStagionaliInstance, field: 'codiceIstanza', 'errors')}">
                                    <g:textField name="codiceIstanza"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="codiceQuestura"><g:message code="praticaFlussiStagionali.codiceQuestura.label" default="Codice Questura" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaFlussiStagionaliInstance, field: 'codiceQuestura', 'errors')}">
                                    <g:textField name="codiceQuestura"  />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="nomeRichiedente">Nome Richiedente</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaFlussiStagionaliInstance, field: 'codiceIstanza', 'errors')}">
                                    <g:textField name="nomeRichiedente"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="cognomeRichiedente">Cognome Richiedente</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaFlussiStagionaliInstance, field: 'codiceQuestura', 'errors')}">
                                    <g:textField name="cognomeRichiedente"  />
                                </td>
                            </tr>
                            
                             <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dataNascitaRichiedente">Data Nascita Richiedente</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaFlussiStagionaliInstance, field: 'codiceQuestura', 'errors')}">
                                    <g:jqDatepicker name="dataNascitaRichiedente"  changeMonth="true" changeYear="true" />
                                </td>
                            </tr>
                            
                           
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="statoPratica"><g:message code="praticaFlussiStagionali.statoPratica.label" default="Stato Pratica" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaFlussiStagionaliInstance, field: 'statoPratica', 'errors')}">
                                    <g:select name="statoPratica" from="${it.solvingteam.gespim.tipologiche.StatoPratica.list()}" optionKey="id" value="${praticaFlussiStagionaliInstance?.statoPratica?.id}" noSelection="['': '- selezionare una voce -']" />
                                </td>
                            </tr>
                        
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tipoPratica">Tipo Pratica</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaFlussiStagionaliInstance, field: 'lavoratore', 'errors')}">
                                    <g:select name="tipoPratica" from="${it.solvingteam.gespim.tipologiche.TipoPratica.list()}" optionKey="id" optionValue="descrizione"  noSelection="['': '- selezionare una voce -']"/>
                                </td>
                            </tr>
                            
                             <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tipologiaLegale">Tipologia Legale</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: praticaFlussiStagionaliInstance, field: 'lavoratore', 'errors')}">
                                    <g:select name="tipologiaLegale" from="${it.solvingteam.gespim.tipologiche.TipologiaLegale.list()}" optionKey="id" optionValue="descrizione"  noSelection="['': '- selezionare una voce -']"/>
                                </td>
                            </tr>
                             <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lavoratoreId">Lavoratore/Straniero</label>
                                </td>
                                <td valign="top" class="name_tokeninput">
                                    <input type="text" id="beneficiari" name="beneficiari" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                  <div class="button"><g:submitButton name="results" value="Cerca" /></div>
                </div>
                
               
            </g:form>
        </div>
    </body>
</html>
