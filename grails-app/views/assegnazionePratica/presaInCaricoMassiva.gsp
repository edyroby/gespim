
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Presa in carico massiva</title>
    </head>
    <body>
    	<g:form  method="post">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
        </div>
        <div class="body">
            <h1>Presa in carico massiva</h1>
            <g:if test="${flash.error}">
            <div class="errors">${flash.error}</div>
            </g:if>
            <g:elseif test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:elseif>
            <g:if test="${assegnazionePraticaInstanceList}">
	            <div class="message">
					Verranno prese in carico le seguenti pratiche, assegnate all'ufficio dell'utenza attualmente in uso.
				</div>
			</g:if>
			<g:else>
				<div class="message">
					Non sono state trovate pratiche da prendere in carico.
				</div>
			</g:else>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <th>Numero Pratica</th>
                            <th>Data Assegnazione</th>
                        <%-- 
                            <g:sortableColumn property="codiceIstanza" title="${message(code: 'praticaArticolo27.codiceIstanza.label', default: 'Codice Istanza')}" />
                        
                            <g:sortableColumn property="codiceQuestura" title="${message(code: 'praticaArticolo27.codiceQuestura.label', default: 'Codice Questura')}" />
                        --%>
                            <th>Stato Pratica</th>
                        
                            <th>Tipo Pratica</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${assegnazionePraticaInstanceList}" status="i" var="assegnazionePraticaInstance">
						<g:hiddenField name="_assegnaz_${i}" value="${assegnazionePraticaInstance.id}" />
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${assegnazionePraticaInstance.praticaAssegnata?.numeroPratica}</td>
                            <td>
                            	<g:formatDate format="dd/MM/yyyy" date="${assegnazionePraticaInstance.dataAssegnazione}"/>
                            </td>
                        <%-- 
                            <td>${fieldValue(bean: assegnazionePraticaInstance, field: "codiceIstanza")}</td>
                        
                            <td>${fieldValue(bean: assegnazionePraticaInstance, field: "codiceQuestura")}</td>
                        --%>
                            <td>${assegnazionePraticaInstance.praticaAssegnata?.statoPratica}</td>
                        
                            <td>${assegnazionePraticaInstance.praticaAssegnata?.tipoPratica}</td>
                            
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="buttons">
                <g:actionSubmit id="mysubmit"  action="confermaPresaInCaricoMassiva" value="Conferma" />
        </div>
        </g:form>
    </body>
</html>
