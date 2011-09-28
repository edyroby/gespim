
<%@ page import="it.solvingteam.gespim.pratica.Pratica" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'pratica.label', default: 'Pratica')}" />
        <title>Dettaglio Pratica</title>
        
         <g:javascript src="jQuery/jquery-1.5.1.min.js" />
	    <g:javascript src="jQuery/jquery-ui-1.8.12.custom.min.js" />
	    <link rel="stylesheet" media="all" type="text/css"  href="${resource(dir:'css/redmond', file:'jquery-ui-1.8.13.custom.css')}" />
        
        <script>
			$(function() {
				$( "#tabs" ).tabs();
			});
		</script>
        
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <%-- 
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
            --%>
        </div>
        <div class="body">
            <h1>Dettaglio Pratica</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
            
 <div id="tabs">
	<ul>
		<li><a href="#tabs-datore">Datore</a></li>
		<li><a href="#tabs-lavoratore">Lavoratore</a></li>
		<li><a href="#tabs-storico">Storico Pratica</a></li>
		<li><a href="#tabs-protocollo">Protocollo</a></li>
	</ul>
	<div id="tabs-datore">
            <table>
                <tbody>
	                <tr class="prop">
	                        <td>&nbsp</td>
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
                        <td valign="top" class="name"><g:message code="pratica.tipologiaLegale.label" default="Tipologia Legale" /></td>
                        
                        <td valign="top" class="value">
                        	${praticaInstance?.tipologiaLegale?.encodeAsHTML()}
                        </td>
                        
                    </tr>
                    
                     <tr class="prop">
                        <td valign="top" class="name"><g:message code="pratica.statoPratica.label" default="Stato Pratica" /></td>
                        
                        <td valign="top" class="value">
                        	${praticaInstance?.statoPratica?.encodeAsHTML()}
                        </td>
                        
                    </tr>
                
                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="pratica.richiedente.label" default="Richiedente" /></td>
                        
                        <td valign="top" class="value">
                        	${praticaInstance?.richiedente?.encodeAsHTML()}
                        </td>
                        
                    </tr>
                
                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="pratica.tipoPratica.label" default="Tipo Pratica" /></td>
                        
                        <td valign="top" class="value">${praticaInstance?.tipoPratica?.encodeAsHTML()}</td>
                        
                    </tr>
                    <tr class="prop">
                        <td>&nbsp</td>
                    </tr>
                
                </tbody>
            </table>
	</div>
	<div id="tabs-lavoratore">
		 <table>
                <tbody>
	               <tr class="prop">
                        <td>&nbsp</td>
                    </tr>
                    <g:each in="${praticaInstance.beneficiari}" var="b">
	                    <tr class="prop">
	                        <td valign="top" class="name">Cognome</td>
	                        <td valign="top" class="value">
	                        	${b?.cognome?.encodeAsHTML()}
	                        </td>
	                    </tr>
	                    <tr class="prop">
	                        <td valign="top" class="name">Nome</td>
	                        <td valign="top" class="value">
	                        	${b?.nome?.encodeAsHTML()}
	                        </td>
	                    </tr>
	                    <tr class="prop">
	                        <td valign="top" class="name">Data di Nascita</td>
	                        <td valign="top" class="value">
	                        	<g:formatDate format="dd/MM/yyyy" date="${b?.dataNascita}"/>
	                        </td>
	                    </tr>
	                    <tr class="prop">
	                        <td valign="top" class="name">Cittadinanza</td>
	                        <td valign="top" class="value">
	                        	${b?.cittadinanza}
	                        </td>
	                    </tr>
                    </g:each>
                    <tr class="prop">
                        <td>&nbsp</td>
                    </tr>
                </tbody>
            </table>
	</div>
	<div id="tabs-storico">
		 <table>
                <tbody>
	               <tr class="prop">
                        <td>&nbsp</td>
                    </tr>
                   
                    <tr class="prop">
                        <td>&nbsp</td>
                    </tr>
                </tbody>
            </table>	
	</div>
	<div id="tabs-protocollo">
		<p>Mauris eleifend est et turpis. Duis id erat. Suspendisse potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce sodales. Quisque eu urna vel enim commodo pellentesque. Praesent eu risus hendrerit ligula tempus pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a, lacus.</p>
		<p>Duis cursus. Maecenas ligula eros, blandit nec, pharetra at, semper at, magna. Nullam ac lacus. Nulla facilisi. Praesent viverra justo vitae neque. Praesent blandit adipiscing velit. Suspendisse potenti. Donec mattis, pede vel pharetra blandit, magna ligula faucibus eros, id euismod lacus dolor eget odio. Nam scelerisque. Donec non libero sed nulla mattis commodo. Ut sagittis. Donec nisi lectus, feugiat porttitor, tempor ac, tempor vitae, pede. Aenean vehicula velit eu tellus interdum rutrum. Maecenas commodo. Pellentesque nec elit. Fusce in lacus. Vivamus a libero vitae lectus hendrerit hendrerit.</p>
	</div>
</div>
            
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${praticaInstance?.id}" />
                    <g:actionSubmit id="mysubmit" action="edit" value="Modifica" />
                    <g:actionSubmit id="mysubmit" action="assegnazione" value="Assegnazione" />
                    <g:actionSubmit id="mysubmit" action="xxx" value="Convocazione" />
                    <g:actionSubmit id="mysubmit" action="xxx" value="Emetti Atto" />
                    <g:actionSubmit id="mysubmit" action="xxx" value="Evidenza" />
                    <g:actionSubmit id="mysubmit" action="xxx" value="Stampa" />
                    <%-- 
                    <span class="button">
                    	<g:actionSubmit class="edit" action="edit" value="Modifica" />
                    </span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    --%>
                </g:form>
            </div>
        </div>
    </body>
</html>
