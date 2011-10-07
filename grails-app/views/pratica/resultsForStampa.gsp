
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>SANA - Sportello Unico Immigrazione - Roma (Risultati Ricerca)</title>
          <g:javascript src="jQuery/jquery-1.5.1.min.js" />
          <g:javascript src="jQuery/jquery-ui-1.8.12.custom.min.js" />
          <link rel="stylesheet" media="all" type="text/css"  href="${resource(dir:'css/redmond', file:'jquery-ui-1.8.13.custom.css')}" />
        <script type="text/javascript">
	        function jqCheckAll2( id, name ){
	           $("INPUT[@name=" + name + "][type='checkbox']").attr('checked', $('#' + id).is(':checked'));
	        }
	        function check(){
				 var the_value;
				 the_value = $("input:checkbox[type='checkbox']:checked").val();
				 if(the_value == null || the_value==''){
					alert('Selezionare una voce.')
					return false
				}
				return true
			}

        </script>
    </head>
    <body>

    	<g:form  method="post">
        

        <div id="wrapper">
            <br />
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            <br />
            </g:if>
            <g:if test="${flash.error}">
            <div class="errors">${flash.error}</div>
            <br />
            </g:if>
            <div class="list">
                <table class="risultati">
                    <thead>
                        <tr>
                        	<th class="checkbox_table">
                        		<g:checkBox name="selectAll" id="checkAllAuto" value="0" checked="false" onclick="jqCheckAll2( this.id, 'praticaId' )"/>
                        	</th>
                            <g:sortableColumn params="${params }" property="numeroPratica" title="${message(code: 'praticaArticolo27.numeroPratica.label', default: 'Numero Pratica')}" />
                            <th>Richiedente</th>
                            <th>Lavoratore/Straniero</th>
                        <%-- 
                            <g:sortableColumn property="codiceIstanza" title="${message(code: 'praticaArticolo27.codiceIstanza.label', default: 'Codice Istanza')}" />
                        
                            <g:sortableColumn property="codiceQuestura" title="${message(code: 'praticaArticolo27.codiceQuestura.label', default: 'Codice Questura')}" />
                        --%>
                            <th>Stato Pratica</th>
                            <th>Tipologia Legale</th>
                            <th>Tipo Pratica</th>
                            <th>Dettaglio</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${listaPratiche}" status="i" var="praticaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                        	<td>
                        		<g:checkBox name="praticaId" id="praticaId" value="${praticaInstance.id}" checked="false"/>
                        	</td>
                            <td>${fieldValue(bean: praticaInstance, field: "numeroPratica")}</td>
                            <td>${praticaInstance.richiedente}</td>
                            <td>${praticaInstance.beneficiari*.cognome}</td>
                        <%-- 
                            <td>${fieldValue(bean: praticaInstance, field: "codiceIstanza")}</td>
                        
                            <td>${fieldValue(bean: praticaInstance, field: "codiceQuestura")}</td>
                        --%>
                            <td>${fieldValue(bean: praticaInstance, field: "statoPratica")}</td>
                        
                            <td>${fieldValue(bean: praticaInstance, field: "tipologiaLegale")}</td>
                            <td>${fieldValue(bean: praticaInstance, field: "tipoPratica")}</td>
                            
                            <td>
                            	<g:link class="show" action="showDettaglioPratica" id="${praticaInstance.id }"><img src="${resource(dir:'images',file:'lente.png')}" alt="Dettaglio"/></g:link>
                            </td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
                </div>
          
            <div class="paginateButtons">
                <g:paginate total="${listaPraticheTotal}" params="${params }"/>
            </div>

        </div>
        <div class="button_incarico">
                <g:actionSubmit id="mysubmit"  action="stampaMassiva" value="Stampa Massiva" onclick="return check()"/>
        </div>
        </g:form>

     

    </body>
</html>
