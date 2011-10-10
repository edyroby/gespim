
<%@page import="it.solvingteam.gespim.tipologiche.TipoOperazione"%>
<g:javascript src="ajaxify/jquery-1.2.6.min.js" />
<g:javascript src="ajaxify/jquery.ajaxify.min.js" />
<script type="text/javascript">
$(function() {
	$('th.sortable a').ajaxify({target:'#cnt3',
				loading_img:'../../images/orange_loading.gif'
		}
	); 
	$('.paginateButtons a').ajaxify({target:'#cnt3',
				loading_img:'../../images/orange_loading.gif'
		}
	); 
});
</script>
<div id="cnt3">
	<div class="list">
		<table class="tabel_storico">
			<thead>
				<tr>
					<g:sortableColumn params="${params }" property="tipoOperazione.descrizione"	title="Operazione" />
					<g:sortableColumn params="${params }" property="areaOperatore"	title="Assegnata Da" />
					<g:sortableColumn params="${params }" property="areaAssegnataria"	title="Assegnata A" />
					<g:sortableColumn params="${params }" property="dataOperazione"	title="Data" />
					<g:sortableColumn params="${params }" property="presaInCarico"	title="In Carico" />
					<g:sortableColumn params="${params }" property="dataPresaInCarico"	title="Presa in carico" />
					<g:sortableColumn params="${params }" property="utenteOperatore"	title="Utente esecutore" />
					<g:sortableColumn params="${params }" property="esito"	title="Esito" />
	
				</tr>
			</thead>
			<tbody>
				<g:each in="${storicoInstanceList}" status="i" var="storicoInstance">
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	
						<td>
							<g:if test="${storicoInstance.tipoOperazione == it.solvingteam.gespim.tipologiche.TipoOperazione.findByCodice(TipoOperazione.COD_DECRETO_EMESSO)}">
							     <g:link action="apriAllegato" controller="storico" params="[idDoc:storicoInstance.allegato.id,id:storicoInstance.id]">
											<img src="/gespim/images/pdf.png" border="0" alt="Visualizza" >
								</g:link>
							</g:if>
							${fieldValue(bean: storicoInstance.tipoOperazione, field: "descrizione")}
						</td>
						<td>
							${storicoInstance.areaOperatore}
						</td>
						<td>
							${storicoInstance.areaAssegnataria}
						</td>
						<td>
							${fieldValue(bean: storicoInstance, field: "dataOperazione")}
						</td>
	
						<td>
							${storicoInstance?.presaInCarico?'SI':'NO'}
						</td>
						<td>
							${fieldValue(bean: storicoInstance, field: "dataPresaInCarico")}
						</td>
						<td>
							${fieldValue(bean: storicoInstance, field: "utenteOperatore")}
						</td>
						<td>
							${fieldValue(bean: storicoInstance, field: "esito")}
						</td>
	<%-- 
						<td class="w12pc"><g:link class="show"
								action="showDettaglioPratica" id="${storicoInstance.id }">Dettaglio</g:link>
						</td>
	--%>
					</tr>
				</g:each>
			</tbody>
		</table>
	</div>

<div class="paginateButtons_storico">
     <g:paginate total="${storicoInstanceTotal}" params="${params }"/>
</div>
</div>