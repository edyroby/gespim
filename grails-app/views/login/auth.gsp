<!DOCTYPE html>
<html>
<head>
 		
       
		<title>SANA - Sportello Unico Immigrazione - Roma</title>
		   <link rel="stylesheet" href="${resource(dir:'css',file:'style.css')}" />
		   <g:javascript library="application" />
	
</head>

<body>
<div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
        </div>
        <div id="header"><img src="${resource(dir:'images',file:'emblema_header.png')}" alt="logo Sana"/></div>

<div id="accordian" >
<!--Start of each accordion item-->
<div id="test-header" class="accordion_headings" >Benvenuto nel software di gestione documentale</div>  
  <div id="test1-content">
      	<div class="accordion_child">
	    	<ul>
				<li>Software per la gestione del flusso documentale dello Sportello Unico per l&#39;Immigrazione di Roma</li>									
			</ul>	
		</div>
    
  </div>
  <div id="test1-header" class="accordion_headings" >Login</div>
  
	<div id='test-content'>
		<div class='accordion_child_form'>
			
	
			<g:if test='${flash.message}'>
				<div class='login_message'>${flash.message}</div>
			</g:if>
	
			<form action='${postUrl}' method='POST' id='loginForm'  autocomplete='off'>
				<ul>
				<li>
					<label for='username'><g:message code="springSecurity.login.username.label"/>:</label>
					<input type='text' class='text_' tabindex="1" name='j_username' id='username'/>
				    
	            </li>
		       <li>
					<label for='password'><g:message code="springSecurity.login.password.label"/>:</label>
					<input type='password' class='text_' tabindex="2" name='j_password' id='password'/>
				    
				</li>
				<li>
				
				<input type='submit' tabindex="3" id="submit" value='${message(code: "springSecurity.login.button")}'/>
				
				</li>	
				</ul>
				
			</form>
		</div>
	</div>
</div>

<div id="test3-header" class="accordion_headings" >
	Credits
</div> 
	<div id="test3-content">
	  <div class="accordion_child">
		 <p class="style4">
		  <img src="${resource(dir:'images',file:'footer.jpg')}" alt="logo Solving Team" width="150" height="82"/>
		 </p>
	  </div>
	 </div>


<script type='text/javascript'>
	<!--
	(function() {
		document.forms['loginForm'].elements['j_username'].focus();
	})();
	// -->
</script>
</body>
</html>


