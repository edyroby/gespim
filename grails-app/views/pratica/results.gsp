
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Risultati Ricerca</title>
          <g:javascript src="jQuery/jquery-1.5.1.min.js" />
        <script type="text/javascript">
	        function jqCheckAll2( id, name ){
	           $("INPUT[@name=" + name + "][type='checkbox']").attr('checked', $('#' + id).is(':checked'));
	        }
        </script>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <%-- 
            <span class="menuButton"><g:link class="create" action="create">Nuova Pratica</g:link></span>
            --%>
        </div>
        <div class="body">
            <h1>Risultati Ricerca</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                        	<th class="w5pc">
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
                            
                             
                            <th class="azioni">Azioni</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${listaPratiche}" status="i" var="praticaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                        	<td class="w5pc">
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
                            
                            <td class="w12pc">
                            	<g:link class="show" action="showDettaglioPratica" id="${praticaInstance.id }">Dettaglio</g:link>
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
        <div class="buttons">
                <g:actionSubmit id="mysubmit"  action="" value="Prendi in Carico" />
        </div>
    </body>
</html>
