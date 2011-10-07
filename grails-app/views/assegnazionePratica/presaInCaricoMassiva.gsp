
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
          <title>SANA - Sportello Unico Immigrazione - Roma (Presa in carico massiva)</title>
    </head>
    <body>
     <div class="body">
     		<br />
           <g:if test="${flash.error}">
            	<div class="errors">${flash.error}</div>
            	  <br />
            </g:if>
            <g:elseif test="${flash.message}">
            	<div class="message">${flash.message}</div>
            	  <br />
            </g:elseif>
          
            
            <div id="presaincarico">
            <g:form  method="post">
            <h3>Presa in carico massiva</h3>
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
           
		                <table class="table_presaincarico">
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
               
        
        <div class="buttons_multipli">
                <g:actionSubmit id="mysubmit"  action="confermaPresaInCaricoMassiva" value="Conferma" />
               <a href="#">Indietro</a>
        </div>
         
        
      </g:form>
      </div>
   </div>
    </body>
</html>
