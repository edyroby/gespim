
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Risultati Ricerca</title>
          <g:javascript src="jQuery/jquery-1.5.1.min.js" />
          <g:javascript src="jQuery/jquery-ui-1.8.12.custom.min.js" />
          <link rel="stylesheet" media="all" type="text/css"  href="${resource(dir:'css/redmond', file:'jquery-ui-1.8.13.custom.css')}" />
        <script>
        	$(function() { 
			
				$( "#dialog-confirm" ).dialog({
					autoOpen: false,
					resizable: false,
					height:140,
					modal: true,
					buttons: {
						"Confermare Operazione?": function() {
							$( this ).dialog( "close" );
							$("#mioform").submit();
						},
						Cancel: function() {
							$( this ).dialog( "close" );
						}
					}
				});


				$('#miobutton').click(function() {
					$dialog.dialog('open');
					// prevent the default action, e.g., following a link
					return result;
				});
				
			});
		</script>
    </head>
    <body>
    <form action="index" id="mioform">
    	<%---------------------- FINESTRA CONFIRM ---------------------%>
    	<div id="dialog-confirm" title="Conferma operazione?">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Confermare Operazione?</p>
		</div>
		<%---------------------- FINE FINESTRA CONFIRM ---------------------%>
		
        <div class="buttons">
        <input type="button" value="andiamo al nigth" id="miobutton">
                <g:actionSubmit id="mysubmit"  action="" value="Prendi in Carico" />
        </div>
        </form>
    </body>
</html>
