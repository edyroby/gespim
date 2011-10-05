
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
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${flash.error}">
            <div class="errors">${flash.error}</div>
            </g:if>
            <div class="message">
				Verranno prese in carico le seguenti pratiche, assegnate all'ufficio dell'utenza attualmente in uso.
			</div>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <th>Numero Pratica</th>
                            <th>Richiedente</th>
                            <th>Lavoratore/Straniero</th>
                        <%-- 
                            <g:sortableColumn property="codiceIstanza" title="${message(code: 'praticaArticolo27.codiceIstanza.label', default: 'Codice Istanza')}" />
                        
                            <g:sortableColumn property="codiceQuestura" title="${message(code: 'praticaArticolo27.codiceQuestura.label', default: 'Codice Questura')}" />
                        --%>
                            <th>Stato Pratica</th>
                        
                            <th>Tipologia Legale</th>
                            <th>Tipo Pratica</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${assegnazionePraticaInstanceList}" status="i" var="assegnazionePraticaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td>${fieldValue(bean: assegnazionePraticaInstance.praticaAssegnata, field: "numeroPratica")}</td>
                            <td>${assegnazionePraticaInstance.praticaAssegnata.richiedente}</td>
                            <td>${assegnazionePraticaInstance.praticaAssegnata.beneficiari*.cognome}</td>
                        <%-- 
                            <td>${fieldValue(bean: assegnazionePraticaInstance, field: "codiceIstanza")}</td>
                        
                            <td>${fieldValue(bean: assegnazionePraticaInstance, field: "codiceQuestura")}</td>
                        --%>
                            <td>${fieldValue(bean: assegnazionePraticaInstance.praticaAssegnata, field: "statoPratica")}</td>
                        
                            <td>${fieldValue(bean: assegnazionePraticaInstance.praticaAssegnata, field: "tipologiaLegale")}</td>
                            <td>${fieldValue(bean: assegnazionePraticaInstance.praticaAssegnata, field: "tipoPratica")}</td>
                            
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
