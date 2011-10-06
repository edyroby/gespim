<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Upload File</title>
       
    </head>
    <body>
        <div class="nav">
       
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <%-- 
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            --%>
             <span class="menuButton"><g:link controller="importFile"  action="uploadfile"><g:message code="default.import.label" args="[entityName]" /></g:link></span>
        </div>
       <div class="body">
      Upload Form: <br />
			<g:form action="upload" controller="importFile" method="post" enctype="multipart/form-data">
				<input type="file" name="myFile" />
				<input type="submit" />
			</g:form>
			
      </div>
      
    </body>
</html>
