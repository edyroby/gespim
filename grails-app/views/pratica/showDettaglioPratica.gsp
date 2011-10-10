
<%@ page import="it.solvingteam.gespim.pratica.Pratica" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'pratica.label', default: 'Pratica')}" />
        <title>SANA - Sportello Unico Immigrazione - Roma (Dettaglio Pratica)</title>
         <g:javascript src="jQuery/jquery-1.5.1.min.js" />
	    <g:javascript src="jQuery/jquery-ui-1.8.12.custom.min.js" />
	    <link rel="stylesheet" media="all" type="text/css"  href="${resource(dir:'css/redmond', file:'jquery-ui-1.8.13.custom.css')}" />
        
        <script>
			$(function() {
				$( "#tabs" ).tabs({ajaxOptions:{cache:false},cache:false});
			});
		</script>
		 <script>
        	$(function() { 
			
				var $dialog = $( "#dialog-confirm" ).dialog({
					autoOpen: false,
					resizable: false,
					height:150,
					modal: true,
					buttons: {
						"Confermare Operazione?": function() {
							$( this ).dialog( "close" );
							$("#mio_form").submit();
						},
						Cancel: function() {
							$( this ).dialog( "close" );
						}
					}
				});


				$('#miobutton').click(function() {
					$dialog.dialog('open');
					// prevent the default action, e.g., following a link
				});
				
			});
		</script>
        
    </head>
    <body>
    	<%---------------------- FINESTRA CONFIRM ---------------------%>
    	<div id="dialog-confirm" title="Conferma operazione?">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Confermare Operazione?</p>
		</div>
		<%---------------------- FINE FINESTRA CONFIRM ---------------------%>
         <%-- 
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
          
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
            
        </div>
        --%>
        <div class="body">
            <!-- <h2>Pratica Nr: ${praticaInstance.numeroPratica }</h2> -->
           
            <g:if test="${flash.message}">
	            <br />
	            <div class="message">${flash.message}</div>
	            <br />
            </g:if>
            <g:if test="${flash.error}">
	            <br />
	            <div class="errors">${flash.error}</div>
	            <br />
            </g:if>
<div id="content_tabs">
  
		 <div id="tabs">
			<ul>
				<li><a href="#tabs-datore">Datore</a></li>
				<li><a href="#tabs-lavoratore">Lavoratore</a></li>
				<li><g:link  action="storicoTab" id="${praticaInstance.id}" title="Storico">Storico</g:link></li>
				<li><a href="#tabs-protocollo">Protocollo</a></li>
			</ul>
			<div id="tabs-datore">
		            <table class="table_detail_datore_lavoratore">
		                <tbody>
		                    <tr>
		                        <td valign="top"><g:message code="pratica.numeroPratica.label" default="Numero Pratica" /></td>
		                        
		                        <td valign="top">${fieldValue(bean: praticaInstance, field: "numeroPratica")}</td>
		                        
		                    </tr>
		                
		                    <tr>
		                        <td valign="top"><g:message code="pratica.codiceIstanza.label" default="Codice Istanza" /></td>
		                        
		                        <td valign="top">${fieldValue(bean: praticaInstance, field: "codiceIstanza")}</td>
		                        
		                    </tr>
		                
		                    <tr>
		                        <td valign="top"><g:message code="pratica.codiceQuestura.label" default="Codice Questura" /></td>
		                        
		                        <td valign="top">${fieldValue(bean: praticaInstance, field: "codiceQuestura")}</td>
		                        
		                    </tr>
		                
		                    <tr>
		                        <td valign="top"><g:message code="pratica.tipologiaLegale.label" default="Tipologia Legale" /></td>
		                        
		                        <td valign="top">
		                        	${praticaInstance?.tipologiaLegale?.encodeAsHTML()}
		                        </td>
		                        
		                    </tr>
		                    
		                     <tr>
		                        <td valign="top"><g:message code="pratica.statoPratica.label" default="Stato Pratica" /></td>
		                        
		                        <td valign="top">
		                        	${praticaInstance?.statoPratica?.encodeAsHTML()}
		                        </td>
		                        
		                    </tr>
		                
		                    <tr>
		                        <td valign="top"><g:message code="pratica.richiedente.label" default="Richiedente" /></td>
		                        
		                        <td valign="top">
		                        	${praticaInstance?.richiedente?.encodeAsHTML()}
		                        </td>
		                        
		                    </tr>
		                
		                    <tr>
		                        <td valign="top"><g:message code="pratica.tipoPratica.label" default="Tipo Pratica" /></td>
		                        
		                        <td valign="top">${praticaInstance?.tipoPratica?.encodeAsHTML()}</td>
		                        
		                    </tr>
		                     <tr>
		                        <td valign="top">Decreti Emessi</td>
		                        
		                        <td valign="top">
		                        	 <g:each in="${praticaInstance?.documenti}" status="i" var="doc">
										<g:link action="apriAllegato" controller="pratica" params="[idDoc:doc.id,id:praticaInstance.id]">
											<img src="/gespim/images/pdf.png" border="0" alt="Visualizza" > Apri Articolo 10 Bis
										</g:link><br>
									</g:each>
								</td>
		                        
		                    </tr>
		                    
		                   
		                </tbody>
		            </table>
			</div>
			<div id="tabs-lavoratore">
				 <table class="table_detail_datore_lavoratore">
		                <tbody>
		                    <g:each in="${praticaInstance.beneficiari}" var="b">
			                    <tr>
			                        <td valign="top">Cognome</td>
			                        <td valign="top">
			                        	${b?.cognome?.encodeAsHTML()}
			                        </td>
			                    </tr>
			                    <tr>
			                        <td valign="top">Nome</td>
			                        <td valign="top">
			                        	${b?.nome?.encodeAsHTML()}
			                        </td>
			                    </tr>
			                    <tr>
			                        <td valign="top">Data di Nascita</td>
			                        <td valign="top">
			                        	<g:formatDate format="dd/MM/yyyy" date="${b?.dataNascita}"/>
			                        </td>
			                    </tr>
			                    <tr>
			                        <td valign="top">Cittadinanza</td>
			                        <td valign="top">
			                        	${b?.cittadinanza}
			                        </td>
			                    </tr>
		                    </g:each>
		                </tbody>
		            </table>
			</div>
			<div id="tabs-protocollo">
				<p>Mauris eleifend est et turpis. Duis id erat. Suspendisse potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce sodales. Quisque eu urna vel enim commodo pellentesque. Praesent eu risus hendrerit ligula tempus pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a, lacus.</p>
				<p>Duis cursus. Maecenas ligula eros, blandit nec, pharetra at, semper at, magna. Nullam ac lacus. Nulla facilisi. Praesent viverra justo vitae neque. Praesent blandit adipiscing velit. Suspendisse potenti. Donec mattis, pede vel pharetra blandit, magna ligula faucibus eros, id euismod lacus dolor eget odio. Nam scelerisque. Donec non libero sed nulla mattis commodo. Ut sagittis. Donec nisi lectus, feugiat porttitor, tempor ac, tempor vitae, pede. Aenean vehicula velit eu tellus interdum rutrum. Maecenas commodo. Pellentesque nec elit. Fusce in lacus. Vivamus a libero vitae lectus hendrerit hendrerit.</p>
			</div>
		</div>



</div>
           <g:if test="${authorized}"> 
	            <div class="buttons_multipli">
	                <g:form name="mio_form">
	                    <g:hiddenField name="id" value="${praticaInstance?.id}" />
	                    <g:actionSubmit id="mysubmit" action="edit" value="Modifica" />
	                    <g:actionSubmit id="mysubmit" action="assegnazione" value="Assegnazione" />
	                    <input type="button" id="miobutton"  value="Presa in carico" />
	                    <input id="actionID" type="hidden" name="_action_presaInCarico" value="_action_presaInCarico" >
	                    <g:link id="link" action="presaInCarico" value="Convocazione" >adsfadfa</g:link>
	                    <g:actionSubmit id="mysubmit" action="xxx" value="Emetti Atto" />
	                    <g:actionSubmit id="mysubmit" action="xxx" value="Evidenza" />
	                    <g:actionSubmit id="mysubmit" action="xxx" value="Stampa" />
	                </g:form>
	            </div>
            </g:if>
        </div>
    </body>
</html>
